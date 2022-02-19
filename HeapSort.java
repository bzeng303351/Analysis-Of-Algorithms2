/*
HeapSort data structure and algorithm:
1. max heapify
2. build heap
3. heap sort of k sorted subarrays
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class HeapSort {

    public class Node {
        public int val;
        public int index;
        public int arrayIndex; // optional, used for k-subarray sorting
        public int elementIndex; // optional, used for k-subarray sorting
        public Node root;
        public Node left;
        public Node right;

        public Node(int val, int index) {
            this.val = val;
            this.index = index;
            root = null;
            left = null;
            right = null;
            arrayIndex = index;
            elementIndex = 0;
        }

        public void setKIndex(int arrayIndex, int elementIndex) {
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }

        public boolean isMaxRoot() {
            if (hasLeft() && val < left.val)
                return false;
            if (hasRight() && val < right.val)
                return false;
            return true;
        }

        public boolean hasLeft() {
            return left != null;
        }

        public boolean hasRight() {
            return right != null;
        }

        public boolean hasChild() {
            return hasLeft() || hasRight();
        }

        public void removeChild(Node child) {
            if (left == child)
                left = null;
            if (right == child)
                right = null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(val);
            String str = "(" + arrayIndex + "," + elementIndex + ")";
            sb.append(str);
            sb.append(": ");
            sb.append((root == null) ? "null" : root.val);
            if (hasLeft()) {
                sb.append(", ");
                sb.append(left.val);
            }
            if (hasRight()) {
                sb.append(", ");
                sb.append(right.val);
            }
            return sb.toString();
        }

    }

    private final ArrayList<Node> nodeArray;

    public HeapSort(int[] a) {
        nodeArray = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            nodeArray.add(new Node(a[i], i));
        }
        for (int i = 0; i < nodeArray.size(); i++) {
            nodeArray.get(i).root = (i == 0) ? null : nodeArray.get((i - 1) / 2);
            nodeArray.get(i).left = (2 * i + 1 >= nodeArray.size()) ? null : nodeArray.get(2 * i + 1);
            nodeArray.get(i).right = (2 * i + 2 >= nodeArray.size()) ? null : nodeArray.get(2 * i + 2);
        }
    }

    public boolean isHeap() {
        for (Node node : nodeArray) {
            if (!node.isMaxRoot())
                return false;
        }
        return true;
    }

    public void maxHeapify(int index) {
        Node curr = nodeArray.get(index);
        while (!curr.isMaxRoot()) {
            curr = promoteMaxChild(curr);
        }
    }

    public void maxHeapify(Node root) {
        while (!root.isMaxRoot()) {
            root = promoteMaxChild(root);
        }
    }

    public void buildHeapTopDown() {
        buildHeapTopDown(nodeArray.get(0));
    }

    public void buildHeapTopDown(Node root) {
        if (!root.hasChild())
            return;
        if (root.hasLeft())
            buildHeapTopDown(root.left);
        if (root.hasRight())
            buildHeapTopDown(root.right);
        maxHeapify(root);
    }

    public void buildHeapBottomUp() {
        for (int i = nodeArray.size() - 1; i >= 0; i--)
            maxHeapify(nodeArray.get(i));
    }

    public Node promoteMaxChild(Node root) {
        Node currMax = root;
        if (root.hasLeft() && root.left.val > currMax.val)
            currMax = root.left;
        if (root.hasRight() && root.right.val > currMax.val)
            currMax = root.right;
        swapVal(root, currMax);
        return currMax;
    }

    public void swapVal(Node n1, Node n2) {

        int temp = n1.val;
        n1.val = n2.val;
        n2.val = temp;

        temp = n1.arrayIndex;
        n1.arrayIndex = n2.arrayIndex;
        n2.arrayIndex = temp;

        temp = n1.elementIndex;
        n1.elementIndex = n2.elementIndex;
        n2.elementIndex = temp;

    }

    public void removeLast() {
//        System.out.println(this);
        Node last = nodeArray.get(nodeArray.size() - 1);
//        System.out.println("last node to remove: " + last.toString());
        Node root = last.root;
//        System.out.println("root of last node: " + root);
        if (root != null)
            root.removeChild(last);
        nodeArray.remove(nodeArray.size() - 1);
    }

    public int decreaseKey(int[][] a) {
        Node root = nodeArray.get(0);
        Node last = nodeArray.get(nodeArray.size() - 1);
        int ret = root.val;
        if (root.elementIndex + 1 < a[root.arrayIndex].length) {
            root.val = a[root.arrayIndex][++root.elementIndex];
        }
        else {
            swapVal(root, last);
            removeLast();
        }
        maxHeapify(root);
        return ret;
    }

    public static boolean isSortedMaxToMin(int[] b) {
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] < b[i + 1])
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodeArray) {
            sb.append(node.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // generate 100 random number between 0 to 100
        int[]  randomIntsArray = IntStream.generate(() -> new Random().nextInt(100)).limit(10).toArray();
        int[] a = {1, 2, 3, 4, 5, 6};
        int ROW = 10;
        int COL = 10;
        int[][] arrays = new int[ROW][];
        int[] heads = new int[ROW];
        for (int i = 0; i < arrays.length; i++) {
            int[] temp = IntStream.generate(() -> new Random().nextInt(100)).limit(COL).toArray();
            Arrays.sort(temp);
            for (int j = 0; j < temp.length; j++) {
                temp[j] = 100 - temp[j];
            }
            arrays[i] = temp;
            heads[i] = temp[0];
        }
        int[][] arrays2 = {{3,2,1}, {6,5,4}, {9,8,7}};
        //arrays = arrays2;
        HeapSort model = new HeapSort(heads);
        System.out.println(Arrays.deepToString(arrays));
        System.out.println(model.isHeap());
        System.out.println(model);
        model.buildHeapTopDown();
        System.out.println(model.isHeap());
        System.out.println(model);

        int[] sorted = new int[arrays.length * arrays[0].length];
        int iSorted = 0;
        //       System.out.println(Arrays.toString(sorted));
        while(!model.nodeArray.isEmpty()) {
            sorted[iSorted] = model.decreaseKey(arrays);
//            System.out.println("iteration: " + iSorted);
//            System.out.println(Arrays.toString(sorted));
//            System.out.println(model);
            iSorted++;
        }
        System.out.println(Arrays.deepToString(arrays));
        System.out.println(Arrays.toString(sorted));
        System.out.println("sorted max to min: " + isSortedMaxToMin(sorted));
    }
}
