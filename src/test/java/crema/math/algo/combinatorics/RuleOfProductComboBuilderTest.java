package crema.math.algo.combinatorics;


import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * CLASS COPIED FROM CREMA-MATH VERSION 1.1 ON 2019-10-08.
 *
 *
 *
 *
 *
 */
public class RuleOfProductComboBuilderTest {

    @Test
    public void basket() throws Exception {
        RuleOfProductComboBuilder<String> builder = new RuleOfProductComboBuilder<String>()
                .newGroup().variant("apple").variant("banana").variant("orange")
                .newGroup().variant("chocolate")
                .newGroup().variant("water").variant("wine");

        long expectedTotal = builder.computeProductSize();
        List<List<String>> result = builder.build();
        assertEquals(expectedTotal, result.size());

        //[[apple, chocolate, water], [banana, chocolate, water], [orange, chocolate, water], [apple, chocolate, wine], [banana, chocolate, wine], [orange, chocolate, wine]]
        assertEquals(6, result.size());
        assertEquals(Arrays.asList("apple", "chocolate", "water"), result.get(0));
        assertEquals(Arrays.asList("orange", "chocolate", "wine"), result.get(5));
    }

    @Test
    public void isGroupEmpty() throws Exception {
        RuleOfProductComboBuilder<String> builder = new RuleOfProductComboBuilder<>();
        builder.newGroup();
        assertTrue(builder.isGroupEmpty());
        builder.variant("foo");
        assertFalse(builder.isGroupEmpty());
        builder.newGroup();
        assertTrue(builder.isGroupEmpty());
    }

    @Test
    public void isEmpty() throws Exception {
        RuleOfProductComboBuilder<String> builder = new RuleOfProductComboBuilder<>();
        assertTrue(builder.isEmpty());
        builder.newGroup();
        assertTrue(builder.isEmpty());
        builder.variant("foo");
        assertFalse(builder.isEmpty());
        builder.newGroup();
        assertFalse(builder.isEmpty());
    }

    @Test
    public void implicitFirstGroup() throws Exception {
        RuleOfProductComboBuilder<String> builder = new RuleOfProductComboBuilder<>();
        assertTrue(builder.isGroupEmpty());
        builder.variant("foo");
    }
}
