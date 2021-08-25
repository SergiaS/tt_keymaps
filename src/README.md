# Keymaps.

Given two keymaps (configs) default and user's one. User's keymap can contain new shortcuts or overriden shortcuts from default keymap (see example below).

__You need to write a program that will be able:__

1) Load two keymaps at startup and merge them.
   Example of merging:

| default				   | user's                     | actual keymap (after merging)|
|:------------------------ |:--------                   |:---------------------------- |
|Save -> Ctrl+S            |  Exit  -> Esc,Ctrl+L       |    Save -> Ctrl+S            |
|Exit -> Ctrl+Q, Esc	   |  Edit  -> Ctrl+D           |    Exit -> Esc,Ctrl+L        |
|Edit -> Ctrl+E            |  Cut   -> Ctrl+X           |    Edit -> Ctrl+D            |
|Cut  -> Ctrl+X,Ctrl+H     |  Paste -> Ctrl+V,Ctrl+P    |    Cut  -> Ctrl+X            |
|                          |  Undo  -> Ctrl+E           |	 Paste-> Ctrl+V,Ctrl+P     |
|                          |						    |    Undo -> Ctrl+E            |

2) User must have a possibility:
   - add/remove/edit shortcuts programmatically (not directly changing configs)
   - take/print a diff between default and actual keymap
   - reset keymap to defaults

3) The state must be saved. So if a user closes program and then runs it, it will have a state before closing

Configs a attached to this zip. They are in JSON format. Language - Java.
You are free to choose a stack of technologies, and a type of application (console, web, desktop, etc.).
