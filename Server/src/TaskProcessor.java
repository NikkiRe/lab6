import commands.Type;
import managers.CollectionManager;
import models.Vehicle;
import utility.Request;

import java.util.Objects;

public class TaskProcessor {
    private CollectionManager collectionManager;

    public TaskProcessor(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String process(Request request) {
        switch (request.getCommandType()) {
            case SHOW -> { return collectionManager.toString(x -> true); }
            case INFO -> {
                var s = "Сведения о коллекции:\n";
                s += " Тип: " + collectionManager.getCollection().getClass().toString() + "\n";
                s += " Количество элементов: " + collectionManager.getCollection().size() + "\n";
                return s;
            }
            case CLEAR -> {
                collectionManager.clearCollection();
                return "Коллекция очищена!";
            }
            case REMOVE_ID -> {
                if (!collectionManager.remove(request.getNumber()))
                    return "Элемент с таким id не найден";
                return "Vehicle успешно удалена!";
            }
            case FILTER_NAME -> {
                return collectionManager.toString(x -> Objects.equals(x.getName(), request.getMessage()));
            }
            case FILTER_FUEL -> {
                return collectionManager.toString(x -> x.getFuelConsumption() > request.getNumber());
            }
            case REMOVE_LOWER -> {
                for(var e: collectionManager.getCollection().stream().filter(e -> e.id < request.getNumber()).toList())
                    collectionManager.remove(e.getId());
                return "Элементы с ID меньше " + request.getNumber() + " успешно удалены!";
            }
            case PRINT_FUEL-> {
                return collectionManager.uniqueType();
            }
            case ADD -> {
                long freeID = collectionManager.getFreeId();
                request.getVehicle().id = freeID;
                collectionManager.add(request.getVehicle());
                return "Vehicle успешно добавлен!";
            }
            case CHECK_ID -> {
                if (collectionManager.getById(request.getNumber()) == null)
                    return "No";
                return "Yes";
            }
            case INSERT -> {
                collectionManager.add(request.getVehicle());
                return "Vehicle успешно добавлен!";
            }
            case UPDATE -> {
                collectionManager.remove(request.getVehicle().getId());
                collectionManager.add(request.getVehicle());
                return "Обновлено!";
            }
            default -> { return "Не удалось выполнить запрос"; }
        }
    }
}
