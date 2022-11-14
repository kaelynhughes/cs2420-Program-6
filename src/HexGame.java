public class HexGame {
    private String[] cellArray;
    private final int sideLength;
    private UnionFind unionFind;

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
            int top = cellArray.length - 3;
            neighbors[0] = top;
            neighbors[1] = top;
        }

        if (cell > sideLength * (sideLength - 1)) {
            int bottom = cellArray.length - 4;
            neighbors[4] = bottom;
            neighbors[5] = bottom;
        }

        if (cell % sideLength == 0) {
            int left = cellArray.length - 1;
            neighbors[2] = left;
            neighbors[4] = left;
        }

        if (cell % sideLength == sideLength - 1) {
            int right = cellArray.length - 2;
            neighbors[3] = right;
            neighbors[5] = right;
        }

        return neighbors;
    }

    public void move(String color, int cell) {
        if (cell > sideLength * sideLength || cell < 0) {
            System.out.println("Invalid move: attempted cell " + cell);
            return;
        }
        else if (cellArray[cell].compareTo("NONE") != 0) {
            System.out.println("Invalid move: cell " + cell + " already has a color");
        }

        cellArray[cell] = color;
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
                sb.append("R ");
            }
            else if (cellArray[i].compareTo("BLUE") == 0) {
                sb.append("B ");
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
        cellArray[cellArray.length - 4] = "RED"; // bottom
        cellArray[cellArray.length - 3] = "RED"; // top
        cellArray[cellArray.length - 2] = "BLUE"; // right
        cellArray[cellArray.length - 1] = "BLUE"; // left
        unionFind = new UnionFind(cellArray.length);
    }

    public static void main(String[] args) {
        System.out.println("Test constructor:");
        HexGame game1 = new HexGame();
        System.out.println(game1.getSize() + " cells for a side length of " + game1.getSideLength());
        System.out.println(game1);
    }
}
