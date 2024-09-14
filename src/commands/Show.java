package commands;

import utility.Console;
import utility.Request;

public class Show extends Command {
    private final Console console;

    public Show(Console console) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Request(Type.SHOW, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");

        return new Request(Type.SHOW);
    }
}