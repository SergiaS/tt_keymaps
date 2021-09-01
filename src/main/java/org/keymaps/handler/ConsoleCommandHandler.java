package org.keymaps.handler;

import java.util.List;
import java.util.Map;

public class ConsoleCommandHandler {

    private final Map<String, List<String>> defaultConfig;
    private final Map<String, List<String>> userConfig;

    public ConsoleCommandHandler(Map<String, List<String>> defaultConfig, Map<String, List<String>> userConfig) {
        this.defaultConfig = defaultConfig;
        this.userConfig = userConfig;
    }

    public void addHotKey(String actionCmd, String hotkey) {
        userConfig.get(actionCmd).add(hotkey);
    }

    public void deleteHotKey(Map<String, List<String>> config, String actionCmd, String hotkeyForDelete) {
        if (config.get(actionCmd).size() == 1) {
            config.remove(actionCmd);
        } else {
            config.get(actionCmd).remove(hotkeyForDelete);
        }
    }

    public void editHotKey(String actionCmd, String actionNameByHotkey, String hotkeyForDelete, String hotkeyForAdd) {
        userConfig.get(actionNameByHotkey).remove(hotkeyForDelete);
        userConfig.get(actionCmd).add(hotkeyForAdd);
    }

    public void resetHotKey(String actionCmd) {
        userConfig.put(actionCmd, defaultConfig.get(actionCmd));
    }

    public String getActionNameByHotkey(Map<String, List<String>> config, String hotkeyCmd) {
        return config.entrySet().stream()
                .filter(l -> l.getValue().contains(hotkeyCmd))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public boolean isHotkeyExist(Map<String, List<String>> config, String hotkeyCmd) {
        return config.values().stream()
                .anyMatch(l -> l.contains(hotkeyCmd));
    }

}
