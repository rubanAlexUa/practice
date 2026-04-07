import java.util.Vector;

/**
 * Реалізує патерн Worker Thread.
 * Фоновий потік Worker послідовно виконує задачі з черги.
 * Черга є потокобезпечною завдяки використанню {@link Vector}
 */
public class CommandQueue implements Queue {

    /**
     * Черга команд для виконання.
     */
    private final Vector<WorkerCommand> tasks = new Vector<>();

    /**
     * Прапор очікування, true значення {@code true} якщо Worker чекає на нові
     * задачі.
     */
    private boolean waiting = false;

    /**
     * Прапор завершення, true значення {@code true} якщо черга отримала сигнал
     * зупинки.
     */
    private boolean shutdown = false;

    /**
     * Конструктор, що запускає Worker потік у фоновому режимі
     */
    public CommandQueue() {
        new Thread(new Worker(), "WorkerThread").start();
    }

    /**
     * Надсилає сигнал про завершення потоку.
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * Додає команду до черги на виконання та сповіщає Worker.
     * 
     * @param cmd команда для виконання
     */
    @Override
    public synchronized void put(WorkerCommand cmd) {
        tasks.add(cmd);
        if (waiting)
            notifyAll();
    }

    /**
     * Вилучає та повертає наступну команду з черги.
     * Якщо черга порожня — блокує потік до появи нової команди
     *
     * @return наступна команда або {@code null} якщо черга завершила роботу
     */
    @Override
    public synchronized WorkerCommand take() {
        while (tasks.isEmpty() && !shutdown) {
            waiting = true;
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                waiting = false;
            }
        }
        return tasks.isEmpty() ? null : tasks.remove(0);
    }

    /**
     * Внутрішній Worker-потік, що послідовно виконує команди з черги.
     */
    private class Worker implements Runnable {
        /**
         * Основний цикл Worker-потоку: бере команди з черги та виконує їх.
         * Завершується після отримання сигналу {@link #shutdown()}.
         */
        @Override
        public void run() {
            while (!shutdown) {
                WorkerCommand cmd = take();
                if (cmd != null)
                    cmd.execute();
            }
            System.out.println("Потік завершено.");
        }
    }
}