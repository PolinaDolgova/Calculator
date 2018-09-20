import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.*;
import sgu.Calculator;
import sgu.Expression;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RunWith(value = Parameterized.class)
public class CalculatorTest {

    private String expression;
    private Double answer;

    Calculator calculator = new Calculator();

    public CalculatorTest(String expression, Double answer){
        this.expression = expression;
        this.answer = answer;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Object[][] data = new Object[][] {
                { "5", 5.0},
                { "-5", -5.0},
                { "-5.0", -5.0},
                { "25", 25.0},
                { "4 + 3", 7.0},
                {"4 * 3", 12.0},
                {"4/3", 1.3333333333333333},
                {"4 ^3", 64.0},
                {"4 - 3", 1.0},
                {"(4 + 3)", 7.0},
                {"((4 + 3))", 7.0},
                {"( 4 + 3) ^ (5 - 3)", 49.0},
                {"-5 --2", -3.0},
                {"-5 * - 2", 10.0},
                {"3 + 4 * 5", 23.0},
                {"3 + 4 - 5 / (7 + 3)", 6.5},
                {"5 - 2 ^ 10", -1019.0},
                {"(0)", 0.0},
                {"- (3 + 4)", -7.0}};
        return Arrays.asList(data);
    }

    @Test
    public void test() {
        assertEquals((Double) calculator.solve(new Expression(expression)), answer);
    }
}

