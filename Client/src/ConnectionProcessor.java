import utility.Request;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

/**
 * Класс для общения с сервером
 */
public class ConnectionProcessor {
    /**
     * Адрес сервера
     */
    private String address;

    /**
     * Порт сервера
     */
    private int port;

    /**
     * Единственный конструктор
     * @param address адрес сервера
     * @param port порт сервера
     */
    public ConnectionProcessor(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Метод для сериализации запроса
     * @param request запрос, который нужно сериализовать
     * @return сериализованные данные
     */
    private String serializeRequest(Request request) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(request);
        objectOutputStream.flush();

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(byteArray);
    }

    /**
     * Основная функция для отправки запроса и получения ответа
     * @param request запрос
     * @return ответ сервера в формате строки
     */
    public String process(Request request) {
        try {
            Socket socket = new Socket(address, port);
            socket.setSoTimeout(5000);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(serializeRequest(request));

            String response = in.readLine();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response += "\n" + responseLine;
            }

            out.close();
            in.close();
            socket.close();
            return response;
        } catch (Exception e) {
            return "Не удаётся установить связь с сервером.";
        }
    }
}
