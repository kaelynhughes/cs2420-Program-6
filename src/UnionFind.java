public class UnionFind {

    private int[] array;

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        // make sure roots aren't the same
        if (rootA == rootB) { return; }
//        if rootA has a greater height, it will be the root of the new tree
        if (array[rootA] < array[rootB]) {
            array[rootB] = rootA;
        }
        // if not rootB will either have the greater height or an equal height to rootA
        else {
            array[rootB] = Math.min(array[rootA] - 1, array[rootB]);
            array[rootA] = rootB;
        }
    }

    public int find(int a) {
        if (array[a] < 0) {
            return a;
        }
        int root = find(array[a]);
        array[a] = root;
        return root;
    }

    public String toStringAlpha() {
        StringBuilder sb = new StringBuilder();
        for (int i = 'a'; i < array.length; i++) {
            sb.append((char) i);
            sb.append(" -> ");
            if (array[i] < 0) {
                sb.append("root (height ");
                sb.append(-1 * array[i]);
                sb.append(")");
            }
            else {
                sb.append((char) array[i]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(i);
            sb.append(" -> ");
            if (array[i] < 0) {
                sb.append("root (height ");
                sb.append(-1 * array[i]);
                sb.append(")");
            } else {
                sb.append(array[i]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public int getSize() {
        return array.length;
    }

    public UnionFind() {
        this(125);
    }

    public UnionFind(int size) {
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    public UnionFind(int[] provided) {
        array = new int[provided.length];
        for (int i = 0; i < provided.length; i++) {
            array[i] = provided[i];
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing union on unlinked & linked nodes: ");
        UnionFind set1 = new UnionFind('k' + 1);
        set1.union('d', 'a');
        set1.union('e', 'b');
        set1.union('k', 'a');
        set1.union('b', 'a');
        set1.union('f', 'c');
        set1.union('i', 'h');
        set1.union('e', 'a');
        System.out.println(set1.toStringAlpha());
        System.out.println("Performing find on f and e:");
        System.out.println((char) set1.find('f'));
        System.out.println((char) set1.find('e'));
        System.out.println();
        System.out.println("After find:");
        System.out.println(set1.toStringAlpha());

        System.out.println("Testing default constructor & performing many unions:");
        UnionFind set2 = new UnionFind();
        for (int i = set2.getSize() - 3; i > 10; i -= 2) {
            set2.union(i, i + 1);
        }
        for (int i = set2.getSize() - 4; i > 10; i -= 2) {
            set2.union(i, i + 1);
        }
        set2.union(119, 120);
        System.out.println(set2);

        System.out.println("Testing path compression\n");
        int[] array = {2, 0, -4, 0, 1, 2, -1, 2, 1};
        UnionFind set3 = new UnionFind(array);
        System.out.println("Before find on 4:");
        System.out.println(set3);
        set3.find(4);
        System.out.println("After find on 4:");
        System.out.println(set3);
    }
}
