import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Map;

import static java.math.BigInteger.valueOf;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
public class SecretResolverTest {

    SecretResolver resolver;

    BigInteger secret;
    BigInteger commonNumber;
    Map<BigInteger, BigInteger> pairs;

    @Before
    public void setUp() {
        resolver = new SecretResolver();

        secret = valueOf(11);
        commonNumber = valueOf(13);
        pairs = ImmutableMap.of(
                valueOf(20), valueOf(1),
                valueOf(23), valueOf(10),
                valueOf(37), valueOf(31)
        );
    }

    @Test
    public void resolves() {
        assertThat(resolver.resolve(pairs, commonNumber)).isEqualTo(secret);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checksPairs() {
        assertThat(resolver.resolve(emptyMap(), commonNumber));
    }
}