package trashsoftware.decimalExpr;

import trashsoftware.decimalExpr.parser.BlockStmt;
import trashsoftware.decimalExpr.parser.Parser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class DecimalExpr {

    private BlockStmt root;

    private Map<String, BigDecimal> variables = new HashMap<>();

    DecimalExpr(DecimalExprBuilder exprBuilder) {
        root = new Parser(exprBuilder).parse();
    }

    public DecimalExpr setVariable(String variable, BigDecimal value) {
        variables.put(variable, value);
        return this;
    }

    public BigDecimal evaluate() {
        return root.eval(variables);
    }
}
