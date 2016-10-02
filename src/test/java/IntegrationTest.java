import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
public class IntegrationTest {

    static final int GROUP_SIZE = 7;
    static final int MIN_GROUP_SIZE = 5;
    static final BigInteger EXPECTED_SECRET = BigInteger.valueOf(9_123_327_543_321_654_789L);

    KeyGenerator generator = new KeyGenerator();
    SecretResolver resolver = new SecretResolver();

    @Test
    public void resolvesWithMinimalGroupSize() {
        final BigInteger secret = generateAndResolve(GROUP_SIZE, MIN_GROUP_SIZE, MIN_GROUP_SIZE);
        assertThat(secret).isEqualByComparingTo(EXPECTED_SECRET);
    }

    @Test
    public void resolvesByGroup() {
        final BigInteger secret = generateAndResolve(GROUP_SIZE, MIN_GROUP_SIZE, GROUP_SIZE);
        assertThat(secret).isEqualByComparingTo(EXPECTED_SECRET);
    }

    @Test
    public void notWorksWithMinimumExcess() {
        final BigInteger secret = generateAndResolve(GROUP_SIZE, MIN_GROUP_SIZE, MIN_GROUP_SIZE - 1);
        assertThat(secret).isNotEqualByComparingTo(EXPECTED_SECRET);
    }

    BigInteger generateAndResolve(int groupSize, int minGroupSize, int realGroupSize) {
        generator.generate(EXPECTED_SECRET, groupSize, minGroupSize);
        return resolver.resolve(
                MapUtil.getMinimalKeyCount(generator.getPairs(), realGroupSize),
                generator.getCommonNumber()
        );
    }
}
