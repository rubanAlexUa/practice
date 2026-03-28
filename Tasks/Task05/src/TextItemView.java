/**
 * Виводить результати у вигляді текстової таблиці.
 */
public class TextItemView implements ItemView {

    private boolean showOct;
    private boolean showHex;

    /** Конструктор за замовчуванням */
    public TextItemView() {
        this.showOct = true;
        this.showHex = true;
    }

    /**
     * Конструктор з параметрами — overloading
     * 
     * @param showOct показувати OCT
     * @param showHex показувати HEX
     */
    public TextItemView(boolean showOct, boolean showHex) {
        this.showOct = showOct;
        this.showHex = showHex;
    }

    /** Виводить заголовок таблиці */
    public void showHeader() {
        String line = "+----------+----------+----------+----------+----------+";
        String header = "| I (A)    | U1 (V)   | U2 (V)   | U3 (V)   | R (Ohm)  |";
        if (showOct) {
            line += "----------+";
            header += " OCT      |";
        }
        if (showHex) {
            line += "----------+";
            header += " HEX      |";
        }
        System.out.println(line);
        System.out.println(header);
        System.out.println(line);
    }

    /** Виводить нижню лінію */
    public void showFooter() {
        String line = "+----------+----------+----------+----------+----------+";
        if (showOct)
            line += "----------+";
        if (showHex)
            line += "----------+";
        System.out.println(line);
    }

    /**
     * Виводить один рядок таблиці — overriding
     * 
     * @param item об'єкт для виводу
     */
    @Override
    public void show(Item item) {
        long r = Math.round(item.getResistance());
        String row = String.format("| %-8.2f | %-8.2f | %-8.2f | %-8.2f | %-8d |",
                item.getCurrent(), item.getU1(), item.getU2(), item.getU3(), r);
        if (showOct)
            row += String.format(" %-8s |", Long.toOctalString(r));
        if (showHex)
            row += String.format(" %-8s |", Long.toHexString(r).toUpperCase());
        System.out.println(row);
    }
}