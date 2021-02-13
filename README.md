# ajmDungeonOfDoom
An implementation of the Dungeon of Doom as required for a University of Bath coursework.

The game prompts you to enter a map filename to load. This must also include the extension and the map must be located in the directory the game is executed from.
It is checked if it has a name and an amount of gold to win.
After the map name is stated, the game begins. There are a total of 6 valid commands:
HELLO - Prints the amount of gold required to collect to win the game.
GOLD - Prints the amount of gold the player has collected so far.
MOVE N,S,E,W - Moves the player north, south, east, or west on the map.
PICKUP - Picks up gold if the player is on a gold tile, else nothing.
LOOK - Prints a 5x5 view of the map centered on the player position.
QUIT - Prints 'WIN' if the player has sufficient gold and is standing on an exit tile, otherwise prints 'LOSE' and then exits the game.
Every input takes up a turn, even unsuccessful and unrecognised inputs, so make sure you make the right moves!
