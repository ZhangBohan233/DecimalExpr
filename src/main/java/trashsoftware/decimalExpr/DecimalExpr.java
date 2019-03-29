package trashsoftware.decimalExpr;

import trashsoftware.decimalExpr.expression.EvaluateTimeException;
import trashsoftware.decimalExpr.parser.BlockStmt;
import trashsoftware.decimalExpr.parser.Parser;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class DecimalExpr {

    private final BlockStmt root;
    private final Map<String, BigDecimal> variables = new HashMap<>();
    private final MathContext mathContext;

    DecimalExpr(DecimalExprBuilder exprBuilder) {
        root = new Parser(exprBuilder).parse();
        mathContext = new MathContext(exprBuilder.getPrecision(), RoundingMode.HALF_UP);
        for (String varName: exprBuilder.getVariableNames()) {
            variables.put(varName, null);
        }
    }

    public DecimalExpr setVariable(String variable, BigDecimal value) {
        if (variables.containsKey(variable)) {
            variables.put(variable, value);
            return this;
        } else {
            throw new EvaluateTimeException(String.format("Variable '%s' is not defined.", variable));
        }
    }

    public BigDecimal evaluate() {
        return root.eval(variables).round(mathContext);
    }
}
