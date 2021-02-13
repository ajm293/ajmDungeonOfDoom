import java.util.*;
import java.io.*;


/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

	/* Representation of the map */
	private char[][] map;
	
	/* Map name */
	private String mapName;
	
	/* Gold required for the human player to win */
	private int goldRequired;
	
	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map() {
		mapName = "Very small Labyrinth of Doom";
		goldRequired = 2;
		map = new char[][] {
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}
	
	/**
	 * Constructor that accepts a map to read in from.
	 * THE MAP MUST BE IN THE DIRECTORY OF EXECUTION.
	 *
	 * @param fileName : The filename of the map file.
	 */
	public Map(String fileName) {
		try {
			readMap(fileName);
		} catch (FileNotFoundException fne) {
			System.out.println("File does not exist or is corrupted, using defaults instead.");
			mapName = "Very small Labyrinth of Doom";
			goldRequired = 2;
			map = new char[][]{
					{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
					{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
					{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
					{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
					{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
					{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
					{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
					{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
					{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
			};
		}
	}

    /**
	 * Returns the amount of gold a player needs to own
	 * to exit a map with a win.
	 *
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /**
	 * Returns the current state of the map in memory.
	 *
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }

	/**
	 * Returns the map character at a specific co-ordinate.
	 *
	 * @param x : x co-ordinate of map tile
	 * @param y : y co-ordinate of map tile
	 * @return : The specified character in the map
	 */
    protected char getMapChar(int x, int y) {
    	return map[x][y];
	}

	/**
	 * Returns the map character at the position of a HumanPlayer instance.
	 *
	 * @param player : The HumanPlayer instance.
	 * @return : The character at the player's position.
	 */
	protected char getMapChar(HumanPlayer player) {
    	return map[player.getPosition()[0]][player.getPosition()[1]];
	}

	/**
	 * Sets the map tile at the specified co-ordinates to the specified character.
	 *
	 * @param x : x co-ordinate of tile.
	 * @param y : y co-ordinate of tile.
	 * @param character : Character to set map tile to.
	 */
	protected void setMapChar(int x, int y, char character){
    	map[x][y] = character;
	}

	/**
	 * Sets the map tile at the HumanPlayer's position to the specified character.
	 *
	 * @param player : The HumanPlayer instance.
	 * @param character : Character to set map tile to.
	 */
	protected void setMapChar(HumanPlayer player, char character) {
    	map[player.getPosition()[0]][player.getPosition()[1]] = character;
	}


    /**
	 * Returns the name of the current map.
	 *
     * @return : The name of the current map.
     */
    protected String getMapName() {
        return mapName;
    }

	/**
	 * Returns the maximum dimensions of the current map.
	 *
	 * @return : An integer array of the maximum dimensions of the map.
	 */
	protected int[] getMapDimensions() {
		int width = 0;
		for (char[] line : map){
			if (line.length > width){
				width = line.length;  // Included for non-square maps, gives maximum y-width.
			}
		}
		return new int[]{this.map.length-1, width-1};
	}

	/**
	 * Save the map character where the player is moving to for future use.
	 *
	 * @param player : The player being moved.
	 */
	protected void liftChar(HumanPlayer player){
		player.setLiftedChar(getMapChar(player));
		setMapChar(player, player.getPlayerChar()); // Put player down on map
	}

	/**
	 * Replace the map character from a previous lifting.
	 *
	 * @param player : The player being moved.
	 */
	protected void replaceChar(HumanPlayer player){
		setMapChar(player, player.getLiftedChar());
	}

    /**
     * Reads the map from file.
	 * The map must be located in the root directory of execution.
     *
     * @param fileName : Name of the map's file.
	 * @throws FileNotFoundException : If no file is found or the map is corrupted and fails verification.
     */
    protected void readMap(String fileName) throws FileNotFoundException {
    	// Setting up IO
		fileName = System.getProperty("user.dir")+File.separator+fileName;
    	File mapFile = new File(fileName);
    	Scanner s = new Scanner(mapFile);
		ArrayList<String> fileContents = new ArrayList<>();
    	while (s.hasNextLine()) {
    		fileContents.add(s.nextLine());
		}
    	if (!this.isValidMapFile(fileContents)) { // Validate and throw if invalid
    		throw new FileNotFoundException("File is corrupted");
		}
    	String[] mapNameLine = fileContents.get(0).split(" ", 2); // Split to 'name' and the map name
    	this.mapName = mapNameLine[1];
    	this.goldRequired = Integer.parseInt(fileContents.get(1).split(" ", 2)[1]); // 'win' and gold num
		String[] mapString = new String[fileContents.size()];
		mapString = fileContents.toArray(mapString);
		this.map = new char[mapString.length-2][]; // Skip name and win lines
		for (int i = 2; i < mapString.length; i++) { // Also skip iterating through name and win lines
			this.map[i-2] = mapString[i].toCharArray();
		}
		s.close();
    }

	/**
	 * Checks if a given file can be a map.
	 *
	 * @param contents : ArrayList of the file's contents.
	 * @return : Boolean stating whether a file is a valid map or not.
	 */
    protected boolean isValidMapFile(ArrayList<String> contents) {
    	// Check if name and win are present, and if gold is an integer and positive/zero
    	boolean nameCheckPassed = contents.get(0).split(" ", 2)[0].equals("name");
    	boolean winCheckPassed = contents.get(1).split(" ", 2)[0].equals("win");
    	boolean goldCheckPassed;
    	try {
			goldCheckPassed = Integer.parseInt(contents.get(1).split(" ", 2)[1]) >= 0;
		} catch (NumberFormatException nfe){
    		goldCheckPassed = false;
		}
    	return nameCheckPassed && winCheckPassed && goldCheckPassed;
	}


}
