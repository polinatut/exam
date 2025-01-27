public class DoubleWrapper {

    // Поле для хранения значения типа double
    private double value;

    // Конструктор класса
    public DoubleWrapper(double value) {
        this.value = value;
    }

    // Геттер для получения значения
    public double getValue() {
        return value;
    }

    // Сеттер для установки значения
    public void setValue(double value) {
        this.value = value;
    }

    // Статический метод для сложения двух чисел
    public static double add(double a, double b) {
        return a + b;
    }

    // Статический метод для деления одного числа на другое
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
        return a / b;
    }

    // Статический метод для возведения числа в степень
    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // Пример использования методов
    public static void main(String[] args) {
        // Пример использования статических методов
        double sum = DoubleWrapper.add(5.5, 4.5);
        System.out.println("Сумма: " + sum);

        double quotient = DoubleWrapper.divide(10.0, 2.0);
        System.out.println("Частное: " + quotient);

        double result = DoubleWrapper.power(2.0, 3.0);
        System.out.println("Возведение в степень: " + result);

        // Пример использования объекта класса
        DoubleWrapper wrapper = new DoubleWrapper(15.0);
        System.out.println("Значение объекта: " + wrapper.getValue());
        wrapper.setValue(20.0);
        System.out.println("Новое значение объекта: " + wrapper.getValue());
    }
}
