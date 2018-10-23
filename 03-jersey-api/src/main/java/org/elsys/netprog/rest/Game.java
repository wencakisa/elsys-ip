package org.elsys.netprog.rest;

import java.util.*;
import java.util.stream.IntStream;

public class Game {

    private static final class Helper {
        private static String generateIdString() {
            return UUID.randomUUID().toString();
        }

        private static Integer[] numberToArray(Integer number) {
            String numText = Integer.toString(number);
            Integer[] numArray = new Integer[numText.length()];

            int i = 0;
            for (Character ch : numText.toCharArray()) {
                numArray[i++] = ch - '0';
            }

            return numArray;
        }

        private static Set<Integer> numberToSet(Integer number) {
            return new HashSet<>(Arrays.asList(numberToArray(number)));
        }

        private static int[] setToIntArray(Set<Integer> intSet) {
            return intSet.stream().mapToInt(Number::intValue).toArray();
        }
    }

    private static final Integer WIN_COUNT = 4;

    private static final String HIDDEN_SECRET = "****";

    private String gameId;

    private String secret;

    private Integer turnsCount;

    private Boolean success;


    public Game(Integer secret) {
        this.gameId = Helper.generateIdString();
        this.secret = secret.toString();
        this.turnsCount = 0;
        this.success = false;

    }

    public String getGameId() {
        return gameId;
    }

    public String getSecret() {
        return (isSuccess()) ? secret : HIDDEN_SECRET;
    }

    public Integer getTurnsCount() {
        return turnsCount;
    }

    public Boolean isSuccess() {
        return success;
    }

    private int calculateCows(Set<Integer> secretSet, Set<Integer> numberSet) {
        int cows = 0;

        int[] secretArray = Helper.setToIntArray(secretSet);
        int[] numberArray = Helper.setToIntArray(numberSet);

        for (int i : secretArray) {
            if (IntStream.of(numberArray).anyMatch(x -> x == i)) {
                cows++;
            }
        }

        return cows;
    }

    private int calculateBulls(Set<Integer> secretSet, Set<Integer> numberSet) {
        int bulls = 0;

        int[] secretArray = Helper.setToIntArray(secretSet);
        int[] numberArray = Helper.setToIntArray(numberSet);

        for (int i = 0; i < secretArray.length; i++) {
            if (secretArray[i] == numberArray[i]) {
                bulls++;
            }
        }

        return bulls;
    }

    public Map<Integer, Integer> guess(Integer guessNumber) {
        Map<Integer, Integer> result = new HashMap<>();

        Set<Integer> secretSet = Helper.numberToSet(Integer.valueOf(secret));
        Set<Integer> guessNumberSet = Helper.numberToSet(guessNumber);

        int cowsCount = calculateCows(secretSet, guessNumberSet);
        int bullsCount = calculateBulls(secretSet, guessNumberSet);

        result.put(cowsCount, bullsCount);
        turnsCount++;

        if (cowsCount == WIN_COUNT && bullsCount == WIN_COUNT) {
            success = true;
        }

        return result;
    }

    public static Boolean isValidNumber(Integer number) {
        return Helper.numberToSet(number).size() == Integer.toString(number).length();
    }
}
