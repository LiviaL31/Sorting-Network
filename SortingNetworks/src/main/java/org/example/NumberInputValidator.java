package org.example;

public class NumberInputValidator {
    private int[] numbers;
    public static boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        String[] numbersStr = input.split(",");
        for (String numStr : numbersStr) {
            try {
                Integer.parseInt(numStr.trim());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
        public int countInputElements(String input) {
            // Împarte șirul de caractere în funcție de un delimitator, de exemplu, virgula
            String[] elements = input.split(",");

            // Returnează numărul de elemente din șirul rezultat
            return elements.length;
        }

}
