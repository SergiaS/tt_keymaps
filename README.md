# Keymap instruction

1. Start application from class `StartApp` - the system will load 2 json files.
Then the system will give you ability  to change keymaps (type command into console) as you want (as described in the task).
   
2. Console command template: `OPERATION;ACTION_NAME;USER_KEYMAP`.
Only one keymap can be added in one command. Example:
- `ADD;Action1;ctrl pressed 1` - will add keymap `ctrl pressed 1` to `Action1`.
- `DELETE;Action2;ctrl pressed 2` - will remove keymap `ctrl pressed 2` from `Action2`. It can be empty!
- `DEFAULT;Action3` - will set default keymap for `Action3`.

