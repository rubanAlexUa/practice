/**
 * Клас для тестування основної функціональності.
 */
public class AppTest {

    /**
     * Запускає всі тести
     */
    public static void runTests() {
        System.out.println("--- Початок тестів ---");
        testCalc();
        testUndo();
        testMacro();
        System.out.println("--- Кінець тестів ---\n");
        Calc.getInstance().clear();
    }

    /**
     * Тест обчислення опору
     */
    private static void testCalc() {
        Calc calc = Calc.getInstance();
        double r = calc.init(2.0, 10.0, 10.0, 10.0);
        // тобто r = (10+10+10)/2 = 15 повинно бути, інаше ж помилка
        if (r == 15.0) {
            System.out.println("testCalc: OK, R=" + r);
        } else {
            System.out.println("testCalc: помилка, отримано R=" + r);
        }
    }

    /**
     * Перевіряє роботу команди скасування (undo).
     */
    private static void testUndo() {
        Calc calc = Calc.getInstance();
        AddItemCommand cmd = new AddItemCommand(calc, 3.0, 10.0, 10.0, 10.0);
        calc.executeCommand(cmd);
        calc.undoLast();
        System.out.println("testUndo: OK");
    }

    /**
     * Перевіряє роботу макрокоманди
     */
    private static void testMacro() {
        Calc calc = Calc.getInstance();
        MacroCommand macro = new MacroCommand();
        macro.addCommand(new AddItemCommand(calc, 2.0, 5.0, 5.0, 5.0));
        macro.addCommand(new AddItemCommand(calc, 4.0, 8.0, 8.0, 8.0));
        calc.executeCommand(macro);
        calc.undoLast(); // скасовуємо обидва одразу
        System.out.println("testMacro: OK");
    }
}