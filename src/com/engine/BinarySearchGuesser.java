package com.engine;

/**
 * Minimax Information-Theoretic Guesser implementing optimal Binary Search.
 * Guarantees convergence in ceil(log2(high - low + 1)) queries.
 */
public class BinarySearchGuesser {
    private int low;
    private int high;

    public BinarySearchGuesser(int low, int high) {
        if (low > high) {
            throw new IllegalArgumentException("Low boundary cannot exceed high boundary.");
        }
        this.low = low;
        this.high = high;
    }

    /**
     * Computes the next query maximizing expected information gain (entropy halving).
     * Uses low + (high - low) / 2 to avoid 32-bit integer overflow.
     */
    public int nextGuess() {
        if (low > high) {
            throw new IllegalStateException("Search interval exhausted. Inconsistent feedback received.");
        }
        return low + (high - low) / 2;
    }

    /**
     * Updates search interval based on ternary feedback.
     */
    public void provideFeedback(Feedback feedback, int guess) {
        switch (feedback) {
            case TOO_LOW -> this.low = guess + 1;
            case TOO_HIGH -> this.high = guess - 1;
            case CORRECT -> {
                this.low = guess;
                this.high = guess;
            }
        }
    }

    public static int calculateTheoreticalMax(int low, int high) {
        int range = high - low + 1;
        if (range <= 0) return 0;
        return (int) Math.floor(Math.log(range) / Math.log(2.0)) + 1;
    }

}
