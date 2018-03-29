// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

import java.util.*;

public class BPlusTree {
    public static int Order;
    public static Node Root;

    public BPlusTree(int order)
    {
        this.Order = order;
        this.Root = new LeafNode();
    }

    public int Search(int key)
    {
        return Root.getValue(key);
    }

    public void Insert(int key, int value)
    {
        Root.InsertValue(key, value);
    }

    public void Delete(int key)
    {
        Root.DeleteValue(key);
    }

    public String toString()
    {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(Root));
		StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) 
        {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            
            while (!queue.isEmpty()) 
            {
				List<Node> nodes = queue.remove();
				sb.append('{');
                Iterator<Node> it = nodes.iterator();
                
                while (it.hasNext()) 
                {
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

}