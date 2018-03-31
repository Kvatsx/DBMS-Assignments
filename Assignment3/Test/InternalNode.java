import java.util.*;

public class InternalNode extends Node {
    public List<Node> children;

    public InternalNode() {
        this.keys = new ArrayList<Integer>();
        this.children = new ArrayList<Node>();
    }

    @Override
    public int getVal(int key) {
        return getChild(key).getVal(key);
    }

    @Override
    public LeafNode getNode() {
        return children.get(0).getNode();
    }

    @Override
    public void Delete(int key) {
        Node child = getChild(key);
        // System.out.println("Child.keys: "+ child.keys);
        child.Delete(key);
        if (child.checkValidity() == 2) {
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
            l.Merge(r);
            deleteChild(r.getFKey());
            if (l.checkValidity() == 1) {
                Node sibling = l.Split();
                insertChild(sibling.getFKey(), sibling);
            }
            if (BPTree.Root.size() == 0)
                BPTree.Root = l;
        }
    }

    @Override
    public void Insert(int key, int value) {
        Node child = getChild(key);
        child.Insert(key, value);
        if (child.checkValidity() == 1) {
            Node sibling = child.Split();
            insertChild(sibling.getFKey(), sibling);
        }
        if (BPTree.Root.checkValidity() == 1) {
            Node sibling = Split();
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(sibling.getFKey());
            newRoot.children.add(this);
            newRoot.children.add(sibling);
            BPTree.Root = newRoot;
        }
    }

    @Override
    public int getFKey() {
        return children.get(0).getFKey();
    }  

    @Override
	public TreeSet<Integer> Range(int key1, int key2) {
		return getChild(key1).Range(key1, key2);
}

    @Override
    public void Merge(Node sibling) {
        InternalNode node = (InternalNode) sibling;
        keys.add(node.getFKey());
        // System.out.println("Keys: "+keys);
        keys.addAll(node.keys);
        // System.out.println("Keys: "+keys);
        children.addAll(node.children);

    }

    @Override
    public Node Split() {
        InternalNode temp = new InternalNode();
        temp.keys.addAll(keys.subList(size() / 2 + 1, size()));
        temp.children.addAll(children.subList(size() / 2 + 1, size() + 1));

        keys.subList(size() / 2 + 1 - 1, size()).clear();
        children.subList(size() / 2 + 1, size() + 1).clear();

        return temp;
    }

    /**
     * 1: If overflow
     * 2: If underflow
     * 0: If valid
     */
    @Override
    public int checkValidity() {
        if (children.size() > BPTree.Order) {
            return 1;
        }
        if (children.size() < (BPTree.Order+1) / 2) {
            return 2;
        } else {
            return 0;
        }
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
        int lc = Collections.binarySearch(keys, key);
        int childIndex = lc >= 0 ? lc + 1 : -lc - 1;
        if (lc >= 0) {
            children.set(childIndex, child);
        } else {
            keys.add(childIndex, key);
            children.add(childIndex + 1, child);
        }
    }

    public Node getChildLeftSibling(int key) {
        int lc = Collections.binarySearch(keys, key);
        int childIndex = lc >= 0 ? lc + 1 : -lc - 1;
        // System.out.println("Child Index Left: " + childIndex);
        if (childIndex > 0) {
            // System.out.println("Children LEFT: " + children.get(childIndex - 1));
            return children.get(childIndex - 1);
        }

        return null;
    }

    public Node getChildRightSibling(int key) {
        int lc = Collections.binarySearch(keys, key);
        int childIndex = lc >= 0 ? lc + 1 : -lc - 1;
        // System.out.println("Child Index Right: " + childIndex);

        if (childIndex < size()) {
            // System.out.println("Children RIGHT: " + children.get(childIndex + 1));
            return children.get(childIndex + 1);
        }

        return null;
    }
}