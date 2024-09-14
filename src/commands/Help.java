package commands;

import utility.Console;
import managers.CommandManager;
import utility.Request;

import java.util.stream.Collectors;

public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public Request apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Request(Type.HELP, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");

        return new Request(Type.HELP, true, commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("")));
    }
}