import java.util.*;
import java.io.*;

public class LeafNode extends Node {
    public List<Integer> values;
    public LeafNode next;

    public LeafNode() {
        keys = new ArrayList<Integer>();
        values = new ArrayList<Integer>();
    }

    @Override
    public int getVal(int key) {
        int index = -1;

        for(int i=0; i<keys.size(); i++) {
            if (keys.get(i) == key) {
                index = i;
                break;
            }
        }
        
        int itemToReturn = -1;
        if(index >= 0) {
            itemToReturn = values.get(index);
        }
        return itemToReturn;
    }

    @Override
    public void Delete(int key) {
        int index = -1;

        for(int i=0; i<keys.size(); i++) {
            if (keys.get(i) == key) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            keys.remove(index);
            values.remove(index);
        }
    }

    @Override
    public void Insert(int key, int value) {
        int index = Collections.binarySearch(keys, key);
        int foundIndex = index;
        
        if(index < 0) {
            foundIndex = (-1)*index - 1;
        }
        if (index >= 0) {
            values.set(foundIndex, value);
        } else {
            keys.add(foundIndex, key);
            values.add(foundIndex, value);
        }
        if (BPTree.Root.checkValidity() == 1) {
            Node nearByNode = Split();
            InternalNode subRoot = new InternalNode();
            subRoot.keys.add(nearByNode.getFKey());
            subRoot.children.add(this);
            subRoot.children.add(nearByNode);
            BPTree.Root = subRoot;
        }
    }

    @Override
    public int getFKey() {
        return keys.get(0);
    }

    
    @Override
    public void Merge(Node paramNode) {
        LeafNode selectedNode = (LeafNode) paramNode;
        keys.addAll(selectedNode.keys);
        values.addAll(selectedNode.values);
        next = selectedNode.next;
    }
    
    public int compare(int a, int b)
    {
        if ( a < b )
        {
            return -1;
        }
        else if ( a > b )
        {
            return 1;
        }
        else 
        {
            return 0;
        }
    }
    
    @Override
	public TreeSet<Integer> Range(int key1, int key2) {
        TreeSet<Integer> result = new TreeSet<Integer>();
        LeafNode node = this;
        while (node != null) {
            for(int i=0; i<node.keys.size(); i++) {
                // int cmp1 = compare(node.keys.get(i), key1);
                // int cmp2 = compare(node.keys.get(i), key2);
                if (compare(node.keys.get(i), key1) >= 0 && compare(node.keys.get(i), key2) <= 0) {
                    result.add(node.values.get(i));
                } 
                else if (compare(node.keys.get(i), key2) >= 0 || compare(node.keys.get(i), key2) > 0) {
                    return result;
                }
            }
            node = node.next;
        }
        return result;
    }
    
    public void clearKeys(int x, int y) {
        keys.subList(x, y).clear();
        values.subList(x, y).clear();
    }
    
    @Override
    public Node Split() {
        LeafNode nearByNode = new LeafNode();
        nearByNode.keys.addAll(keys.subList((size() + 1) / 2, size()));
        nearByNode.values.addAll(values.subList((size() + 1) / 2, size()));
        clearKeys((size() + 1) / 2, size());
        nearByNode.next = next;
        next = nearByNode;
        return nearByNode;
    }

    /**
     * 1: If overflow
     * 2: If underflow
     * 0: If valid
     */
    @Override
    public int checkValidity() {
        if(values.size() > BPTree.Order-1) {
            return 1;
        }
        if (values.size() < BPTree.Order / 2) {
           return 2;
        }
        else {
            return 0;
        }
    }

    @Override
    public LeafNode getNode() {
        return this;
    } 
}