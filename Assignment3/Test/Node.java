import java.util.*;

public abstract class Node {
    public List<String> keys;

    public int keyNumber() {
        return keys.size();
    }

    public abstract int getValue(String key);

    public abstract void deleteValue(String key);

    public abstract void insertValue(String key, int value);

    public abstract String getFirstLeafKey();

    public abstract LeafNode getFirstLeafNode();    

    public abstract List<Integer> getRange(String key1, String key2);

    public abstract void merge(Node sibling);

    public abstract Node split();

    public abstract boolean isOverflow();

    public abstract boolean isUnderflow();

    public String toString() {
        return keys.toString();
    }
}
