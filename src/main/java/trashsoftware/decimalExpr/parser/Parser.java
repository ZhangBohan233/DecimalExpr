package trashsoftware.decimalExpr.parser;

import trashsoftware.decimalExpr.DecimalExprBuilder;
import trashsoftware.decimalExpr.expression.*;
import trashsoftware.numbers.Real;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Parser {

    private DecimalExprBuilder builder;
    private final List<Token> tokens;

    public Parser(DecimalExprBuilder builder) {
        this.builder = builder;
        Tokenizer tokenizer = new Tokenizer(builder.getExpression());
        this.tokens = tokenizer.tokenize();
    }

    public BlockStmt parse() {
        AbstractSyntaxTree ast = new AbstractSyntaxTree();
        int parCount = 0;
        Stack<Integer> functionCallParCounts = new Stack<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.isIdentifier()) {
                String identifier = (String) token.getValue();
                if (Operators.isPossibleOperator(identifier)) {
                    processOperator(identifier, ast, i);
                } else if (identifier.equals("(")) {
                    parCount++;
                    ast.addParenthesis();
                } else if (identifier.equals(")")) {
                    if (peekEquals(functionCallParCounts, --parCount)) {
                        functionCallParCounts.pop();
                        ast.buildFunction();
                    } else {
                        ast.buildParenthesis();
                    }
                } else if (identifier.equals(",")) {
                    ast.build();
                } else if (builder.getVariableNames().contains(identifier)) {
                    ast.addVariable(identifier);
                } else if (builder.getMacroNames().contains(identifier)) {
                    ast.addMacro(identifier);
                } else {
                    i += 1;  // skip next front parenthesis
                    processFunction(identifier, ast, i);
                    functionCallParCounts.push(parCount++);
                }
            } else if (token.isNumber()) {
                ast.addNumber((Real) token.getValue());
            } else {
                throw new ParseTimeException("Unexpected token");
            }
        }
        ast.build();
        return ast.getRoot();
    }

    private boolean peekEquals(Stack<Integer> stack, int value) {
        return !stack.isEmpty() && stack.peek() == value;
    }

    private void processOperator(String op, AbstractSyntaxTree ast, int index) {
        UnaryOperator unaryOperator = builder.getUnaryOperatorMap().get(op);
        if (unaryOperator != null) {
            // token at the side of the operator
            int adjTokenIndex = unaryOperator.isLeftAssociative() ?
                    index + 1 : index - 1;
            if (isUnary(adjTokenIndex, unaryOperator.isLeftAssociative())) {
                ast.addUnaryOperator(unaryOperator);
                return;
            }
        }
        BinaryOperator binaryOperator = builder.getBinaryOperatorMap().get(op);
        if (binaryOperator != null) {
            ast.addBinaryOperator(binaryOperator);
            return;
        }
        throw new ParseTimeException("Unsolved operator");
    }

    private boolean isUnary(int adjacentTokenIndex, boolean isLeftAssociative) {
        if (adjacentTokenIndex < 0 || adjacentTokenIndex >= tokens.size()) {
            return true;
        } else {
            Token token = tokens.get(adjacentTokenIndex);
            if (token.isNumber()) return false;
            else if (token.isIdentifier()) {
                String symbol = (String) token.getValue();
                return builder.getBinaryOperatorMap().containsKey(symbol)
                        || symbol.equals(",")
                        || (!isLeftAssociative && symbol.equals("(")
                        || (isLeftAssociative && symbol.equals(")"))
                );
            } else {
                throw new ParseTimeException("Unexpected token");
            }
        }
    }

    private void processFunction(String identifier, AbstractSyntaxTree ast, int index) {
        AbstractFunction function = builder.getFunctions().get(identifier);
        if (function == null) {
            throw new ParseTimeException(String.format("Unsolved function '%s'", identifier));
        } else {
            Token parToken = tokens.get(index);
            assert parToken.isIdentifier() && "(".equals(parToken.getValue());
            if (function instanceof Function) {
                ast.addFunction((Function) function);
            } else if (function instanceof LoopFunction) {
                ast.addLoopFunction((LoopFunction) function);
            } else {
                throw new ParseTimeException(String.format("Unsolved function '%s'", identifier));
            }
        }
    }
}

class AbstractSyntaxTree {

    private AbstractSyntaxTree inner;
    private Stack<Node> stack = new Stack<>();
    private BlockStmt element = new BlockStmt();
    private boolean inExpr;

    void addNumber(Real number) {
        if (inner == null) {
            stack.push(new NumberNode(number));
        } else {
            inner.addNumber(number);
        }
    }

    void addUnaryOperator(UnaryOperator operator) {
        if (inner == null) {
            stack.push(new UnaryOperatorNode(operator));
            inExpr = true;
        } else {
            inner.addUnaryOperator(operator);
        }
    }

    void addBinaryOperator(BinaryOperator operator) {
        if (inner == null) {
            stack.push(new BinaryOperatorNode(operator));
            inExpr = true;
        } else {
            inner.addBinaryOperator(operator);
        }
    }

    void addVariable(String variableName) {
        if (inner == null) {
            stack.push(new VariableNode(variableName));
        } else {
            inner.addVariable(variableName);
        }
    }

    void addMacro(String macroName) {
        if (inner == null) {
            stack.push(new MacroNode(macroName));
        } else {
            inner.addMacro(macroName);
        }
    }

    void addFunction(Function function) {
        if (inner == null) {
            stack.push(new FunctionNode(function));
            inner = new AbstractSyntaxTree();
        } else {
            inner.addFunction(function);
        }
    }

    void addLoopFunction(LoopFunction function) {
        if (inner == null) {
            stack.push(new LoopFunctionNode(function));
            inner = new AbstractSyntaxTree();
        } else {
            inner.addLoopFunction(function);
        }
    }

    void addParenthesis() {
        if (inner == null) {
            inner = new AbstractSyntaxTree();
        } else {
            inner.addParenthesis();
        }
    }

    void buildParenthesis() {
        if (inner.inner == null) {
            inner.build();
            BlockStmt innerRoot = inner.getRoot();
            if (innerRoot.getChildren().isEmpty())
                throw new ParseTimeException("Empty parenthesis");
            stack.push(innerRoot.getChildren().get(0));
            inner = null;
        } else {
            inner.buildParenthesis();
        }
    }

    void buildFunction() {
        if (inner.inner == null) {
            inner.build();
            BlockStmt innerRoot = inner.getRoot();
            inner = null;
            FunctionBaseNode functionNode = (FunctionBaseNode) stack.peek();
            functionNode.throwErrorIfArgumentTooFew(innerRoot.getChildren().size());
            for (Node node : innerRoot.getChildren()) {
                functionNode.addArgument(node);
            }
        } else {
            inner.buildFunction();
        }
    }

    private void buildExpr() {
        if (inner == null) {
            if (inExpr) {
                inExpr = false;
                List<Node> exprNodes = new ArrayList<>();
                while (!stack.isEmpty()) {
                    Node node = stack.peek();
                    if (node instanceof LeafNode || node instanceof OperatorNode
                            || (node instanceof FunctionBaseNode && ((FunctionBaseNode) node).fulfilled())) {
                        exprNodes.add(node);
                        stack.pop();
                    } else {
                        break;
                    }
                }
                if (!exprNodes.isEmpty()) {
                    Collections.reverse(exprNodes);
                    Node node = parseExpr(exprNodes);
                    stack.push(node);
                }
            }
        } else {
            inner.buildExpr();
        }
    }

    void build() {
        if (inner == null) {
            buildExpr();
            if (!stack.isEmpty()) element.addChild(stack.pop());
            if (!stack.isEmpty()) throw new ParseTimeException("Unexpected");
        } else {
            inner.build();
        }
    }

    BlockStmt getRoot() {
        return element;
    }

    private static Node parseExpr(List<Node> exprNodes) {
        while (exprNodes.size() > 1) {
            int maxPrecedence = 0;
            int index = 0;
            for (int i = 0; i < exprNodes.size(); i++) {
                Node node = exprNodes.get(i);
                if (node instanceof UnaryOperatorNode) {
                    UnaryOperatorNode uon = (UnaryOperatorNode) node;
                    int pre = uon.getPrecedence();
                    if (pre > maxPrecedence && uon.operand == null) {
                        maxPrecedence = pre;
                        index = i;
                    }
                } else if (node instanceof BinaryOperatorNode) {
                    BinaryOperatorNode bon = (BinaryOperatorNode) node;
                    int pre = bon.getPrecedence();
                    if (pre > maxPrecedence && bon.left == null && bon.right == null) {
                        maxPrecedence = pre;
                        index = i;
                    }
                }
            }
            OperatorNode operatorNode = (OperatorNode) exprNodes.get(index);
            if (operatorNode instanceof UnaryOperatorNode) {
                int valueIndex = ((UnaryOperatorNode) operatorNode).operator.isLeftAssociative()
                        ? index - 1 : index + 1;
                ((UnaryOperatorNode) operatorNode).operand = exprNodes.remove(valueIndex);
            } else if (operatorNode instanceof BinaryOperatorNode) {
                ((BinaryOperatorNode) operatorNode).left = exprNodes.get(index - 1);
                ((BinaryOperatorNode) operatorNode).right = exprNodes.get(index + 1);
                exprNodes.remove(index + 1);
                exprNodes.remove(index - 1);
            }
        }
        return exprNodes.get(0);
    }
}
