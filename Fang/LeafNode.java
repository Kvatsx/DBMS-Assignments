import java.util.*;

public class LeafNode extends Node {
    public List<Integer> Values;
    public LeafNode next;

    public LeafNode()
    {
        this.keys = new ArrayList<Integer>();
        this.Values = new ArrayList<Integer>();
    }

    @Override
    public int getValue(int key)
    {
        int index = -1;
        // System.out.println(keys+" "+key);
        for ( int i=0; i<keys.size(); i++ )
        {
            if ( keys.get(i) == key )
            {
                index = i;
                break;
            }
        }
        if ( index >= 0 )
        {
            return Values.get(index);
        }
        return Integer.MIN_VALUE;
    }
    
    @Override
    public void DeleteValue(int key)
    {
        int index = -1;
        for ( int i=0; i<keys.size(); i++ )
        {
            if ( keys.get(i) == key )
            {
                index = i;
                break;
            }
        }
        if ( index >= 0 )
        {
            keys.remove(index);
            Values.remove(index);
        }
    }
    
    @Override
    public void InsertValue(int key, int value)
    {
        int index = -1;
        for ( int i=0; i<keys.size(); i++ )
        {
            if ( keys.get(i) == key )
            {
                index = i;
                break;
            }
        }
        if ( index >= 0 )
        {
            Values.set(index, value);
        }
        else
        {
            keys.add(key);
            Values.add(value);
        }
        if ( BPlusTree.Root.isOverflow() )
        {
            Node sibl = Split();
            InternalNode nroot = new InternalNode();
            nroot.keys.add(sibl.getFirst_Leaf_Key());
            nroot.children.add(this);
            nroot.children.add(sibl);
            BPlusTree.Root = nroot;
        }
    }

    @Override
    public int getFirst_Leaf_Key()
    {
        return keys.get(0);
    }

    @Override
    public void Merge(Node sibling)
    {
        LeafNode lnode = (LeafNode) sibling;
        keys.addAll(lnode.keys);
        Values.addAll(lnode.Values);
        next = lnode.next;
    }

    @Override
    public Node Split()
    {
        LeafNode sibl = new LeafNode();
        int f = (KeySize() + 1) / 2;
        int t = KeySize();
        sibl.keys.addAll(keys.subList(f, t));
        sibl.Values.addAll(Values.subList(f, t));
        keys.subList(f, t).clear();
        Values.subList(f, t).clear();
        sibl.next = next;
        next = sibl;
        return sibl;
    }

    @Override
    public boolean isOverflow()
    {
        if ( Values.size() > BPlusTree.Order-1 )
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUnderflow()
    {
        if ( Values.size() < (BPlusTree.Order / 2) )
        {
            return true;
        }
        return false;
    }
}