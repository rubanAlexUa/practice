/**
 * Інтерфейс для відображення {@link Item}
 */
public interface ItemView {
    /**
     * Виводить інформацію про переданий об'єкт {@link Item}
     * 
     * @param item відображуваний об'єкт
     */
    void show(Item item);
}