import commands.Command;
import commands.Type;
import managers.AskManager;
import models.Vehicle;
import utility.Console;
import utility.Request;

/**
 * Класс команды для добавления нового элемента в коллекцию
 */
public class InsertIndex extends Command {
    /**
     * Консоль
     */
    private final Console console;
    ConnectionProcessor processor;

    /**
     * Конструктор
     *
     * @param console           консоль
     */
    public InsertIndex(Console console, ConnectionProcessor processor) {
        super("insert_index {id}", "добавить новый элемент с заданным ключом");
        this.console = console;
        this.processor = processor;
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
            if (arguments[1].isEmpty())
                return new Request(Type.INSERT, false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'\n");
            long key = -1;
            try {
                key = Long.parseLong(arguments[1].trim());
            } catch (NumberFormatException e) {
                return new Request(Type.INSERT, false, "Ключ не распознан");
            }
            String checkID = processor.process(new Request(Type.CHECK_ID, null, key, true, ""));
            if (checkID.charAt(0) == 'Y')
                return new Request(Type.INSERT, false, "Элемент с таким ключем уже существует\n");

            console.println("* Создание нового Vehicle:");
            Vehicle v = AskManager.askVehicle(console, key);

            if (v != null && v.validate()) {
                return new Request(Type.INSERT, v, 0, true, "");
            } else return new Request(Type.INSERT, false, "Поля Vehicle не валидны! Vehicle не создан!");
        } catch (AskManager.AskBreak e) {
            return new Request(Type.INSERT, false, "Отмена...");
        }
    }
}