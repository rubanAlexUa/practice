import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Клас для обрахунків та виконання дій, як-от: Вивід результатів,
 * створення нового об'єкту, збереження у бінарний файл та відновлення змінних
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
     * Список усіх записів {@link Item}
     */
    private ArrayList<Item> items = new ArrayList<>();

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
     * @return новий опір (Ом)
     */
    public double init(double current, double u1, double u2, double u3) {
        Item result = new Item();
        result.setCurrent(current);
        result.setU1(u1);
        result.setU2(u2);
        result.setU3(u3);
        double resistance = (u1 + u2 + u3) / current;
        result.setResistance(resistance);
        items.add(result);
        return resistance;
    }

    /**
     * Створює та повертає {@link ItemView} для виводу об'єктів {@link Item}.
     *
     * @return новий екземпляр {@link TextItemView}
     */
    @Override
    public ItemView createView() {
        return new TextItemView();
    }

    /**
     * Виводить поточні значення об'єкту {@link Item} у консоль
     */
    public void show() {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        ItemView view = createView();
        for (Item item : items) {
            view.show(item);
            System.out.println();
        }
    }

    /**
     * Зберігає усі об'єкт {@link Item} у текстовий файл {@value #FNAME}.
     *
     * @throws Exception якщо не вдалося записати файл
     */
    public void save() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(FNAME));
        for (Item item : items)
            writer.printf("%.4f %.4f %.4f %.4f%n",
                    item.getCurrent(), item.getU1(), item.getU2(), item.getU3());
        writer.close();
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