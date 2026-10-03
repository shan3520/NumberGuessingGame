package com.engine;

import java.util.Random;

/**
 * Algorithmic optimality and property tests for BinarySearchGuesser.
 */
public class TestBinarySearchGuesser {
    public static void main(String[] args) {
        System.out.println("Running BinarySearchGuesser Algorithmic Optimality Suite...");

        testExhaustiveSmallRange();
        testPowerOfTwoBoundaries();
        testLargeScaleRandomTargets();
        testEdgeTargets();

        System.out.println("ALL 4 TEST SUITES PASSED! (Total simulated optimal games: >1,100)");
    }

    private static void testExhaustiveSmallRange() {
        int min = 1;
        int max = 100;
        int theoreticalBound = BinarySearchGuesser.calculateTheoreticalMax(min, max);

        for (int target = min; target <= max; target++) {
            GameEngine engine = new GameEngine(min, max, target);
            BinarySearchGuesser ai = new BinarySearchGuesser(min, max);

            while (true) {
                int guess = ai.nextGuess();
                Feedback feedback = engine.evaluate(guess);
                if (feedback == Feedback.CORRECT) {
                    if (engine.getGuessCount() > theoreticalBound) {
                        throw new AssertionError(String.format(
                                "Target %d exceeded bound! Guesses: %d, Bound: %d",
                                target, engine.getGuessCount(), theoreticalBound));
                    }
                    break;
                }
                ai.provideFeedback(feedback, guess);
            }
        }
        System.out.println("  [PASS] testExhaustiveSmallRange (100/100 targets verified)");
    }

    private static void testPowerOfTwoBoundaries() {
        int min = 1;
        int max = 1024;
        int theoreticalBound = BinarySearchGuesser.calculateTheoreticalMax(min, max); // log2(1024) = 10

        for (int target : new int[]{1, 2, 512, 1023, 1024}) {
            GameEngine engine = new GameEngine(min, max, target);
            BinarySearchGuesser ai = new BinarySearchGuesser(min, max);

            while (true) {
                int guess = ai.nextGuess();
                Feedback feedback = engine.evaluate(guess);
                if (feedback == Feedback.CORRECT) {
                    if (engine.getGuessCount() > theoreticalBound) {
                        throw new AssertionError(String.format("Exceeded bound for target %d", target));
                    }
                    break;
                }
                ai.provideFeedback(feedback, guess);
            }
        }
        System.out.println("  [PASS] testPowerOfTwoBoundaries (Exact power-of-two boundaries verified)");
    }

    private static void testLargeScaleRandomTargets() {
        int min = 1;
        int max = 1_000_000;
        int theoreticalBound = BinarySearchGuesser.calculateTheoreticalMax(min, max); // 20
        Random rng = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int target = rng.nextInt(max) + 1;
            GameEngine engine = new GameEngine(min, max, target);
            BinarySearchGuesser ai = new BinarySearchGuesser(min, max);

            while (true) {
                int guess = ai.nextGuess();
                Feedback feedback = engine.evaluate(guess);
                if (feedback == Feedback.CORRECT) {
                    if (engine.getGuessCount() > theoreticalBound) {
                        throw new AssertionError(String.format(
                                "Target %d exceeded bound 20 in range [1, 1000000]! Took %d",
                                target, engine.getGuessCount()));
                    }
                    break;
                }
                ai.provideFeedback(feedback, guess);
            }
        }
        System.out.println("  [PASS] testLargeScaleRandomTargets (1,000 random targets in [1, 1M] solved in <= 20 queries)");
    }

    private static void testEdgeTargets() {
        int min = 50;
        int max = 50;
        GameEngine engine = new GameEngine(min, max, 50);
        BinarySearchGuesser ai = new BinarySearchGuesser(min, max);
        int guess = ai.nextGuess();
        Feedback feedback = engine.evaluate(guess);
        if (feedback != Feedback.CORRECT || engine.getGuessCount() != 1) {
            throw new AssertionError("Single element search failed");
        }
        System.out.println("  [PASS] testEdgeTargets (Single element interval handled)");
    }
}
