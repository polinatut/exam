public class Main {
    public static void main(String[] args) {
        Function line = new Line(2, 3);
        System.out.println("Значение прямой в точке x = 5: " + line.calculate(5));

        Function parabola = new Parabola(1, -4, 5);
        System.out.println("Значение параболы в точке x = 2: " + parabola.calculate(2));
    }
}

