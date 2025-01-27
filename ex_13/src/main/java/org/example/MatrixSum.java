package org.example;

import java.util.Random;
import java.util.Scanner;

public class MatrixSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введите количество строк матрицы: ");
        int rows = scanner.nextInt();

        System.out.print("Введите количество столбцов матрицы: ");
        int cols = scanner.nextInt();

        if (rows < 2 || cols < 2) {
            System.out.println("Размер матрицы должен быть не менее 2x2.");
            return;
        }

        int[][] matrix = new int[rows][cols];
        int borderSum = 0;
        int attempts = 0; // Счетчик попыток
        final int MAX_ATTEMPTS = 10000000; // Максимальное количество попыток

        while (borderSum != 666 && attempts < MAX_ATTEMPTS) {
            // Заполнение матрицы случайными числами
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = random.nextInt(100) + 1;
                }
            }

            // Подсчет суммы граничных элементов
            borderSum = calculateBorderSum(matrix, rows, cols);
            attempts++;
        }

        if (borderSum == 666) {
            System.out.println("Матрица, где сумма граничных элементов равна 666:");
            printMatrix(matrix);
        } else {
            System.out.println("Не удалось найти подходящую матрицу за " + MAX_ATTEMPTS + " попыток.");
        }
    }

    // Метод для подсчета суммы граничных элементов
    private static int calculateBorderSum(int[][] matrix, int rows, int cols) {
        int sum = 0;

        // Верхняя и нижняя границы
        for (int j = 0; j < cols; j++) {
            sum += matrix[0][j]; // Верхняя граница
            sum += matrix[rows - 1][j]; // Нижняя граница
        }

        // Левая и правая границы (без учета углов)
        for (int i = 1; i < rows - 1; i++) {
            sum += matrix[i][0]; // Левая граница
            sum += matrix[i][cols - 1]; // Правая граница
        }

        return sum;
    }

    // Метод для вывода матрицы
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
