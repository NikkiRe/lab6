import commands.*;
import managers.CommandManager;
import utility.Console;
import utility.Request;
import utility.StandartConsole;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс запуска клиентского приложения
 */
public class Main {
    /**
     * Консоль
     */
    private static Console console;

    /**
     * Объект для хранения команд
     */
    private static CommandManager commandManager;

    /**
     * Объект для отправки запросов и принятия ответов
     */
    private static ConnectionProcessor processor;

    /**
     * Основной метод с главным циклом
     * @param args аргументы, в программе не используются
     */
    public static void main(String[] args) {
        console = new StandartConsole();
        processor = new ConnectionProcessor("localhost", 8686);

        commandManager = new CommandManager();
        commandManager.register("help", new Help(console, commandManager));
        commandManager.register("info", new Info(console));
        commandManager.register("show", new Show(console));
        commandManager.register("add", new Add(console));
        commandManager.register("insert_index", new InsertIndex(console, processor));
        commandManager.register("update", new Update(console, processor));
        commandManager.register("clear", new Clear(console));
        commandManager.register("remove_id", new RemoveId(console));
        commandManager.register("execute_script", new ExecuteScript(console));
        commandManager.register("filter_contains_name", new FilterName(console));
        commandManager.register("filter_consumption", new FilterFuel(console));
        commandManager.register("remove_lower", new RemoveLower(console));
        commandManager.register("print_unique_fuel_type", new PrintType(console));
        commandManager.register("exit", new Exit(console));
        String[] userCommand = {"", ""};

        while (true) {
            if (console.isCanReadln()) {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                if (userCommand[0].equals(""))
                    continue;
                var command = commandManager.getCommands().get(userCommand[0]);
                if (command == null) {
                    console.println("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки\n");
                    continue;
                }
                Request request = command.apply(userCommand);
                if (request.isSuccess()) {
                    if (request.getCommandType() == Type.EXIT)
                        break;
                    switch (request.getCommandType()) {
                        case SHOW, INFO, CLEAR, REMOVE_ID, FILTER_NAME, FILTER_FUEL, REMOVE_LOWER, PRINT_FUEL, ADD, INSERT, UPDATE -> {
                            console.println(processor.process(request));
                            break;
                        }
                        case SCRIPT -> {
                            console.println(executeScript(userCommand[1]).getMessage());
                            break;
                        }
                        default -> {
                        }
                    }
                }
                if (request.getCommandType() != Type.FILTER_NAME && request.getCommandType() != Type.SCRIPT)
                    console.println(request.getMessage());
                else
                    console.println("");
            }
        }
    }

    /**
     * Класс для работы со скриптом
     * @param argument название файла скрипта
     * @return возвращает результат работы скрипта
     */
    private static Request executeScript(String argument) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new Request(Type.SCRIPT, false, "Файл не существет!");
        if (!Files.isReadable(Paths.get(argument))) return new Request(Type.SCRIPT, false, "Прав для чтения нет!");

        try (Scanner scriptScanner = new Scanner(new File(argument))) {

            Request commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }

                commandStatus = launchCommand(userCommand);
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(userCommand[0] + "\n" + commandStatus.getMessage() + "\n");
            } while (commandStatus.getCommandType() != Type.EXIT && scriptScanner.hasNext());

            console.selectConsoleScanner();
            if (commandStatus.getCommandType() == Type.EXIT && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Проверьте скрипт на корректность введенных данных!\n");
            }

            return new Request(commandStatus.getCommandType(), commandStatus.isSuccess(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new Request(Type.SCRIPT, false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new Request(Type.SCRIPT, false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        }
        return new Request(Type.SCRIPT);
    }

    /**
     * ЗВыполняет одну строку из скрипта
     * @param userCommand принимает разбитую строку
     * @return возвращает результат выполнения строки
     */
    private static Request launchCommand(String[] userCommand) {
        if (userCommand[0].equals("")) return new Request(Type.SCRIPT);
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) {
            return new Request(Type.SCRIPT, false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки\n");
        }

        String out = "";
        Request request = command.apply(userCommand);
        if (request.isSuccess()) {
            if (request.getCommandType() == Type.EXIT)
                return new Request(Type.EXIT);
            switch (request.getCommandType()) {
                case SHOW, INFO, CLEAR, REMOVE_ID, FILTER_NAME, FILTER_FUEL, REMOVE_LOWER, PRINT_FUEL, ADD, INSERT, UPDATE -> {
                    out += processor.process(request) + "\n";
                    break;
                }
                case SCRIPT -> {
                    return new Request(Type.SCRIPT, false, "Нельзя запускать скрипт из скрипта\n");
                }
                default -> {
                }
            }
        }
        if (request.getCommandType() != Type.FILTER_NAME)
            out += request.getMessage();
        else
            out += "";
        return new Request(Type.SCRIPT, request.isSuccess(), out);
    }
}