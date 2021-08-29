package org.keymaps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.keymaps.enums.Operation;
import org.keymaps.model.ConsoleCommand;
import org.keymaps.model.KeyMapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//        saveKeymapChanges(mergedKeymaps);
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
//        mergedMap.entrySet().stream()
//                .filter(k -> k.getValue().size() > 0)
//                .forEach(System.out::println);
        return mergedKeymaps;
    }

    private void consoleKeymapChanger(KeyMapper defaultKeymaps, KeyMapper mergedKeymaps) {
        Map<String, List<String>> defaultConfig = defaultKeymaps.getKeymap();
        Map<String, List<String>> userConfig = mergedKeymaps.getKeymap();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String userCommand;
            while (!(userCommand = reader.readLine()).equals("SAVE")) {
                System.out.println("YOUR COMMAND IS: " + userCommand);

                String[] splitCommand = userCommand.split(";");

                // validation
                ConsoleCommand consoleCommand = consoleCommandValidation(splitCommand, defaultConfig, userConfig);
                if (consoleCommand == null) {
                    System.out.println("ERROR - WRONG COMMAND!");
                } else {
                    // make changes
                    String commandOperation = consoleCommand.getOperation();
                    String commandActionName = consoleCommand.getActionName();
                    String commandUserKeymap = consoleCommand.getUserKeymap();
                    if (commandOperation.equals(Operation.DEFAULT.toString()) && splitCommand.length == 2) {
                        userConfig.put(commandActionName, defaultConfig.get(commandActionName));
                        System.out.println("RESTORING TO DEFAULT...");
                    } else if (commandOperation.equals(Operation.ADD.toString()) && splitCommand.length == 3) {
                        userConfig.get(commandActionName).add(commandUserKeymap);
                    } else if (commandOperation.equals(Operation.DELETE.toString()) && splitCommand.length == 3) {
                        userConfig.get(commandActionName).remove(commandUserKeymap);
                    } else if (commandOperation.equals(Operation.EDIT.toString()) && splitCommand.length == 4) {
                        String userKeymapReplaceBy = consoleCommand.getUserKeymapReplaceBy();
                        userConfig.get(commandActionName).add(userKeymapReplaceBy);
                    } else {
                        System.out.println("ERROR - WRONG COMMAND!");
                    }
                }
                System.out.println(userConfig);
            }
//            for (String userCommand = reader.readLine(); !userCommand.equals("SAVE"); userCommand = reader.readLine()) {
//                System.out.println(userCommand);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConsoleCommand consoleCommandValidation(String[] splitCommand,
                                                    Map<String, List<String>> defaultConfig,
                                                    Map<String, List<String>> userConfig) {

        if (splitCommand.length <= 1 || splitCommand.length >= 5) {
            return null;
        }

        String operation;
        String actionName;
        String userKeymapTo = null;
        String userKeymapFrom = null;

        // 1 - operation validating
        if (Arrays.stream(Operation.values()).noneMatch(o -> o.toString().equals(splitCommand[0]))) {
            System.out.println("The app doesn't support such operation. Please try again!");
            return null;
        } else {
            operation = splitCommand[0];
        }

        // 2 - Action validating
        if (!userConfig.containsKey(splitCommand[1]) || !defaultConfig.containsKey(splitCommand[1])) {
            System.out.println("There is no such action name. Please try again!");
            return null;
        } else {
            actionName = splitCommand[1];
        }

        // 3 - userKeymapTo
        if (splitCommand.length == 3 || splitCommand.length == 4) {
//            boolean isExist = userConfig.values().stream()
//                    .anyMatch(l -> l.removeIf(el -> el.equals(splitCommand[2])));


            Map<String, List<String>> collect = userConfig.values().stream()
                    .filter(l -> l.contains(splitCommand[2]))
                    .collect(Collectors.toMap(k -> k.get(0), v -> v));

            if (collect.size() > 0) {
                if (operation.equals(Operation.DELETE.toString())) {
                    System.out.println("DELETED SUCCESSFUL!");
                } else if (operation.equals(Operation.EDIT.toString())) {
                    System.out.println("EDITED SUCCESSFUL!");
                } else if (operation.equals(Operation.ADD.toString())) {
                    System.out.println("The keymap is already exist in your config!");
                    return null;
                }
            } else {
                if (operation.equals(Operation.DELETE.toString())) {
                    System.out.println("There is no such binded keymap for delete!");
                    return null;
                } else if (operation.equals(Operation.EDIT.toString())) {
                    System.out.println("There is no such binded keymap for edit!");
                    return null;
                } else if (operation.equals(Operation.ADD.toString())) {
                    System.out.println("ADDED SUCCESSFUL!");
                }
            }
            userKeymapTo = splitCommand[2];
//            boolean contains = userConfig.get(actionName).contains(splitCommand[2]);
        }

        // 4 - userKeymapFrom
        if (splitCommand.length == 4) {
            if (splitCommand[3].length() < 5) {
                System.out.println("Keymap length should be more than 5");
                return null;
            }
            userKeymapFrom = splitCommand[3];
        }

        return new ConsoleCommand(operation, actionName, userKeymapTo, userKeymapFrom);
    }

    private void saveKeymapChanges(KeyMapper mergedKeymaps) {
        // save keymap to file
        try (FileWriter writer = new FileWriter(MERGED_KEYMAPS_PATH)) {
            gson.toJson(mergedKeymaps, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAnimationBar(int time, String status) {

    }

    private static KeyMapper createJson() {
        List<String> list1 = Arrays.asList("ctrl pressed 2", "ctrl pressed U");
        List<String> list2 = Arrays.asList("ctrl pressed 3");
        List<String> list3 = new ArrayList<>();
        List<String> list5 = Arrays.asList("ctrl pressed A");
        List<String> list6 = Arrays.asList("ctrl pressed 6");

        Map<String, List<String>> map = new HashMap<>();
        map.put("Action1", list1);
        map.put("Action2", list2);
        map.put("Action3", list3);
        map.put("Action5", list5);
        map.put("Action6", list6);

        return new KeyMapper(map);
    }

    private static String getExampleJson() {
        return "{\n" +
                "    \"keymap\": {\n" +
                "        \"Action1\": [\n" +
                "            \"ctrl pressed 2\",\n" +
                "            \"ctrl pressed U\"\n" +
                "        ],\n" +
                "        \"Action2\": [\n" +
                "            \"ctrl pressed 3\"\n" +
                "        ],\n" +
                "        \"Action3\": [],\n" +
                "        \"Action5\": [\n" +
                "            \"ctrl pressed A\"\n" +
                "        ],\n" +
                "        \"Action6\": [\n" +
                "            \"ctrl pressed 6\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }

}
