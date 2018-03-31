import java.util.*;

public class InternalNode extends Node {
    public List<Node> children;

    public InternalNode() {
        this.keys = new ArrayList<Integer>();
        this.children = new ArrayList<Node>();
    }

    @Override
    public int getValue(int key) {
        return getChild(key).getValue(key);
    }

    @Override
    public void deleteValue(int key) {
        Node child = getChild(key);
        // System.out.println("Child.keys: "+ child.keys);
        child.deleteValue(key);
        if (child.isUnderflow()) {
            Node childLeftSibling = getChildLeftSibling(key);
            Node childRightSibling = getChildRightSibling(key);
            Node left = childLeftSibling != null ? childLeftSibling : child;
            Node right = childLeftSibling != null ? child : childRightSibling;
            // System.out.print("Left: " + left+" || ");
            // System.out.println("Right: " + right);
            left.merge(right);
            deleteChild(right.getFirstLeafKey());
            if (left.isOverflow()) {
                Node sibling = left.split();
                insertChild(sibling.getFirstLeafKey(), sibling);
            }
            if (BPTree.Root.keyNumber() == 0)
                BPTree.Root = left;
        }
    }

    @Override
    public void insertValue(int key, int value) {
        Node child = getChild(key);
        child.insertValue(key, value);
        if (child.isOverflow()) {
            Node sibling = child.split();
            insertChild(sibling.getFirstLeafKey(), sibling);
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
        return children.get(0).getFirstLeafKey();
    }

    @Override
    public LeafNode getFirstLeafNode() {
        return children.get(0).getFirstLeafNode();
    }    
    

    @Override
	public TreeSet<Integer> getRange(int key1, int key2) {
		return getChild(key1).getRange(key1, key2);
}

    @Override
    public void merge(Node sibling) {
        InternalNode node = (InternalNode) sibling;
        keys.add(node.getFirstLeafKey());
        // System.out.println("Keys: "+keys);
        keys.addAll(node.keys);
        // System.out.println("Keys: "+keys);
        children.addAll(node.children);

    }

    @Override
    public Node split() {
        int from = keyNumber() / 2 + 1, to = keyNumber();
        InternalNode sibling = new InternalNode();
        sibling.keys.addAll(keys.subList(from, to));
        sibling.children.addAll(children.subList(from, to + 1));

        keys.subList(from - 1, to).clear();
        children.subList(from, to + 1).clear();

        return sibling;
    }

    @Override
    public boolean isOverflow() {
        return children.size() > BPTree.Order;
          }

    @Override
    public boolean isUnderflow() {
        return children.size() < (BPTree.Order + 1) / 2;
    }

    public Node getChild(int key) {
        int loc = Collections.binarySearch(keys, key);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        // System.out.println("Child index: "+ childIndex);
        return children.get(childIndex);
    }

    public void deleteChild(int key) {
        int loc = Collections.binarySearch(keys, key);
        // System.out.println("Delete Index: " + loc);
        // System.out.println("Key To Find: " + key);
        if (loc >= 0) {
            keys.remove(loc);
            children.remove(loc + 1);
        }
    }

    public void insertChild(int key, Node child) {
        int loc = Collections.binarySearch(keys, key);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        if (loc >= 0) {
            children.set(childIndex, child);
        } else {
            keys.add(childIndex, key);
            children.add(childIndex + 1, child);
        }
    }

    public Node getChildLeftSibling(int key) {
        int loc = Collections.binarySearch(keys, key);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        // System.out.println("Child Index Left: " + childIndex);
        if (childIndex > 0) {
            // System.out.println("Children LEFT: " + children.get(childIndex - 1));
            return children.get(childIndex - 1);
        }

        return null;
    }

    public Node getChildRightSibling(int key) {
        int loc = Collections.binarySearch(keys, key);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        // System.out.println("Child Index Right: " + childIndex);

        if (childIndex < keyNumber()) {
            // System.out.println("Children RIGHT: " + children.get(childIndex + 1));
            return children.get(childIndex + 1);
        }

        return null;
    }
}