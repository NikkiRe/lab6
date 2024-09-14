package commands;

import managers.CollectionManager;
import utility.Console;
import utility.Request;

/**
 * Класс команды для очищения коллекции
 */
public class Clear extends Command {
    /**
     * Консоль
     */
    private final Console console;

    /**
     * Конструктор
     *
     * @param console           консоль
     */
    public Clear(Console console) {
        super("clear", "очистить коллекцию");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Request(Type.CLEAR, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");

        return new Request(Type.CLEAR);
    }
}