import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Calc implements ViewFactory {

    private static final String FNAME = "Item.txt";

    private ArrayList<Item> items = new ArrayList<>();

    public Item getResult() {
        if (items.isEmpty())
            return new Item();
        return items.get(items.size() - 1);
    }

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

    public void show() {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        ItemView view = createView();
        for (Item item : items)
            view.show(item);
    }

    public void save() throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(FNAME));
        for (Item item : items)
            pw.println(item);
        pw.close();
    }

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