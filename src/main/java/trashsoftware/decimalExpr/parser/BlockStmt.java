package trashsoftware.decimalExpr.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockStmt extends Node {

    private List<Node> children = new ArrayList<>();

    @Override
    public BigDecimal eval(Map<String, BigDecimal> variables) {
        return children.get(0).eval(variables);
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "BlockStmt{" + children + "}";
    }
}
