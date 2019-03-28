package trashsoftware.decimalExpr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class Unittest {

    @Test
    void testSingleNumber() {
        DecimalExpr expr = new DecimalExprBuilder().expression("2").build();
        assert expr.evaluate().equals(new BigDecimal(2));
    }
}
