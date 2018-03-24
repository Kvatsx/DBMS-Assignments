// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

import java.util.ArrayList;

public class Index_Node extends Node {
    protected ArrayList<Node> Value;

    public Index_Node(String key, Node value1, Node value2)
    {
        this.isLeafNode = false;
        this.Keys_data = new ArrayList<String>();
        this.Value = new ArrayList<Node>();
        this.Value.add(value1);
        this.Value.add(value2);
    }

    public Index_Node(ArrayList<String> childs, ArrayList<Node> values)
    {
        this.isLeafNode = false;
        this.Keys_data = childs;
        this.Value = values;
    }
}