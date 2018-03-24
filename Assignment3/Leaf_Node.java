// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

import java.util.ArrayList;

public class Leaf_Node extends Node {
    private ArrayList<Data> data;
    private LeafNode left;
    private LeafNode right;

    public Leaf_Node()
    {
        this.isLeafNode = true;
        this.Keys_data = new ArrayList<String>();
        this.data = new ArrayList<Data>();
    }

    public Leaf_Node(ArrayList<String> key, ArrayList<Data> value)
    {
        this.isLeafNode = true;
        this.Keys_data = key;
        this.data = value;
    }

    public void add(String key, Data value)
    {
        Keys_data.add(key);
        data.add(value);
    }

    public void Insert(String key, Data value)
    {

    }
}