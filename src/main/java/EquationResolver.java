import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
final class EquationResolver {

    /* Solve: (a * b) mod c = 1 */
    public BigInteger findSolve(BigInteger a, BigInteger c) {
        final BigInteger res = new FastFactorSearcher().search(a, c);
        return res.equals(ZERO) ? slowSearch(a, c) : res;
    }

    private BigInteger slowSearch(BigInteger a, BigInteger c) {
        BigInteger b = ONE;
        while (!a.multiply(b).mod(c).equals(ONE)) {
            b = b.add(ONE);
        }
        return b;
    }

    private final static class FastFactorSearcher {
        private BigInteger x;
        private BigInteger y;

        public BigInteger search(BigInteger a, BigInteger m) {
            BigInteger g = execute(a, m);
            BigInteger result = ZERO;
            if (g.equals(ONE)) result = x.mod(m).add(m).mod(m);
            return result;
        }

        private BigInteger execute(BigInteger a, BigInteger b) {
            if (b.equals(ZERO)) {
                x = ONE;
                y = ZERO;
                return a;
            }
            x = ZERO;
            y = ZERO;
            final BigInteger result = execute(b, a.mod(b));
            final BigInteger lastX = x;
            x = y;
            y = lastX.subtract(a.divide(b).multiply(y));
            return result;
        }
    }
}
