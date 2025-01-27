package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрос ввода натурального числа
        System.out.print("Введите натуральное число: ");
        int n = scanner.nextInt();

        // Проверка на натуральное число
        if (n <= 0) {
            System.out.println("Введенное число не является натуральным.");
            return;
        }

        System.out.print("Цифры в числе: ");

        // Преобразование числа в строку и вывод каждой цифры через запятую
        String number = Integer.toString(n);
        for (int i = 0; i < number.length(); i++) {
            System.out.print(number.charAt(i));
            if (i < number.length() - 1) {
                System.out.print(", ");
            }
        }

        scanner.close();
    }
}

