/**
 * Інтерфейс для команд, що виконуються у фоновому потоці (Worker
 * Thread).
 */
public interface WorkerCommand {
    /**
     * Виконує команду у фоновому потоці.
     */
    void execute();

    /**
     * Повертає {@code true} якщо команда ще виконується, {@code false} якщо
     * завершена.
     *
     * @return стан виконання команди
     */
    boolean running();
}