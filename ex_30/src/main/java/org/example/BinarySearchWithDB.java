package org.example;

import java.sql.*;
import java.util.*;

public class BinarySearchWithDB {

    private static final String DB_URL = "jdbc:h2:./test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод параметров
        System.out.print("Введите количество элементов: ");
        int size = scanner.nextInt();

        System.out.print("Введите минимальное значение диапазона: ");
        int min = scanner.nextInt();

        System.out.print("Введите максимальное значение диапазона: ");
        int max = scanner.nextInt();

        // Генерация списка случайных чисел
        List<Integer> numbers = generateRandomNumbers(size, min, max);
        Collections.sort(numbers);
        System.out.println("Сгенерированный список: " + numbers);

        // Сохранение списка в базу данных
        saveToDatabase(numbers);

        // Поиск числа
        System.out.print("Введите число для поиска: ");
        int target = scanner.nextInt();

        boolean found = binarySearch(numbers, target);
        System.out.println("Число " + target + (found ? " найдено." : " не найдено."));

        // Вывод данных из базы данных
        System.out.println("Данные из базы данных:");
        retrieveFromDatabase();

        scanner.close();
    }

    private static List<Integer> generateRandomNumbers(int size, int min, int max) {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            numbers.add(random.nextInt(max - min + 1) + min);
        }

        return numbers;
    }

    private static boolean binarySearch(List<Integer> numbers, int target) {
        int left = 0;
        int right = numbers.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (numbers.get(mid) == target) {
                return true;
            }

            if (numbers.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    private static void saveToDatabase(List<Integer> numbers) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Создание таблицы, если она не существует
            statement.execute("CREATE TABLE IF NOT EXISTS Numbers (id INT AUTO_INCREMENT PRIMARY KEY, number INT)");

            // Очистка таблицы
            statement.execute("TRUNCATE TABLE Numbers");

            // Вставка данных
            String sql = "INSERT INTO Numbers (number) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int number : numbers) {
                    preparedStatement.setInt(1, number);
                    preparedStatement.executeUpdate();
                }
            }

            System.out.println("Данные сохранены в базу данных.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void retrieveFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Запрос данных из таблицы
            ResultSet resultSet = statement.executeQuery("SELECT number FROM Numbers ORDER BY number");

            // Вывод данных
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("number"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
