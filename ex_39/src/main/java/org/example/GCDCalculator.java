package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class GCDCalculator {

    // Рекурсивный метод для нахождения НОД
    public static int gcdRecursive(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }

    // Итеративный метод для нахождения НОД
    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Метод для сохранения результатов в базу данных H2
    public static void saveToDatabase(int a, int b, int gcd, String method) {
        String url = "jdbc:h2:./test"; // Путь к базе данных H2
        String user = "sa"; // Логин
        String password = ""; // Пароль

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Создание таблицы, если она еще не существует
            String createTableSQL = "CREATE TABLE IF NOT EXISTS gcd_results ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "number1 INT,"
                    + "number2 INT,"
                    + "gcd INT,"
                    + "method VARCHAR(50)"
                    + ");";
            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableSQL);
            }

            // Вставка данных
            String insertSQL = "INSERT INTO gcd_results (number1, number2, gcd, method) VALUES (?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, a);
                preparedStatement.setInt(2, b);
                preparedStatement.setInt(3, gcd);
                preparedStatement.setString(4, method);
                preparedStatement.executeUpdate();
            }

            System.out.println("Результаты сохранены в базу данных.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int number1 = 56; // Пример первого числа
        int number2 = 98; // Пример второго числа

        // Вычисление НОД рекурсивным методом
        int gcdRec = gcdRecursive(number1, number2);
        System.out.println("НОД (рекурсивно): " + gcdRec);
        saveToDatabase(number1, number2, gcdRec, "Recursive");

        // Вычисление НОД итеративным методом
        int gcdIter = gcdIterative(number1, number2);
        System.out.println("НОД (итеративно): " + gcdIter);
        saveToDatabase(number1, number2, gcdIter, "Iterative");
    }
}
