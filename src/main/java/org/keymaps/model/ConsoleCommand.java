package org.keymaps.model;

public class ConsoleCommand {
    private final String operation;
    private final String actionName;
    private String userKeymap;
    private String userKeymapReplaceBy;

    public ConsoleCommand(String operation, String actionName) {
        this.operation = operation;
        this.actionName = actionName;
    }

    public ConsoleCommand(String operation, String actionName, String userKeymap) {
        this.operation = operation;
        this.actionName = actionName;
        this.userKeymap = userKeymap;
    }

    public ConsoleCommand(String operation, String actionName, String userKeymap, String userKeymapReplaceBy) {
        this.operation = operation;
        this.actionName = actionName;
        this.userKeymap = userKeymap;
        this.userKeymapReplaceBy = userKeymapReplaceBy;
    }

    public String getOperation() {
        return operation;
    }

    public String getActionName() {
        return actionName;
    }

    public String getUserKeymap() {
        return userKeymap;
    }

    public String getUserKeymapReplaceBy() {
        return userKeymapReplaceBy;
    }

    @Override
    public String toString() {
        return "ConsoleCommand{" +
                "operation=" + operation +
                ", actionName='" + actionName + '\'' +
                ", userKeymap='" + userKeymap + '\'' +
                ", userKeymapReplaceBy='" + userKeymapReplaceBy + '\'' +
                '}';
    }
}
