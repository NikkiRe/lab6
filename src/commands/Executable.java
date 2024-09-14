package commands;

import utility.Request;

public interface Executable {
    Request apply(String[] arguments);
}
