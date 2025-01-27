package org.example;

import java.util.Scanner;

// Базовый класс Form
class Form {
    protected String textInput;

    // Метод для ввода текста
    public void inputText() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите текст: ");
        textInput = scanner.nextLine();
    }

    // Метод для отправки текста
    public void submit() {
        System.out.println("Текст отправлен: " + textInput);
    }
}