import java.util.*;

/**
 * Інтерфейс для спостерігачів колекції.
 * Обидва спостерігачі реалізують цей метод посвоєму.
 */
interface CalcObserver {
    void update(List<Item> items);
}

/**
 * Рахує середнє значення опору по всій колекції.
 * avg потім використовується в MainGUI для червоної лінії на графіку.
 */
@ObserverRole("stats")
class StatsObserver implements CalcObserver {

    double avg;

    @Override
    public void update(List<Item> items) {
        avg = items.stream().mapToDouble(Item::getResistance).average().orElse(0);
        System.out.println("[Stats] середнє: " + String.format("%.2f", avg));
    }
}

/**
 * Виводить опори відсортовані від меншого до більшого.
 * Оригінальний список не чіпає.
 */
@ObserverRole("sort")
class SortObserver implements CalcObserver {

    @Override
    public void update(List<Item> items) {
        System.out.print("[Sort] ");
        items.stream()
                .mapToDouble(Item::getResistance)
                .sorted()
                .forEach(r -> System.out.printf("%.1f ", r));
        System.out.println();
    }
}