package trashsoftware.decimalExpr.parser;

import trashsoftware.decimalExpr.expression.EvaluateTimeException;
import trashsoftware.decimalExpr.parser.Node;
import trashsoftware.numbers.Real;

import java.util.HashMap;
import java.util.Map;

public class ValuesBundle {

    private final Map<String, Real> variables;
    private final Map<String, Node> macros;

    public ValuesBundle(Map<String, Real> variables, Map<String, Node> macros) {
        this.variables = variables;
        this.macros = macros;
    }

    public void putVariable(String variable, Real value) {
        if (variables.containsKey(variable)) {
            variables.put(variable, value);
        } else {
            throw new EvaluateTimeException(String.format("Variable '%s' is not defined.", variable));
        }
    }

    public void putMacro(String macro, Node node) {
        if (macros.containsKey(macro)) {
            macros.put(macro, node);
        } else {
            throw new EvaluateTimeException(String.format("Macro '%s' is not defined.", macro));
        }
    }

    Real getVariable(String varName) {
        return variables.get(varName);
    }

    Node getMacro(String macroName) {
        return macros.get(macroName);
    }

    public Map<String, Node> getMacros() {
        return macros;
    }

    public Map<String, Real> getVariables() {
        return variables;
    }
}
