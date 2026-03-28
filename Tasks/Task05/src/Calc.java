import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Калькулятор опору. Реалізує ViewFactory.
 */
public class Calc implements ViewFactory {

    private static final String FNAME = "Item.txt";

    // Singleton — єдиний екземпляр
    private static Calc instance;

    // приватний конструктор — щоб не можна було зробити new Calc()
    private Calc() {
    }

    // єдиний спосіб отримати об'єкт
    public static Calc getInstance() {
        if (instance == null)
            instance = new Calc();
        return instance;
    }

    // список всіх обчислень
    private ArrayList<Item> items = new ArrayList<>();

    // історія виконаних команд для undo
    private ArrayList<Command> history = new ArrayList<>();

    // налаштування відображення
    private boolean showOct = true;
    private boolean showHex = true;

    public void setShowOct(boolean v) {
        showOct = v;
    }

    public void setShowHex(boolean v) {
        showHex = v;
    }

    public Item getResult() {
        if (items.isEmpty())
            return new Item();
        return items.get(items.size() - 1);
    }

    /** Рахуємо опір і додаємо в список */
    public double init(double current, double u1, double u2, double u3) {
        Item result = new Item();
        result.setCurrent(current);
        result.setU1(u1);
        result.setU2(u2);
        result.setU3(u3);
        double r = (u1 + u2 + u3) / current;
        result.setResistance(r);
        items.add(result);
        return r;
    }

    // видаляє останній запис — використовується в undo
    public void removeLast() {
        if (!items.isEmpty()) {
            items.remove(items.size() - 1);
            System.out.println("Скасовано.");
        }
    }

    // виконати команду і зберегти в історію
    public void executeCommand(Command cmd) {
        cmd.execute();
        history.add(cmd);
    }

    // скасувати останню команду
    public void undoLast() {
        if (history.isEmpty()) {
            System.out.println("Немає що скасовувати.");
            return;
        }
        Command cmd = history.remove(history.size() - 1);
        cmd.undo();
    }

    /**
     * Фабричний метод — повертає TextItemView з поточними налаштуваннями
     */
    @Override
    public ItemView createView() {
        return new TextItemView(showOct, showHex);
    }

    /** Виводимо всі записи у вигляді таблиці */
    public void show() {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        TextItemView view = (TextItemView) createView();
        view.showHeader();
        for (Item item : items)
            view.show(item);
        view.showFooter();
    }

    /** Зберігаємо в файл */
    public void save() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(FNAME));
        for (Item item : items)
            writer.printf("%.4f %.4f %.4f %.4f%n",
                    item.getCurrent(), item.getU1(), item.getU2(), item.getU3());
        writer.close();
    }

    /** Зчитуємо з файлу */
    public void restore() throws Exception {
        items.clear();
        Scanner sc = new Scanner(new File(FNAME));
        while (sc.hasNextDouble()) {
            Item item = new Item();
            item.setCurrent(sc.nextDouble());
            item.setU1(sc.nextDouble());
            item.setU2(sc.nextDouble());
            item.setU3(sc.nextDouble());
            item.recalculate();
            items.add(item);
        }
        sc.close();
    }
}