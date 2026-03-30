import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Консольна команда 'e'xecute.
 * Створює дві черги потоків і паралельно запускає обробку колекції.
 */
public class ExecuteWorkerCommand implements Command {

    private final List<Item> items;

    public ExecuteWorkerCommand(List<Item> items) {
        this.items = items;
    }

    @Override
    public void execute() {
        if (items.isEmpty()) {
            System.out.println("Колекція порожня. Спочатку згенеруйте дані");
            return;
        }

        // Задачі
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

        // Виводимо підсумок
        System.out.println("\n--- Результати ---");
        if (maxCmd.getResult() != null)
            System.out.println("Максимум: " + maxCmd.getResult());
        if (minCmd.getResult() != null)
            System.out.println("Мінімум:  " + minCmd.getResult());
        System.out.printf("Середнє:  %.2f Ohm%n", avgCmd.getResult());
        System.out.printf("Фільтр (R > %.2f): %d елементів%n",
                avgCmd_threshold(items), filterCmd.getFiltered().size());
    }

    /** Поріг для фільтра = середнє арифметичне опорів */
    private double avgCmd_threshold(List<Item> items) {
        double sum = 0;
        for (Item it : items)
            sum += it.getResistance();
        return items.isEmpty() ? 0 : sum / items.size();
    }

    @Override
    public void undo() {
        /* не застосовується */ }
}