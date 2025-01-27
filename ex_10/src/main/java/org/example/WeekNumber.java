package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeekNumber {
    public static void main(String[] args) {
        // Создаем сканер для ввода даты
        Scanner scanner = new Scanner(System.in);

        // Просим пользователя ввести дату в формате ДД.ММ.ГГ
        System.out.println("Введите дату в формате ДД.ММ.ГГ: ");
        String inputDate = scanner.nextLine();

        try {
            // Парсим дату
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            LocalDate date = LocalDate.parse(inputDate, formatter);

            // Проверяем диапазон годов
            if (date.getYear() < 2020 || date.getYear() > 2022) {
                System.out.println("Дата должна быть в диапазоне с 2020 по 2022 годы.");
                return;
            }

            // Вычисляем номер недели
            LocalDate startOfYear = LocalDate.of(date.getYear(), 1, 1);
            int weekNumber = (int) Math.ceil((date.getDayOfYear() + startOfYear.getDayOfWeek().getValue() - 1) / 7.0);

            // Выводим номер недели
            System.out.println("Неделя " + weekNumber);
        } catch (Exception e) {
            System.out.println("Некорректный формат даты. Пожалуйста, введите дату в формате ДД.ММ.ГГ.");
        }
    }
}
