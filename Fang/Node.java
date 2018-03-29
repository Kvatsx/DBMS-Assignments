// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

import java.util.List;

public abstract class Node {
    public List<Integer> keys;

    public int KeySize()
    {
        return keys.size();
    }
    public abstract int getValue(int key);
    public abstract void InsertValue(int key, int value);
    public abstract void DeleteValue(int key);
    public abstract void Merge(Node sibling);
    public abstract int getFirst_Leaf_Key();
    public abstract Node Split();
    public abstract boolean isUnderflow();
    public abstract boolean isOverflow();
    public String toString()
    {
        return keys.toString();
    }
}