package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.Scanner;

public class StringDatabaseApp {

    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            createTables(connection);

            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1. Вывести все таблицы из MySQL.");
                System.out.println("2. Создать таблицу в MySQL.");
                System.out.println("3. Ввести две строки с клавиатуры и сохранить в MySQL.");
                System.out.println("4. Подсчитать длину строк и сохранить в MySQL.");
                System.out.println("5. Объединить строки и сохранить в MySQL.");
                System.out.println("6. Сравнить строки и сохранить результат в MySQL.");
                System.out.println("7. Сохранить данные из MySQL в Excel.");
                System.out.println("0. Выйти.");

                System.out.print("Введите номер действия: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1 -> showTables(connection);
                    case 2 -> createCustomTable(connection, scanner);
                    case 3 -> insertStrings(connection, scanner);
                    case 4 -> calculateStringLengths(connection);
                    case 5 -> concatenateStrings(connection);
                    case 6 -> compareStrings(connection);
                    case 7 -> saveToExcel(connection);
                    case 0 -> {
                        System.out.println("Выход из программы.");
                        return;
                    }
                    default -> System.out.println("Неверный выбор, попробуйте снова.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Strings " +
                "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                "string1 VARCHAR(255), " +
                "string2 VARCHAR(255), " +
                "length1 INT, " +
                "length2 INT, " +
                "concatenated VARCHAR(510), " +
                "comparison_result VARCHAR(50));";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Таблица Strings успешно создана или уже существует.");
        }
    }

    private static void showTables(Connection connection) throws SQLException {
        String showTablesSQL = "SHOW TABLES";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(showTablesSQL)) {

            System.out.println("Список таблиц:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        }
    }

    private static void createCustomTable(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите имя новой таблицы: ");
        String tableName = scanner.nextLine();

        String createTableSQL = "CREATE TABLE " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY, data VARCHAR(255));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Таблица " + tableName + " успешно создана.");
        }
    }

    private static void insertStrings(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите первую строку (не менее 50 символов): ");
        String string1 = scanner.nextLine();
        System.out.print("Введите вторую строку (не менее 50 символов): ");
        String string2 = scanner.nextLine();

        if (string1.length() < 50 || string2.length() < 50) {
            System.out.println("Обе строки должны быть не менее 50 символов.");
            return;
        }

        String insertSQL = "INSERT INTO Strings (string1, string2) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, string1);
            preparedStatement.setString(2, string2);
            preparedStatement.executeUpdate();
            System.out.println("Строки успешно сохранены в базе данных.");
        }
    }

    private static void calculateStringLengths(Connection connection) throws SQLException {
        String selectSQL = "SELECT id, string1, string2 FROM Strings WHERE length1 IS NULL AND length2 IS NULL;";
        String updateSQL = "UPDATE Strings SET length1 = ?, length2 = ? WHERE id = ?;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String string1 = resultSet.getString("string1");
                String string2 = resultSet.getString("string2");

                preparedStatement.setInt(1, string1.length());
                preparedStatement.setInt(2, string2.length());
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();

                System.out.printf("Длины строк обновлены: [%d, %d]%n", string1.length(), string2.length());
            }
        }
    }

    private static void concatenateStrings(Connection connection) throws SQLException {
        String selectSQL = "SELECT id, string1, string2 FROM Strings WHERE concatenated IS NULL;";
        String updateSQL = "UPDATE Strings SET concatenated = ? WHERE id = ?;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String string1 = resultSet.getString("string1");
                String string2 = resultSet.getString("string2");

                String concatenated = string1 + string2;
                preparedStatement.setString(1, concatenated);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                System.out.printf("Объединение строк успешно: %s%n", concatenated);
            }
        }
    }

    private static void compareStrings(Connection connection) throws SQLException {
        String selectSQL = "SELECT id, string1, string2 FROM Strings WHERE comparison_result IS NULL;";
        String updateSQL = "UPDATE Strings SET comparison_result = ? WHERE id = ?;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String string1 = resultSet.getString("string1");
                String string2 = resultSet.getString("string2");

                String comparisonResult = string1.equals(string2) ? "Equal" : "Not Equal";
                preparedStatement.setString(1, comparisonResult);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                System.out.printf("Результат сравнения строк: %s%n", comparisonResult);
            }
        }
    }

    private static void saveToExcel(Connection connection) throws Exception {
        String selectSQL = "SELECT * FROM Strings;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL);
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Strings");
            Row headerRow = sheet.createRow(0);

            String[] headers = {"ID", "String1", "String2", "Length1", "Length2", "Concatenated", "Comparison Result"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIndex = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(resultSet.getInt("id"));
                row.createCell(1).setCellValue(resultSet.getString("string1"));
                row.createCell(2).setCellValue(resultSet.getString("string2"));
                row.createCell(3).setCellValue(resultSet.getInt("length1"));
                row.createCell(4).setCellValue(resultSet.getInt("length2"));
                row.createCell(5).setCellValue(resultSet.getString("concatenated"));
                row.createCell(6).setCellValue(resultSet.getString("comparison_result"));
            }

            try (FileOutputStream fos = new FileOutputStream("StringsData.xlsx")) {
                workbook.write(fos);
            }

            System.out.println("Данные успешно сохранены в StringsData.xlsx.");
        }
    }
}

