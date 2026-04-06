import java.io.Serializable;

/**
 * Клас для збереження значень сили струму, напруг та опору
 * 
 * @author Alex Ruban
 */
public class Item implements Serializable {

    /**
     * Ідентифікатор версії серіалізації
     * Необхідний для сумісності між версіями класів
     */
    private static final long serialVersionUID = 1L;

    /**
     * Сила струму (А)
     */
    private double current;

    /**
     * Напруга у провідниках (В)
     */
    private double u1, u2, u3;

    /**
     * Загальний опір (Ом)
     */
    private transient double resistance;

    /**
     * Конструктор, що створює пустий об'єкт із нульовими значеннями змінних
     */
    public Item() {
    }

    /**
     * Повертає значення сили струму
     * 
     * @return сила струму (А)
     */
    public double getCurrent() {
        return current;
    }

    /**
     * Сеттер сили струму, приймає як параметр значення для встановення
     * 
     * @param current сила струму (А)
     */
    public void setCurrent(double current) {
        this.current = current;
    }

    /**
     * Геттер першої напруги
     * 
     * @return значення першої напруги (В)
     */
    public double getU1() {
        return u1;
    }

    /**
     * Сеттер першої напруги
     * 
     * @param u1 перша напруга (В)
     */
    public void setU1(double u1) {
        this.u1 = u1;
    }

    /**
     * Геттер другої напруги
     * 
     * @return значення другої напруги (В)
     */
    public double getU2() {
        return u2;
    }

    /**
     * Сеттер другої напруги (В)
     * 
     * @param u2 друга напруга (В)
     */
    public void setU2(double u2) {
        this.u2 = u2;
    }

    /**
     * Геттер третьої напруги (В)
     * 
     * @return значення третьої напруги (В)
     */
    public double getU3() {
        return u3;
    }

    /**
     * Сеттер третьої напруги (В)
     * 
     * @param u3 третя напруга (В)
     */
    public void setU3(double u3) {
        this.u3 = u3;
    }

    /**
     * Геттер опору (Ом)
     * 
     * @return опір (Ом)
     */
    public double getResistance() {
        return resistance;
    }

    /**
     * Сеттер опору (Ом)
     * 
     * @param resistance опір (Ом)
     */
    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    /**
     * Метод, що перераховує опір на основі поточних значень.
     */
    public void recalculate() {
        if (current != 0)
            resistance = (u1 + u2 + u3) / current;
    }

    /**
     * Повертає об'єкт у рядок зі значеннями усіх змінних,
     * а також опором у 8-ому та 16-ому форматі
     * 
     * @return значення усіх змінних у відформатованому вигляді
     */
    @Override
    public String toString() {
        long r = Math.round(resistance);
        return String.format(
                "I=%.2f A, U1=%.2f V, U2=%.2f V, U3=%.2f V\n" +
                        "R = %d Ohm\n" +
                        "OCT: %s\n" +
                        "HEX: %s",
                current, u1, u2, u3,
                r,
                Long.toOctalString(r),
                Long.toHexString(r).toUpperCase());
    }
}