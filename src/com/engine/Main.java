package com.engine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   Algorithmic Number Guessing & Search Engine    ");
        System.out.println("==================================================");

        int min = 1;
        int max = 100;
        int maxTheoretical = BinarySearchGuesser.calculateTheoreticalMax(min, max);

        System.out.printf("Range: [%d, %d] | Information Entropy: %d guesses max (ceil(log2(N)))\n\n",
                min, max, maxTheoretical);

        System.out.println("1. Human Player Mode");
        System.out.println("2. Minimax AI Auto-Solve Demonstration");
        System.out.print("\nSelect mode (1 or 2): ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.hasNextLine() ? scanner.nextLine().trim() : "2";

        if ("1".equals(choice)) {
            runHumanMode(min, max, scanner);
        } else {
            runAIMode(min, max);
        }
    }

    private static void runHumanMode(int min, int max, Scanner scanner) {
        GameEngine engine = new GameEngine(min, max);
        System.out.printf("\nSecret number generated between %d and %d. Start guessing!\n", min, max);

        while (true) {
            System.out.print("Enter guess: ");
            if (!scanner.hasNextInt()) break;
            int guess = scanner.nextInt();
            Feedback feedback = engine.evaluate(guess);

            if (feedback == Feedback.CORRECT) {
                System.out.printf("Bingo! Solved in %d guesses (Theoretical bound: %d)\n",
                        engine.getGuessCount(), BinarySearchGuesser.calculateTheoreticalMax(min, max));
                break;
            } else if (feedback == Feedback.TOO_LOW) {
                System.out.println("-> Higher!");
            } else {
                System.out.println("-> Lower!");
            }
        }
    }

    private static void runAIMode(int min, int max) {
        GameEngine engine = new GameEngine(min, max);
        BinarySearchGuesser ai = new BinarySearchGuesser(min, max);
        int theoreticalMax = BinarySearchGuesser.calculateTheoreticalMax(min, max);

        System.out.printf("\n[AI Simulation] Target secretly set to %d.\n", engine.getSecretNumber());

        int step = 1;
        while (true) {
            int guess = ai.nextGuess();
            Feedback feedback = engine.evaluate(guess);
            System.out.printf("  Step %d: AI queries %d -> %s\n", step++, guess, feedback);

            if (feedback == Feedback.CORRECT) {
                System.out.printf("\nAI reached target in %d guesses. Provably optimal <= %d!\n",
                        engine.getGuessCount(), theoreticalMax);
                break;
            }
            ai.provideFeedback(feedback, guess);
        }
    }
}
