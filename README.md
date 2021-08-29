# Keymap instruction

1. Start application from class `StartApp` - the system will load 2 json files.
Then the system will give you ability  to change keymaps (type command into console) as you want (as described in the task).
   
2. Console command template, there are 3 constructors: 
- __2 values__: `1_OPERATION;2_ACTION_NAME`, for `DEFAULT` operation;
- __3 values__: `1_OPERATION;2_ACTION_NAME;3_USER_KEYMAP`, for `ADD` and `DELETE` operations;
- __4 values__: `1_OPERATION;2_ACTION_NAME;3_USER_KEYMAP;4_USER_KEYMAP_REPLACE`, for `EDIT` operation.

Only one keymap can be added in one command. Examples:
- `ADD;Action1;ctrl pressed 1` - will add keymap `ctrl pressed 1` to `Action1`.
- `DELETE;Action2;ctrl pressed 2` - will remove keymap `ctrl pressed 2` from `Action2`. It can be empty!
- `DEFAULT;Action3` - will set default keymap for `Action3`.
- `EDIT;Action4;ctrl pressed 2;ctrl pressed 5` - will change keymap for `Action4` from `ctrl pressed 2` to `ctrl pressed 5`.  
