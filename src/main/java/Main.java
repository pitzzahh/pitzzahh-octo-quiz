
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) throws FileNotFoundException {

        final Scanner scanner = new Scanner(System.in);
        final ArrayList<String> questions = new ArrayList<>();
        final ArrayList<String> choices = new ArrayList<>();
        final ArrayList<String> answers = new ArrayList<>();

        loadQuestions(questions, choices, answers);

        boolean running = true;

        while (running) {
            runQuiz(scanner, questions, choices, answers);
            while (true) {
                try {
                    System.out.print(PURPLE + "PLAY AGAIN ? " + GREEN + "(" + BLUE + " Y " + YELLOW + ":" + RED + " N " + GREEN + "): ");
                    String response = scanner.nextLine().toUpperCase().trim();
                    if (response.equals("Y") || response.equals("N")) {
                        if (response.equals("Y")) break;
                        else running = false;
                        break;
                    }
                    else {
                        if (containsSpecialCharacter(response)) throw new SpecialCharacterAnswerException();
                        else if (isNumber(response)) throw new NumberAnswerException();
                        else if (response.isEmpty()) throw new BlankAnswerException();
                        else System.out.println(RED + "Y or N only" + RESET);
                    }
                } catch (RuntimeException runtimeException) {
                    System.out.println(RED  + runtimeException.getMessage() + RESET);
                }
            }
        }
        System.out.println("THANK YOU FOR USING MY PROGRAM");
    }

    /**
     * Method that start the quiz.
     * @param scanner the {@code Scanner} object needed for user input.
     * @param questions the {@code ArrayList<String>} that contains the questions.
     * @param choices the {@code ArrayList<String>} that contains the choices.
     * @param answers the {@code ArrayList<String>} that contains the answers.
     */
    private static void runQuiz(Scanner scanner, ArrayList<String> questions, ArrayList<String> choices, ArrayList<String> answers) {
        System.out.println(GREEN + "###########################");
        System.out.println(CYAN + "|" + PURPLE + "WELCOME TO MY SIMPLE QUIZ" + CYAN + "|");
        System.out.println(GREEN + "###########################");
        byte correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            while (true) {
                try {
                    System.out.println(BLUE + "QUESTION NUMBER: " + RED + (i + 1) + RESET);
                    System.out.println(YELLOW + questions.get(i));
                    System.out.println(choices.get(i));
                    System.out.print(": " + RESET);
                    String choice = scanner.nextLine().trim();
                    if (choice.equalsIgnoreCase("A") || choice.equalsIgnoreCase("B") || choice.equalsIgnoreCase("C")) {
                        if (choice.equalsIgnoreCase(answers.get(i))) correctAnswers++;
                        break;
                    }
                    else {
                        if (containsSpecialCharacter(choice)) throw new SpecialCharacterAnswerException();
                        else if (isNumber(choice)) throw new NumberAnswerException();
                        else if (choice.isEmpty()) throw new BlankAnswerException();
                        else System.out.println(RED + "A B C only" + RESET);
                    }
                } catch (Exception exception) {
                    System.out.println(RED + exception.getMessage() + RESET);
                }
            }
        }
        System.out.println(viewScore(questions, correctAnswers));
    }
    /**
     * Method that contains all the questions.
     * @param questions the {@code ArrayList<String>} that contains the questions.
     * @param choices the {@code ArrayList<String>} that contains the choices.
     * @param answers the {@code ArrayList<String>} that contains the answers.
     */
    private static void loadQuestions(ArrayList<String> questions, ArrayList<String> choices, ArrayList<String> answers) throws FileNotFoundException {

        Scanner questionsScanner = new Scanner(new File("src\\main\\resources\\questions.txt"));
        Scanner choicesScanner = new Scanner(new File("src\\main\\resources\\choices.txt"));
        Scanner answersScanner = new Scanner(new File("src\\main\\resources\\answers.txt"));
        while (questionsScanner.hasNextLine()) {
            questions.add(questionsScanner.nextLine());
            choices.add(choicesScanner.nextLine());
            answers.add(answersScanner.nextLine());
        }
        questionsScanner.close();
        choicesScanner.close();
        answersScanner.close();
    }
    /**
     * inner static class used for exception handling on blank answers.
     * This class extends the {@code RuntimeException}
     */
    static class BlankAnswerException extends RuntimeException {
        public BlankAnswerException() {
            super("Blank Answer Not Allowed");
        }
    }
    /**
     * inner static class used for exception handling on number answers.
     * This class extends the {@code RuntimeException}
     */
    static class NumberAnswerException extends RuntimeException {
        public NumberAnswerException() {
            super("Number Answer Not Allowed");
        }
    }
    /**
     * inner static class used for exception handling on special character answers.
     * This class extends the {@code RuntimeException}
     */
    static class SpecialCharacterAnswerException extends RuntimeException {
        public SpecialCharacterAnswerException() {
            super("Special Character Answer Not Allowed");
        }
    }

    /**
     * Method that returns {@code true} or {@code false} based on the {@code String} passed in.
     * @param numberString the {@code String} that might contain all numbers
     * @return {@code true} if the {@code String} can be parsed to an Integer.
     */
    private static boolean isNumber(String numberString) {
        try {
            Integer.parseInt(numberString);
            return true;
        } catch (Exception ignored) {}
        return false;
    }

    /**
     * Method that returns {@code true} or {@code false} based on the {@code String} passed in.
     * @param stringInput the {@code String} that might contain any special character.
     * @return {@code true} if the {@code String} passed in contains any special character.
     */
    private static boolean containsSpecialCharacter(String stringInput) {
        Pattern my_pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher my_match = my_pattern.matcher(stringInput);
        return my_match.find();
    }
    private static String viewScore(ArrayList<String> questions, byte numberOfCorrectAnswers) {
        if (numberOfCorrectAnswers <= 5) {
            return GREEN + "SCORE: " + RED + numberOfCorrectAnswers + RESET + " / " + BLUE + questions.size();
        }
        return GREEN + "SCORE: " + BLUE + numberOfCorrectAnswers + RESET + " / " + BLUE + questions.size();
    }
}
