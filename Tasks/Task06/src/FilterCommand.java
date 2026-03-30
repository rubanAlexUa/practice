import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Відбирає елементи, у яких опір перевищує поріг.
 */
public class FilterCommand implements WorkerCommand {

    private final List<Item> items;
    private final double threshold;
    private final List<Item> filtered = new ArrayList<>();
    private int progress = 0;

    public FilterCommand(List<Item> items, double threshold) {
        this.items = items;
        this.threshold = threshold;
    }

    public List<Item> getFiltered() {
        return filtered;
    }

    @Override
    public boolean running() {
        return progress < 100;
    }

    @Override
    public void execute() {
        progress = 0;
        filtered.clear();
        int size = items.size();
        if (size == 0) {
            progress = 100;
            return;
        }

        System.out.printf("Початок роботи, поріг = %.2f Ohm%n", threshold);

        for (int i = 0; i < size; i++) {
            if (items.get(i).getResistance() > threshold)
                filtered.add(items.get(i));
            progress = (i + 1) * 100 / size;
            sleep(size);
        }

        System.out.printf("Знайдено %d елементів з R > %.2f%n",
                filtered.size(), threshold);
    }

    private void sleep(int size) {
        try {
            TimeUnit.MILLISECONDS.sleep(Math.max(1, 1000 / size));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}