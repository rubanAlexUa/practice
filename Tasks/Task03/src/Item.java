import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    // Струм
    private double current;

    // Напруга
    private double u1, u2, u3;

    // Опір
    private transient double resistance;

    public Item() {
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getU1() {
        return u1;
    }

    public void setU1(double u1) {
        this.u1 = u1;
    }

    public double getU2() {
        return u2;
    }

    public void setU2(double u2) {
        this.u2 = u2;
    }

    public double getU3() {
        return u3;
    }

    public void setU3(double u3) {
        this.u3 = u3;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    // Розрахунок опору
    public void recalculate() {
        if (current != 0)
            resistance = (u1 + u2 + u3) / current;
    }

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