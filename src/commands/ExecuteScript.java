package commands;

import utility.Console;
import utility.Request;

/**
 * Класс команды для выполнения скрипта из файла
 */
public class ExecuteScript extends Command {
    /** Консоль */
    private final Console console;

    /**
     * Конструктор
     *
     * @param console консоль
     */
    public ExecuteScript(Console console) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     *Исполнение команды
     * @param arguments массив с аргументами
     * @return возвращает ответ о выполнении команды
     */
    @Override
    public Request apply(String[] arguments) {
        if (arguments[1].isEmpty()) return new Request(Type.SCRIPT, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new Request(Type.SCRIPT, true, "Выполнение скрипта '" + arguments[1] + "'...");
    }
}