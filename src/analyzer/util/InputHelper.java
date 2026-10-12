package analyzer.util;

import java.util.Scanner;

/**
 * Central place for ALL keyboard input so that every menu in the
 * application validates user input in the same way.
 */
public final class InputHelper {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputHelper() { }

    /** Reads one raw line; exits cleanly if the input stream is closed. */
    private static String nextLine() {
        if (!SCANNER.hasNextLine()) {
            System.out.println("\nInput closed. Exiting program.");
            System.exit(0);
        }
        return SCANNER.nextLine();
    }

    /** Keeps asking until the user types a valid whole number. */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a whole number.");
            }
        }
    }

    /** Keeps asking until the user types a whole number within [min, max]. */
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("  [!] Please enter a number between " + min + " and " + max + ".");
        }
    }

    /** Keeps asking until the user types a non-empty single word. */
    public static String readName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("  [!] Input cannot be empty.");
            } else if (line.contains(" ")) {
                System.out.println("  [!] Please enter a single word (no spaces), e.g. A or CityX.");
            } else {
                return line.toUpperCase();
            }
        }
    }

    /** Yes / No question. */
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = nextLine().trim().toLowerCase();
            if (line.equals("y") || line.equals("yes")) {
                return true;
            }
            if (line.equals("n") || line.equals("no")) {
                return false;
            }
            System.out.println("  [!] Please answer y or n.");
        }
    }

    /** Waits for ENTER so the user can read the output before the menu repaints. */
    public static void pause() {
        System.out.print("\nPress ENTER to continue...");
        nextLine();
    }
}
