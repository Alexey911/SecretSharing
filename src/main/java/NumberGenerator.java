import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
final class NumberGenerator {

    static final BigInteger INT_MAX_VALUE = BigInteger.valueOf(Integer.MAX_VALUE);
    static final BigInteger TWO = BigInteger.valueOf(2);

    public BigInteger getRandomWithMaxRange(BigInteger max) {
        if (max.compareTo(INT_MAX_VALUE) > 0) max = INT_MAX_VALUE;
        Random random = new Random(0);
        int bound = max.intValue() - 1;
        return BigInteger.valueOf(1 + random.nextInt(bound));
    }

    public BigInteger nextPrimeNumber(BigInteger start) {
        return start.nextProbablePrime();
    }

    public BigInteger nextStrictPrimeNumber(BigInteger start) {
        BigInteger prime = start;
        while (!isPrime(prime)) {
            prime = prime.add(ONE);
        }
        return prime;
    }

    public List<BigInteger> getRelativePrimeNumbers(int count, BigInteger min) {
        List<BigInteger> numbers = newArrayList();

        BigInteger last = min;
        while (numbers.size() < count) {
            while (!isRelativePrime(numbers, last)) last = last.add(ONE);
            numbers.add(last);
            last = last.add(ONE);
        }
        return numbers;
    }

    private static boolean isPrime(BigInteger number) {
        if (number.remainder(TWO).equals(ZERO)) return false;

        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(number) < 0; i = i.add(TWO)) {
            if (number.remainder(i).equals(ZERO)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRelativePrime(List<BigInteger> numbers, BigInteger potential) {
        for (BigInteger number : numbers) {
            if (!number.gcd(potential).equals(ONE)) return false;
        }
        return true;
    }
}
