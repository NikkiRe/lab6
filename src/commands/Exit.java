package commands;

import utility.Console;
import utility.Request;

public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Request(Type.EXIT, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");

        return new Request(Type.EXIT);
    }
}