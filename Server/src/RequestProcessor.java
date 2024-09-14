import utility.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Base64;

/**
 * Класс для работы с запросами
 */
public class RequestProcessor {
    /**
     * Канал для подключений
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * Объект для обработки запросов
     */
    private TaskProcessor taskProcessor;

    /**
     * Конструктор
     * @param serverSocketChannel канал для подключений
     * @param taskProcessor объект для обработки запросов
     */
    public RequestProcessor(ServerSocketChannel serverSocketChannel, TaskProcessor taskProcessor) {
        this.serverSocketChannel = serverSocketChannel;
        this.taskProcessor = taskProcessor;
    }

    /**
     * Методл для десериализации запроса
     * @param byteArrayString принимает строку для обработки
     * @return возвращает запрос
     */
    private Request readRequest(String byteArrayString) throws IOException, ClassNotFoundException {
        byte[] restoredByteArray = Base64.getDecoder().decode(byteArrayString);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(restoredByteArray);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Request request = (Request) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return request;
    }

    private void responseSender(ByteBuffer buffer, SocketChannel socketChannel, String response) throws IOException {
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    public void receive() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                ByteBuffer buffer = ByteBuffer.allocate(8192);

                int bytesRead = socketChannel.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    String message = new String(buffer.array(), 0, bytesRead).trim();

                    Request request = readRequest(message);

                    String response = taskProcessor.process(request);

                    responseSender(buffer, socketChannel, response);

                    socketChannel.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
