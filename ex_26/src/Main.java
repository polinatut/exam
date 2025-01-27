// Абстрактный класс для цветовой модели
abstract class ColorModel {
    public abstract void convert();
}

// Класс для конвертации CMYK -> RGB
class CMYKconverter extends ColorModel {
    private double cyan;
    private double magenta;
    private double yellow;
    private double black;

    public CMYKconverter(double cyan, double magenta, double yellow, double black) {
        this.cyan = cyan;
        this.magenta = magenta;
        this.yellow = yellow;
        this.black = black;
    }

    @Override
    public void convert() {
        int red = (int) Math.round(255 * (1 - cyan) * (1 - black));
        int green = (int) Math.round(255 * (1 - magenta) * (1 - black));
        int blue = (int) Math.round(255 * (1 - yellow) * (1 - black));

        System.out.println("CMYK (" + cyan + ", " + magenta + ", " + yellow + ", " + black + ") -> RGB (" + red + ", " + green + ", " + blue + ")");
    }
}

// Класс для конвертации RGB -> CMYK
class RGBconverter extends ColorModel {
    private int red;
    private int green;
    private int blue;

    public RGBconverter(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void convert() {
        double r = red / 255.0;
        double g = green / 255.0;
        double b = blue / 255.0;

        double black = 1 - Math.max(r, Math.max(g, b));
        double cyan = (1 - r - black) / (1 - black);
        double magenta = (1 - g - black) / (1 - black);
        double yellow = (1 - b - black) / (1 - black);

        // Если все цвета равны нулю, то CMY тоже равны нулю
        if (Double.isNaN(cyan)) cyan = 0;
        if (Double.isNaN(magenta)) magenta = 0;
        if (Double.isNaN(yellow)) yellow = 0;

        cyan = Math.round(cyan * 100000.0) / 100000.0; // Округление до 5 знаков
        magenta = Math.round(magenta * 100000.0) / 100000.0;
        yellow = Math.round(yellow * 100000.0) / 100000.0;
        black = Math.round(black * 100000.0) / 100000.0;

        System.out.println("RGB (" + red + ", " + green + ", " + blue + ") -> CMYK (" + cyan + ", " + magenta + ", " + yellow + ", " + black + ")");
    }
}

// Тестирование
public class Main {
    public static void main(String[] args) {
        // Пример конвертации CMYK -> RGB
        ColorModel cmykToRgb = new CMYKconverter(0.5, 0.3, 0.2, 0.1);
        cmykToRgb.convert(); // Ожидается RGB (115, 161, 184)

        // Пример конвертации RGB -> CMYK
        ColorModel rgbToCmyk = new RGBconverter(128, 64, 255);
        rgbToCmyk.convert(); // Ожидается CMYK (0.498039, 0.74902, 0.0, 0.0)
    }
}