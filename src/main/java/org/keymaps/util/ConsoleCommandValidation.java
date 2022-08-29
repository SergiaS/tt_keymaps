package org.keymaps.util;

import org.keymaps.enums.Operation;
import org.keymaps.exception.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConsoleCommandValidation {

    public static boolean isCommandValid(String[] splitCommand, Map<String, List<String>> defaultConfig, Map<String, List<String>> userConfig) {
        try {
            if (splitCommand.length < 2 || splitCommand.length > 4) {
                throw new ValidationException();
            }

            isOperationValid(splitCommand[0]);
            isActionValid(splitCommand[0], splitCommand[1], defaultConfig, userConfig);
            isNumberOfParametersRight(splitCommand[0], splitCommand.length);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean isOperationValid(String operation) throws ValidationException {
        if (Arrays.stream(Operation.values()).noneMatch(o -> o.toString().equals(operation))) {
            throw new ValidationException("The app doesn't support such operation. Please try again!");
        }
        return true;
    }

    private static boolean isActionValid(String operationCmd, String actionCmd, Map<String, List<String>> defaultConfig, Map<String, List<String>> userConfig) throws ValidationException {
        if (operationCmd.equals(Operation.ADD.toString()) ||
                (operationCmd.equals(Operation.RESET.toString()) && defaultConfig.containsKey(actionCmd)) ||
                ((operationCmd.equals(Operation.EDIT.toString()) || operationCmd.equals(Operation.DELETE.toString())) && userConfig.containsKey(actionCmd))) {
            return true;
        } else {
            throw new ValidationException("There is no such action name. Please try again!");
        }
    }

    private static boolean isNumberOfParametersRight(String operationCmd, int numberOfParameters) throws ValidationException {
        if ((operationCmd.equals(Operation.DELETE.toString()) && numberOfParameters == 3) ||
                (operationCmd.equals(Operation.RESET.toString()) && numberOfParameters == 2) ||
                (operationCmd.equals(Operation.EDIT.toString()) && numberOfParameters == 4) ||
                (operationCmd.equals(Operation.ADD.toString()) && numberOfParameters == 3) ) {
            return true;
        } else {
            throw new ValidationException(String.format("For %s operation number of parameters is incorrect!", operationCmd));
        }
    }

}
