package commands;

import java.io.Serializable;

public enum Type implements Serializable {
    HELP,
    INFO,
    SHOW,
    CHECK_ID,
    ADD,
    INSERT,
    UPDATE,
    CLEAR,
    REMOVE_ID,
    FILTER_NAME,
    FILTER_FUEL,
    REMOVE_LOWER,
    PRINT_FUEL,
    SCRIPT,
    SAVE,
    EXIT;
    private static final long serialVersionUID = 1L;
}
