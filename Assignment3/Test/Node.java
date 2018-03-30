import java.util.*;

public abstract class Node {
    public List<Integer> keys;

    public int keyNumber() {
        return keys.size();
    }

    public abstract int getValue(int key);

    public abstract void deleteValue(int key);

    public abstract void insertValue(int key, int value);

    public abstract int getFirstLeafKey();

    public abstract List<Integer> getRange(int key1, int key2);

    public abstract void merge(Node sibling);

    public abstract Node split();

    public abstract boolean isOverflow();

    public abstract boolean isUnderflow();

    public String toString() {
        return keys.toString();
    }
}
