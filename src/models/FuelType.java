package models;

import java.io.Serializable;

public enum FuelType implements Serializable {
    GASOLINE,
    DIESEL,
    ELECTRIC,
    HYBRID;

    private static final long serialVersionUID = 1L;
    public static String names() {
        StringBuilder names = new StringBuilder();
        for (FuelType type : values()) {
            names.append(type.name()).append(", ");
        }
        return names.substring(0, names.length() - 2); // Убираем последнее ", "
    }
}
