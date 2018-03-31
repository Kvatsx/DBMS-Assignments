import java.util.*;
import java.io.*;

public class BPTree implements Serializable{

    public static int Order = 10;
    public static Node Root;
    public ArrayList<Data> data;

    public BPTree() {
        this.Root = new LeafNode();
    }

    public int search(int key) {
        // System.out.println(key instanceof String);
        return Root.getVal(key);
    }

    public void insert(int key, int value) {
        Root.Insert(key, value);
    }

    public void delete(int key) {
        Root.Delete(key);
    }

    public TreeSet<Integer> searchRange(int key1, int key2) {
        return Root.Range(key1, key2);
    }

    public void printAll()
    {
        LeafNode node = (LeafNode) Root.getNode();
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

    public void CSVReader()
    {
        String csv = "record.csv";
        BufferedReader br = null;
        String line = "";
        String del = ",";

        try {
            data = new ArrayList<Data>();

            br = new BufferedReader(new FileReader(csv));
            br.readLine();
            while ((line = br.readLine()) != null) {

                String[] rec = line.split(del);
                // System.out.println(rec.length);

                String out = rec[0] + " " + rec[1] + " " + rec[2] + " " + rec[3] + " " + rec[4];
                // System.out.println(out);
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
            if ( e.getValidityTag().compareTo("0000") != 0 )
            {
                insert(Integer.valueOf(e.getInstructorId()), i);
            }
        }
        createCSV(data, "local_record.csv");
    }

    public static void serialize(ArrayList<Data> obj) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("indexfile.txt"));
            out.writeObject(obj);
        } finally {
            out.close();
        }
    }

    public static ArrayList<Data> deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("indexfile.txt"));
            ArrayList<Data> obj = (ArrayList<Data>) in.readObject();
            return obj;
        } finally {
            in.close();
        }
    }
    
    public static void init() throws IOException{
        BPTree bt = new BPTree();
        bt.CSVReader();
        bt.bulkInsert();
        // System.out.println(bt);
        serialize(bt.data);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Reader.init(System.in);
        // System.out.print("Please Enter Order for B+Tree: ");
        // int order = Reader.nextInt();
        // init();
        // To Start from begining comment down below code and uncomment init() method.*************
        BPTree bt = new BPTree();
        System.out.println("Order of the Tree is 10");
        bt.data = deserialize();
        bt.bulkInsert();
        while (true) {
            System.out.println("\nMenu");
            System.out.println("1) Find");
            System.out.println("2) Print All");
            System.out.println("3) Find Range");
            System.out.println("4) Print All values for a key");
            System.out.println("5) Insert");
            System.out.println("6) Delete");
            System.out.println("7) Exit\n");
            int input = Reader.nextInt();
            if ( input == 1 )
            {
                System.out.print("Enter the key: ");
                int key = Reader.nextInt();
                boolean flag = false;
                for ( int i=0; i<bt.data.size(); i++ )
                {
                    if ( bt.data.get(i).getValidityTag().compareTo("0000") != 0 )
                    {
                        int k = Integer.valueOf(bt.data.get(i).getInstructorId());
                        if ( k == key )
                        {
                            flag = true;
                            break;
                        }
                    } 
                }
                if ( flag )
                {
                    int out = bt.search(key);
                    System.out.println(bt.data.get(out));
                }
                else
                {
                    System.out.println("Wrong key number!\nTry Again");
                }
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
                    System.out.println(bt.data.get(itr.next()));
                }
            }
            else if ( input == 4 ) 
            {
                // Since Keys are unique there will be only one data.
                System.out.print("Enter the key for which you want to print all data: ");
                int key = Reader.nextInt();
                boolean flag = false;
                for (int i = 0; i < bt.data.size(); i++) {
                    if (bt.data.get(i).getValidityTag().compareTo("0000") != 0) {
                        int k = Integer.valueOf(bt.data.get(i).getInstructorId());
                        if (k == key) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    int out = bt.search(key);
                    System.out.println(bt.data.get(out));
                } else {
                    System.out.println("Wrong key number!\nTry Again");
                }
            }
            else if ( input == 5 )
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
                boolean Flag = true;
                for ( int i=0; i<bt.data.size(); i++ )
                {
                    int inID = Integer.valueOf(bt.data.get(i).getInstructorId());
                    if ( inID == Integer.valueOf(id) && bt.data.get(i).getValidityTag().compareTo("0000") != 0 )
                    {
                        Flag = false;
                        break;
                    }
                }
                if ( Flag )
                {
                    // Write to the file and insert in Tree
                    Data obj = new Data(id, name, department, salary);
                    bt.data.add(obj);
                    bt.insert(Integer.valueOf(id), bt.data.size() - 1);
                    createCSV(bt.data, "local_record.csv");
                    File file = new File("indexfile");

                    file.delete();
                    serialize(bt.data);
                    createCSV(bt.data, "local_record.csv");
                }
                else
                {
                    System.out.println("Record already exist with same InstructorID.");
                }
            }
            else if ( input == 6 )
            {
                System.out.print("Enter the key: ");
                int key = Reader.nextInt();
                boolean Flag = false;
                for (int i = 0; i < bt.data.size(); i++) {
                    int inID = Integer.valueOf(bt.data.get(i).getInstructorId());
                    if (inID == key && bt.data.get(i).getValidityTag().compareTo("0000") != 0) {
                        Flag = true;
                        break;
                    }
                }
                if ( Flag )
                {
                    bt.delete(key);
                    for (int i = 0; i < bt.data.size(); i++) {
                        Data e = bt.data.get(i);
                        if (Integer.valueOf(e.getInstructorId()) == key) {
                            e.Delete();
                            break;
                        }
                    }
                    serialize(bt.data);
                    createCSV(bt.data, "local_record.csv");
                }
                else
                {
                    System.out.println("Wrong key Entered.");
                }
            }
            else
            {
                break;
            }
        }
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