# Keymaps.

Given two keymaps (configs) default and user's one. User's keymap can contain new shortcuts or overriden shortcuts from default keymap (see example below).

__You need to write a program that will be able:__

1) Load two keymaps at startup and merge them.
   Example of merging:

| operation | default		 | user's         | actual keymap (after merging) |
|:--------- |:-------------- |:-------------- |:----------------------------- |
| Exit      | Ctrl+Q, Esc    | Esc, Ctrl+L    | Exit -> Esc, Ctrl+L           |
| Save      | Ctrl+S	     |                | Save -> Ctrl+S                |
| Edit      | Ctrl+E         | Ctrl+D         | Edit -> Ctrl+D                |
| Cut       | Ctrl+X, Ctrl+H | Ctrl+X         | Cut  -> Ctrl+X                |
| Paste     |                | Ctrl+V, Ctrl+P | Paste-> Ctrl+V, Ctrl+P        |
| Undo      |                | Ctrl+E	      | Undo -> Ctrl+E                |

2) User must have a possibility:
   - add/remove/edit shortcuts programmatically (not directly changing configs)
   - take/print a diff between default and actual keymap
   - reset keymap to defaults

3) The state must be saved. So if a user closes program and then runs it, it will have a state before closing

Configs a attached to this zip. They are in JSON format. Language - Java.
You are free to choose a stack of technologies, and a type of application (console, web, desktop, etc.).
