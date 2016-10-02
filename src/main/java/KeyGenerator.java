import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
public final class KeyGenerator {

    private BigInteger commonNumber;
    private Map<BigInteger, BigInteger> pairs;

    private NumberGenerator generator = new NumberGenerator();

    public void generate(BigInteger secret, int groupSize, int minGroupSize) {
        checkArguments(groupSize, minGroupSize);

        final BigInteger p = generateP(secret);

        List<BigInteger> numbers = generateRelativePrimeNumbers(groupSize, p);
        validateNumbers(numbers, p, minGroupSize);

        BigInteger r = generateR(numbers, p, secret, minGroupSize);
        BigInteger m = secret.add(r.multiply(p));

        this.commonNumber = p;
        this.pairs = generatePairs(numbers, m);
    }

    private void checkArguments(int groupSize, int minGroupSize) {
        if (groupSize < 3)            throw new IllegalArgumentException();
        if (minGroupSize < 2)         throw new IllegalArgumentException();
        if (minGroupSize > groupSize) throw new IllegalArgumentException();
    }

    private BigInteger generateP(BigInteger min) {
        return generator.nextPrimeNumber(min.add(ONE));
    }

    private List<BigInteger> generateRelativePrimeNumbers(int count, BigInteger primeNumber) {
        return generator.getRelativePrimeNumbers(count, primeNumber.add(ONE));
    }

    private void validateNumbers(List<BigInteger> numbers, BigInteger primeNumber, int minGroupSize) {
        BigInteger last = ZERO;
        for (BigInteger number : numbers) {
            if (number.compareTo(last) <= 0 || number.compareTo(primeNumber) <= 0) {
                throw new IllegalArgumentException();
            }
            last = number;
        }
        BigInteger left = ONE, right = primeNumber;

        for (int i = 0; i < minGroupSize; i++) {
            left = left.multiply(numbers.get(i));
        }
        for (int i = numbers.size() - minGroupSize + 1; i < numbers.size(); i++) {
            right = right.multiply(numbers.get(i));
        }
        if (left.compareTo(right) >= 0) throw new IllegalArgumentException();
    }


    private BigInteger generateR(List<BigInteger> numbers, BigInteger primeNumber,
                                 BigInteger secret, int minGroupSize) {
        BigInteger mul = ONE;
        for (int i = 0; i < minGroupSize; i++) {
            mul = mul.multiply(numbers.get(i));
        }
        BigInteger max = mul.subtract(secret).divide(primeNumber);
        return max.subtract(generator.getRandomWithMaxRange(max));
    }

    @SuppressWarnings("unchecked")
    private Map<BigInteger, BigInteger> generatePairs(List<BigInteger> keys, BigInteger m) {
        final Map<BigInteger, BigInteger> groups = newHashMap();
        for (BigInteger key : keys) groups.put(key, m.mod(key));
        return groups;
    }

    public BigInteger getCommonNumber() {
        return commonNumber;
    }

    public Map<BigInteger, BigInteger> getPairs() {
        return pairs;
    }
}
