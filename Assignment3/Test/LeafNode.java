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
        int loc = Collections.binarySearch(keys, key);
        return loc >= 0 ? values.get(loc) : -1;
    }

    @Override
    public void deleteValue(int key) {
        int loc = Collections.binarySearch(keys, key);
        if (loc >= 0) {
            keys.remove(loc);
            values.remove(loc);
        }
    }

    @Override
    public void insertValue(int key, int value) {
        int loc = Collections.binarySearch(keys, key);
        int valueIndex = loc >= 0 ? loc : -loc - 1;
        if (loc >= 0) {
            values.set(valueIndex, value);
        } else {
            keys.add(valueIndex, key);
            values.add(valueIndex, value);
        }
        if (BPTree.Root.isOverflow()) {
            Node sibling = split();
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(sibling.getFirstLeafKey());
            newRoot.children.add(this);
            newRoot.children.add(sibling);
            BPTree.Root = newRoot;
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
    public void merge(Node sibling) {
        LeafNode node = (LeafNode) sibling;
        keys.addAll(node.keys);
        values.addAll(node.values);
        next = node.next;
    }

    public int compare(int a, int b)
    {
        if ( a < b )
        {
            return -1;
        }
        else if ( a >  b )
        {
            return 1;
        }
        else 
        {
            return 0;
        }
    }

    @Override
	public	TreeSet<Integer> getRange(int key1, int key2) {
		    TreeSet<Integer> result = new TreeSet<Integer>();
			LeafNode node = this;
			while (node != null) {
				Iterator<Integer> kIt = node.keys.iterator();
				Iterator<Integer> vIt = node.values.iterator();
				while (kIt.hasNext()) {
					int key = kIt.next();
                    int value = vIt.next();
                    int cmp1 = compare(key, key1);
                    // int cmp1 = key.compareTo(key1);
                    int cmp2 = compare(key, key2);
                    // int cmp2 = key.compareTo(key2);
                    if (cmp1 >= 0 && cmp2 <= 0) {
                        result.add(value);
                    }
					else if (cmp2 >= 0 || cmp2 > 0)
						return result;
				}
				node = node.next;
			}
			return result;
		}


    @Override
    public Node split() {
        LeafNode sibling = new LeafNode();
        int from = (keyNumber() + 1) / 2, to = keyNumber();
        sibling.keys.addAll(keys.subList(from, to));
        sibling.values.addAll(values.subList(from, to));

        keys.subList(from, to).clear();
        values.subList(from, to).clear();

        sibling.next = next;
        next = sibling;
        return sibling;
    }

    @Override
    public boolean isOverflow() {
        return values.size() > BPTree.Order - 1;
    }

    @Override
    public boolean isUnderflow() {
        return values.size() < BPTree.Order / 2;
    }
}