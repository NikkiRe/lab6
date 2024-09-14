package commands;

import managers.CollectionManager;
import models.Vehicle;
import utility.Console;
import utility.Request;

import java.util.LinkedList;
import java.util.List;

public class RemoveLower extends Command {

    private final Console console;

    /**
     * Конструктор
     *
     * @param console           консоль
     * @param collectionManager менеджер коллекции
     */
    public RemoveLower(Console console) {
        super("remove_lower id", "удалить из коллекции все элементы, id которых меньше, чем заданный");
        this.console = console;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами
     * @return возвращает ответ о выполнении команды
     */
    @Override
    public Request apply(String[] arguments) {
        if (arguments.length < 2 || arguments[1].isEmpty()) {
            return new Request(Type.REMOVE_LOWER, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + " <key>'\n");
        }

        long key;
        try {
            key = Long.parseLong(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new Request(Type.REMOVE_LOWER,false, "Id не распознан");
        }

        return new Request(Type.REMOVE_LOWER, null, key, true, "");
    }
}
