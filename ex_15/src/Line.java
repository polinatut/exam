// Класс для уравнения прямой: y = kx + b
class Line extends Function {
    private double k; // Коэффициент наклона
    private double b; // Свободный член

    public Line(double k, double b) {
        this.k = k;
        this.b = b;
    }

    @Override
    public double calculate(double x) {
        return k * x + b;
    }
}