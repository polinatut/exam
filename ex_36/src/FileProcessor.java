import java.io.*;
import java.util.Scanner;

public class FileProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Получение имени файла и операции от пользователя
            System.out.print("Введите имя файла (например, 1.txt): ");
            String inputFileName = scanner.nextLine();

            // Добавление пути к файлу
            inputFileName = "src/" + inputFileName;

            System.out.print("Введите операцию (сложение, умножение, разность): ");
            String operation = scanner.nextLine().toLowerCase();

            // Чтение данных из файла
            File inputFile = new File(inputFileName);
            if (!inputFile.exists()) {
                System.out.println("Файл не найден.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            double number1 = Double.parseDouble(reader.readLine());
            double number2 = Double.parseDouble(reader.readLine());
            reader.close();

            // Выполнение операции
            double result = 0;
            switch (operation) {
                case "сложение":
                    result = number1 + number2;
                    break;
                case "умножение":
                    result = number1 * number2;
                    break;
                case "разность":
                    result = number1 - number2;
                    break;
                default:
                    System.out.println("Неверная операция.");
                    return;
            }

            // Вывод результата на экран
            System.out.println("Результат: " + result);

            // Запись результата в новый файл
            String outputFileName = inputFileName.replace(".txt", "_out.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
            writer.write("Результат: " + result);
            writer.close();

            System.out.println("Результат записан в файл: " + outputFileName);

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: некорректный формат чисел в файле.");
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
