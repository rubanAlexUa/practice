/**
 * Шаблону Worker Thread.
 */
public interface WorkerCommand {
    void execute();

    boolean running();
}