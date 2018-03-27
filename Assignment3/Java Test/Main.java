import java.util.*;
import java.io.*;

class BPTree {
    int data;
    BPTree[] children;
    int leafNode;
    int size;

    public BPTree() {
        this.children = new BPTree[6];
        for(int child = 0; child < children.length; child ++) {
            children[child] = new BPTree();
        }
        this.size = 0;
        this.leafNode = 1; 
    }

}

public class Main {
    
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public static BPTree root = new BPTree();

    public static void split(BPTree root, int position) {
        return;
    }

    public static void insert(int item) {
        BPTree tempRoot = root;
        if (tempRoot.leafNode == 1 && tempRoot.size == 5) {
            // temp = split_child(x, -1);
            // x = root;
            // for (i = 0; i < (x -> n); i++) {
            //     if ((a > x -> data[i]) && (a < x -> data[i + 1])) {
            //         i++;
            //         break;
            //     } else if (a < x -> data[0]) {
            //         break;
            //     } else {
            //         continue;
            //     }
            // }
            // x = x -> child_ptr[i];
        }
    }

    public static void main(String[] args) throws IOException{
        System.out.print("Enter the number of Items you want to add: ");
        int numNodes = Integer.parseInt(input.readLine());

        for(int i=0; i<numNodes; i++) {
            int integerInput = Integer.parseInt(input.readLine());
            insert(integerInput);
        }
        
    }
}