package com.optimaize.wanakana;

import java.util.Collections;
import java.util.List;

/**
 * Converts strings from one script to another, or any combination thereof.
 *
 * A converter may do simple 1 to 1 conversion, or support things like mixed Japanese (including more than 1 script)
 * to Romaji.
 *
 * The converter may keep, remove, or replace with placeholder (eg question mark) the characters that it does not
 * understand. It must document its decision.
 *
 */
public interface Converter {

    /**
     */
    String convert(String input);

    /**
     * @return with 1 to n unique entries.
     */
    default List<String> convertToVariants(String input) {
        return Collections.singletonList(convert(input));
    }

}
