package org.example;

// Демонстрация работы классов
public class Main {
    public static void main(String[] args) {
        SmartForm smartForm = new SmartForm();

        // Ввод текста и пароля
        smartForm.inputText();
        smartForm.inputPassword();

        // Отправка данных
        smartForm.submit();

        // Отображение сохраненного пароля
        smartForm.displayPassword();
    }
}
