public interface Queue {
    void put(WorkerCommand cmd);

    WorkerCommand take();
}
