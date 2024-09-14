package commands;

import managers.AskManager;
import managers.CollectionManager;
import models.Vehicle;
import utility.Console;
import utility.Request;

import java.util.Objects;

public class Add extends Command  {
    private final Console console;

    /**
     * Конструктор
     *
     * @param console           консоль
     * @param collectionManager менеджер коллекции
     */
    public Add(Console console) {
        super("add", "добавить новый элемент");
        this.console = console;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает ответ о выполнении команды
     */
    @Override
    public Request apply(String[] arguments) {
        try {
            if (!Objects.equals(arguments[1], "") || arguments.length > 2) {return new Request(Type.ADD, false, "Тут не указывается Id");}
            console.println("* Создание нового Vehicle:");
            Vehicle v = AskManager.askVehicle(console, 0);

            if (v != null && v.validate()) {
                return new Request(Type.ADD, v, 0, true, "");
            } else {
                return new Request(Type.ADD, false, "Поля Vehicle не валидны! Vehicle не создан!");
            }
        } catch (AskManager.AskBreak e) {
            return new Request(Type.ADD, false, "Отмена...");
        }
    }
}
