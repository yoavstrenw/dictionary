package com.amenity.test.interview;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by yoavstern on 17/08/2018.
 */
@Getter
@Setter
public class DictionaryNode {

    private final Character key;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<DictionaryNode> topCandidates;
    private int numOfOccurrences;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private HashMap<Character, DictionaryNode> children;
    private String originalString;

    public DictionaryNode(Character key, int numOfSuggestions) {
        this.key = key;
        topCandidates = new ArrayList<>(numOfSuggestions);
        children = new HashMap<>();
        numOfOccurrences = 1;
    }


}
