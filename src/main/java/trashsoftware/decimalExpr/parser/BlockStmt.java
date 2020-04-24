package trashsoftware.decimalExpr.parser;

import trashsoftware.numbers.Real;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockStmt extends Node {

    private final List<Node> children = new ArrayList<>();

    @Override
    public Real eval(ValuesBundle valuesBundle) {
        return children.get(0).eval(valuesBundle);
    }

    void addChild(Node node) {
        children.add(node);
    }

    List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "BlockStmt{" + children + "}";
    }
}
