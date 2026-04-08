import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Команда для пошуку елемента з мінімальним опором у списку {@link Item}.
 */
public class MinCommand implements WorkerCommand {

    /**
     * Список елементів для пошуку.
     */
    private final List<Item> items;

    /**
     * Індекс елемента з мінімальним опором. {@code -1} якщо пошук ще не
     * виконувався.
     */
    private int resultIndex = -1;

    /**
     * Прогрес виконання у відсотках (0–100).
     */
    private int progress = 0;

    /**
     * Конструктор команди пошуку мінімального опору.
     *
     * @param items список елементів {@link Item} для обробки
     */
    public MinCommand(List<Item> items) {
        this.items = items;
    }

    /**
     * Повертає індекс елемента з мінімальним опором у списку.
     *
     * @return індекс елемента або {@code -1} якщо список порожній
     */
    public int getResultIndex() {
        return resultIndex;
    }

    /**
     * Повертає елемент {@link Item} з мінімальним опором.
     *
     * @return елемент з мінімальним опором або {@code null} якщо список порожній
     */
    public Item getResult() {
        return resultIndex >= 0 ? items.get(resultIndex) : null;
    }

    @Override
    public boolean running() {
        return progress < 100;
    }

    /**
     * Виконує пошук елемента з мінімальним опором, та оновлює прогрес виконання
     * після кожного кроку.
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

        System.out.println("Пошук мінімального опору...");
        resultIndex = 0;

        for (int i = 1; i < size; i++) {
            if (items.get(i).getResistance() < items.get(resultIndex).getResistance())
                resultIndex = i;
            progress = i * 100 / size;
            sleep(size);
        }

        progress = 100;
        System.out.printf("Готово. Мін. опір = %.2f Ohm (елемент #%d)%n",
                items.get(resultIndex).getResistance(), resultIndex);
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