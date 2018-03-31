import java.util.*;

public class LeafNode extends Node {
    public List<Integer> values;
    public LeafNode next;

    public LeafNode() {
        keys = new ArrayList<Integer>();
        values = new ArrayList<Integer>();
    }

    @Override
    public int getValue(int key) {
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
    public void deleteValue(int key) {
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
    public void insertValue(int key, int value) {
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
        if (BPTree.Root.isOverflow()) {
            Node nearByNode = split();
            InternalNode subRoot = new InternalNode();
            subRoot.keys.add(nearByNode.getFirstLeafKey());
            subRoot.children.add(this);
            subRoot.children.add(nearByNode);
            BPTree.Root = subRoot;
        }
    }

    @Override
    public int getFirstLeafKey() {
        return keys.get(0);
    }

    @Override
    public LeafNode getFirstLeafNode() {
        return this;
    } 

    @Override
    public void merge(Node paramNode) {
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
	public TreeSet<Integer> getRange(int key1, int key2) {
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
    public Node split() {
        LeafNode nearByNode = new LeafNode();
        nearByNode.keys.addAll(keys.subList((size() + 1) / 2, size()));
        nearByNode.values.addAll(values.subList((size() + 1) / 2, size()));
        clearKeys((size() + 1) / 2, size());
        nearByNode.next = next;
        next = nearByNode;
        return nearByNode;
    }

    @Override
    public boolean isOverflow() {
        if(values.size() > BPTree.Order-1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isUnderflow() {
        if (values.size() < BPTree.Order / 2) {
            return true;
        } 
        else {
            return false;
        }
    }
}