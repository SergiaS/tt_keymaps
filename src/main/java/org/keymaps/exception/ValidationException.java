package org.keymaps.exception;

public class ValidationException extends Exception {

    public ValidationException() {
        super(" > Command is incorrect!");
    }

    public ValidationException(String message) {
        super(" > " + message);
    }

    public static void throwError(String msg) {
        try {
            throw new ValidationException(msg);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}
