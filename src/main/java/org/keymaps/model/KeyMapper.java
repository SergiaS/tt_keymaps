package org.keymaps.model;

import java.util.List;
import java.util.Map;

public class KeyMapper {

    private final Map<String, List<String>> keymap;

    public KeyMapper(Map<String, List<String>> keymap) {
        this.keymap = keymap;
    }

    public Map<String, List<String>> getKeymap() {
        return keymap;
    }

    @Override
    public String toString() {
        return "KeyMapper{" +
                "keymap=" + keymap +
                '}';
    }
}
