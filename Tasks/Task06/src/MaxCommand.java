import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Команда для пошуку елемента з максимальним опором у колекції {@link Item}.
 */
public class MaxCommand implements WorkerCommand {
    /**
     * Колекція елементів для пошуку.
     */
    private final List<Item> items;

    /**
     * Індекс елемента з максимальним опором. {@code -1} якщо пошук ще не
     * виконувався.
     */
    private int resultIndex = -1;

    /**
     * Прогрес виконання у відсотках (0–100).
     */
    private int progress = 0;

    /**
     * Конструктор команди пошуку максимального опору.
     *
     * @param items колекція елементів {@link Item} для обробки
     */
    public MaxCommand(List<Item> items) {
        this.items = items;
    }

    /**
     * Повертає індекс елемента з максимальним опором.
     *
     * @return індекс елемента або {@code -1} якщо колекція порожня
     */
    public int getResultIndex() {
        return resultIndex;
    }

    /**
     * Повертає елемент {@link Item} з максимальним опором.
     *
     * @return елемент з максимальним опором або {@code null} якщо колекція порожня
     */
    public Item getResult() {
        return resultIndex >= 0 ? items.get(resultIndex) : null;
    }

    @Override
    public boolean running() {
        return progress < 100;
    }

    /**
     * Виконує пошук елемента з максимальним опором, та оновлює прогрес після
     * кожного кроку.
     */
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

    /**
     * Затримує виконання для імітації тривалої обробки.
     *
     * @param size кількість елементів у колекції (визначає тривалість затримки)
     */
    private void sleep(int size) {
        try {
            TimeUnit.MILLISECONDS.sleep(Math.max(1, 1000 / size));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}