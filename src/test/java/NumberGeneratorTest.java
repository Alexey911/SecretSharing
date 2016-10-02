import org.junit.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
public class NumberGeneratorTest {

    NumberGenerator generator = new NumberGenerator();

    @Test
    public void getsRandomWithMaxRange() {
        assertThat(generator.getRandomWithMaxRange(number(15)))
                .isLessThan(number(15))
                .isGreaterThan(ZERO);
    }

    @Test
    public void getsPrimeNumber() {
        assertThat(generator.nextPrimeNumber(number(8)))
                .isEqualByComparingTo(number(11));
    }

    @Test
    public void getsStrictPrimeNumber() {
        assertThat(generator.nextStrictPrimeNumber(number(8)))
                .isEqualByComparingTo(number(11));
    }

    @Test
    public void getsRelativePrimeNumbers() {
        assertThat(generator.getRelativePrimeNumbers(5, number(2)))
                .hasSize(5)
                .containsOnly(number(2), number(3), number(5), number(7), number(11));
    }

    BigInteger number(int value) {
        return BigInteger.valueOf(value);
    }
}