package models;

import utility.Element;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Vehicle extends Element implements Serializable {
    private static final long serialVersionUID = 1L;
    public Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private LocalDate creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer enginePower; // Поле не может быть null, Значение поля должно быть больше 0
    private Long numberOfWheels; // Поле не может быть null, Значение поля должно быть больше 0
    private int fuelConsumption; // Значение поля должно быть больше 0
    private FuelType fuelType; // Поле может быть null

    public Vehicle(Long id, String name, Coordinates coordinates, LocalDate creationDate, Integer enginePower, Long numberOfWheels, int fuelConsumption, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.fuelConsumption = fuelConsumption;
        this.fuelType = fuelType;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public Long getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(Long numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public boolean validate() {
        return true;
    }

    public static Vehicle fromArray(String[] a) {
        try {
            Long id = Long.parseLong(a[0]);
            String name = a[1];
            Coordinates coordinates = new Coordinates(a[2]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            LocalDate creationDate = LocalDate.parse(a[3], formatter);
            Integer enginePower = Integer.parseInt(a[4]);
            Long numberOfWheels = Long.parseLong(a[5]);
            int fuelConsumption = Integer.parseInt(a[6]);
            FuelType fuelType = Objects.equals(a[7], "null") ? null : FuelType.valueOf(a[7].toUpperCase());

            return new Vehicle(id, name, coordinates, creationDate, enginePower, numberOfWheels, fuelConsumption, fuelType);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] toArray(Long id, Vehicle vehicle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new String[]{
                vehicle.id.toString(),
                vehicle.name,
                "\"" + vehicle.coordinates.getX() + ";" + vehicle.coordinates.getY() + "\"",
                vehicle.creationDate.format(formatter),
                vehicle.enginePower.toString(),
                vehicle.numberOfWheels.toString(),
                Integer.toString(vehicle.fuelConsumption),
                vehicle.fuelType != null ? vehicle.fuelType.name() : "null"
        };
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle that = (Vehicle) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vehicle{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates.getX() + ", " + coordinates.getY() + "\", " +
                "\"creationDate\": \"" + creationDate + "\", " +
                "\"enginePower\": " + enginePower + ", " +
                "\"numberOfWheels\": " + numberOfWheels + ", " +
                "\"fuelConsumption\": " + fuelConsumption + ", " +
                "\"fuelType\": " + fuelType + "}";
    }

    @Override
    public int compareTo(Element o) {
        return this.id.compareTo(o.getId());
    }
}
