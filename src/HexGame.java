import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HexGame {
    private String[] cellArray;
    private final int sideLength;
    private UnionFind unionFind;

    private int[] sides; // top, bottom, left, right
    private int totalMoves = 0;
    private static final String COLOR_RESET =  "\u001B[0m";
    private static final String COLOR_RED =  "\u001B[31m";
    private static final String COLOR_BLUE =  "\u001B[34m";

    private int[] findNeighbors(int cell) {
        // neighbor list: { top left, top right, left vertical, right vertical, bottom left, bottom right }
        int[] neighbors = new int[6];

        // default
        neighbors[0] = cell - sideLength;
        neighbors[1] = cell - (sideLength - 1);
        neighbors[2] = cell - 1;
        neighbors[3] = cell + 1;
        neighbors[4] = cell + sideLength;
        neighbors[5] = cell + (sideLength - 1);

        // special cases
        if (cell < sideLength) {
            int top = sides[0];
            neighbors[0] = top;
            neighbors[1] = top;
        }

        if (cell > sideLength * (sideLength - 1)) {
            int bottom = sides[1];
            neighbors[4] = bottom;
            neighbors[5] = bottom;
        }

        if (cell % sideLength == 0) {
            int left = sides[2];
            neighbors[2] = left;
            neighbors[4] = left;
        }

        if (cell % sideLength == sideLength - 1) {
            int right = sides[3];
            neighbors[3] = right;
            neighbors[5] = right;
        }

        return neighbors;
    }

    public boolean checkForWin() {
        if (unionFind.find(sides[0]) == unionFind.find(sides[1])) {
            System.out.printf("--------> Red has won after %d moves! Here is the final board:", totalMoves);
            System.out.println(this);
            return true;
        }
        else if (unionFind.find(sides[2]) == unionFind.find(sides[3])) {
            System.out.printf("--------> Blue has won after %d moves! Here is the final board:", totalMoves);
            System.out.println(this);
            return true;
        }
        else return false;
    }

    public void move(String color, int cell) {
        if (cell > sideLength * sideLength || cell < 0) {
            System.out.println("Invalid move: attempted cell " + cell);
            return;
        }
        else if (cellArray[cell].compareTo("NONE") != 0) {
            System.out.println("Invalid move: cell " + cell + " already has a color");
        }
        totalMoves++;
        cellArray[cell] = color;
        for (int i : findNeighbors(cell)) {
            if (cellArray[i].compareTo(color) == 0) {
                unionFind.union(i, cell);
            }
        }
    }

    public String toString() {
        String indent = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cellArray.length - 4; i++) {
            if (i % sideLength == 0) {
                sb.append("\n");
                sb.append(indent);
                indent = indent + " ";
            }
            if (cellArray[i].compareTo("RED") == 0) {
                sb.append(COLOR_RED + "R " + COLOR_RESET);
            }
            else if (cellArray[i].compareTo("BLUE") == 0) {
                sb.append(COLOR_BLUE + "B " + COLOR_RESET);
            }
            else {
                sb.append("0 ");
            }
        }
        return sb.toString();
    }

    public int getSize() {
        return cellArray.length;
    }

    public int getSideLength() {
        return sideLength;
    }

    public HexGame() {
        this(11);
    }
    public HexGame(int sideLength) {
        this.sideLength = sideLength;
        cellArray = new String[(sideLength * sideLength) + 4];
        for (int i = 0; i < cellArray.length - 4; i++) {
            cellArray[i] = "NONE";
        }

        // setting top, bottom, left, right
        sides = new int[4];
        sides[0] = cellArray.length - 4; // top
        cellArray[cellArray.length - 4] = "RED";
        sides[1] = cellArray.length - 3; // bottom
        cellArray[cellArray.length - 3] = "RED";
        sides[2] = cellArray.length - 2; // left
        cellArray[cellArray.length - 2] = "BLUE";
        sides[3] = cellArray.length - 1; // right
        cellArray[cellArray.length - 1] = "BLUE";

        unionFind = new UnionFind(cellArray.length);
    }

    public static void main(String[] args) {
        System.out.println("Test constructor:");
        HexGame game0 = new HexGame();
        System.out.println(game0.getSize() + " cells for a side length of " + game0.getSideLength());
        System.out.println(game0);

        System.out.println("Test findNeighbors, overlapping moves, alternative sized boards");
        HexGame game1 = new HexGame(14);
        int[] cellsToTest = {25, 47, 63, 81, 107, 160};
        for (int i : cellsToTest) {
            game1.move("BLUE", i);
            for (int j : game1.findNeighbors(i)) {
                game1.move("RED", j);
            }
        }
        System.out.println(game1);

        HexGame game2 = new HexGame();
        String[] colors = {"BLUE", "RED"};
        System.out.println("Testing board with file moves.txt");
        try {
            Scanner scanner = new Scanner(new File("moves.txt"));
            int moves = 0;
            while (scanner.hasNextInt()) {
                game2.move(colors[moves % 2], scanner.nextInt());
                moves++;
            }
            if (!game2.checkForWin()) {
                System.out.print("No win found! Final board:");
                System.out.println(game2);
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error: File not found");
        }

        System.out.println("Testing board with file moves2.txt");
        HexGame game3 = new HexGame();
        try {
            Scanner scanner = new Scanner(new File("moves2.txt"));
            int moves = 0;
            while (scanner.hasNextInt()) {
                game3.move(colors[moves % 2], scanner.nextInt());
                moves++;
            }
            if (!game3.checkForWin()) {
                System.out.print("No win found! Final board:");
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error: File not found");
        }
    }
}
