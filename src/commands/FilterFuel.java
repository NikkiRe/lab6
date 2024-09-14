package commands;

import utility.Console;
import utility.Request;

/**
 * Класс команды для вывода конкретной коллекции по имени
 */

public class FilterFuel extends Command {
    /**
     * Консоль
     */
    private final Console console;

    public FilterFuel(Console console) {
        super("filter_consumption fuelConsumption", "вывести элементы, значение поля fuelConsumption которых больше заданного");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {

        if (arguments[1].isEmpty() || arguments.length > 2)
            return new Request(Type.FILTER_FUEL, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        int fuelConsuption;
        try {
            fuelConsuption = Integer.parseInt(arguments[1]);
        }
        catch (Exception e){return new Request(Type.FILTER_FUEL, false, "Аргумент не является числом! '" );}
        return new Request(Type.FILTER_FUEL, null, fuelConsuption, true, "");

    }
}
