import java.util.ArrayList;

/**
 * Макрокоманда для реалізації паттерну {@link Command}, що об'єднує команди
 * в одну.
 * Скасовування виконується до всіх команд зі списку.
 */
public class MacroCommand implements Command {

    /**
     * Список команд, що входять у макрокоманду.
     */
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * Додає команди до списку макрокоманди
     * 
     * @param cmd додана команда
     */
    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    /**
     * Виконує усі команди зі списку макрокоманди.
     */
    @Override
    public void execute() {
        for (Command cmd : commands)
            cmd.execute();
    }

    /**
     * Скасовує усі команди зі списку, у зворотньому порядку.
     */
    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--)
            commands.get(i).undo();
    }
}
