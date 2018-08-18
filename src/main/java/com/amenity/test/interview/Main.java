package com.amenity.test.interview;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * Created by yoavstern on 16/08/2018.
 */
public class Main {

    static List<String> wordsList = Arrays.asList("bird", "kangaroo", "spider", "horse", "shark", "octopus", "camel", "rat", "giraffe", "goat", "goldfish", "crocodile", "seal", "snail", "hippopotamus", "monkey", "panda", "cow", "bear", "frog", "eagle", "turtle", "cheetah", "bee", "sheep", "alligator", "wolf", "fox", "deer", "cat", "fly", "kitten", "chicken", "scorpion", "elephant", "zebra", "chimpanzee", "tiger", "rabbit", "lion", "pig", "ant", "hamster", "snake", "lobster", "dolphin", "squirrel", "dog", "fish", "puppy", "duck", "owl");


    public static void main(String[] args) throws IOException {
        AutoCompleteDictionary dictionary = new AutoCompleteDictionary(3);
        dictionary.build(wordsList);
        Scanner sc = new Scanner(System.in);

        System.out.println("please enter the prefix ");
        String prefix = sc.nextLine();
        System.out.println(dictionary.getAutocompleteCandidates(prefix));

    }




}
