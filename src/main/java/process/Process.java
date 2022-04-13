package process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Process {
    private static final byte KEY = ((((10 * 3) + 4 ) / 4) * 2) - 26;

    /**
     * Decrypts the answers from the answers file.
     * @param fileToDecrypt the file of where the answers contained.
     * @return an {@code ArrayList<Character>} of {@code Characters} decrypted from the answers file.
     * @throws IOException if the file does not exist.
     */
    public static ArrayList<Character> decrypt(File fileToDecrypt) throws IOException {
        if (fileToDecrypt.isFile()) {
            Scanner fileScanner = new Scanner(fileToDecrypt);
            ArrayList<Character> encryptedList = new ArrayList<>();
            ArrayList<Character> decryptedList = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                encryptedList.add(fileScanner.nextLine().charAt(0));
            }
            for (char element : encryptedList) {
                element -= (KEY * (-10));
                decryptedList.add(element);
            }
            fileScanner.close();
            return decryptedList;
        }
        return new ArrayList<>();
    }
}
