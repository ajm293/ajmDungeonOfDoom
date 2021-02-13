import java.util.Arrays;
import java.util.Random;

/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {
	
	private Map map;
	private HumanPlayer player;
	private BotPlayer bot;
	private boolean running = true;
	
	/**
	 * Default constructor
     * Builds the player and bot, loads the map into memory, and
     * places the player and bot into different random locations
     * on the map.
	 */
	public GameLogic() {
	    Random rand = new Random(); // Init needed objects
        player = new HumanPlayer();
        bot = new BotPlayer();
	    String fileName = player.getInputFromConsole("Please enter a valid map filename with extension:");
		map = new Map(fileName);
        int random_x;
        int random_y;
		do { // Place human down at random tiles until character is floor.
		    random_x = rand.nextInt(map.getMapDimensions()[0]);
		    random_y = rand.nextInt(map.getMap()[random_x].length);
        } while (map.getMapChar(random_x, random_y) != '.');
		player.setPosition(random_x, random_y);
		map.liftChar(player);

        do { // Place human down at random tiles until character is floor.
            random_x = rand.nextInt(map.getMapDimensions()[0]);
            random_y = rand.nextInt(map.getMap()[random_x].length);
        } while (map.getMapChar(random_x, random_y) != '.');
        bot.setPosition(random_x, random_y);
        map.liftChar(bot);
	}

    /**
	 * Returns the gold required to win.
	 *
     * @return : Gold required to win.
     */
    protected String hello() {
        String goldRequired = Integer.toString(map.getGoldRequired());
        return "Gold to win: " + goldRequired;
    }
	
	/**
	 * Returns the gold currently owned by the player.
	 *
     * @param player : Player querying about currently owned gold.
     * @return : Gold currently owned.
     */
    protected String gold(HumanPlayer player) {
        String goldOwned = Integer.toString(player.getGold());
        return "Gold owned: " + goldOwned;
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction to move on the map.
     * @param player : The specified player.
     * @return : Whether the move is a success or not.
     */
    protected String move(String direction, HumanPlayer player) {
        int dx = 0;
        int dy = 0;
        switch (direction){
            case "N" :
                dx = -1;
                break;
            case "S" :
                dx = 1;
                break;
            case "E" :
                dy = 1;
                break;
            case "W" :
                dy = -1;
                break;
            default :
                return "Fail";
        }
        if (nextMoveIsWall(dx,dy, player)){
            return "Fail";
        }
        map.replaceChar(player);
        player.movePosition(dx, dy);
        map.liftChar(player);
        return "Success";
    }

    /**
     * Checks if the specified move on the player will impact a wall.
     *
     * @param dx : Change of the x co-ordinate in the move.
     * @param dy : Change of the y co-ordinate in the move.
     * @param player : The player being moved.
     * @return : Whether the move impacts a wall or not.
     */
    protected boolean nextMoveIsWall(int dx, int dy, HumanPlayer player){
        return map.getMapChar(player.getPosition()[0]+dx,player.getPosition()[1]+dy) == '#';
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @param player : The HumanPlayer instance looking at the map.
     * @return : A String representation of the game map.
     */
    protected String look(HumanPlayer player) {
        int[] pos = player.getPosition();
        StringBuilder output = new StringBuilder();
        for (int x = pos[0]-2; x < pos[0]+3; x++){ // From -2 up to 3 goes through 5 rows around player position
            for (int y = pos[1]-2; y < pos[1]+3; y++){  // Iterate through view in 2 dimensions
                try {
                    output.append(map.getMap()[x][y]);
                } catch (ArrayIndexOutOfBoundsException abe) { // Catch areas outside of map and replace with #
                    output.append("#");
                }
            }
            output.append('\n'); // Ensure map is printed as square
        }
        return output.toString();
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @param player : Player picking up.
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup(HumanPlayer player) {
        if (player.getLiftedChar() == 'G'){ // If the player is on gold
            player.incrementGold();
            player.setLiftedChar('.');
            return "Success";
        }
        return "Fail";
    }

    /**
     * Quits the game, shutting down the application.
     *
     * @param player : Player quitting.
     * @return : WIN or LOSE depending on a win or a loss.
     */
    protected String quitGame(HumanPlayer player) {
        running = false;
        if (player.getLiftedChar() == 'E' && player.getGold() >= map.getGoldRequired()){ // If on E and suff. gold
            return "WIN";
        }
        else {
            return "LOSE";
        }
    }

    /**
     * Assigns an input string array to a command.
     *
     * @param player : The HumanPlayer instance being processed.
     */
    protected void processCommand(HumanPlayer player) {
        String[] input = player.getNextAction().split(" ", 2);
        switch (input[0]){
            case "HELLO":
                player.printActionResults(hello());
                break;
            case "GOLD":
                player.printActionResults(gold(player));
                break;
            case "MOVE":
                player.printActionResults(move(input[1], player));
                break;
            case "PICKUP":
                player.printActionResults(pickup(player));
                break;
            case "LOOK":
                player.printActionResults(look(player));
                break;
            case "QUIT":
                player.printActionResults(quitGame(player));
                break;
            default : // Returns "Invalid" always
                player.printActionResults(input[0]);
        }
    }

    /**
     * The main function, plays the game.
     *
     * The game prompts you to enter a map filename to load. This must also include
     * the extension and the map must be located in the directory the game is
     * executed from. It is checked if it has a name and an amount of gold to win.
     *
     *
     * After the map name is stated, the game begins. There are a total of 6 valid
     * commands:
     * HELLO - Prints the amount of gold required to collect to win the game.
     * GOLD - Prints the amount of gold the player has collected so far.
     * MOVE N,S,E,W - Moves the player north, south, east, or west on the map.
     * PICKUP - Picks up gold if the player is on a gold tile, else nothing.
     * LOOK - Prints a 5x5 view of the map centered on the player position.
     * QUIT - Prints 'WIN' if the player has sufficient gold and is standing
     * on an exit tile, otherwise prints 'LOSE' and then exits the game.
     *
     *
     * Every input takes up a turn, even unsuccessful and unrecognised inputs,
     * so make sure you make the right moves!
     *
     * @param args : Command line arguments, unused.
     */
	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.player.printActionResults("Welcome to the Dungeon of Doom!");
		logic.player.printActionResults("You are now playing the Dungeon of Doom on the map:");
		logic.player.printActionResults(logic.map.getMapName());
		while (logic.running){
		    logic.processCommand(logic.player);
		    logic.processCommand(logic.bot);
            if (Arrays.equals(logic.player.getPosition(), logic.bot.getPosition())){ // Capture check
                logic.player.printActionResults("The bot hath slain you!");
                logic.quitGame(logic.player);
            }
        }

    }
}