package org.example;

class Triangle {
    protected double sideA;
    protected double sideB;
    protected double sideC;

    // Конструктор для инициализации сторон треугольника
    public Triangle(double sideA, double sideB, double sideC) {
        if (isValidTriangle(sideA, sideB, sideC)) {
            this.sideA = sideA;
            this.sideB = sideB;
            this.sideC = sideC;
        } else {
            throw new IllegalArgumentException("Стороны не образуют треугольник.");
        }
    }

    // Проверка, образуют ли стороны треугольник
    private boolean isValidTriangle(double a, double b, double c) {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    // Метод для вычисления периметра
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    // Метод для вычисления площади (по формуле Герона)
    public double getArea() {
        double semiPerimeter = getPerimeter() / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - sideA) * (semiPerimeter - sideB) * (semiPerimeter - sideC));
    }

    @Override
    public String toString() {
        return String.format("Треугольник со сторонами: A=%.2f, B=%.2f, C=%.2f", sideA, sideB, sideC);
    }
}