package org.keymaps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.keymaps.enums.Operation;
import org.keymaps.exception.ValidationException;
import org.keymaps.handler.ConsoleCommandHandler;
import org.keymaps.model.KeyMapper;
import org.keymaps.util.ConsoleUtil;

import java.io.*;
import java.util.*;

import static org.keymaps.util.ConsoleCommandValidation.*;

public class StartApp {

    private final static String DEFAULT_KEYMAPS_PATH = "src\\main\\resources\\keymap-default.json";
    private final static String USER_KEYMAPS_PATH = "src\\main\\resources\\keymap-users.json";

    private final static String MERGED_KEYMAPS_PATH = "src\\main\\resources\\keymap-merged.json";

    public static void main(String[] args) throws IOException {
        StartApp startApp = new StartApp();
        startApp.turnOn();
    }

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private void turnOn() throws FileNotFoundException {
        KeyMapper defaultKeymaps = createKeyMapperFromJsonFile(DEFAULT_KEYMAPS_PATH);
        KeyMapper userKeymaps = createKeyMapperFromJsonFile(USER_KEYMAPS_PATH);
        System.out.println("DEFAULT: " + defaultKeymaps);
        System.out.println("USERS  : " + userKeymaps);
        System.out.println();

        KeyMapper mergedKeymaps = new KeyMapper(mergeKeyMapper(defaultKeymaps, userKeymaps));
        System.out.println("MERGED : " + mergedKeymaps);
        System.out.println();

        consoleKeymapChanger(defaultKeymaps, mergedKeymaps);

        saveKeymapChanges(mergedKeymaps);
    }

    private KeyMapper createKeyMapperFromJsonFile(String filename) throws FileNotFoundException {
        return gson.fromJson(new FileReader(filename), KeyMapper.class);
    }

    private Map<String, List<String>> mergeKeyMapper(KeyMapper defaultKeymaps, KeyMapper userKeymaps) {
        Map<String, List<String>> mergedKeymaps = new TreeMap<>(userKeymaps.getKeymap());
        for (Map.Entry<String, List<String>> e : defaultKeymaps.getKeymap().entrySet()) {
            if (!mergedKeymaps.containsKey(e.getKey())) {
                mergedKeymaps.put(e.getKey(), e.getValue());
            }
        }
        return mergedKeymaps;
    }

    private void consoleKeymapChanger(KeyMapper defaultKeymaps, KeyMapper mergedKeymaps) {
        Map<String, List<String>> defaultConfig = defaultKeymaps.getKeymap();
        Map<String, List<String>> userConfig = mergedKeymaps.getKeymap();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            ConsoleCommandHandler consoleCommandHandler = new ConsoleCommandHandler(defaultConfig, userConfig);
            String cmd;
            while (!(cmd = reader.readLine()).equals("SAVE")) {
                System.out.println("YOUR COMMAND IS: " + cmd);

                String[] splitCommand = cmd.split(";");

                // validation
                boolean commandValid = isCommandValid(splitCommand, defaultConfig, userConfig);

                // cmd action
                if (commandValid) {
                    String operationCmd = splitCommand[0];
                    String actionCmd = splitCommand[1];

                    if (operationCmd.equals(Operation.DELETE.toString())) {
                        String hotkeyForDelete = splitCommand[2];

                        boolean isHotkeyExistForDelete = consoleCommandHandler.isHotkeyExist(userConfig, hotkeyForDelete);
                        if (isHotkeyExistForDelete) {
                            consoleCommandHandler.deleteHotKey(userConfig, actionCmd, hotkeyForDelete);
                            ConsoleUtil.showAnimationBar("DELETING");
                        } else {
                            ValidationException.throwError("Error - impossible to delete");
                        }
                    } else if (operationCmd.equals(Operation.RESET.toString())) {

                        if (defaultConfig.containsKey(actionCmd)) {
                            consoleCommandHandler.resetHotKey(actionCmd);
                            ConsoleUtil.showAnimationBar("RESETTING");
                        } else {
                            ValidationException.throwError("Error - impossible to reset");
                        }
                    } else if (operationCmd.equals(Operation.EDIT.toString())) {
                        String hotkeyForDelete = splitCommand[2];
                        String hotkeyForAdd = splitCommand[3];

                        String actionNameByHotkey = consoleCommandHandler.getActionNameByHotkey(userConfig, hotkeyForDelete);
                        if (actionNameByHotkey != null) {
                            consoleCommandHandler.editHotKey(actionCmd, actionNameByHotkey, hotkeyForDelete, hotkeyForAdd);
                            ConsoleUtil.showAnimationBar("EDITING");
                        } else {
                            ValidationException.throwError("Error - impossible to edit");
                        }
                    } else if (operationCmd.equals(Operation.ADD.toString())) {
                        String hotkey = splitCommand[2];

                        String actionNameByHotkeyInUserConfig = consoleCommandHandler.getActionNameByHotkey(userConfig, hotkey);;
                        if (actionNameByHotkeyInUserConfig != null) {
                            consoleCommandHandler.deleteHotKey(userConfig, actionNameByHotkeyInUserConfig, hotkey);
                        }

                        if (userConfig.containsKey(actionCmd)) {
                            consoleCommandHandler.addHotKey(actionCmd, hotkey);
                        } else {
                            userConfig.put(actionCmd, new ArrayList<>(Collections.singletonList(hotkey)));
                        }

                        ConsoleUtil.showAnimationBar("ADDING");
                    }
                }
                System.out.println(userConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // save hotkeys to file
    private void saveKeymapChanges(KeyMapper mergedKeymaps) {
        try (FileWriter writer = new FileWriter(MERGED_KEYMAPS_PATH)) {
            gson.toJson(mergedKeymaps, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
