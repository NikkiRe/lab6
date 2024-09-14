import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

import commands.Exit;
import commands.Help;
import commands.Save;
import commands.Type;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Console;
import utility.Request;
import utility.StandartConsole;

/**
 * Основной класс серверного приложения
 */
public class Main {
    /**
     * Цикл серверного приложения
     * @param args неиспользуемые аргументы
     * @throws IOException может вызвать ошибку, если при запуске не получилось открыть порты
     */
    public static void main(String[] args) throws IOException {
        Console console = new StandartConsole();
        var dumpManager = new DumpManager("ploxo.csv", console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
            System.exit(1);
        }

        CommandManager commandManager = new CommandManager();
        commandManager.register("help", new Help(console, commandManager));
        commandManager.register("save", new Save(console, collectionManager, dumpManager));
        commandManager.register("exit", new Exit(console));
        String[] userCommand = {"", ""};

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8686));
        serverSocketChannel.configureBlocking(false);
        TaskProcessor taskProcessor = new TaskProcessor(collectionManager);
        RequestProcessor receiver = new RequestProcessor(serverSocketChannel, taskProcessor);
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
                if (request.isSuccess()){
                    if (request.getCommandType() == Type.EXIT) {
                        collectionManager.saveCollection();
                        break;
                    }
                    switch (request.getCommandType()) {
                        default -> {}
                    }
                }
                console.println(request.getMessage());
            }

            receiver.receive();
        }
        console.println("Программа завершена");
    }
}
