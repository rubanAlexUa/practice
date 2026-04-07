/**
 * Клас для додавання нового запису {@link Item} до {@link Calc}.
 * Реалізує {@link Command} з підтримкою скасування.
 */
public class AddItemCommand implements Command {
    /**
     * Екземпляр {@link Calc} над яким виконується команда
     */
    private Calc calc;

    /**
     * Сила струму (А) та три напруги (В)
     */
    private double current, u1, u2, u3;

    /**
     * Конструктор для створення запису.
     * 
     * @param calc    {@link Calc} до якого буде додано запис
     * @param current сила струму (А)
     * @param u1      перша напруга (В)
     * @param u2      друга напруга (В)
     * @param u3      третя напруга (В)
     */
    public AddItemCommand(Calc calc, double current, double u1, double u2, double u3) {
        this.calc = calc;
        this.current = current;
        this.u1 = u1;
        this.u2 = u2;
        this.u3 = u3;
    }

    /**
     * Метод для створення запису {@link Item} у {@link Calc}.
     */
    @Override
    public void execute() {
        calc.init(current, u1, u2, u3);
    }

    /**
     * Метод видалення останнього запису {@link Item} із {@link Calc}
     */
    @Override
    public void undo() {
        calc.removeLast();
    }
}
