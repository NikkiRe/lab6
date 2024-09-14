package utility;

import commands.Type;
import models.Vehicle;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private Type commandType;
    private boolean success;
    private String message;
    private Vehicle vehicle;
    private long number;

    public Request(Type commandType)
    {
        this(commandType, true, "");
    }

    public Request(Type commandType, boolean success, String message) {
        this(commandType, null, 0, success, message);
    }

    public Request(Type commandType, Vehicle vehicle, long number, boolean success, String message) {
        this.commandType = commandType;
        this.success = success;
        this.message = message;
        this.vehicle = vehicle;
        this.number = number;
    }

    public Type getCommandType() {
        return commandType;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getNumber() {
        return number;
    }
}
