package exceptions;

import exceptions.FormInputException;

/**
 * Custom exceptions
 */
public class DuplicatePlayerException extends FormInputException {

    public DuplicatePlayerException(String s) {
        super(s);
    }

    /**
     * Default chain constructor
     */
    public DuplicatePlayerException() {
        this("Players cannot have the same name and/or icon!");
    }
}
