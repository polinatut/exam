public class ComplexNumber {
    private int real; // Вещественная часть
    private int imaginary; // Мнимая часть

    // Конструктор с массивом
    public ComplexNumber(int[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Массив должен содержать ровно два элемента.");
        }
        this.real = parts[0];
        this.imaginary = parts[1];
    }

    // Конструктор с отдельными частями
    public ComplexNumber(int real, int imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    // Метод для сложения комплексных чисел
    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
    }

    // Метод для вычитания комплексных чисел
    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real, this.imaginary - other.imaginary);
    }

    // Метод для умножения комплексных чисел
    public ComplexNumber multiply(ComplexNumber other) {
        int realPart = this.real * other.real - this.imaginary * other.imaginary;
        int imaginaryPart = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(realPart, imaginaryPart);
    }

    // Переопределим toString() для удобного вывода
    @Override
    public String toString() {
        return real + " + " + imaginary + "i";
    }

    public static void main(String[] args) {
        ComplexNumber num1 = new ComplexNumber(new int[]{3, 4});
        ComplexNumber num2 = new ComplexNumber(new int[]{1, 2});

        ComplexNumber sum = num1.add(num2);
        ComplexNumber diff = num1.subtract(num2);
        ComplexNumber prod = num1.multiply(num2);

        System.out.println("Сумма: " + sum);
        System.out.println("Разность: " + diff);
        System.out.println("Произведение: " + prod);
    }
}

