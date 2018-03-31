import java.util.*;
import java.io.*;

public class BPTree {

    public static int Order;
    public static Node Root;
    public static ArrayList<Data> data;

    public BPTree(int Order) {
        this.Order = Order;
        this.Root = new LeafNode();
    }

    public int search(int key) {
        // System.out.println(key instanceof String);
        return Root.getValue(key);
    }

    public void insert(int key, int value) {
        Root.insertValue(key, value);
    }

    public void delete(int key) {
        Root.deleteValue(key);
    }

    public TreeSet<Integer> searchRange(int key1, int key2) {
        return Root.getRange(key1, key2);
    }

    public void printAll()
    {
        LeafNode node = (LeafNode) Root.getFirstLeafNode();
        TreeSet<Integer> al = new TreeSet<Integer>();
        do {
            // System.out.println("\nNodes Values\n"+node.values+"\n");
            for ( int i=0; i<node.values.size(); i++ )
            {
                int out = node.values.get(i);
                al.add(out);
            }
            node = node.next;
        } while(node != null);
        Iterator<Integer> itr = al.iterator();
        while (itr.hasNext()) {
            System.out.println(data.get(itr.next()));
        }
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
    
    public static String getCSVString(Data item) {
        String finalString = item.getValidityTag() + "," + item.getInstructorId() + "," + item.getInstructorName() + "," + item.getDepartment() + ","
                + String.valueOf(item.getSalary()) + '\n';
        return finalString;
    }
    
    public static void createCSV(ArrayList<Data> dataList, String fileName) throws IOException {
        // String fileName = "testDataFile.csv";
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.append("Validity Tag,Instructor ID,Instructor Name,Department,Salary");
        fileWriter.append("\n");
        for (Data d : dataList) {
            fileWriter.append(getCSVString(d));
        }
        fileWriter.flush();
        fileWriter.close();

    }

    public static void CSVReader()
    {
        String csvFile = "record.csv";
        BufferedReader br = null;
        String line = "";
        String del = ",";

        try {
            data = new ArrayList<Data>();

            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {

                String[] rec = line.split(del);
                System.out.println(rec.length);

                String out = rec[0] + " " + rec[1] + " " + rec[2] + " " + rec[3] + " " + rec[4] + "\n";
                Data d = new Data(rec[1], rec[2], rec[3], Long.parseLong(rec[4]));
                data.add(d);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void bulkInsert() throws IOException
    {
        for ( int i=0; i<data.size(); i++ )
        {
            Data e = data.get(i);
            insert(Integer.valueOf(e.getInstructorId()), i);
        }
        createCSV(data, "local_record.csv");
    }

    // @Override
    // public int compare(String a, String b)
    // {
    //     return a.compareTo(b);
    // }

    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        // System.out.print("Please Enter Order for B+Tree: ");
        // int order = Reader.nextInt();
        System.out.println("Order of the Tree is 10");
        BPTree bt = new BPTree(10);
        CSVReader();
        bt.bulkInsert();
        // BPTree bpt = new BPTree(Order);
        while (true) {
            System.out.println("\nMenu");
            System.out.println("1) Find");
            System.out.println("2) Print All");
            System.out.println("3) Find Range");
            System.out.println("4) Insert");
            System.out.println("5) Delete");
            System.out.println("6) Exit\n");
            int input = Reader.nextInt();
            if ( input == 1 )
            {
                System.out.print("Enter the key: ");
                int key = Reader.nextInt();
                int out = bt.search(key);
                System.out.println(data.get(out));
            }
            else if ( input == 2 )
            {
                bt.printAll();
            }
            else if ( input == 3 )
            {
                System.out.print("Enter First Key: ");
                int k1 = Reader.nextInt();
                System.out.print("Enter Second Key: ");                
                int k2 = Reader.nextInt();
                if ( k1 > k2 )
                {
                    System.out.println("Keys input error.");
                    continue;
                }
                TreeSet<Integer> al = bt.searchRange(k1, k2);
                Iterator<Integer> itr = al.iterator();
                while (itr.hasNext()) {
                    System.out.println(data.get(itr.next()));
                }
            }
            else if ( input == 4 )
            {
                System.out.println("Enter Record Details: ");
                System.out.print("Enter Instructor ID: ");
                String id = Reader.next();
                System.out.print("Enter Name: ");
                String name = Reader.next();
                System.out.print("Enter Department Name: ");
                String department = Reader.next();
                System.out.print("Enter Salary: ");
                int salary = Reader.nextInt();
                // Write to the file and insert in Tree
                Data obj = new Data(id, name, department, salary);
                data.add(obj);
                bt.insert(Integer.valueOf(id), data.size()-1);
                createCSV(data, "local_record.csv");

            }
            else if ( input == 5 )
            {
                System.out.print("Enter the key: ");
                int key = Reader.nextInt();
                bt.delete(key);
                for ( int i=0; i<data.size(); i++ )
                {
                    Data e = data.get(i);
                    if ( Integer.valueOf(e.getInstructorId()) == key )
                    {
                        e.Delete();
                        break;
                    }
                }
                createCSV(data, "local_record.csv");
            }
            else
            {
                break;
            }
        }
// To test for String keys ***********************************
        // BPTree bt = new BPTree(order);
        // System.out.println("Started.......");
        // bt.insert("0", 100);
        // System.out.println("First Test Done");
        // // System.out.println(bt);
        // bt.insert("1", 101);
        // bt.insert("2", 102);
        // System.out.println(bt);
        // bt.insert("3", 103);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("4", 104);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("5", 105);
        // // System.out.println(BPTree.Root.keys);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("6", 106);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("7", 107);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("50", 150);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("30", 130);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("20", 120);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("11", 111);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("8", 108);
        // System.out.println("BPTree\n" + bt);
        // bt.insert("9", 109);
        // System.out.println("BPTree CURRENT\n" + bt);
        // bt.delete("1");
        // System.out.println("BPTree\n" + bt);
        // bt.delete("4");
        // System.out.println("BPTree\n" + bt);
        // bt.delete("3");
        // System.out.println("BPTree\n" + bt);
        // System.out.println(bt.search("0"));
        // System.out.println(bt.search("6"));
        // System.out.println(bt.search("9"));
        // System.out.println("Search Range ");
        // System.out.println(bt.searchRange("3", "8"));

// Test for Integer keys working fine except delete***********************************
// To test for Integer Need to change key data type to Integer in all files :(
        // BPTree bt = new BPTree(order);
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
        // System.out.println("BPTree DELETION\n" + bt);
        // bt.delete(1);
        // System.out.println("BPTree\n" + bt);
        // bt.delete(4);
        // System.out.println("BPTree\n" + bt);
        // bt.delete(3);
        // System.out.println("BPTree\n" + bt);
        // System.out.println(bt.search(0));
        // System.out.println(bt.search(3));
        // System.out.println(bt.search(11));
        // System.out.println("Search Range ");
        // System.out.println(bt.searchRange(3, 8));
        // System.out.println("Print All\n");
        // bt.printAll();
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