package org.example;

import java.sql.*;
import java.util.Scanner;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MathOperations {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            createResultsTable(connection);

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                displayMenu();
                choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> showTables(connection);
                    case 2 -> createTable(connection, scanner);
                    case 3 -> performOperation(connection, scanner, "сложение", (a, b) -> a + b);
                    case 4 -> performOperation(connection, scanner, "вычитание", (a, b) -> a - b);
                    case 5 -> performOperation(connection, scanner, "умножение", (a, b) -> a * b);
                    case 6 -> performOperation(connection, scanner, "деление", (a, b) -> a / b);
                    case 7 -> performOperation(connection, scanner, "деление по модулю", (a, b) -> a % b);
                    case 8 -> performUnaryOperation(connection, scanner, "модуль", Math::abs);
                    case 9 -> performPowerOperation(connection, scanner);
                    case 10 -> exportToExcel(connection);
                    case 0 -> System.out.println("Выход из программы.");
                    default -> System.out.println("Некорректный выбор, попробуйте снова.");
                }
            } while (choice != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1. Вывести все таблицы из MySQL.");
        System.out.println("2. Создать таблицу в MySQL.");
        System.out.println("3. Сложение чисел.");
        System.out.println("4. Вычитание чисел.");
        System.out.println("5. Умножение чисел.");
        System.out.println("6. Деление чисел.");
        System.out.println("7. Деление чисел по модулю.");
        System.out.println("8. Модуль числа.");
        System.out.println("9. Возведение числа в степень.");
        System.out.println("10. Сохранить данные в Excel.");
        System.out.println("0. Выход.");
        System.out.print("Ваш выбор: ");
    }

    private static void createResultsTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Results (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "operation VARCHAR(255), " +
                "result DOUBLE)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void showTables(Connection connection) throws SQLException {
        String sql = "SHOW TABLES";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Список таблиц:");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        }
    }

    private static void createTable(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите имя таблицы: ");
        String tableName = scanner.next();
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INT PRIMARY KEY, name VARCHAR(255))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица создана: " + tableName);
        }
    }

    private static void performOperation(Connection connection, Scanner scanner, String operation, BinaryOperation op) throws SQLException {
        System.out.print("Введите первое число: ");
        double num1 = scanner.nextDouble();
        System.out.print("Введите второе число: ");
        double num2 = scanner.nextDouble();

        double result = op.apply(num1, num2);
        saveResult(connection, operation, result);

        System.out.println("Результат " + operation + ": " + result);
    }

    private static void performUnaryOperation(Connection connection, Scanner scanner, String operation, UnaryOperation op) throws SQLException {
        System.out.print("Введите число: ");
        double num = scanner.nextDouble();

        double result = op.apply(num);
        saveResult(connection, operation, result);

        System.out.println("Результат " + operation + ": " + result);
    }

    private static void performPowerOperation(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите основание: ");
        double base = scanner.nextDouble();
        System.out.print("Введите показатель степени: ");
        double exponent = scanner.nextDouble();

        double result = Math.pow(base, exponent);
        saveResult(connection, "возведение в степень", result);

        System.out.println("Результат возведения в степень: " + result);
    }

    private static void saveResult(Connection connection, String operation, double result) throws SQLException {
        String sql = "INSERT INTO Results (operation, result) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, operation);
            pstmt.setDouble(2, result);
            pstmt.executeUpdate();
        }
    }

    private static void exportToExcel(Connection connection) {
        String sql = "SELECT * FROM Results";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Results");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Operation");
            headerRow.createCell(2).setCellValue("Result");

            int rowIndex = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getInt("id"));
                row.createCell(1).setCellValue(rs.getString("operation"));
                row.createCell(2).setCellValue(rs.getDouble("result"));
            }

            try (FileOutputStream fos = new FileOutputStream("Results.xlsx")) {
                workbook.write(fos);
                System.out.println("Данные успешно сохранены в файл Results.xlsx");
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface BinaryOperation {
        double apply(double a, double b);
    }

    @FunctionalInterface
    interface UnaryOperation {
        double apply(double a);
    }
}
