import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
public final class SecretResolver {

    private EquationResolver resolver = new EquationResolver();

    public BigInteger resolve(Map<BigInteger, BigInteger> pairs, BigInteger commonNumber) {
        checkPairs(pairs);
        List<BigInteger> keys = newArrayList(pairs.keySet());
        BigInteger[] d = searchD(keys);
        BigInteger[] dRevert = searchRevertD(keys, d);

        return combine(pairs, d, dRevert, keys)
                .mod(getComposition(keys))
                .mod(commonNumber);
    }

    private void checkPairs(Map pairs) {
        if (pairs.size() < 3) throw new IllegalArgumentException();
    }

    private BigInteger combine(Map<BigInteger, BigInteger> pairs,
                               BigInteger[] d, BigInteger[] dRevert,
                               List<BigInteger> keys) {
        BigInteger sum = ZERO;
        for (int i = 0; i < pairs.size(); i++) {
            BigInteger keyValue = pairs.get(keys.get(i));
            BigInteger partial = d[i].multiply(dRevert[i]).multiply(keyValue);
            sum = sum.add(partial);
        }
        return sum;
    }

    private BigInteger[] searchD(List<BigInteger> keys) {
        final BigInteger[] d = new BigInteger[keys.size()];
        final BigInteger composition = getComposition(keys);

        for (int i = 0; i < keys.size(); i++) {
            d[i] = composition.divide(keys.get(i));
        }
        return d;
    }

    private BigInteger[] searchRevertD(List<BigInteger> keys, BigInteger[] d) {
        final BigInteger[] factors = new BigInteger[d.length];
        for (int i = 0; i < d.length; i++) {
            factors[i] = resolver.findSolve(d[i], keys.get(i));
        }
        return factors;
    }

    private static BigInteger getComposition(List<BigInteger> numbers) {
        BigInteger r = ONE;
        for (BigInteger number : numbers) r = r.multiply(number);
        return r;
    }
}
