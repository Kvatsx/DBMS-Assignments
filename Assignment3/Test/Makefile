all: Node.class InternalNode.class LeafNode.class BPTree.class Data.class
	java BPTree
Node.class: Node.java
	javac Node.java

InternalNode.class: InternalNode.java
	javac InternalNode.java

LeafNode.class: LeafNode.java
	javac LeafNode.java

BPlusTree.class: BPTree.java
	javac BPTree.java

Data.class: Data.java
	javac Data.java

clean:
	rm Node.class
	rm InternalNode.class
	rm LeafNode.class
	rm BPTree.class
	rm Data.class
	rm Reader.class
	rm local_record.csv
	rm indexfile