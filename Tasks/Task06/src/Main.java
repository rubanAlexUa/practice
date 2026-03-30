import java.util.Scanner;

/**
 * Головний клас з меню.
 */
public class Main {

    private Calc calc = Calc.getInstance();

    private void menu() {
        Scanner in = new Scanner(System.in);
        String s;
        do {
            System.out.println(
                    "\nКоманди: 'q'uit, 'v'iew, 'g'enerate,  'u'ndo, 'm'acro, 'r'estore, 's'ave, 't'able settings, 'e'xecute ");
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
                    calc.executeCommand(new AddItemCommand(calc, I, u1, u2, u3));
                    calc.show();
                    break;
                case 'u':
                    // скасовуємо останню команду
                    calc.undoLast();
                    calc.show();
                    break;
                case 'm':
                    // макрокоманда — генеруємо 3 записи за раз
                    MacroCommand macro = new MacroCommand();
                    for (int i = 0; i < 3; i++) {
                        macro.addCommand(new AddItemCommand(calc,
                                1 + Math.random() * 9,
                                Math.random() * 50,
                                Math.random() * 50,
                                Math.random() * 50));
                    }
                    calc.executeCommand(macro);
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
                case 't':
                    System.out.print("Показати OCT? (1-так / 0-ні): ");
                    String oct = in.nextLine().trim();
                    System.out.print("Показати HEX? (1-так / 0-ні): ");
                    String hex = in.nextLine().trim();
                    calc.setShowOct(oct.equals("1"));
                    calc.setShowHex(hex.equals("1"));
                    System.out.println("Налаштування збережено.");
                    break;
                case 'e':
                    // Запуск паралельної обробки (Worker Thread)
                    new ExecuteWorkerCommand(calc.getItems()).execute();
                    break;
                default:
                    System.out.println("Невідома команда.");
            }
        } while (s.charAt(0) != 'q');
    }

    public static void main(String[] args) {
        AppTest.runTests();
        new Main().menu();
    }
}