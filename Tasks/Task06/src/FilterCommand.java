import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Команда для відбору елементів {@link Item}, у яких опір перевищує заданий
 * поріг, заданий середнім арефметичним.
 */
public class FilterCommand implements WorkerCommand {

    /**
     * Список елементів для фільтрації.
     */
    private final List<Item> items;

    /**
     * Поріг опору (Ом).
     */
    private final double threshold;

    /**
     * Список елементів, що пройшли фільтр.
     */
    private final List<Item> filtered = new ArrayList<>();

    /**
     * Прогрес виконання у відсотках (0–100).
     */
    private int progress = 0;

    /**
     * Конструктор команди фільтрації.
     *
     * @param items     список елементів {@link Item}
     * @param threshold поріг опору (Ом)
     */
    public FilterCommand(List<Item> items, double threshold) {
        this.items = items;
        this.threshold = threshold;
    }

    /**
     * Повертає список елементів, що пройшли фільтр після виконання команди.
     *
     * @return відфільтровані елементи
     */
    public List<Item> getFiltered() {
        return filtered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean running() {
        return progress < 100;
    }

    /**
     * Виконує фільтрацію списку за порогом опору.
     * Оновлює прогрес виконання після кожного кроку.
     */
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

    /**
     * Затримує виконання для імітації тривалої обробки.
     *
     * @param size кількість елементів у списку (визначає тривалість затримки)
     */
    private void sleep(int size) {
        try {
            TimeUnit.MILLISECONDS.sleep(Math.max(1, 1000 / size));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}