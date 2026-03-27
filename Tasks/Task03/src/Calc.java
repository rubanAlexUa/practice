import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Calc implements ViewFactory {

    private static final String FNAME = "Item.txt";

    // Список усіх записів
    private ArrayList<Item> items = new ArrayList<>();

    public Item getResult() {
        if (items.isEmpty())
            return new Item();
        return items.get(items.size() - 1);
    }

    // Задавання значень для змінних
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

    @Override
    public ItemView createView() {
        return new TextItemView();
    }

    // Виводимо усі записи
    public void show() {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        ItemView view = createView();
        for (Item item : items)
            view.show(item);
    }

    // Вводе усі записи у файл
    public void save() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(FNAME));
        for (Item item : items)
            writer.printf("%.4f %.4f %.4f %.4f%n",
                    item.getCurrent(), item.getU1(), item.getU2(), item.getU3());
        writer.close();
    }

    // Зчитує записи з файлу
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