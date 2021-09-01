# Keymap instruction

1. Start application from class `StartApp` - launch main method - the system will load 2 json files (hotkey configs) and merge them.
Then the system will give you ability to change hotkeys (type command into console) as you want (as described in the task).
   
2. Console command template could be 3 types: 
- __2 values__: `1_OPERATION;2_ACTION_NAME`, for `RESET` operation;
- __3 values__: `1_OPERATION;2_ACTION_NAME;3_USER_HOTKEY`, for `ADD` and `DELETE` operations;
- __4 values__: `1_OPERATION;2_ACTION_NAME;3_USER_HOTKEY;4_USER_HOTKEY_REPLACE`, for `EDIT` operation.

Only one hotkey can be added in one command. Examples:
- `ADD;Action1;ctrl pressed 1` - will add hotkey `ctrl pressed 1` to `Action1`.
- `DELETE;Action2;ctrl pressed 2` - will remove hotkey `ctrl pressed 2` from `Action2`.
- `RESET;Action3` - will set default hotkey for `Action3`.
- `EDIT;Action4;ctrl pressed 2;ctrl pressed 5` - will change hotkey for `Action4` from `ctrl pressed 2` to `ctrl pressed 5`.  

In the end, when you add all you needed command via console, you can save it into file by typing word `SAVE`.
Program will stop.

### Test cases for use:

| console cmd                           | description                                   |
|:-------                               |:-------                                       |
| ADD;Action1;Ctrl+8                    | добавляет новый хк в существующий Action      |
| ADD;Action9;Ctrl+9                    | пытается добавить хк в НЕ существующий Action |
| ADD;Action6;Ctrl+8                    | перемещает добавленый хк                      |
| ADD;Action1;ctrl pressed 3            | перемещает существующий хк                    |
| ADD;Action2;ctrl pressed 3            | добавляет лист хк из дефолта в юзер конфиг    |
| EDIT;Action2;ctrl pressed 3;ctrl      | изменяет существующий хк                      |
| EDIT;Action2;ctrl pressed 3;ctrl55    | пытается изменить уже не существующий хк      |
| DELETE;Action1;ctrl pressed U         | удаляет существующий                          |
| DELETE;Action1;ctrl pressed U         | пытается удалить уже не существующий хк       |
| RESET;Action5                         | сбрасывает существующий Action                |
| RESET;Action8                         | сбрасывает не существующий Action             |
