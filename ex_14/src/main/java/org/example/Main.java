package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Создание обычного треугольника
            Triangle triangle = new Triangle(5, 6, 7);
            System.out.println(triangle);
            System.out.println("Периметр: " + triangle.getPerimeter());
            System.out.println("Площадь: " + triangle.getArea());

            System.out.println();

            // Создание прямоугольного треугольника
            RightTriangle rightTriangle = new RightTriangle(3, 4, 5);
            System.out.println(rightTriangle);
            System.out.println("Периметр: " + rightTriangle.getPerimeter());
            System.out.println("Площадь: " + rightTriangle.getArea());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}