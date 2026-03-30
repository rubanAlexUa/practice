import java.util.Vector;

/**
 * Реалізує шаблон Worker Thread.
 * Фоновий потік Worker послідовно виконує задачі з черги.
 */
public class CommandQueue implements Queue {

    private final Vector<WorkerCommand> tasks = new Vector<>();
    private boolean waiting = false;
    private boolean shutdown = false;

    public CommandQueue() {
        new Thread(new Worker(), "WorkerThread").start();
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public synchronized void put(WorkerCommand cmd) {
        tasks.add(cmd);
        if (waiting)
            notifyAll();
    }

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

    private class Worker implements Runnable {
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