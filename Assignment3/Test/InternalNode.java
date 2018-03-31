import java.util.*;
import java.io.*;

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

            if(getLeft(key) == null) {
                l = child;
                r = getRight(key);
            }
            else {
                l = getLeft(key);
                r = child;
            }
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
        Node foundChild = getChild(key);
        foundChild.Insert(key, value);
        if (foundChild.checkValidity() == 1) {
            Node nearByNode = foundChild.Split();
            insertChild(nearByNode.getFKey(), nearByNode);
        }
        if (BPTree.Root.checkValidity() == 1) {
            Node nearByNode = Split();
            InternalNode temp = new InternalNode();
            temp.keys.add(nearByNode.getFKey());
            temp.children.add(this);
            temp.children.add(nearByNode);
            BPTree.Root = temp;
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

    public void clearKeys(int x, int y) {
        keys.subList(x, y).clear();
    }

    public void clearValues(int x, int y) {
        children.subList(x, y).clear();
    }

    @Override
    public Node Split() {
        InternalNode internalNode = new InternalNode();
        internalNode.keys.addAll(keys.subList(size() / 2 + 1, size()));
        internalNode.children.addAll(children.subList(size() / 2 + 1, size() + 1));
        clearKeys((size() / 2 + 1) - 1, size());
        clearValues(size() / 2 + 1, size() + 1);
        return internalNode;
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
        int foundIndex = Collections.binarySearch(keys, key);
        int foundInnerIndex = -1;
        if(foundIndex >= 0) {
            foundInnerIndex = foundIndex + 1;
        }
        else {
            foundInnerIndex = (-1)*foundIndex - 1;
        }

        if (foundIndex >= 0) {
            children.set(foundInnerIndex, child);
        } else {
            keys.add(foundInnerIndex, key);
            children.add(foundInnerIndex + 1, child);
        }
    }

    public Node getLeft(int key) {
        int foundIndex = Collections.binarySearch(keys, key);
        int foundInnerIndex = -1;
        if(foundIndex >= 0) {
            foundInnerIndex = foundIndex + 1;
        }
        else {
            foundInnerIndex = (-1)*foundIndex - 1;
        }

        // System.out.println("Child Index Left: " + foundInnerIndex);
        if (foundInnerIndex > 0) {
            // System.out.println("Children LEFT: " + children.get(foundInnerIndex - 1));
            return children.get(foundInnerIndex - 1);
        }

        return null;
    }

    public Node getRight(int key) {
        int foundIndex = Collections.binarySearch(keys, key);
        int foundInnerIndex = -1;
        if (foundIndex >= 0) {
            foundInnerIndex = foundIndex + 1;
        } else {
            foundInnerIndex = (-1) * foundIndex - 1;
        }
        if (foundInnerIndex < size()) {
            return children.get(foundInnerIndex + 1);
        }

        return null;
    }
}