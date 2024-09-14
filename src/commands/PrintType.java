package commands;

import managers.CollectionManager;
import utility.Console;
import utility.Request;

public class PrintType extends Command {

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
    public PrintType(Console console) {
        super("print_unique_fuel_type", "вывести уникальные значения поля fuelType всех элементов в коллекции");
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
        if (!arguments[1].isEmpty())
            return new Request(Type.PRINT_FUEL, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        return new Request(Type.PRINT_FUEL);
    }
}
