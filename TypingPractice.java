import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TypingPractice {

    public static void main(String[] args) throws IOException {
        List<String> sentences = Files.readAllLines(Paths.get("sentences.txt"));
        if (sentences.isEmpty()) {
            System.out.println("The sentences file is empty.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean continuePractice = true;
        while (continuePractice) {
            System.out.println("Here are the sentences:");
            for (String sentence : sentences) {
                System.out.println(sentence);
            }

            int randomIndex = random.nextInt(sentences.size());
            String selectedSentence = sentences.get(randomIndex);
            System.out.println("\nType the following sentence: ");
            System.out.println("\u001B[34m" + selectedSentence + "\u001B[0m"); 

            System.out.println("Start typing:");
            long startTime = System.currentTimeMillis();
            String userInput = scanner.nextLine();
            long endTime = System.currentTimeMillis();

            double timeTaken = (endTime - startTime) / 1000.0;

            int errors = calculateErrors(selectedSentence, userInput);
            double errorPercentage = ((double) errors / selectedSentence.length()) * 100;

            System.out.println("You typed: " + highlightErrors(selectedSentence, userInput));
            System.out.println("Errors: " + errors + " (" + String.format("%.2f", errorPercentage) + "%)");
            System.out.println("Time taken: " + String.format("%.2f", timeTaken) + " seconds");

            System.out.println("\nDo you want to try again? (yes/no):");
            String response = scanner.nextLine();
            continuePractice = response.equalsIgnoreCase("yes");
        }

        scanner.close();
    }

    private static int calculateErrors(String correct, String typed) {
        int length = Math.min(correct.length(), typed.length());
        int errors = Math.abs(correct.length() - typed.length());
        for (int i = 0; i < length; i++) {
            if (correct.charAt(i) != typed.charAt(i)) {
                errors++;
            }
        }
        return errors;
    }

    private static String highlightErrors(String correct, String typed) {
        StringBuilder highlighted = new StringBuilder();
        int length = Math.min(correct.length(), typed.length());
        for (int i = 0; i < length; i++) {
            if (correct.charAt(i) == typed.charAt(i)) {
                highlighted.append(correct.charAt(i));
            } else {
                highlighted.append("\u001B[31m").append(typed.charAt(i)).append("\u001B[0m");
            }
        }
        
        if (typed.length() > correct.length()) {
            highlighted.append("\u001B[31m").append(typed.substring(correct.length())).append("\u001B[0m");
        }
        return highlighted.toString();
    }
}
