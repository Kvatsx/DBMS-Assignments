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
    public LeafNode getFirstLeafNode() {
        return children.get(0).getFirstLeafNode();
    }

    @Override
    public void deleteValue(int key) {
        Node child = getChild(key);
        // System.out.println("Child.keys: "+ child.keys);
        child.deleteValue(key);
        if (child.isUnderflow()) {
            Node l;
            Node r;

            if(getChildLeftSibling(key) == null) {
                l = child;
                r = getChildRightSibling(key);
            }
            else {
                l = getChildLeftSibling(key);
                r = child;
            }
            // System.out.print("Left: " + l+" || ");
            // System.out.println("Right: " + r);
            l.merge(r);
            deleteChild(r.getFirstLeafKey());
            if (l.isOverflow()) {
                Node sibling = l.split();
                insertChild(sibling.getFirstLeafKey(), sibling);
            }
            if (BPTree.Root.size() == 0)
                BPTree.Root = l;
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
        InternalNode temp = new InternalNode();
        temp.keys.addAll(keys.subList(size() / 2 + 1, size()));
        temp.children.addAll(children.subList(size() / 2 + 1, size() + 1));

        keys.subList(size() / 2 + 1 - 1, size()).clear();
        children.subList(size() / 2 + 1, size() + 1).clear();

        return temp;
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
        int foundIndex = Collections.binarySearch(keys, key);
        if(foundIndex >= 0) {
            foundIndex += 1;
        }
        else {
            foundIndex *= -1;
            foundIndex -= 1;
        }
        // int childIndex = foundIndex >= 0 ? foundIndex + 1 : -foundIndex - 1;
        // System.out.println("foundIndex index: "+ foundIndex);
        return children.get(foundIndex);
    }

    public void deleteChild(int key) {
        int foundIndex = Collections.binarySearch(keys, key);
        // System.out.println("Delete Index: " + foundIndex);
        // System.out.println("Key To Find: " + key);
        if (foundIndex >= 0) {
            keys.remove(foundIndex);
            children.remove(foundIndex + 1);
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

        if (childIndex < size()) {
            // System.out.println("Children RIGHT: " + children.get(childIndex + 1));
            return children.get(childIndex + 1);
        }

        return null;
    }
}