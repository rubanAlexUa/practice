import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Консольна команда {@code 'e'xecute}.
 * Паралельно запускає обробку списку у двох чергах Worker Thread:
 * перша черга шукає максимум і мінімум, друга — обчислює середнє та виконує
 * фільтрацію.
 */
public class ExecuteWorkerCommand implements Command {

    /**
     * Список елементів {@link Item} для обробки.
     */
    private final List<Item> items;

    /**
     * Конструктор команди для паралельної обробки.
     *
     * @param items список елементів {@link Item}
     */
    public ExecuteWorkerCommand(List<Item> items) {
        this.items = items;
    }

    /**
     * Запускає паралельну обробку списку у двох чергах Worker Thread.
     * Метод блокується до завершення всіх задач, потім виводить підсумок у консоль.
     */
    @Override
    public void execute() {
        if (items.isEmpty()) {
            System.out.println("Список порожній. Спочатку згенеруйте дані");
            return;
        }

        MaxCommand maxCmd = new MaxCommand(items);
        MinCommand minCmd = new MinCommand(items);
        AvgCommand avgCmd = new AvgCommand(items);
        FilterCommand filterCmd = new FilterCommand(items, avgCmd_threshold(items));

        // Черга 1: Max + Min (послідовно в одному потоці)
        CommandQueue queue1 = new CommandQueue();
        queue1.put(maxCmd);
        queue1.put(minCmd);

        // Черга 2: Avg + Filter (послідовно в іншому потоці)
        CommandQueue queue2 = new CommandQueue();
        queue2.put(avgCmd);
        queue2.put(filterCmd);

        System.out.println("\n--- Паралельне виконання запущено ---");

        try {
            while (maxCmd.running() || minCmd.running() ||
                    avgCmd.running() || filterCmd.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue1.shutdown();
            queue2.shutdown();
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- Результати ---");
        if (maxCmd.getResult() != null)
            System.out.println("Максимум: " + maxCmd.getResult());
        if (minCmd.getResult() != null)
            System.out.println("Мінімум:  " + minCmd.getResult());
        System.out.printf("Середнє:  %.2f Ohm%n", avgCmd.getResult());
        System.out.printf("Фільтр (R > %.2f): %d елементів%n",
                avgCmd_threshold(items), filterCmd.getFiltered().size());
    }

    /**
     * Обчислює середнє арифметичне опорів списку як поріг для фільтрації.
     *
     * @param items список елементів {@link Item}
     * @return середній опір (Ом) або {@code 0.0} якщо список порожній
     */
    private double avgCmd_threshold(List<Item> items) {
        double sum = 0;
        for (Item it : items)
            sum += it.getResistance();
        return items.isEmpty() ? 0 : sum / items.size();
    }

    /**
     * Не застосовується для цієї команди.
     */
    @Override
    public void undo() {
    }
}