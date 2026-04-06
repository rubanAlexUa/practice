import java.io.*;

/**
 * Клас для обрахунків та виконання дій, як-от: Вивід результатів,
 * створення нового об'єкту, збереження у бінарний файл та відновлення змінних
 * із файлу.
 */
public class Calc {

    /**
     * Ім'я файлу для збереження значень, відповідно і серіалізації {@link Item}.
     */
    private static final String FNAME = "Item.bin";

    /**
     * Об'єкт, що зберігає поточні значення обчислень.
     */
    private Item result;

    /**
     * Конструктор, що створює новий {@link Calc}, із порожнім об'єктом результату.
     */
    public Calc() {
        result = new Item();
    }

    /**
     * Повертає поточний результат обчислень.
     * 
     * @return об'єкт {@link Item} із новими полями
     */
    public Item getResult() {
        return result;
    }

    /**
     * Метод для обчислення опору, та встановлення нових значень змінних.
     * 
     * @param current сила струму (А)
     * @param u1      перша напруга (В)
     * @param u2      друга напруга (В)
     * @param u3      третя напруга (В)
     * @return новий опір (Ом)
     */
    public double init(double current, double u1, double u2, double u3) {
        result.setCurrent(current);
        result.setU1(u1);
        result.setU2(u2);
        result.setU3(u3);
        double resistance = (u1 + u2 + u3) / current;
        result.setResistance(resistance);
        return resistance;
    }

    /**
     * Виводить поточні значення об'єкту {@link Item} у консоль
     */
    public void show() {
        System.out.println(result);
    }

    /**
     * Зберігає поточний об'єкт {@link Item} у бінарний файл {@value #FNAME}.
     *
     * @throws Exception якщо не вдалося записати файл
     */
    public void save() throws Exception {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FNAME))) {
            os.writeObject(result);
        }
    }

    /**
     * Виймає значення із бінарного файлу {@value #FNAME}, та оновлює об'єкт
     * {@link Item}
     * 
     * @throws Exception якщо файл пошкоджено або не знайдено
     */
    public void restore() throws Exception {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME))) {
            result = (Item) is.readObject();
        }
    }
}