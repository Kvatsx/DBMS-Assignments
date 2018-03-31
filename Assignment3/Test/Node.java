import java.util.*;

public abstract class Node {
    public List<Integer> keys;
    
    public abstract void Delete(int key);
    public abstract void Insert(int key, int value);
    public abstract int checkValidity();
    public abstract Node Split();
    public abstract int getFKey();
    public abstract LeafNode getNode();    
    public abstract void Merge(Node sibling);
    public abstract TreeSet<Integer> Range(int key1, int key2);
    public abstract int getVal(int key);
    
    public String toString() {
        return keys.toString();
    }

    public int size() {
        return keys.size();
    }
}
