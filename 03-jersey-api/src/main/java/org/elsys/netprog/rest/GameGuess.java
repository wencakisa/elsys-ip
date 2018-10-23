package org.elsys.netprog.rest;

import java.util.Map;

public class GameGuess {
    private String gameId;

    private Integer cowsNumber;

    private Integer bullsNumber;

    private Integer turnsCount;

    private Boolean success;

    public GameGuess(Game game, Integer guessNumber) {
        Map<Integer, Integer> result = game.guess(guessNumber);

        this.gameId = game.getGameId();
        this.cowsNumber = result.keySet().iterator().next();
        this.bullsNumber = result.values().iterator().next();
        this.turnsCount = game.getTurnsCount();
        this.success = game.isSuccess();
    }

    public String getGameId() {
        return gameId;
    }

    public Integer getCowsNumber() {
        return cowsNumber;
    }

    public Integer getBullsNumber() {
        return bullsNumber;
    }

    public Integer getTurnsCount() {
        return turnsCount;
    }

    public Boolean getSuccess() {
        return success;
    }
}
