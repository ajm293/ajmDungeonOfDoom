import java.util.*;
import java.util.Set;

/**
 * Runs the game with a human player, and contains code needed to read inputs and store co-ordinates.
 *
 */
public class HumanPlayer {

    /* Co-ordinates of the human player */
    private int[] position;

    /* Amount of gold the human player owns */
    private int gold;

    /* Player character to display on map */
    private final char playerChar = 'P';

    /* Character that the player has replaced on the map */
    private char liftedChar;

    /* Array of valid commands */
    private final Set<String> UNARY_COMMANDS = Set.of(
            "HELLO",
            "GOLD",
            "PICKUP",
            "LOOK",
            "QUIT"
            // MOVE is omitted as it takes an operand and must be checked separately.
            );

    /* Array of valid movements / operands */
    private final Set<String> OPERANDS = Set.of(
            "N",
            "S",
            "E",
            "W"
    );

    /**
     * Default constructor
     *
     */
    public HumanPlayer(){
        gold = 0;
        position = new int[2];
    }

    /**
     * Returns amount of gold the player currently owns.
     *
     * @return : Amount of gold the player has.
     */
    protected int getGold(){
        return gold;
    }

    /**
     * Add 1 gold to the player's total.
     */
    protected void incrementGold(){
        gold += 1;
    }

    /**
     * Returns the position of the player.
     *
     * @return : Integer array of player's position.
     */
    protected int[] getPosition(){
        return position;
    }

    /**
     * Sets the player's position.
     *
     * @param x : x co-ordinate of player's desired position.
     * @param y : y co-ordinate of player's desired position.
     */
    protected void setPosition(int x, int y) {
        position[0] = x;
        position[1] = y;
    }

    /**
     * Changes the player's co-ord position according to integers dx and dy.
     *
     * @param dx : How much to change the player's x position.
     * @param dy : How much to change to player's y position.
     */
    protected void movePosition(int dx, int dy){
        position[0] += dx;
        position[1] += dy;
    }

    /**
     * Returns the player's character.
     *
     * @return : The player's character.
     */
    protected char getPlayerChar(){
        return playerChar;
    }

    /**
     * Set the character being lifted to the specified character.
     *
     * @param mapChar : Character from the map that is being replaced with the player character.
     */
    protected void setLiftedChar(char mapChar){
        liftedChar = mapChar;
    }

    /**
     * Returns the character the player character is 'on top of', replaced
     * at the last move by the player character.
     *
     * @return : The character the player character replaced at the last move.
     */
    protected char getLiftedChar(){
        return liftedChar;
    }

    /**
     * Reads player's input from the console.
     *
     * @return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() {
        Scanner s = new Scanner(System.in);
        return s.nextLine().toUpperCase();
    }

    /**
     * Displays a prompt and reads player's input from the console.
     * In this case, the input is not made upper case as the prompt
     * input may potentially be case sensitive, as in unix-like
     * file systems that have case sensitive file names.
     *
     * @param prompt : A prompt for the console to print first.
     * @return : A string containing the input the player entered.
     */
    protected String getInputFromConsole(String prompt) {
        System.out.println(prompt);
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction() {
        String[] input = getInputFromConsole().split(" ");
        if (input[0].equals("MOVE") && input.length == 2 && OPERANDS.contains(input[1])){ // MOVE check
            return input[0] + " " + input[1];
        }
        else if (UNARY_COMMANDS.contains(input[0]) && input.length == 1){ // Check for all other commands
            return input[0];
        }
        return "Invalid";
    }

    /**
     * Prints the given result string of an action.
     *
     * @param output : String returned by action.
     */
    protected void printActionResults(String output){
        System.out.println(output);
    }




}