/**
 * Текстова реалізація {@link ItemView}
 * Виводить об'єкт {@link Item}
 */
public class TextItemView implements ItemView {
    /**
     * Виводить рядком об'єкт {@link Item} у консоль
     * 
     * @param item об'єкт для виводу у консоль
     */
    @Override
    public void show(Item item) {
        System.out.println(item);
    }
}