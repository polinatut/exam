package org.example;

import java.util.Scanner;
class SmartForm extends Form {
    private String password;

    // Метод для ввода пароля
    public void inputPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пароль: ");
        password = scanner.nextLine();
    }

    // Метод для проверки и отображения пароля
    public void displayPassword() {
        System.out.println("Сохранённый пароль: " + password);
    }

    // Переопределение метода submit для отображения текста и пароля
    @Override
    public void submit() {
        System.out.println("Текст отправлен: " + textInput);
        System.out.println("Пароль отправлен: " + password);
    }
}

