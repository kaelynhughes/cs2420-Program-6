public class UnionFind {

    private int[] array;

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        // make sure roots arent the same
        if (rootA == rootB) {
            System.out.println(a + " and " + b + " are already linked");
            return;
        }
//        if rootA has a greater height, it will be the root of the new tree
        if (array[rootA] < array[rootB]) {
            array[rootB] = rootA;
        }
        // otherwise rootB will have the greater root
        else {
            array[rootB] = Math.min(array[rootA] - 1, array[rootB]);
            array[rootA] = rootB;
        }

    }

    public int find(int a) {
        int parent = array[a];
        int child = a;
        while (parent >= 0) {
            child = parent;
            parent = array[parent];
        }
        return child;
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
            if (i == array[i]) {
                sb.append("**");
            } else {
                sb.append(array[i]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public UnionFind() {
        this(121);
    }

    public UnionFind(int size) {
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    public static void main(String[] args) {
        UnionFind set1 = new UnionFind('k' + 1);
        set1.union('d', 'a');
        set1.union('e', 'b');
        set1.union('k', 'a');
        set1.union('b', 'a');
        set1.union('f', 'c');
        set1.union('i', 'h');
        set1.union('e', 'a');
        System.out.println(set1.toStringAlpha());
        System.out.println((char) set1.find('f'));
        System.out.println((char) set1.find('e'));
        System.out.println();
        System.out.println(set1.toStringAlpha());

        UnionFind set2 = new UnionFind();

    }
}
