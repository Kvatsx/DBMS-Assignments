import java.util.*;
import java.io.*;
public class BPTree<K extends Comparable<? super K>, V> {

	private int Order;
	private Node Root;

	public BPTree(int Order) {
		this.Order = Order;
		this.Root = new LeafNode();
	}

	public V search(K key) {
		return Root.getValue(key);
	}
	
	public void insert(K key, V value) {
		Root.insertValue(key, value);
	}
	
	public void delete(K key) {
		Root.deleteValue(key);
	}

	public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(Root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else
					sb.append('\n');
			}
			queue = nextQueue;
		}

		return sb.toString();
	}

	private abstract class Node {
		List<K> keys;

		int keyNumber() {
			return keys.size();
		}

		abstract V getValue(K key);

		abstract void deleteValue(K key);

		abstract void insertValue(K key, V value);

		abstract K getFirstLeafKey();

		abstract void merge(Node sibling);

		abstract Node split();

		abstract boolean isOverflow();

		abstract boolean isUnderflow();

		public String toString() {
			return keys.toString();
		}
	}

	private class InternalNode extends Node {
		List<Node> children;

		InternalNode() {
			this.keys = new ArrayList<K>();
			this.children = new ArrayList<Node>();
		}

		@Override
		V getValue(K key) {
			return getChild(key).getValue(key);
		}

		@Override
		void deleteValue(K key) {
			Node child = getChild(key);
			child.deleteValue(key);
			if (child.isUnderflow()) {
				Node childLeftSibling = getChildLeftSibling(key);
				Node childRightSibling = getChildRightSibling(key);
				Node left = childLeftSibling != null ? childLeftSibling : child;
				Node right = childLeftSibling != null ? child : childRightSibling;
				System.out.println("Right: " + right);
				System.out.println("Left: " + left);
				left.merge(right);
				deleteChild(right.getFirstLeafKey());
				if (left.isOverflow()) {
					Node sibling = left.split();
					insertChild(sibling.getFirstLeafKey(), sibling);
				}
				if (Root.keyNumber() == 0)
					Root = left;
			}
		}

		@Override
		void insertValue(K key, V value) {
			Node child = getChild(key);
			child.insertValue(key, value);
			if (child.isOverflow()) {
				Node sibling = child.split();
				insertChild(sibling.getFirstLeafKey(), sibling);
			}
			if (Root.isOverflow()) {
				Node sibling = split();
				InternalNode newRoot = new InternalNode();
				newRoot.keys.add(sibling.getFirstLeafKey());
				newRoot.children.add(this);
				newRoot.children.add(sibling);
				Root = newRoot;
			}
		}

		@Override
		K getFirstLeafKey() {
			return children.get(0).getFirstLeafKey();
		}

		@Override
		void merge(Node sibling) {
			@SuppressWarnings("unchecked")
			InternalNode node = (InternalNode) sibling;
			keys.add(node.getFirstLeafKey());
			keys.addAll(node.keys);
			children.addAll(node.children);

		}

		@Override
		Node split() {
			int from = keyNumber() / 2 + 1, to = keyNumber();
			InternalNode sibling = new InternalNode();
			sibling.keys.addAll(keys.subList(from, to));
			sibling.children.addAll(children.subList(from, to + 1));

			keys.subList(from - 1, to).clear();
			children.subList(from, to + 1).clear();

			return sibling;
		}

		@Override
		boolean isOverflow() {
			return children.size() > Order;
		}

		@Override
		boolean isUnderflow() {
			return children.size() < (Order + 1) / 2;
		}

		Node getChild(K key) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			return children.get(childIndex);
		}

		void deleteChild(K key) {
			int loc = Collections.binarySearch(keys, key);
			System.out.println("Delete Index: " + loc);
			System.out.println("Key To Find: " + key);
			if (loc >= 0) {
				keys.remove(loc);
				children.remove(loc + 1);
			}
		}

		void insertChild(K key, Node child) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			if (loc >= 0) {
				children.set(childIndex, child);
			} else {
				keys.add(childIndex, key);
				children.add(childIndex + 1, child);
			}
		}

		Node getChildLeftSibling(K key) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			System.out.println("Child Index Left: " + childIndex);
			if (childIndex > 0) {
				System.out.println("Children LEFT: " + children.get(childIndex - 1));
				return children.get(childIndex - 1);
			}

			return null;
		}

		Node getChildRightSibling(K key) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			System.out.println("Child Index Right: " + childIndex);
			
			if (childIndex < keyNumber()) {
				System.out.println("Children RIGHT: " + children.get(childIndex + 1)); 
				return children.get(childIndex + 1);
			}

			return null;
		}
	}

	private class LeafNode extends Node {
		List<V> values;
		LeafNode next;

		LeafNode() {
			keys = new ArrayList<K>();
			values = new ArrayList<V>();
		}

		@Override
		V getValue(K key) {
			int loc = Collections.binarySearch(keys, key);
			return loc >= 0 ? values.get(loc) : null;
		}

		@Override
		void deleteValue(K key) {
			int loc = Collections.binarySearch(keys, key);
			if (loc >= 0) {
				keys.remove(loc);
				values.remove(loc);
			}
		}

		@Override
		void insertValue(K key, V value) {
			int loc = Collections.binarySearch(keys, key);
			int valueIndex = loc >= 0 ? loc : -loc - 1;
			if (loc >= 0) {
				values.set(valueIndex, value);
			} else {
				keys.add(valueIndex, key);
				values.add(valueIndex, value);
			}
			if (Root.isOverflow()) {
				Node sibling = split();
				InternalNode newRoot = new InternalNode();
				newRoot.keys.add(sibling.getFirstLeafKey());
				newRoot.children.add(this);
				newRoot.children.add(sibling);
				Root = newRoot;
			}
		}

		@Override
		K getFirstLeafKey() {
			return keys.get(0);
		}

		@Override
		void merge(Node sibling) {
			@SuppressWarnings("unchecked")
			LeafNode node = (LeafNode) sibling;
			keys.addAll(node.keys);
			values.addAll(node.values);
			next = node.next;
		}

		@Override
		Node split() {
			LeafNode sibling = new LeafNode();
			int from = (keyNumber() + 1) / 2, to = keyNumber();
			sibling.keys.addAll(keys.subList(from, to));
			sibling.values.addAll(values.subList(from, to));

			keys.subList(from, to).clear();
			values.subList(from, to).clear();

			sibling.next = next;
			next = sibling;
			return sibling;
		}

		@Override
		boolean isOverflow() {
			return values.size() > Order - 1;
		}

		@Override
		boolean isUnderflow() {
			return values.size() < Order / 2;
		}
	}

	public static void main(String[] args) {
		BPTree bt = new BPTree(4);
		System.out.println("Started.......");
		bt.insert(0, 100);
		System.out.println("First Test Done");
		// System.out.println(bt);
		bt.insert(1, 101);
		bt.insert(2, 102);
		System.out.println(bt);
		bt.insert(3, 103);
		System.out.println("BPTree\n" + bt);
		bt.insert(4, 104);
		System.out.println("BPTree\n" + bt);
		bt.insert(5, 105);
		// System.out.println(BPTree.Root.keys);
		System.out.println("BPTree\n" + bt);
		bt.insert(6, 106);
		System.out.println("BPTree\n" + bt);
		bt.insert(7, 107);
		System.out.println("BPTree\n" + bt);
		bt.insert(20, 108);
		System.out.println("BPTree\n" + bt);
		bt.insert(8, 109);
		System.out.println("BPTree\n" + bt);
		bt.insert(9, 110);
		System.out.println("BPTree CURRENT\n" + bt);
		bt.delete(1);
		System.out.println("BPTree\n" + bt);
		bt.delete(4);
		System.out.println("BPTree\n" + bt);
		bt.delete(3);
		System.out.println("BPTree\n" + bt);
		System.out.println(bt.search(0));
		System.out.println(bt.search(6));
		System.out.println(bt.search(9));
	}}