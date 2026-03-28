import java.util.ArrayList;

public class MacroCommand implements Command {
    private ArrayList<Command> commands = new ArrayList<>();

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    @Override
    public void execute() {
        for (Command cmd : commands)
            cmd.execute();
    }

    // скасування — скасовуємо всі команди у зворотньому порядку
    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--)
            commands.get(i).undo();
    }
}
