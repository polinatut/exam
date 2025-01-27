package org.example;

class RightTriangle extends Triangle {

    // Конструктор для инициализации прямоугольного треугольника
    public RightTriangle(double sideA, double sideB, double sideC) {
        super(sideA, sideB, sideC);
        if (!isRightTriangle()) {
            throw new IllegalArgumentException("Стороны не образуют прямоугольный треугольник.");
        }
    }

    // Проверка, является ли треугольник прямоугольным (теорема Пифагора)
    private boolean isRightTriangle() {
        double[] sides = {sideA, sideB, sideC};
        java.util.Arrays.sort(sides); // Сортировка сторон по возрастанию
        return Math.abs(sides[0] * sides[0] + sides[1] * sides[1] - sides[2] * sides[2]) < 1e-9;
    }

    @Override
    public String toString() {
        return String.format("Прямоугольный треугольник со сторонами: A=%.2f, B=%.2f, C=%.2f", sideA, sideB, sideC);
    }
}
