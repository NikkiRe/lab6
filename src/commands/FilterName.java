package commands;

import managers.CollectionManager;
import utility.Console;
import utility.Request;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс команды для вывода конкретной коллекции по имени
 */

public class FilterName extends Command {
    /**
     * Консоль
     */
    private final Console console;

    public FilterName(Console console) {
        super("filter_contains_name name", "вывести элементы, значение поля name которых содержит");
        this.console = console;
    }
    @Override
    public Request apply(String[] arguments) {
        if (arguments[1].isEmpty())
            return new Request(Type.FILTER_NAME, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");
        StringBuilder name = new StringBuilder();
        for (var s : Arrays.copyOfRange(arguments, 1, arguments.length))
            name.append(s);

        return new Request(Type.FILTER_NAME, true, name.toString());
    }
}
