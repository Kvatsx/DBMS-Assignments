
public class Test {

    public static void main(String[] args) {
        BPlusTree bt = new BPlusTree(4);
        System.out.println("Started.......");
        bt.Insert(0, 100);
        System.out.println("First Test Done");
        // System.out.println(bt);
        bt.Insert(1, 101);
        bt.Insert(2, 102);
        System.out.println(bt);
        bt.Insert(3, 103);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(4, 104);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(5, 105);
        // System.out.println(BPlusTree.Root.keys);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(6, 106);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(7, 107);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(8, 108);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(50, 120);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(30, 120);
        System.out.println("BPlusTree\n" + bt);        
        bt.Insert(20, 120);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(9, 109);
        System.out.println("BPlusTree\n"+bt);
        bt.Insert(11, 109);
        System.out.println("BPlusTree\n" + bt);
        bt.Insert(10, 109);
        System.out.println("BPlusTree\n" + bt);
        bt.Delete(1);
        System.out.println("BPlusTree DELETE\n" + bt);
        bt.Delete(4);
        bt.Delete(3);
        System.out.println(bt.Search(0));
        System.out.println(bt.Search(6));
        System.out.println(bt.Search(9));        
    }
}