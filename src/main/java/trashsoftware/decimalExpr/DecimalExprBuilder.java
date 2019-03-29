package trashsoftware.decimalExpr;

import trashsoftware.decimalExpr.expression.*;
import trashsoftware.decimalExpr.util.ExpressionBuilderException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class DecimalExprBuilder {

    private String expression;
    private final Set<String> variableNames = new HashSet<>();
    private final Map<String, Function> functions = new HashMap<>();
    private final Map<String, BinaryOperator> binaryOperatorMap = new HashMap<>();
    private final Map<String, UnaryOperator> unaryOperatorMap = new HashMap<>();

    public DecimalExprBuilder() {
        operator(Operators.ADDITION);
        operator(Operators.SUBTRACTION);
        operator(Operators.MULTIPLICATION);
        operator(Operators.DIVISION);
        operator(Operators.MODULO);
        operator(Operators.POWER);
        operator(Operators.NEGATION);

        function(Functions.ABS);
    }

    public DecimalExprBuilder expression(String expression) {
        this.expression = expression;
        return this;
    }

    public DecimalExprBuilder variable(String variable) {
        if (variableNames.contains(variable)) {
            throw new ExpressionBuilderException("Variable " + variable + " already defined");
        }
        variableNames.add(variable);
        return this;
    }

    public DecimalExprBuilder function(Function function) {
        functions.put(function.getName(), function);
        return this;
    }

    public DecimalExprBuilder operator(Operator operator) {
        if (operator instanceof UnaryOperator) {
            unaryOperatorMap.put(operator.getSymbol(), (UnaryOperator) operator);
        } else if (operator instanceof BinaryOperator) {
            binaryOperatorMap.put(operator.getSymbol(), (BinaryOperator) operator);
        } else {
            throw new ExpressionBuilderException("Unknown operator type");
        }
        return this;
    }

    public Set<String> getVariableNames() {
        return variableNames;
    }

    public String getExpression() {
        return expression;
    }

    public Map<String, Function> getFunctions() {
        return functions;
    }

    public Map<String, UnaryOperator> getUnaryOperatorMap() {
        return unaryOperatorMap;
    }

    public Map<String, BinaryOperator> getBinaryOperatorMap() {
        return binaryOperatorMap;
    }

    public DecimalExpr build() {
        return new DecimalExpr(this);
    }
}
