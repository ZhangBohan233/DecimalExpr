package trashsoftware.decimalExpr;

import trashsoftware.decimalExpr.parser.Node;
import trashsoftware.decimalExpr.parser.ValuesBundle;
import trashsoftware.decimalExpr.parser.BlockStmt;
import trashsoftware.decimalExpr.parser.Parser;
import trashsoftware.numbers.Real;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class DecimalExpr {

    private final BlockStmt root;
    private final ValuesBundle valuesBundle;

    DecimalExpr(DecimalExprBuilder exprBuilder) {
        root = new Parser(exprBuilder).parse();
        Map<String, Real> variables = new HashMap<>();
        for (String varName: exprBuilder.getVariableNames()) {
            variables.put(varName, null);
        }
        Map<String, Node> macros = new HashMap<>();
        for (String macroName : exprBuilder.getMacroNames()) {
            macros.put(macroName, null);
        }
        valuesBundle = new ValuesBundle(variables, macros);
    }

    public DecimalExpr setVariable(String variable, Real value) {
        valuesBundle.putVariable(variable, value);
        return this;
    }

    public DecimalExpr setMacro(String macro, String macroContent) {
        DecimalExprBuilder subBuilder = new DecimalExprBuilder().expression(macroContent);
        subBuilder.getVariableNames().addAll(valuesBundle.getVariables().keySet());
        subBuilder.getMacroNames().addAll(valuesBundle.getMacros().keySet());
        subBuilder.getMacroNames().remove(macro);
        Parser subParser = new Parser(subBuilder);
        valuesBundle.putMacro(macro, subParser.parse());
        return this;
    }

    public Real evaluate() {
        return root.eval(valuesBundle);
    }
}
