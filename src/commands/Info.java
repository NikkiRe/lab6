package commands;

import managers.CollectionManager;
import utility.Console;
import utility.Request;

public class Info extends Command {
    private final Console console;

    public Info(Console console) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Request(Type.INFO, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");
        return new Request(Type.INFO);
    }
}