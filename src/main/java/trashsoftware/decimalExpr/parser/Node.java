package trashsoftware.decimalExpr.parser;

import trashsoftware.decimalExpr.expression.BinaryOperator;
import trashsoftware.decimalExpr.expression.Function;
import trashsoftware.decimalExpr.expression.UnaryOperator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Node {

    public abstract BigDecimal eval(Map<String, BigDecimal> variables);
}

abstract class LeafNode extends Node {

}

class NumberNode extends LeafNode {

    private final BigDecimal number;

    NumberNode(BigDecimal number) {
        this.number = number;
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> variables) {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}


abstract class OperatorNode extends Node {
    final int extraPrecedence;

    OperatorNode(int extraPrecedence) {
        this.extraPrecedence = extraPrecedence;
    }

    abstract int getPrecedence();
}

class BinaryOperatorNode extends OperatorNode {
    private final BinaryOperator operator;
    Node left;
    Node right;

    BinaryOperatorNode(BinaryOperator operator, int extraPrecedence) {
        super(extraPrecedence);
        this.operator = operator;
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> variables) {
        return operator.eval(left.eval(variables), right.eval(variables));
    }

    @Override
    int getPrecedence() {
        return operator.getPrecedence() + extraPrecedence;
    }

    @Override
    public String toString() {
        return "BO{" + left + " " + operator.getSymbol() + " " + right + "}";
    }
}

class UnaryOperatorNode extends OperatorNode {
    final UnaryOperator operator;
    Node operand;

    UnaryOperatorNode(UnaryOperator operator, int extraPrecedence) {
        super(extraPrecedence);
        this.operator = operator;
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> variables) {
        return operator.eval(operand.eval(variables));
    }

    @Override
    int getPrecedence() {
        return operator.getPrecedence() + extraPrecedence;
    }

    @Override
    public String toString() {
        if (operator.isLeftAssociative()) {
            return "UO{" + operator.getSymbol() + " " + operand + "}";
        } else {
            return "UO{" + operand + " " + operator.getSymbol() + "}";
        }
    }
}

class VariableNode extends LeafNode {
    private final String variableName;

    VariableNode(String variableName) {
        // TODO: check exist
        this.variableName = variableName;
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> variables) {
        BigDecimal obj = variables.get(variableName);
        if (obj == null) throw new NoSuchVariableException(String.format("Variable '%s' is not set", variableName));
        return obj;
    }

    @Override
    public String toString() {
        return variableName;
    }
}

class FunctionNode extends Node {
    private final Function function;
    private final List<Node> arguments = new ArrayList<>();

    FunctionNode(Function function) {
        this.function = function;
    }

    void addArgument(Node node) {
        if (arguments.size() >= function.getMostNumArgument()) {
            throw new ParseTimeException(String.format("Too many arguments for function '%s'", function.getName()));
        } else {
            arguments.add(node);
        }
    }

    void throwErrorIfArgumentTooFew(int argumentCount) {
        if (argumentCount < function.getLeastNumArgument())
            throw new ParseTimeException(String.format("Too few arguments for function '%s'", function.getName()));
    }

    boolean fulfilled() {
        return arguments.size() >= function.getLeastNumArgument();
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> variables) {
        BigDecimal[] args = new BigDecimal[arguments.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = arguments.get(i).eval(variables);
        }
        return function.eval(args);
    }

    @Override
    public String toString() {
        return function.getName() + "(" + arguments + ")";
    }
}
