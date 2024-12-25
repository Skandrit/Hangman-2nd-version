package viselitsa;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    private final String wordPickedStr;
    private int attempts;
    private final List<Character> lettersGuessed;
    private static final Random rand = new Random();

    public static void main(String[] args) {
        List<String> words = WordsLoadingUtil.loadWords("./slovarik.txt");
        if (words.isEmpty()) {
            System.out.println("Ошибка - не удалось загрузить слова.");
            return;
        }
        GameConfig config = new GameConfig();
        HangmanGame game = new HangmanGame(words, config);
        game.startGame();
    }

    private HangmanGame(List<String> words, GameConfig config) {
        this.wordPickedStr = words.get(rand.nextInt(words.size()));
        this.attempts = config.getMaxAttempts();
        this.lettersGuessed = new ArrayList<>();
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (attempts > 0) {
            displayGame();
            System.out.print("Введите букву - ");
            String guess = scanner.nextLine().toLowerCase();

            if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                System.out.println("Введите одну букву.");
                continue;
            }

            char guessedChar = guess.charAt(0);
            if (lettersGuessed.contains(guessedChar)) {
                System.out.println("Вы уже вводили эту букву.");
                continue;
            }

            lettersGuessed.add(guessedChar);

            if (wordPickedStr.contains(String.valueOf(guessedChar))) {
                System.out.println("Правильно");
            } else {
                System.out.println("Неправильная буква!");
                attempts--;
            }

            if (isWordGuessed()) {
                System.out.println("Вы выиграли!");
                System.out.println("Вы угадали слово: " + wordPickedStr);
                break;
            }
        }

        if (attempts == 0) {
            System.out.println("Вы проиграли.");
            System.out.println("Загаданное слово - " + wordPickedStr);
        }

        scanner.close();
    }

    private void displayGame() {
        System.out.println("Попытки - " + attempts);
        System.out.print("Слово - ");
        for (int i = 0; i < wordPickedStr.length(); i++) {
            char c = wordPickedStr.charAt(i);
            if (lettersGuessed.contains(c)) {
                System.out.print(c + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    private boolean isWordGuessed() {
        for (char c : wordPickedStr.toCharArray()) {
            if (!lettersGuessed.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
