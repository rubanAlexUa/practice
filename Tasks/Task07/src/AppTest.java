import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        testMaxCommand();
        testMinCommand();
        testAvgCommand();
        testFilterCommand();
        testCommandQueue();
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
        calc.undoLast();
        System.out.println("testMacro: OK");
    }

    /**
     * Створює тестову колекцію з 5 елементів із відомими значеннями опору.
     *
     * @return тестова колекція {@link Item}
     */
    private static List<Item> makeSample() {
        List<Item> list = new ArrayList<>();
        double[][] data = {
                { 2.0, 10, 10, 10 }, // R=15
                { 5.0, 25, 25, 25 }, // R=15
                { 1.0, 30, 30, 30 }, // R=90
                { 3.0, 3, 3, 3 }, // R=3
                { 4.0, 20, 20, 20 } // R=15
        };
        for (double[] d : data) {
            Item it = new Item();
            it.setCurrent(d[0]);
            it.setU1(d[1]);
            it.setU2(d[2]);
            it.setU3(d[3]);
            it.recalculate();
            list.add(it);
        }
        return list;
    }

    /**
     * Перевіряє {@link MaxCommand}, де очікується елемент з індексом 2 (R=90).
     */
    private static void testMaxCommand() {
        MaxCommand cmd = new MaxCommand(makeSample());
        cmd.execute();
        assert cmd.getResultIndex() == 2 : "testMaxCommand FAILED";
        System.out.println("testMaxCommand: OK (індекс=" + cmd.getResultIndex() + ")");
    }

    /**
     * Перевіряє {@link MinCommand}: очікується елемент з індексом 3 (R=3).
     */
    private static void testMinCommand() {
        MinCommand cmd = new MinCommand(makeSample());
        cmd.execute();
        assert cmd.getResultIndex() == 3 : "testMinCommand FAILED";
        System.out.println("testMinCommand: OK (індекс=" + cmd.getResultIndex() + ")");
    }

    /**
     * Перевіряє {@link AvgCommand}: середнє значення має бути більше нуля.
     */
    private static void testAvgCommand() {
        AvgCommand cmd = new AvgCommand(makeSample());
        cmd.execute();
        assert cmd.getResult() > 0 : "testAvgCommand FAILED";
        System.out.printf("testAvgCommand: OK (avg=%.2f)%n", cmd.getResult());
    }

    /**
     * Перевіряє {@link FilterCommand}: з порогом 10.0 очікується 4 елементи з R >
     * 10.
     */
    private static void testFilterCommand() {
        List<Item> sample = makeSample();
        FilterCommand cmd = new FilterCommand(sample, 10.0);
        cmd.execute();
        // очікуємо 4 елементи з R > 10
        assert cmd.getFiltered().size() == 4 : "testFilterCommand FAILED";
        System.out.println("testFilterCommand: OK (знайдено=" + cmd.getFiltered().size() + ")");
    }

    /**
     * Перевіряє роботу {@link CommandQueue}: паралельне виконання
     * {@link MaxCommand}
     * та {@link AvgCommand} через чергу Worker Thread.
     */
    private static void testCommandQueue() {
        List<Item> sample = makeSample();
        MaxCommand maxCmd = new MaxCommand(sample);
        AvgCommand avgCmd = new AvgCommand(sample);

        CommandQueue queue = new CommandQueue();
        queue.put(maxCmd);
        queue.put(avgCmd);

        try {
            while (maxCmd.running() || avgCmd.running())
                TimeUnit.MILLISECONDS.sleep(50);
            queue.shutdown();
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assert maxCmd.getResultIndex() == 2 : "testCommandQueue(max) FAILED";
        assert avgCmd.getResult() > 0 : "testCommandQueue(avg) FAILED";
        System.out.println("testCommandQueue: OK");
    }
}