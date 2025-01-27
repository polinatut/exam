package org.example;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateToDayOfWeek {
    public static void main(String[] args) {
        // Создаем сканер для ввода даты
        Scanner scanner = new Scanner(System.in);

        // Просим пользователя ввести дату в формате ГГГГ-ММ-ДД
        System.out.println("Введите дату в формате ДД-ММ-ГГГГ: ");
        String inputDate = scanner.nextLine();

        try {
            LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            // Определяем день недели
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // Выводим день недели
            System.out.println("День недели для даты " + inputDate + " это " + dayOfWeek);
        } catch (Exception e) {
            System.out.println("Некорректный формат даты. Пожалуйста, введите дату в формате ДД-ММ-ГГГГ.");
        }
    }
}
