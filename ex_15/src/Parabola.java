// Класс для уравнения параболы: y = ax^2 + bx + c
class Parabola extends Function {
    private double a; // Коэффициент при x^2
    private double b; // Коэффициент при x
    private double c; // Свободный член

    public Parabola(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculate(double x) {
        return a * x * x + b * x + c;
    }
}
