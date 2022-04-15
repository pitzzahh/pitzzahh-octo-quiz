package com.pitzzahh;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Scanner;
import com.pitzzahh.exception.*;
import com.pitzzahh.process.Process;
import java.io.File;


public class Quiz {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private static final ArrayList<String> questions = new ArrayList<>();
    private static final ArrayList<String> choices = new ArrayList<>();
    private static ArrayList<Character> answers = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        final Scanner scanner = new Scanner(System.in);

        if (mainInterface(scanner)) {
            boolean running = verify();
            while (running) {
                runQuiz(scanner);
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
                            if (containsSpecialCharacter(response)) throw new SpecialCharacterResponseException();
                            else if (isNumber(response)) throw new NumberResponseException();
                            else if (response.isEmpty()) throw new BlankResponseException();
                            else throw new InvalidLetterResponseException(RED + "Y or N only" + RESET);
                        }
                    } catch (RuntimeException runtimeException) {
                        System.out.println(RED  + runtimeException.getMessage() + RESET);
                    }
                }
            }
        }

        System.out.println(CYAN + "THANK " + YELLOW + "YOU" + GREEN + " FOR "  + BLUE + "USING " + PURPLE + "MY " + RED + "PROGRAM");
    }
    /**
     * Method that start the quiz.
     * @param scanner the {@code Scanner} object needed for user input.
     */
    private static void runQuiz(Scanner scanner) {
        byte correctAnswers = 0;
        try {
            for (int i = 0; i < Quiz.questions.size(); i++) {
                while (true) {
                    try {
                        System.out.println(BLUE + "QUESTION NUMBER: " + PURPLE + (i + 1) + RESET);
                        System.out.println(YELLOW + Quiz.questions.get(i));
                        System.out.println(Quiz.choices.get(i));
                        System.out.print(": " + RESET);
                        String choice = scanner.nextLine().trim();
                        if (choice.equalsIgnoreCase("A") || choice.equalsIgnoreCase("B") || choice.equalsIgnoreCase("C")) {
                            if (choice.equalsIgnoreCase(String.valueOf(Quiz.answers.get(i)))) correctAnswers++;
                            break;
                        }
                        else {
                            if (containsSpecialCharacter(choice)) throw new SpecialCharacterResponseException();
                            else if (isNumber(choice)) throw new NumberResponseException();
                            else if (choice.isEmpty()) throw new BlankResponseException();
                            else if (choice.length() != 1) throw new MultipleCharactersInputException();
                            else throw new InvalidLetterResponseException();
                        }
                    } catch (RuntimeException runtimeException) {
                        System.out.println(RED + runtimeException.getMessage() + RESET);
                    }
                }
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println(RED + nullPointerException.getMessage() + RESET);
        } catch (Exception exception) {
            System.out.println(RED + exception + RESET);
        }
        System.out.println(viewScore(correctAnswers));
    }
    /**
     * Method that verifies the questions, choices, and answers from the file.
     * Outputs report which files are missing.
     * @return {@code true} if the files are valid text files.
     * @throws IOException if error occurred when accessing the files.
     */
    private static boolean verify() throws IOException {
        final Path filesPath = Paths.get("src\\main\\resources"); // directory where the files are stored.
        try {
            File questionsFile = new File(filesPath + "\\questions.txt");
            File choicesFile = new File(filesPath + "\\choices.txt");
            File answersFile = new File(filesPath + "\\answers.txt");
            if (questionsFile.exists() && questionsFile.isFile()) {
                if (choicesFile.exists() && choicesFile.isFile()) {
                    if (answersFile.exists() && answersFile.isFile()) {
                        answers = Process.decrypt(answersFile);
                        importQuestions(questionsFile, choicesFile, answersFile);
                        return true;
                    } else throw new AnswersNotFoundException();
                } else {
                    if (!choicesFile.exists() && !choicesFile.isFile()) System.out.println(RED + new ChoicesNotFoundException().getMessage() + RESET);
                    if (!answersFile.exists() && !answersFile.isFile()) System.out.println(RED + new AnswersNotFoundException().getMessage() + RESET);
                }
            } else {
                if (!questionsFile.exists() && !questionsFile.isFile()) System.out.println(RED + new QuestionsNotFoundException().getMessage() + RESET);
                if (!choicesFile.exists() && !choicesFile.isFile()) System.out.println(RED + new ChoicesNotFoundException().getMessage() + RESET);
                if (!answersFile.exists() && !answersFile.isFile()) System.out.println(RED + new AnswersNotFoundException().getMessage() + RESET);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(RED + fileNotFoundException.getMessage() + RESET);
        }
        return false;
    }
    /**
     * Method that imports all the questions, choices, and answers from the file.
     * @param questionsFile the file that contains the questions.
     * @param choicesFile the file that contains the choices.
     * @param answersFile the file that contains the answers.
     * @throws QuestionsNotFoundException if the questions file is missing.
     */
    private static void importQuestions(File questionsFile, File choicesFile, File answersFile) throws QuestionsNotFoundException {
        try {
            Scanner questionsScanner = new Scanner(questionsFile);
            Scanner choicesScanner = new Scanner(choicesFile);
            Scanner answersScanner = new Scanner(answersFile);
            while (questionsScanner.hasNextLine()) {
                Quiz.questions.add(questionsScanner.nextLine());
                Quiz.choices.add(choicesScanner.nextLine());
                Quiz.answers.add(answersScanner.nextLine().charAt(0));
            }
            questionsScanner.close();
            choicesScanner.close();
            answersScanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new QuestionsNotFoundException();
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
        Pattern my_pattern = Pattern.compile("[^a-z\\d]", Pattern.CASE_INSENSITIVE);
        Matcher my_match = my_pattern.matcher(stringInput);
        return my_match.find();
    }
    /**
     * Method that output the score on the test.
     * If the score accumulated is a failing score, it will be colored as red, else blue if it is a passing score.
     * @param numberOfCorrectAnswers the number of correct answers answered from the questions.
     * @return a {@code String} representation of the score.
     */
    private static String viewScore(byte numberOfCorrectAnswers) {
        try {
            if (numberOfCorrectAnswers <= 5) {
                return GREEN + "SCORE: " + RED + numberOfCorrectAnswers + RESET + " / " + BLUE + Quiz.questions.size();
            }
            return GREEN + "SCORE: " + BLUE + numberOfCorrectAnswers + RESET + " / " + BLUE + Quiz.questions.size();
        } catch (Exception exception) {
            return RED + exception.getMessage() + RESET;
        }
    }
    private static boolean mainInterface(Scanner scanner) {
        while (true) {
            try {
                System.out.println(GREEN + "###########################");
                System.out.println(CYAN + "|" + PURPLE + "WELCOME TO MY SIMPLE QUIZ" + CYAN + "|");
                System.out.println(GREEN + "###########################");
                System.out.println(PURPLE + ": " + YELLOW + "1" + PURPLE + " : " + BLUE + "START");
                System.out.println(PURPLE + ": " + YELLOW + "2" + PURPLE + " : " + RED + "EXIT");
                System.out.print(GREEN + ": " + RESET);
                String choice = scanner.nextLine().toUpperCase().trim();
                if (choice.equals("1") || choice.equals("2")) {
                    return choice.equals("1");
                }
                else {
                    if (containsSpecialCharacter(choice)) throw new SpecialCharacterResponseException();
                    else if (choice.isEmpty()) throw new BlankResponseException();
                    else if (!isNumber(choice) && choice.length() != 1) throw new MultipleCharactersInputException("String Response Not Allowed");
                    else if (!isNumber(choice)) throw new InvalidLetterResponseException("Character Response Not Allowed");
                    else throw new InvalidNumberResponseException("1 or 2 Response Only");
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(RED + runtimeException.getMessage() + RESET);
            }
        }
    }
}
