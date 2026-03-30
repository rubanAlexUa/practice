public class AddItemCommand implements Command {
    private Calc calc;
    private double current, u1, u2, u3;

    public AddItemCommand(Calc calc, double current, double u1, double u2, double u3) {
        this.calc = calc;
        this.current = current;
        this.u1 = u1;
        this.u2 = u2;
        this.u3 = u3;
    }

    // створюємо запис
    @Override
    public void execute() {
        calc.init(current, u1, u2, u3);
    }

    // видаляємо останній доданий запис
    @Override
    public void undo() {
        calc.removeLast();
    }
}
