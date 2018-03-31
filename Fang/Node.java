import java.util.*;

public abstract class Node {
    public List<Integer> keys;

    public int KeySize() {
        return keys.size();
    }

    public abstract int getValue(int key);

    public abstract void DeleteValue(int key);

    public abstract void InsertValue(int key, int value);

    public abstract int getFirst_Leaf_Key();

    // public abstract LeafNode getFirstLeafNode();

    public abstract void Merge(Node sibling);

    public abstract Node Split();

    public abstract boolean isOverflow();

    public abstract boolean isUnderflow();

    public String toString() {
        return keys.toString();
    }
}
