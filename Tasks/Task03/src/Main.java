import java.util.Scanner;

/**
 * Головний клас із текстовим меню, та можливістю взаємодіяти із ним.
 * 
 * @author Alex Ruban
 */
public class Main {

    /**
     * Екземпляр класу {@link Calc} для обрахунків та зберігання результатів.
     */
    private Calc calc = new Calc();

    /**
     * Метод запускає меню, та обробляє команди задані користувачем.
     * Доступні такі команди:
     * {@code q} — вийти із програми,
     * {@code v} — показати поточні результати обрахунків,
     * {@code g} — згенерувати рандомні значення та додає новий об'єкт із ними до
     * списку,
     * {@code s} — зберегти поточний об'єкт у бінарний файл,
     * {@code r} — відновити значення із файлу у поточний об'єкт.
     */
    private void menu() {
        Scanner in = new Scanner(System.in);
        String s;
        do {
            System.out.println("\nКоманди: 'q'uit, 'v'iew, 'g'enerate, 'r'estore, 's'ave");
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
                        calc.show();
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e);
                    }
                    break;
                case 's':
                    try {
                        calc.save();
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e);
                    }
                    break;
                default:
                    System.out.println("Невідома команда.");
            }
        } while (s.charAt(0) != 'q');
    }

    /**
     * Початок роботи програми
     * 
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        new Main().menu();
    }
}