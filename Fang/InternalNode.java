// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox.KeySelectionManager;

public class InternalNode extends Node {
    List<Node> children;

    public InternalNode()
    {
        this.keys = new ArrayList<Integer>();
        this.children = new ArrayList<Node>();
    }
    @Override
    public int getValue(int key)
    {
        return getChild(key).getValue(key);
    }
    @Override
    public int getFirst_Leaf_Key() {
        return children.get(0).getFirst_Leaf_Key();
    }

    @Override
    public void InsertValue(int key, int value)
    {
        Node child_node = getChild(key);
        child_node.InsertValue(key, value);
        if ( child_node.isOverflow())
        {
            Node sibling = child_node.Split();
            InsertChild(sibling.getFirst_Leaf_Key(), sibling);
        }
        if ( BPlusTree.Root.isOverflow() )
        {
            Node sibling = Split();
            InternalNode new_root = new InternalNode();
            new_root.keys.add(sibling.getFirst_Leaf_Key());
            new_root.children.add(this);
            new_root.children.add(sibling);
            BPlusTree.Root = new_root;
        }
    }

    @Override
    public void DeleteValue(int key)
    {
        Node child_node = getChild(key);
        child_node.DeleteValue(key);
        if ( child_node.isUnderflow() )
        {
            Node left_child = getChild_Left_sibling(key);
            Node right_child = getChild_Right_sibling(key);
            // if ( left_child == null )
            // {
            //     left_child = child_node;
            // }
            // if ( right_child != null )
            // {
            //     right_child = child_node;
            // }

            if (left_child == null) {
                left_child = child_node;
            } else {
                right_child = child_node;
            }

            System.out.println("Right: " + right_child);
            System.out.println("Left: " + left_child);
            left_child.Merge(right_child);
            DeleteChild(right_child.getFirst_Leaf_Key());
            if ( left_child.isOverflow() )
            {
                Node sibl = left_child.Split();
                InsertChild(sibl.getFirst_Leaf_Key(), sibl);
            }
            if ( BPlusTree.Root.KeySize() == 0 )
            {
                BPlusTree.Root = left_child;
            }
        }
    }
    @Override
    public void Merge(Node sibling)
    {
        InternalNode in = (InternalNode) sibling;
        keys.add(in.getFirst_Leaf_Key());
        keys.addAll(in.keys);
        children.addAll(in.children);
    }

    @Override
    public Node Split()
    {
        int f = KeySize()/2 + 1;
        int t = KeySize();
        InternalNode sibl = new InternalNode();
        sibl.keys.addAll(keys.subList(f, t));
        sibl.children.addAll(children.subList(f, t+1));
        keys.subList(f-1, t).clear();
        children.subList(f, t+1).clear();
        return sibl;
    }

    @Override
    public boolean isOverflow()
    {
        if ( children.size() > BPlusTree.Order )
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUnderflow()
    {
        if ( children.size() < ((BPlusTree.Order + 1) / 2) )
        {
            return true;
        }
        return false;
    }

    public Node getChild(int key)
    {
        int index = Collections.binarySearch(keys, key);
        if ( index >= 0 )
        {
            index += 1;
        }
        else
        {
            index = index*(-1) - 1;
        }
        return children.get(index);
    }

    public void DeleteChild(int key)
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

        System.out.println("Delete Index: " + index);
        System.out.println("Key To Find: " + key);
        if ( index >= 0 )
        {
            keys.remove(index);
            children.remove(index+1);
        }
    }

    public void InsertChild(int key, Node child)
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
            index += 1;
            children.set(index, child);
        }
        else
        {
            index = index*(-1) - 1;
            // keys.add(index, key);
            // children.add(index+1, child);
            keys.add(key);
            children.add(child);
        }
    }

    public Node getChild_Left_sibling(int key)
    {
        int index = Collections.binarySearch(keys, key);

        if(index > 0) {
            System.out.println("Children LEFT: " + children.get(index - 1));
            return children.get(index - 1);
        }
        return null;
    }

    public Node getChild_Right_sibling(int key)
    {
        
        int index = Collections.binarySearch(keys, key);
        if (index >= 0) {
            index += 1;
        } else {
            index = index * (-1) - 1;
        }

        System.out.println("Child Index Right: " + index);        

        if (index > 0) {
            System.out.println("Children RIGHT: " + children.get(index + 1));            
            return children.get(index + 1);
        }
        return null;
    }
}