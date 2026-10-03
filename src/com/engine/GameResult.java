package com.engine;

/**
 * Encapsulates the execution result and statistical performance of a game session.
 */
public record GameResult(
    boolean won,
    int targetNumber,
    int totalGuesses,
    int theoreticalMaxGuesses,
    long durationMillis
) {
    public boolean isOptimallySolved() {
        return totalGuesses <= theoreticalMaxGuesses;
    }
}
