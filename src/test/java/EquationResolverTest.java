import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 02-Oct-16
 */
@RunWith(DataProviderRunner.class)
public class EquationResolverTest {

    EquationResolver resolver = new EquationResolver();

    @Test
    @UseDataProvider("rootDataSet")
    public void test(BigInteger a, BigInteger b, BigInteger c) {
        assertThat(resolver.findSolve(a, b)).isEqualByComparingTo(c);
    }

    @DataProvider
    public static List<Object> rootDataSet() {
        return newArrayList(
                roots(851, 20, 11),
                roots(740, 23, 6),
                roots(460, 37, 7)
        );
    }

    static List<Object> roots(int... roots) {
        final List<Object> result = new ArrayList<>(roots.length);
        for (int root : roots) result.add(BigInteger.valueOf(root));
        return result;
    }
}
