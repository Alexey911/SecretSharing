import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
class MapUtil {

    private MapUtil(){
    }

    public static Map<BigInteger, BigInteger> getMinimalKeyCount(Map<BigInteger, BigInteger> pairs, int expectedSize) {
        Random random = new Random();
        List<BigInteger> keys = newArrayList(pairs.keySet());

        while (keys.size() > expectedSize) {
            int index = random.nextInt(keys.size());
            BigInteger key = keys.remove(index);
            pairs.remove(key);
        }
        return pairs;
    }
}
