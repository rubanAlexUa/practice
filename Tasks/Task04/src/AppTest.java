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
        testView();
        System.out.println("--- Кінець тестів ---\n");
    }

    /**
     * Тест обчислення опору
     */
    private static void testCalc() {
        Calc calc = new Calc();
        double r = calc.init(2.0, 10.0, 10.0, 10.0);
        // тобто r = (10+10+10)/2 = 15 повинно бути, інаше ж помилка
        if (r == 15.0) {
            System.out.println("Тест пройшов успішно!\n");
        } else {
            System.out.println("Помилка: очікувалось 15.0, отримано " + r);
        }
    }

    /**
     * Тест виводу через ItemView — поліморфізм
     */
    private static void testView() {
        Item item = new Item();
        item.setCurrent(2.0);
        item.setU1(10.0);
        item.setU2(10.0);
        item.setU3(10.0);
        item.recalculate();
        ItemView view = new TextItemView();
        view.show(item);
        System.out.println("testView: OK");
    }
}