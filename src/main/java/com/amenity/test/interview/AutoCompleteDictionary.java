package com.amenity.test.interview;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by yoavstern on 18/08/2018.
 *
 */
@Getter
public class AutoCompleteDictionary {

    private int numOfSuggetions = 3;
    private DictionaryNode root;

    public AutoCompleteDictionary(int numOfSuggetions) {
        this.numOfSuggetions = numOfSuggetions;
        root = new DictionaryNode(' ', numOfSuggetions);
    }

    public void build(List<String> stringList) {
        stringList.stream().forEach(x -> addWord(root, x.toLowerCase(), x.toLowerCase()));
    }

    public List getAutocompleteCandidates(String prefix) {
        return getSuggestions(root, prefix.toLowerCase());
    }

    public List<String> getSuggestions(DictionaryNode curr, String prefix) {
        final DictionaryNode nodeByPrefix = findNodeByPrefix(curr, prefix);
        if (nodeByPrefix != null)
            return nodeByPrefix.getTopCandidates().stream().map(DictionaryNode::getOriginalString).collect(Collectors.toList());
        else return new ArrayList<>();
    }

    private DictionaryNode addWord(DictionaryNode current, String str, String orignalString) {

        if (StringUtils.isEmpty(str)) {
            current.setNumOfOccurrences(current.getNumOfOccurrences() + 1);
            current.setOriginalString(orignalString);
            return current;
        }
        final HashMap<Character, DictionaryNode> children = current.getChildren();
        final Character key = str.charAt(0);
        final DictionaryNode node = children.get(key);
        DictionaryNode next;
        if (node == null) {
            next = new DictionaryNode(key, numOfSuggetions);
            children.put(next.getKey(), next);
        } else next = node;

        final DictionaryNode wordNode = addWord(next, str.substring(1, str.length()), orignalString);
        handleTopXWords(current, wordNode);
        return wordNode;
    }


    private DictionaryNode findNodeByPrefix(DictionaryNode curr, String prefix) {
        if (StringUtils.isEmpty(prefix))
            return curr;
        final HashMap<Character, DictionaryNode> children = curr.getChildren();
        final Character key = prefix.charAt(0);
        final DictionaryNode node = children.get(key);
        if (node == null) {
            return null;
        } else return findNodeByPrefix(node, prefix.substring(1, prefix.length()));
    }


    private void handleTopXWords(DictionaryNode curr, DictionaryNode newNodeCandidate) {

        final List<DictionaryNode> topCandidates = curr.getTopCandidates();
        final DictionaryNode minOccurrencesNode = topCandidates.stream()
                .min((s1, s2) -> Integer.compare(s1.getNumOfOccurrences(), s2.getNumOfOccurrences()))
                .orElse(null);
        if (minOccurrencesNode == null || topCandidates.size() < numOfSuggetions) {
            if (!topCandidates.stream().map(DictionaryNode::getOriginalString).collect(Collectors.toList()).contains(newNodeCandidate.getOriginalString()))
                topCandidates.add(newNodeCandidate);
        } else if (newNodeCandidate.getNumOfOccurrences() > minOccurrencesNode.getNumOfOccurrences()) {
            topCandidates.remove(minOccurrencesNode);
            topCandidates.add(newNodeCandidate);
        }
    }


}
