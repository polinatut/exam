package org.example;

public class Cubes {
    public static void main(String[] args) {
        // Выводим кубы первых десяти положительных чисел
        for (int i = 1; i <= 10; i++) {
            int cube = i * i * i; // Вычисление куба числа
            System.out.println("Куб числа " + i + " равен " + cube);
        }
    }
}