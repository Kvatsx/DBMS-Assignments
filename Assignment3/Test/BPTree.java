import java.util.*;
import java.io.*;

public class BPTree {

    public static int Order;
    public static Node Root;

    public BPTree(int Order) {
        this.Order = Order;
        this.Root = new LeafNode();
    }

    public int search(int key) {
        return Root.getValue(key);
    }

    public void insert(int key, int value) {
        Root.insertValue(key, value);
    }

    public void delete(int key) {
        Root.deleteValue(key);
    }

    public List<Integer> searchRange(int key1, int key2) {
        return Root.getRange(key1, key2);
    }

    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(Root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else
                    sb.append('\n');
            }
            queue = nextQueue;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Reader.init(System.in);
        System.out.print("Please Enter Order for B+Tree: ");
        int order = Reader.nextInt();
        BPTree bpt = new BPTree(Order);
        while (true) {
            System.out.println("Menu");
            System.out.println("1) Find");
            System.out.println("2) Print All");
            System.out.println("3) Find Range");
            System.out.println("4) Insert");
            System.out.println("5) Delete");
        }
        // BPTree bt = new BPTree(4);
        // System.out.println("Started.......");
        // bt.insert(0, 100);
        // System.out.println("First Test Done");
        // // System.out.println(bt);
        // bt.insert(1, 101);
        // bt.insert(2, 102);
        // System.out.println(bt);
        // bt.insert(3, 103);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(4, 104);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(5, 105);
        // // System.out.println(BPTree.Root.keys);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(6, 106);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(7, 107);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(50, 150);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(30, 130);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(20, 120);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(11, 111);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(8, 108);
        // System.out.println("BPTree\n" + bt);
        // bt.insert(9, 109);
        // System.out.println("BPTree CURRENT\n" + bt);
        // bt.delete(1);
        // System.out.println("BPTree\n" + bt);
        // bt.delete(4);
        // System.out.println("BPTree\n" + bt);
        // bt.delete(3);
        // System.out.println("BPTree\n" + bt);
        // System.out.println(bt.search(0));
        // System.out.println(bt.search(6));
        // System.out.println(bt.search(9));
        // System.out.println("Search Range ");
        // System.out.println(bt.searchRange(3, 8));
    }
}

/** Class for buffered reading int and double values */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }
}