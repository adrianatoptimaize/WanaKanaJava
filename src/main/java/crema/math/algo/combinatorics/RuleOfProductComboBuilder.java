package crema.math.algo.combinatorics;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASS COPIED FROM CREMA-MATH VERSION 1.1 ON 2019-10-08.
 * 
 * 
 * 
 * 
 * 
 * Builds variants of lists of objects by taking 1 of each group.
 *
 * <p>See http://en.wikipedia.org/wiki/Rule_of_product and for starters the "Simple example" section.</p>
 *
 * <p>This is best explained by an example:
 * Got groups of products and want to build baskets with 1 element of each category.
 * Categories:
 * <pre>
 *   fruits [apple, banana, orange]
 *   sweets [chocolate]
 *   drinks [water, wine]
 * </pre>
 *
 * The code:<pre><code>
 * List<List<String>> result = new RuleOfProductComboBuilder<String>()
 *   .newGroup().variant("apple").variant("banana").variant("orange")
 *   .newGroup().variant("chocolate")
 *   .newGroup().variant("water").variant("wine")
 * .build();
 * </code></pre>
 * Result: [[apple, chocolate, water], [banana, chocolate, water], [orange, chocolate, water], [apple, chocolate, wine], [banana, chocolate, wine], [orange, chocolate, wine]]
 * </p>
 *
 * <p>Note: null values and empty lists are not permitted.</p>
 *
 * <p>See also crema-string StringVariantBuilder.</p>
 *
 * <p>If you need the variants and results to be weighted, then just create a wrapper object containing your
 * T plus a weight, and after calling build() then sort/filter by the sum of the weights or whatever your needs are.
 * See the WeightedStringsRuleOfProduct for an example.</p>
 *
 */
public class RuleOfProductComboBuilder<T> {

    private final List<List<T>> groups = new ArrayList<>();
    protected List<T> currentGroup;



    /**
     * Adds the given <code>variant</code> to the current group.
     * <p>If no group was added explicitly with {@link #newGroup()} then one is created for you.</p>
     * @return the builder
     */
    public RuleOfProductComboBuilder<T> variant(T variant) {
        if (currentGroup==null) newGroup();
        currentGroup.add(variant);
        return this;
    }

    /**
     * Calls variant() for each item.
     * @return the builder
     */

    public RuleOfProductComboBuilder<T> variants(Iterable<T> variants) {
        for (T variant : variants) {
            variant(variant);
        }
        return this;
    }

    /**
     * @return the builder
     * @throws IllegalStateException if the last group was empty. See isGroupEmpty()
     */

    public RuleOfProductComboBuilder<T> newGroup() throws IllegalStateException {
        if (currentGroup!=null) {
            checkGroupNotEmpty();
        }
        currentGroup = new ArrayList<>();
        groups.add(currentGroup);
        return this;
    }

    /**
     * Tells if the current group is empty.
     */
    public boolean isGroupEmpty() {
        if (currentGroup==null) return true;
        return currentGroup.isEmpty();
    }

    /**
     * Tells if nothing was added at all.
     */
    public boolean isEmpty() {
        if (currentGroup==null) return true;
        if (groups.size()>1) return false;
        return currentGroup.isEmpty();
    }

    private void checkGroupNotEmpty() {
        if (currentGroup==null || currentGroup.isEmpty()) {
            throw new IllegalStateException("Last group is empty!");
        }
    }

    /**
     * It's a simple multiplication of all group sizes.
     *
     * This is useful to prevent creating too many products. Chances are the program can't handle those
     * products anyway when the groups are too large or too many. Then you can call this and throw an
     * UnsupportedOperationException early.
     *
     * @throws IllegalStateException if a group is empty. returns 0 if there are no groups.
     */
    public long computeProductSize() {
        checkGroupNotEmpty();
        long total = 1;
        for (List<T> group : groups) {
            total *= group.size();
        }
        return total;
    }

    /**
     * @return Not empty.
     * @throws IllegalStateException if the last group was empty, or nothing was added at all. See isEmpty()
     */

    public List<List<T>> build() throws IllegalStateException {
        checkGroupNotEmpty();

        List<List<T>> ret = new ArrayList<>();

        for (int i=0; i<groups.size(); i++) {
            List<T> group = groups.get(i);
            if (i==0) {
                //first round, every entry is a new one
                for (T variant : group) {
                    List<T> newList = new ArrayList<>();
                    newList.add(variant);
                    ret.add(newList);
                }
            } else {
                if (group.size()==1) {
                    //just add:
                    for (List<T> retList : ret) {
                        retList.add(group.get(0));
                    }
                } else {
                    //duplicate for each
                    assert group.size() > 1;
                    List<List<T>> retTmp = new ArrayList<>();
                    for (T variant : group) {
                        for (List<T> retList : ret) {
                            List<T> copy = new ArrayList<>(retList);
                            copy.add(variant);
                            retTmp.add(copy);
                        }
                    }
                    ret = retTmp;
                }
            }
        }
        return ret;
    }

}
