/**
 * Інтерфейс фабрики для створеня об'єкту {@link ItemView}
 */
public interface ViewFactory {
    /**
     * Створює та повертає новий екземпляр {@link ItemView}
     * 
     * @return {@link ItemView}
     */
    ItemView createView();
}