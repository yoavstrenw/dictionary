package com.amenity.test;

import com.amenity.test.interview.AutoCompleteDictionary;
import com.amenity.test.interview.DictionaryNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 *
 * Created by yoavstern on 18/08/2018.
 */
public class AutoCompleteDictionaryTest {
    @Test
    public void build_shouldBuildSimpleTreeForOneWord() {
        AutoCompleteDictionary t = new AutoCompleteDictionary(3);
        final String insertedWord = "abcd";
        t.build(Collections.singletonList(insertedWord));

        final DictionaryNode root = t.getRoot();
        assertEquals(1, root.getChildren().size());

        final DictionaryNode a = root.getChildren().get('a');
        final List<DictionaryNode> aCandidates = a.getTopCandidates();
        assertEquals(1, a.getChildren().size());

        final DictionaryNode b = a.getChildren().get('b');
        final List<DictionaryNode> bCandidates = b.getTopCandidates();
        assertEquals(1, b.getChildren().size());

        final DictionaryNode c = b.getChildren().get('c');
        final List<DictionaryNode> cCandidates = c.getTopCandidates();
        assertEquals(1, c.getChildren().size());

        final DictionaryNode d = c.getChildren().get('d');
        assertEquals(0, d.getChildren().size());

        final String originalString = d.getOriginalString();

        assertEquals(originalString, aCandidates.get(0).getOriginalString());
        assertEquals(originalString, bCandidates.get(0).getOriginalString());
        assertEquals(originalString, cCandidates.get(0).getOriginalString());

    }

    @Test
    public void build_shouldBuildTreeTwoWords() {
        AutoCompleteDictionary t = new AutoCompleteDictionary(3);
        final String abcd = "abcd";
        final String abce = "abce";

        final List<String> stringList = Arrays.asList(abcd, abce);
        t.build(stringList);

        final DictionaryNode root = t.getRoot();
        assertEquals(1, root.getChildren().size());

        final DictionaryNode a = root.getChildren().get('a');
        final List<DictionaryNode> aCandidates = a.getTopCandidates();
        assertEquals(1, a.getChildren().size());

        final DictionaryNode b = a.getChildren().get('b');
        final List<DictionaryNode> bCandidates = b.getTopCandidates();
        assertEquals(1, b.getChildren().size());

        final DictionaryNode c = b.getChildren().get('c');
        final List<DictionaryNode> cCandidates = c.getTopCandidates();
        assertEquals(2, c.getChildren().size());

        final DictionaryNode d = c.getChildren().get('d');
        final DictionaryNode e = c.getChildren().get('e');

        assertEquals(0, d.getChildren().size());
        assertEquals(0, e.getChildren().size());
        assertEquals(abcd, d.getOriginalString());
        assertEquals(abce, e.getOriginalString());
        assertEquals(stringList, nodeListToStringList(aCandidates));
        assertEquals(stringList, nodeListToStringList(bCandidates));
        assertEquals(stringList, nodeListToStringList(cCandidates));

    }

    @Test
    public void getAutocompleteCandidates_given2MatchedPrefix_shouldReturn2Suggestion() {
        AutoCompleteDictionary t = new AutoCompleteDictionary(3);
        final String abcd = "abcd";
        final String abce = "abce";

        final List<String> stringList = Arrays.asList(abcd, abce);
        t.build(stringList);

        final List abc = t.getAutocompleteCandidates("abc");
        assertEquals(2, abc.size());
    }

    @Test
    public void getAutocompleteCandidates_givenNoMatchedPrefix_shouldReturnEmptyLst() {
        AutoCompleteDictionary t = new AutoCompleteDictionary(3);
        final String abcd = "abcd";
        final String abce = "abce";

        final List<String> stringList = Arrays.asList(abcd, abce);
        t.build(stringList);

        final List lst = t.getAutocompleteCandidates("LOL");
        assertEquals(0, lst.size());
    }

    @Test
    public void getAutocompleteCandidates_given6MatchedPrefix_shouldReturn3SuggestionWithMostOccurnces() {
        AutoCompleteDictionary t = new AutoCompleteDictionary(3);

        final String abcde = "abcde";
        final String abcdd = "abcdd";
        final String abcdq = "abcdq";
        List<String> expected = new ArrayList<>(Arrays.asList(abcde, abcdd, abcdq));
        final List<String> stringList = Arrays.asList(
                abcde, abcde, abcdd, abcdd, "abcdxyz", "abcdyo", abcdq, abcdq, "abcdw", "eee", "lll");
        t.build(stringList);

        final List sug = t.getAutocompleteCandidates("abcd");
        assertEquals(expected, sug);

    }
    @Test
    public void getAutocompleteCandidates_given6MatchedPrefix_shouldReturnListWithWordWithGreatestWeight() {
        AutoCompleteDictionary t = new AutoCompleteDictionary(3);
        final List<String> stringList = Arrays.asList(
                "cheetah", "cow", "chicken", "camel", "cat", "chimpanzee", "cow");
        t.build(stringList);

        final List sug = t.getAutocompleteCandidates("c");

        Assert.assertEquals(true, sug.contains("cow"));

    }

    private List<String> nodeListToStringList(List<DictionaryNode> nodeLst) {
        return nodeLst.stream().map(DictionaryNode::getOriginalString).collect(Collectors.toList());
    }


}