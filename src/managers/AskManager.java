package managers;

import models.Coordinates;
import models.FuelType;
import models.Vehicle;
import utility.Console;

import java.time.LocalDate;
import java.util.NoSuchElementException;


/**
 * Класс менеджера  по запросам данных из командной строки
 */
public class AskManager {
    /**
     * исключение для выхода из цикла опроса
     */
    public static class AskBreak extends Exception {
    }

    public static Vehicle askVehicle(Console console, long id) throws AskBreak {
        try {
            console.print("name: ");
            String name;
            while (true) {
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.equals("")) break;
                console.print("name: ");
            }

            var coordinates = askCoordinates(console);

            console.print("enginePower: ");
            Integer enginePower;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    enginePower = Integer.parseInt(line);
                    if (enginePower > 0) break;
                } catch (NumberFormatException e) {
                }
                console.print("enginePower: ");
            }

            console.print("numberOfWheels: ");
            Long numberOfWheels;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    numberOfWheels = Long.parseLong(line);
                    if (numberOfWheels > 0) break;
                } catch (NumberFormatException e) {
                }
                console.print("numberOfWheels: ");
            }

            console.print("fuelConsumption: ");
            int fuelConsumption;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    fuelConsumption = Integer.parseInt(line);
                    if (fuelConsumption > 0) break;
                } catch (NumberFormatException e) {
                }
                console.print("fuelConsumption: ");
            }

            var fuelType = askFuelType(console);

            LocalDate creationDate = LocalDate.now(); // Используем текущую дату как дату создания

            return new Vehicle(id, name, coordinates, creationDate, enginePower, numberOfWheels, fuelConsumption, fuelType);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }




// Остальные методы, необходимые для работы askVehicle, например, Console, CollectionManager, AskBreak и т.д.


    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {

            console.print("coordinates.x: ");
            int x;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        x = Integer.parseInt(line);
                        if (x > -91) break;
                    } catch (NumberFormatException e) {
                    }
                }
                console.print("coordinates.x: ");
            }
            console.print("coordinates.y: ");
            int y;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        y = (int) Long.parseLong(line);
                        if (y <= 638) break;
                    } catch (NumberFormatException e) {
                    }
                }
                console.print("coordinates.y: ");
            }

            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static FuelType askFuelType(Console console) throws AskBreak {
        try {
            console.print("FuelType (" + FuelType.names() + "): ");
            FuelType r;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        r = FuelType.valueOf(line.toUpperCase());
                        break;
                    } catch (IllegalArgumentException e) {
                        console.print("Неверный FuelType. Введите снова: ");
                    }
                }
                else {
                    r = null;
                    break;
                }
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}