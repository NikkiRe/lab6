package managers;


import au.com.bytecode.opencsv.*;
import models.Vehicle;
import utility.Console;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    private String collection2CSV(LinkedList<Vehicle> collection) {
        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw, ';');
            for (Vehicle v : collection) {
                csvWriter.writeNext(Vehicle.toArray((long) v.getId(), v));
            }
            String csv = sw.toString();
            return csv;
        } catch (Exception e) {
            console.printError("Ошибка сериализации");
            console.printError(e.getMessage());
            return null;
        }
    }

    public void writeCollection(LinkedList<Vehicle> collection) {
        var csv = collection2CSV(collection);
        if (csv == null) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(csv);
            writer.flush();
            console.println("Коллекция успешно сохранена в файл!");
        } catch (FileNotFoundException | NullPointerException e) {
            console.printError("Файл не найден");
        } catch (IOException e) {
            console.printError("Неожиданная ошибка сохранения");
        }
    }

    private LinkedList<Vehicle> CSV2collection(String s) {
        try {
            StringReader sr = new StringReader(s);
            CSVReader csvReader = new CSVReader(sr, ';');
            LinkedList<Vehicle> ds = new LinkedList<>();
            String[] record = null;
            while ((record = csvReader.readNext()) != null) {
                if (record.length == 8) {
                    long key = Long.parseLong(record[0]);
                    Vehicle d = Vehicle.fromArray(Arrays.copyOfRange(record, 0, 8));
                    if (d.validate() && (d.getId() == key)) {
                        ds.add(d);
                    } else {
                        console.println("Найдено невалидное значение!"); // если надо принтить на каждый невалидный
                    }
                }
            }
            csvReader.close();
            return ds;
        } catch (Exception e) {
            console.printError("Ошибка десериализации");
            console.printError(e.getMessage());
            return null;
        }
    }

    public void readCollection(LinkedList<Vehicle> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                var s = new StringBuilder();
                while (scanner.hasNextLine()) {
                    s.append(scanner.nextLine()).append("\n");
                }
                collection.clear();
                collection.addAll(CSV2collection(s.toString()));

                console.println("Коллекция успешно загружена!\n");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!\n");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!\n");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!\n");
        }
        collection = new LinkedList<Vehicle>();
    }
}