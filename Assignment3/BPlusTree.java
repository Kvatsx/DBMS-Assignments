// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)
import java.util.*;

public class BPlusTree {
    private Node Root;
    private int Order;

    // public Data SearchData(String key)
    // {
    //     if ( Root == null || key == null )
    //     {
            
    //     }
    // }

    private Node SearchNode(Node root, String key)
    {
        if ( root.isLeafNode )
        {
            return root;
        }
        else
        {
            Index_Node index_Node = (Index_Node) root;
            for ( int i=0; i<index_Node.Keys_data.size()-1; i++ )
            {
                if ( key.compareTo(index_Node.Keys_data.get(i)) >= 0 && key.compareTo(index_Node.Keys_data.get(i+1)) < 0 )
                {
                    return SearchNode((Node)index_Node.Keys_data.get(i+1), key);
                }
                else if ( i == 0 && key.compareTo(index_Node.Keys_data.get(i)) < 0 )
                {
                    return SearchNode((Node)index_Node.Keys_data.get(i), key);
                }
                else if ( i == index_Node.Keys_data.size()-2 && key.compareTo(index_Node.Keys_data.get(i+1)) >= 0 )
                {
                    return SearchNode((Node)index_Node.Keys_data.get(i+1), key);
                }
            }
            return null;
        }
    }
}