import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Шукає елемент з максимальним опором.
 */
public class MaxCommand implements WorkerCommand {

    private final List<Item> items;
    private int resultIndex = -1;
    private int progress = 0;

    public MaxCommand(List<Item> items) {
        this.items = items;
    }

    public int getResultIndex() {
        return resultIndex;
    }

    public Item getResult() {
        return resultIndex >= 0 ? items.get(resultIndex) : null;
    }

    @Override
    public boolean running() {
        return progress < 100;
    }

    @Override
    public void execute() {
        progress = 0;
        resultIndex = -1;
        int size = items.size();
        if (size == 0) {
            progress = 100;
            return;
        }

        System.out.println("Пошук максимального опору...");
        resultIndex = 0;

        for (int i = 1; i < size; i++) {
            if (items.get(i).getResistance() > items.get(resultIndex).getResistance())
                resultIndex = i;
            progress = i * 100 / size;
            sleep(size);
        }

        progress = 100;
        System.out.printf("Макс. опір = %.2f Ohm (елемент #%d)%n",
                items.get(resultIndex).getResistance(), resultIndex);
    }

    private void sleep(int size) {
        try {
            TimeUnit.MILLISECONDS.sleep(Math.max(1, 1000 / size));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}