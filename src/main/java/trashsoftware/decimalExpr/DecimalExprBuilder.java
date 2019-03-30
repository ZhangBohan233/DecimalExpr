package trashsoftware.decimalExpr;

import trashsoftware.decimalExpr.expression.*;
import trashsoftware.decimalExpr.parser.ParseTimeException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class DecimalExprBuilder {

    private final Set<String> variableNames = new HashSet<>();
    private final Set<String> macroNames = new HashSet<>();
    private final Map<String, Function> functions = new HashMap<>();
    private final Map<String, BinaryOperator> binaryOperatorMap = new HashMap<>();
    private final Map<String, UnaryOperator> unaryOperatorMap = new HashMap<>();

    private String expression;
    private int precision = 16;

    public DecimalExprBuilder() {
        operator(Operators.ADDITION);
        operator(Operators.SUBTRACTION);
        operator(Operators.MULTIPLICATION);
        operator(Operators.DIVISION);
        operator(Operators.MODULO);
        operator(Operators.POWER);
        operator(Operators.NEGATION);
        operator(Operators.POSITIVE_SIGN);

        function(Functions.ABS);
        function(Functions.CEIL);
        function(Functions.E);
        function(Functions.FLOOR);
        function(Functions.PI);
        function(Functions.SQRT);
    }

    /**
     * Sets up the expression {@code String} object to be evaluated.
     * <p>
     * The expression is case-sensitive, but not space-sensitive.
     *
     * @param expression the expression text
     * @return a reference to this object
     */
    public DecimalExprBuilder expression(String expression) {
        this.expression = expression;
        return this;
    }

    /**
     * Sets up the rounding precision of this expression, in decimal.
     * <p>
     * If the user does not call this method, the default precision is 16 digits.
     *
     * @param precision the rounding precision, in decimal
     * @return a reference to this object
     */
    public DecimalExprBuilder precision(int precision) {
        this.precision = precision;
        return this;
    }

    public DecimalExprBuilder variable(String variable) {
        if (variableNames.contains(variable)) {
            throw new ParseTimeException("Variable '" + variable + "' already defined");
        }
        variableNames.add(variable);
        return this;
    }

    public DecimalExprBuilder macro(String macroName) {
        if (macroNames.contains(macroName)) {
            throw new ParseTimeException("Variable '" + macroName + "' already defined");
        }
        macroNames.add(macroName);
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
            throw new ParseTimeException("Unknown operator type");
        }
        return this;
    }

    /**
     * Returns the {@code DecimalExpr} built by this builder.
     *
     * @return the @code DecimalExpr} built by this builder
     */
    public DecimalExpr build() {
        return new DecimalExpr(this);
    }

    public Set<String> getVariableNames() {
        return variableNames;
    }

    public Set<String> getMacroNames() {
        return macroNames;
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

    public int getPrecision() {
        return precision;
    }
}
