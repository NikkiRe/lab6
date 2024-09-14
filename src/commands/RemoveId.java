package commands;

import managers.CollectionManager;
import utility.Console;
import utility.Request;

/**
 * Класс команды для удаления элемента из коллекции по его ключу
 */
public class RemoveId extends Command {
    /**
     * Консоль
     */
    private final Console console;

    /**
     * Конструктор
     *
     * @param console           консоль
     * @param collectionManager менеджер коллекции
     */
    public RemoveId(Console console) {
        super("remove_id <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
    }

    /**
     * Валидация
     *
     * @param arguments массив с аргументами
     * @return возвращает запрос на выполнение команды
     */
    @Override
    public Request apply(String[] arguments) {
        if (arguments[1].isEmpty())
            return new Request(Type.REMOVE_ID, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");
        try {
            return new Request(Type.REMOVE_ID, null, Integer.parseInt(arguments[1]), true, "");
        } catch (NumberFormatException e) {
            return new Request(Type.REMOVE_ID, false, "Ключ не распознан\n");
        }
    }
}