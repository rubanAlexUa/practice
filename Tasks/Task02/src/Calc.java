import java.io.*;

/**
 * Обчислює опір.
 */
public class Calc {

    private static final String FNAME = "Item.bin";

    /** Результат обчислень */
    private Item result;

    /** Конструктор */
    public Calc() {
        result = new Item();
    }

    public Item getResult() {
        return result;
    }

    /**
     * Обчислює R = (U1+U2+U3) / I
     */
    public double init(double current, double u1, double u2, double u3) {
        result.setCurrent(current);
        result.setU1(u1);
        result.setU2(u2);
        result.setU3(u3);
        double r = (u1 + u2 + u3) / current;
        result.setResistance(r);
        return r;
    }

    /** Виводить результат */
    public void show() {
        System.out.println(result);
    }

    /**
     * Зберігає у файл
     */
    public void save() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FNAME));
        os.writeObject(result);
        os.flush();
        os.close();
    }

    /* Відновлює з файлу */
    public void restore() throws Exception {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME));
        result = (Item) is.readObject();
        is.close();
    }
}