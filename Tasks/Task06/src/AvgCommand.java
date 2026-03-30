import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Обчислює середній опір у колекції.
 */
public class AvgCommand implements WorkerCommand {

    private final List<Item> items;
    private double result = 0.0;
    private int progress = 0;

    public AvgCommand(List<Item> items) {
        this.items = items;
    }

    public double getResult() {
        return result;
    }

    @Override
    public boolean running() {
        return progress < 100;
    }

    @Override
    public void execute() {
        progress = 0;
        result = 0.0;
        int size = items.size();
        if (size == 0) {
            progress = 100;
            return;
        }

        System.out.println("Пошук середнього значення опорів...");

        for (int i = 0; i < size; i++) {
            result += items.get(i).getResistance();
            progress = (i + 1) * 100 / size;
            sleep(size);
        }

        result /= size;
        System.out.printf("Середній опір = %.2f Ohm%n", result);
    }

    private void sleep(int size) {
        try {
            TimeUnit.MILLISECONDS.sleep(Math.max(1, 1000 / size));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}