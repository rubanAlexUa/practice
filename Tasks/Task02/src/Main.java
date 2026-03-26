import java.io.*;
import java.util.Scanner;

/**
 * Головний клас з меню.
 */
public class Main {

    private Calc calc = new Calc();

    private void menu() {
        Scanner in = new Scanner(System.in);
        String s;
        do {
            System.out.println("\nКоманди: 'q'uit, 'v'iew, 'g'enerate, 'r'estore");
            System.out.print("Введіть команду: ");
            s = in.nextLine();

            if (s.length() != 1)
                continue;

            switch (s.charAt(0)) {
                case 'q':
                    System.out.println("Вихід.");
                    break;
                case 'v':
                    calc.show();
                    break;
                case 'g':
                    double I = 1 + Math.random() * 9;
                    double u1 = Math.random() * 50;
                    double u2 = Math.random() * 50;
                    double u3 = Math.random() * 50;
                    calc.init(I, u1, u2, u3);
                    calc.show();
                    break;
                case 'r':
                    try {
                        calc.restore();
                        System.out.println("Відновлено! resistance (transient) = " + calc.getResult().getResistance());
                        calc.show();
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e);
                    }
                    break;
                default:
                    System.out.println("Невідома команда.");
            }
        } while (s.charAt(0) != 'q');
    }

    public static void main(String[] args) {
        new Main().menu();
    }
}