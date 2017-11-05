package maze;


import graphics.Svg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xun007
 * @Team: rni006
 */
public class MazeSolver {

    private final static String MAZE_FILE = "maze/maze.txt"; // or maze.txt
    private boolean[][] grid;
    private int height;
    private int width;
    private int[][] map;
    private static ArrayList<Point> listOfPoints = new ArrayList<Point>();
    Maze m;

    /**
     * Solves the maze
     *
     * @return A list of points showing where the player needs to go to reach the end.
     * The points should be in the correct order, meaning the first point in the
     * list should be next to the player.
     */
    public static List<Point> solve(Maze m) {
        boolean[][] walkable = walkablePath(m);
        MazeSolver mz = new MazeSolver(walkable, m);
        boolean solved = mz.solve();
        if (solved) {
            return listOfPoints;
        } else return null;
    }

    // Method that creates a boolean 2d array if it is walkable
    public static boolean[][] walkablePath(Maze m) {
        boolean[][] array = new boolean[m.getWidth()][m.getHeight()];

        for (Object wall : m.getWalls().toArray()) {
            Point point = (Point) wall;
            array[point.getX()][point.getY()] = true;
        }
        return array;
    }


    // Updates values
    public MazeSolver(boolean[][] grid, Maze m) {
        this.m = m;
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;
        this.map = new int[height][width];
    }

    public boolean solve() {
        return searchGrid(m.getPlayer().x, m.getPlayer().y);
    }

    //Method that searches grid until isEnd is reached
    private boolean searchGrid(int i, int j) {
        int TRIED = 2;
        int PATH = 3;
        Point placement;
        if (!isValid(i, j)) {
            return false;
        }

        if (isEnd(i, j)) {
            map[i][j] = PATH;
            placement = new Point(i, j);
            listOfPoints.add(placement);
            return true;
        } else {
            map[i][j] = TRIED;
        }

        // North
        if (searchGrid(i - 1, j)) {
            map[i - 1][j] = PATH;
            placement = new Point(i, j);
            listOfPoints.add(placement);
            return true;
        }
        // East
        if (searchGrid(i, j + 1)) {
            map[i][j + 1] = PATH;
            placement = new Point(i, j);
            listOfPoints.add(placement);
            return true;
        }
        // South
        if (searchGrid(i + 1, j)) {
            map[i + 1][j] = PATH;
            placement = new Point(i, j);
            listOfPoints.add(placement);
            return true;
        }
        // West
        if (searchGrid(i, j - 1)) {
            map[i][j - 1] = PATH;
            placement = new Point(i, j);
            listOfPoints.add(placement);
            return true;
        }

        return false;
    }

    // Checks if grid is goal
    private boolean isEnd(int i, int j) {
        return i == m.getGoal().x && j == m.getGoal().y;
    }

    //Checks if values are in range, is open and not tried
    private boolean isValid(int i, int j) {
        if (inRange(i, j) && isOpen(i, j) && !isTried(i, j)) {
            return true;
        }

        return false;
    }

    // Checks if grid is walkable
    private boolean isOpen(int i, int j) {
        return grid[i][j] == false;
    }

    // Checks if grid has been walked upon
    private boolean isTried(int i, int j) {
        int TRIED = 2;
        return map[i][j] == TRIED;
    }

    // Checks if height and width is in range
    private boolean inRange(int i, int j) {
        return inHeight(i) && inWidth(j);
    }

    // Checks if height is a value that is over 0
    private boolean inHeight(int i) {
        return i >= 0 && i < height;
    }

    // Checks if width is a value over 0
    private boolean inWidth(int j) {
        return j >= 0 && j < width;
    }


    public static void main(String[] args) {
        Maze maze = new Maze(MAZE_FILE);
        String finalHtmlContent = Svg.buildSvgFromMaze(maze);
        Svg.runSVG(finalHtmlContent);
    }
}






