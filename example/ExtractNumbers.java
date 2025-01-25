package org.example;

import java.util.ArrayList;

public class ExtractNumbers {
    public static void main(String[] args) {
        // Исходная строка
        String input = "I have 3 cats, 4 dogs, and 1 turtle";

        // Создаем список для хранения чисел
        ArrayList<Integer> numbers = new ArrayList<>();

        // Разбиваем строку на символы и проходим по каждому из них
        for (char ch : input.toCharArray()) {
            // Проверяем, является ли символ цифрой
            if (Character.isDigit(ch)) {
                // Преобразуем символ в число и добавляем в список
                numbers.add(Character.getNumericValue(ch));
            }
        }

        // Преобразуем список в массив
        int[] resultArray = numbers.stream().mapToInt(Integer::intValue).toArray();

        // Выводим результат
        System.out.print("Извлеченные числа: ");
        for (int num : resultArray) {
            System.out.print(num + " ");
        }
    }
}
