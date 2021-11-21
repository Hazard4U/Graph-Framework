package GraphAlgorithms;


import java.util.ArrayList;
import java.util.List;

public class BinaryHeap {

    private List<Integer> nodes;

    public BinaryHeap() {
        this.nodes = new ArrayList<>();
    }

    public boolean isEmpty() {
        return nodes.size() == 0;
    }

    public void insert(int element) {
        nodes.add(nodes.size(), element);

        percolateUp(nodes.size()-1);
    }

    private void percolateUp(int src) {
        int fatherIndex = getFatherIndex(src);
        if (nodes.get(fatherIndex) > nodes.get(src)) {
            swap(fatherIndex, src);
            percolateUp(fatherIndex);
        }
    }

    private int getFatherIndex(int src) {
        return (src - 1) / 2;
    }

    private int[] getChildsIndex(int src) {
        return new int[]{src * 2 + 1, src * 2 + 2};
    }

    public int remove() {
        int value = nodes.get(0);
        swap(nodes.size()-1, 0);

        nodes.remove(nodes.size()-1);

        percolateDown(0);
        return value;
    }

    private void percolateDown(int src) {
        if (isLeaf(src)) {
            return;
        }

        int smallest = smallestChildIndex(src);

        if (nodes.get(src) > nodes.get(smallest)) {
            swap(src, smallest);

            percolateDown(smallest);
        }
    }

    private int smallestChildIndex(int src) {
        int[] childs = getChildsIndex(src);
        int smallestChildIndex = Integer.MAX_VALUE;
        if (nodes.size() > childs[1]){
            smallestChildIndex = nodes.get(childs[0]) > nodes.get(childs[1]) ? childs[1] : childs[0];
        }else if (nodes.size() > childs[0]){
            smallestChildIndex = childs[0];
        }
        return smallestChildIndex;
    }

    /**
     * Test if the node is a leaf in the binary heap
     *
     * @returns true if it's a leaf or false else
     */
    private boolean isLeaf(int src) {
        return nodes.size() <= src * 2 + 1;
    }

    private void swap(int father, int child) {
        int temp = nodes.get(father);
        nodes.set(father, nodes.get(child));
        nodes.set(child, temp);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            s.append(nodes.get(i)).append(", ");
        }
        return s.toString();
    }

    /**
     * Recursive test to check the validity of the binary heap
     *
     * @returns a boolean equal to True if the binary tree is compact from left to right
     */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= nodes.size()) {
                return nodes.get(left) >= nodes.get(root) && testRec(left);
            } else {
                return nodes.get(left) >= nodes.get(root) && testRec(left) && nodes.get(right) >= nodes.get(root) && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty() + "\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            jarjarBin.insert(rand);
            k--;
        }

        // A completer
        System.out.println("\n" + jarjarBin);
        System.out.println("test : " + jarjarBin.test());

        System.out.println("\nRemove root :");
        jarjarBin.remove();
        System.out.println(jarjarBin);
        System.out.println("test : " + jarjarBin.test());

    }

}
