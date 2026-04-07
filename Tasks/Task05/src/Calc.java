import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Клас для обрахунків та виконання дій, як-от: Вивід результатів,
 * створення нового об'єкту, збереження у текстовий файл та відновлення змінних
 * із файлу ітд.
 * 
 * @author Alex Ruban
 */
public class Calc implements ViewFactory {

    /**
     * Ім'я файлу для збереження значень, відповідно і серіалізації {@link Item}.
     */
    private static final String FNAME = "Item.txt";

    /**
     * Singleton — єдиний екземпляр
     */
    private static Calc instance;

    /**
     * Приватний конструктор — щоб не можна було зробити new Calc()
     */
    private Calc() {
    }

    /**
     * Повертає єдиний екземпляр {@link Calc}, якщо не існує, то створює новий
     * екземпляр
     * 
     * @return існуючий екземпляр {@link Calc} або новий
     */
    public static Calc getInstance() {
        if (instance == null)
            instance = new Calc();
        return instance;
    }

    /**
     * Список усіх записів {@link Item}
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Історія виконаних команд для можливості скасування (undo)
     */
    private ArrayList<Command> history = new ArrayList<>();

    /**
     * Прапорець відображення опору у вісімковій системі
     */
    private boolean showOct = true;

    /**
     * Прапорець відображення опору у шістнадцятковій системі
     */
    private boolean showHex = true;

    /**
     * Встановлює чи показувати опір у вісімковій системі чи ні
     * 
     * @param v {@code true} - показувати, {@code false} - не показувати
     */
    public void setShowOct(boolean v) {
        showOct = v;
    }

    /**
     * Встановлює чи показувати опір у шістнадцятковій системі чи ні
     * 
     * @param v {@code true} - показувати, {@code false} - не показувати
     */
    public void setShowHex(boolean v) {
        showHex = v;
    }

    /**
     * Якщо об'єкт пустий, то повертає новий пустий об'єкт, інакше повертає останній
     * доданий об'єкт {@link Item} зі списку.
     * 
     * @return останній доданий об'єкт або пустий об'єкт
     */
    public Item getResult() {
        if (items.isEmpty())
            return new Item();
        return items.get(items.size() - 1);
    }

    /**
     * Метод для обчислення опору, та встановлення нових значень змінних.
     * 
     * @param current сила струму (А)
     * @param u1      перша напруга (В)
     * @param u2      друга напруга (В)
     * @param u3      третя напруга (В)
     * @return новий опір (Ом) або {@code 0.0} якщо {@code current == 0}
     */
    public double init(double current, double u1, double u2, double u3) {

        Item result = new Item();
        result.setCurrent(current);
        result.setU1(u1);
        result.setU2(u2);
        result.setU3(u3);
        if (current == 0) {
            items.add(result);
            return 0.0;
        }
        double resistance = (u1 + u2 + u3) / current;
        result.setResistance(resistance);
        items.add(result);
        return resistance;
    }

    /**
     * Видаляє останній запис {@link Item} із {@link Calc}.
     * Метод використовується у команді скасування (undo).
     */
    public void removeLast() {
        if (!items.isEmpty()) {
            items.remove(items.size() - 1);
            System.out.println("Скасовано.");
        }
    }

    /**
     * Виконує передану команду та зберігає її в історії для можливого скасування.
     * 
     * @param cmd команда що виконується
     */
    public void executeCommand(Command cmd) {
        cmd.execute();
        history.add(cmd);
    }

    /**
     * Скасовує останню команду, та видаляє із історії.
     * Якщо ж історія пуста, то виводиться повідомлення і все.
     */
    public void undoLast() {
        if (history.isEmpty()) {
            System.out.println("Немає що скасовувати.");
            return;
        }
        Command cmd = history.remove(history.size() - 1);
        cmd.undo();
    }

    /**
     * Створює та повертає {@link ItemView} для виводу об'єктів {@link Item}.
     *
     * @return новий екземпляр {@link TextItemView}
     */
    @Override
    public ItemView createView() {
        return new TextItemView(showOct, showHex);
    }

    /**
     * Виводить поточні значення об'єкту {@link Item} у вигляді таблиці в консоль
     */
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

    /**
     * Зберігає усі об'єкти {@link Item} у текстовий файл {@value #FNAME}.
     *
     * @throws Exception якщо не вдалося записати файл
     */
    public void save() throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FNAME))) {
            for (Item item : items)
                writer.printf("%.4f %.4f %.4f %.4f%n",
                        item.getCurrent(), item.getU1(), item.getU2(), item.getU3());
        }
    }

    /**
     * Виймає значення із текстового файлу {@value #FNAME}, та оновлює список
     * об'єктів
     * {@link Item}
     * 
     * @throws Exception якщо файл пошкоджено або не знайдено
     */
    public void restore() throws Exception {
        items.clear();
        try (Scanner sc = new Scanner(new File(FNAME))) {
            while (sc.hasNextDouble()) {
                Item item = new Item();
                item.setCurrent(sc.nextDouble());
                item.setU1(sc.nextDouble());
                item.setU2(sc.nextDouble());
                item.setU3(sc.nextDouble());
                item.recalculate();
                items.add(item);
            }
        }
    }

    /**
     * Очищає історію та список записів.
     * Використовується після тестів.
     */
    public void clear() {
        items.clear();
        history.clear();
    }
}