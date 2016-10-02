import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
public class KeyGeneratorTest {

    static final int GROUP_SIZE     = 5;
    static final int MIN_GROUP_SIZE = 3;
    static final BigInteger SECRET  = BigInteger.valueOf(11);

    KeyGenerator generator = new KeyGenerator();

    @Test
    public void generates() {
        generator.generate(SECRET, GROUP_SIZE, MIN_GROUP_SIZE);

        assertThat(generator.getCommonNumber()).isNotNull();
        assertThat(generator.getPairs()).hasSize(GROUP_SIZE);
    }
}