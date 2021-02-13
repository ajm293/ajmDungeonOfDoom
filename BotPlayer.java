import java.util.Random;

/**
 * Extends and overrides the HumanPlayer class to allow a bot to interact
 * autonomously with the game.
 *
 */
public class BotPlayer extends HumanPlayer{

    private final char playerChar = 'B';
    private final int[] botPos = {2,2};
    private boolean lookTurn = true;
    private String nextCommand = "LOOK";
    private String[] map;
    private final String[] moves = {"MOVE N","MOVE S","MOVE E","MOVE W"};

    /**
     * Examines a 5x5 LOOK string array for the player character P and decides
     * which direction to move in depending on where that player character is.
     *
     * @param map : The 5x5 map array to be examined.
     */
    protected void decideMove(String[] map){
        int[] humanPos = {-1,-1}; // Initialise with impossible value
        Random rand = new Random();
        String direction;
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length(); j++){
                if (map[i].charAt(j) == 'P'){ // When found, assign the absolute co-ords
                    humanPos[0] = i;
                    humanPos[1] = j;
                }
            }
        }
        if (humanPos[0] == -1){ // If P is not present
            direction = moves[rand.nextInt(moves.length)];
            nextCommand = direction;
        } else {
            int dx = humanPos[0] - 2; // Convert absolute co-ords to relative around 2,2 (Centre of 5x5 array)
            int dy = humanPos[1] - 2;

            if (dy <= 0){
                nextCommand = "MOVE W";
            }
            if (dy >= 0){
                nextCommand = "MOVE E";
            }
            if (dx > 0){
                nextCommand = "MOVE S";
            }
            if (dx < 0){
                nextCommand = "MOVE N";
            }
        }
    }

    /**
     * Switches between looking and choosing a move direction.
     *
     * @return : The next command for the bot to execute.
     */
    protected String getNextAction(){
        if (lookTurn){
            nextCommand = "LOOK";
        } else {
            decideMove(map);
        }
        lookTurn = !lookTurn;
        return nextCommand;
    }

    /**
     *
     * @return : The bot player character.
     */
    protected char getPlayerChar(){
        return playerChar;
    }

    /**
     * Overrides human method and instead reads the output of a LOOK
     * command into the map string array.
     *
     * @param output : String returned by action.
     */
    protected void printActionResults(String output){
        if (nextCommand.equals("LOOK") && !output.equals("Invalid")){
            map = output.split("\n");
        }
    }

}
