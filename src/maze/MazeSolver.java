package maze;

import graphics.Svg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by knutandersstokke on 28 28.10.2017.
 */
public class MazeSolver {

    private final static String MAZE_FILE = "maze/maze.txt"; // or maze.txt

    /**
     * Solves the maze
     * @return A list of points showing where the player needs to go to reach the end.
	 * The points should be in the correct order, meaning the first point in the
     * list should be next to the player.
     */
	public static List<Point> solve(Maze m) {

	    // TODO: Solution here

	    return new ArrayList<>();
	}

    public static void main(String[] args) {
        Maze maze = new Maze(MAZE_FILE);
        String finalHtmlContent = Svg.buildSvgFromMaze(maze);
        Svg.runSVG(finalHtmlContent);
    }
}
