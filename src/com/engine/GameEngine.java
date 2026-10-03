package com.engine;

import java.util.Random;

/**
 * Core engine managing game state, validation, and AI simulations.
 */
public class GameEngine {
    private final int min;
    private final int max;
    private final int secretNumber;
    private int guessCount;

    public GameEngine(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min must be strictly less than Max");
        }
        this.min = min;
        this.max = max;
        this.secretNumber = new Random().nextInt(max - min + 1) + min;
        this.guessCount = 0;
    }

    public GameEngine(int min, int max, int fixedSecret) {
        if (fixedSecret < min || fixedSecret > max) {
            throw new IllegalArgumentException("Secret number must lie within [min, max]");
        }
        this.min = min;
        this.max = max;
        this.secretNumber = fixedSecret;
        this.guessCount = 0;
    }

    public Feedback evaluate(int guess) {
        guessCount++;
        if (guess < secretNumber) {
            return Feedback.TOO_LOW;
        } else if (guess > secretNumber) {
            return Feedback.TOO_HIGH;
        } else {
            return Feedback.CORRECT;
        }
    }

    public int getMin() { return min; }
    public int getMax() { return max; }
    public int getGuessCount() { return guessCount; }
    public int getSecretNumber() { return secretNumber; }
}
