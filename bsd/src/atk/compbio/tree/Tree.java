/*
 Copyright (c) 2002 Compaq Computer Corporation

 SOFTWARE RELEASE

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 - Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.

 - Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.

 - Neither the names of Compaq Research, Compaq Computer Corporation
 nor the names of its contributors may be used to endorse or promote
 products derived from this Software without specific prior written
 permission.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL COMPAQ COMPUTER CORPORATION BE LIABLE FOR ANY CLAIM,
 DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package atk.compbio.tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Vector;

/**
 * A public class representing a (phylognenetic) tree. Nodes of the tree are of
 * type TreeNode. Nodes are traversed in pre- and post-orders.
 * 
 * @author Yunhong Zhou, Li Zhang
 * @author Thomas Abeel
 * 
 * @version 2.1
 * @see TreeNode
 */

public class Tree {

	/** The list of nodes of the tree indexed by their keys, indexed by key */
	private ArrayList<TreeNode> nodes;

	private HashMap<String, TreeNode> nodesByName;

	// private File file;

	// reference for array of leaves in SC.cullingObject
	/**
	 * Split axis reference for leaf recovery (leaves are attached to split line
	 * culling objects)
	 */
	// private StaticSplitAxis leafSplitAxis;

	// public File file(){
	// return file;
	// }
	/**
	 * Default tree constructor. Nodes are created by parser and added in later.
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public Tree(String path) throws FileNotFoundException {
		// root = new TreeNode();
		// this.file=new File(path);

		TreeParser tp = new TreeParser(new BufferedReader(new FileReader(path)));
		root = tp.tokenize(1000, "TestTree", null);
		postProcess();
		// nodesByName = new HashMap();
	}

	/**
	 * Removes a leaf node, does nothing if the node is not leaf.
	 * 
	 * @param tn
	 */
	public void removeLeaf(String name) {
		TreeNode tn = getNodeByName(name);
		if (tn.isLeaf()) {
			TreeNode p = tn.parent;
			p.children.remove(tn);
			System.out.println(p.children);
			if (p.children.size() == 1) {
				System.out.println("stepping up");
				if (p.children.get(0).isLeaf()) {
					p.name = p.children.get(0).name;
					p.weight = p.children.get(0).weight;
					p.children.clear();
				} else {
					float w = p.children.get(0).weight;
					p.children = p.children.get(0).children;
					System.out.println(w);
					p.weight = w;
				}
			}
			postProcess();

		}

	}

	/**
	 * Export as NWK format
	 */
	public String exportNWK() {
		return root.exportNWK();
	}

	// /**
	// * Clean up method, called when the tree is deleted.
	// *
	// * @see TreeNode#close()
	// *
	// */
	// public void close() {
	// TreeNode pren = root.leftmostLeaf;
	// for (TreeNode n = pren.preorderNext; n != null; n = n.preorderNext) {
	// n.close();
	// }
	// }
	//
	// /**
	// * Calls to #close() when tree is deleted.
	// */
	// protected void finalize() throws Throwable {
	//
	// try {
	// close();
	// } finally {
	// super.finalize();
	// }
	// }

	// /**
	// * Returns the number of interior nodes in this tree. For debugging.
	// * @return Total number of nodes minus the number of leaves.
	// */
	// private int getInteriorCount() { return nodes.size() - numLeaves;}
	// /**
	// * Returns the node count, for internal and leaf nodes.
	// *
	// * @return Size of the {@link #nodes} array, which contains all nodes.
	// */
	// private int getTotalNodeCount() {
	// return nodes.size();
	// }

//	/**
//	 * Returns the node indexed by the given key.
//	 * 
//	 * @param key
//	 *            Key of the node to retrieve.
//	 * @return Treenode referenced by the given key.
//	 */
//	public TreeNode getNodeByKey(int key) {
//		if (key >= nodes.size())
//			return null;
//		return (TreeNode) nodes.get(key);
//	}

	/**
	 * Returns the node given by the string.
	 * 
	 * @param s
	 *            Name/label of node to retrieve.
	 * @return Treenode referenced by the given name.
	 */
	private TreeNode getNodeByName(String s) {
		return nodesByName.get(s);
	}

	/**
	 * Height of tree, which is also the longest path from the root to some leaf
	 * node.
	 */
	private int height = 0;

	/**
	 * Accessor for height of tree. This is also the longest path from the root
	 * to some leaf node.
	 * 
	 * @return value of {@link #height}.
	 */
	public int getHeight() {
		return height;
	}

	// /**
	// * Mutator for key
	// *
	// * @param i
	// * New value for {@link #key}.
	// */
	// public void setKey(int i) {
	// key = i;
	// }

	// /**
	// * Accessor for key.
	// *
	// * @return Value of {@link #key}.
	// */
	// public int getKey() {
	// return key;
	// }

	// /**
	// * File name accessor.
	// *
	// * @return value of {@link #fileName}.
	// */
	// public String getName() {
	// return fileName;
	// }

	/**
	 * Left most leaf accessor. This is the "min leaf"
	 * 
	 * @return root's left most leaf, which is the smallest indexed leaf node in
	 *         the tree.
	 */
	public TreeNode getLeftmostLeaf() {
		return root.leftmostLeaf;
	}

	// /**
	// * Root accessor.
	// *
	// * @return Value of {@link #root}
	// */
	// public TreeNode getRoot() {
	// return root;
	// }
	//
	// public void setRootNode(TreeNode newRoot) {
	// this.root = newRoot;
	// }

	// /**
	// * File name for this tree.
	// */
	// private String fileName = null; // the filename
//	/**
//	 * Index of tree in nexus file, if this tree is from a nexus file.
//	 */
//	private int nexusIndex = 0; // the number (>0 for nexus, appended to nexus
//								// filename)
	/**
	 * Root node of this tree
	 */
	public TreeNode root = null;

	// /**
	// * Sets the file name. Copies the value for some reason.
	// *
	// * @param tn
	// * New value for file name.
	// */
	// public void setFileName(String tn) {
	// fileName = new String(tn);
	// }

	// /**
	// * Returns the number of leaves in this tree.
	// *
	// * @return value of {@link #numLeaves}.
	// */
	// public int getLeafCount() {
	// return numLeaves;
	// }

	/**
	 * Post processing includes computing size of each node, linking nodes in
	 * different order, etc. Sets left and right-most leaves of the tree.
	 * Computes and stores pre- and post-orders for leaves. Can't do minmax
	 * until after linkNodesInPreorder is called to set index values!
	 * 
	 * @see TreeNode
	 */
	private void postProcess() {
		nodesByName = new HashMap<String, TreeNode>();
		nodes = new ArrayList<TreeNode>();
		preorderPostProcess();
		linkLeaves();
		// System.out.println("progress bar updated: min:" + jpb.getMinimum() +
		// " max:" + jpb.getMaximum() + " value:" + jpb.getValue());
	}

	/**
	 * 
	 * Traverses the tree in pre-order, stores the ordering in the preorderNext
	 * field of TreeNodes Sets node count for the tree.
	 * 
	 * @see TreeNode
	 */
	private void preorderPostProcess() {

		// munge names here, names become fully qualified, labels are what names
		// were
		// final char separator = '/'; // separator between name fields
		// arbitrary seen by users in search, no parsing on this is required
		// later
		int index = 0;
		height = 1;
		for (TreeNode n = root; n != null; n = n.preorderNext) {

			n.key = index++;
			nodes.add(n);
			if (n.name != null && n.name.length() > 0) {
				// // don't put an empty string in the
				// // hash table
				nodesByName.put(n.name, n);
			}
			n.height = (null != n.parent) ? n.parent.height + 1 : 1;
			height = (n.height > height) ? n.height : height;
		}

	}

	// /**
	// * Traverse the tree and initialize the {@link #nodesByName} and
	// * {@link #nodes} data structures. Used when modifying the names of nodes
	// as
	// * well as initialization.
	// *
	// */
	// public void setUpNameLists() {
	// nodes = new ArrayList<TreeNode>();
	// // nodesByName = new HashMap();
	// // final char separator = '/'; // separator between name fields
	// for (TreeNode n = root; n != null; n = n.preorderNext) {
	// n.label = n.name;
	// nodes.add(n);
	// // if (n.name != null && n.name.length() > 0) {
	// // // don't put an empty string in the
	// // // hash table
	// // nodesByName.put(n.name, n);
	// // }
	// n.height = (null != n.parent) ? n.parent.height + 1 : 1;
	// height = (n.height > height) ? n.height : height;
	// }
	// }

	// /**
	// * Wrapper for initiating {@link #linkSubtreeNodesInPreorder(TreeNode)}
	// with the root node.
	// */
	// private void linkNodesInPreorder() {
	//
	// linkSubtreeNodesInPreorder(root);
	//
	// }

	// /**
	// * Traverses the subtree rooted at TreeNode n in pre-order, stores the
	// * ordering in the preorderNext field of TreeNodes.
	// * @param n the root of the subtree
	// *
	// * @see TreeNode
	// */
	// private void linkSubtreeNodesInPreorder(TreeNode n) {
	//
	// if(n.isLeaf()) return;
	// for(int i=0; i<n.numberChildren(); i++) {
	// linkSubtreeNodesInPreorder(n.getChild(i));
	// }
	//
	// n.preorderNext = n.firstChild();
	// for(int i=0; i<n.numberChildren()-1; i++) {
	// n.getChild(i).rightmostLeaf.preorderNext = n.getChild(i+1);
	// }
	// n.rightmostLeaf.preorderNext = null;
	// }

	/**
	 * 
	 * Links leaves of the tree in pre-order, check to see whether leaves have
	 * distinct names. If leaves have the same name, add a suffix index
	 * separated by " "
	 * 
	 * @see #linkNodesInPreorder()
	 * @see TreeNode
	 * @see NameComparator
	 * @param jpb
	 *            Progress bar.
	 */
	private void linkLeaves() {
		// int counter = 0;
		// int percentage = 0;
		TreeNode pren = root.leftmostLeaf;
		Vector<TreeNode> leaves = new Vector<TreeNode>();
		leaves.add(pren);
		// pren.lindex = 0;
		for (TreeNode n = pren.preorderNext; n != null; n = n.preorderNext) {
			// counter++;
			if (n.isLeaf()) {
				leaves.add(n);
			}
		}
		// numLeaves = leaves.size();

		NameComparator myNameComparator = new NameComparator();
		TreeNode[] sortedLeafArray = (TreeNode[]) leaves
				.toArray(new TreeNode[leaves.size()]);
		Arrays.sort(sortedLeafArray, myNameComparator);
		int index = 0;
		TreeNode curr = sortedLeafArray[0];
		TreeNode next;
		for (int i = 0; i < leaves.size() - 1; i++) {
			next = sortedLeafArray[i + 1]; // only 1 index lookup per iteration
			boolean compare = myNameComparator.compare(curr, next) == 0;
			if (compare || index > 0) {
				String name = curr.getName();
				// nodesByName.remove(curr); // before all nodes with
				// same name were being ignored in search and comparing two
				// identically named
				// leaves was broken, much fewer differences in trees with many
				// leaves that
				// have the same name (imagine: all index.html occurences being
				// marked as
				// different since numbering convention doesn't string match the
				// original node name)
				curr.name = name + " " + index; // sb.toString());
				// nodesByName.put(name + " " + index, curr);//
				// sortedLeafArray[i].getName(),
				// sortedLeafArray[i]);
				// // add the node
				// back with number
				// convention
				if (!compare)
					index = 0;
				else
					index++;
			}
			curr = next;
		}
	}

	// /**
	// * Get the leaf associated with the given leaf index.
	// *
	// * @param index
	// * A leaf index of interest.
	// * @return The leaf node at the index, or null on error.
	// * */
	// public TreeNode getLeaf(int index) {
	// // System.out.println("getting leaf: " + index );
	// return null;
	// }

	// /** Stub function */
	// public float getMinObjectValue() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// /** Stub function */
	// public float getMaxObjectValue() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
//
//	/**
//	 * @return Returns the index number of this tree in the nexus file it was
//	 *         found in. Indexing starts at 0, and 0 for non-nexus.
//	 */
//	public int getNexusIndex() {
//		return nexusIndex;
//	}

	/**
	 * Get the leaves under this node. Used for tree to tree comparison,
	 * removing leaf nodes from difference calculations when they only appear in
	 * one side of the tree. This operation is constant time per leaf, since it
	 * relies on pre-ordered node links and pointers to extreme leaves. Time
	 * complexity of this function is linear in the number of leaves in the
	 * subtree under the node.
	 * 
	 * @param node
	 *            Node to get leaves under. The root node will return all leaves
	 *            in the tree, leaves return a list of just themselves.
	 * @return List of leaves under this node.
	 */
	public LinkedList<TreeNode> getLeaves(TreeNode node) {
		LinkedList<TreeNode> leaves = new LinkedList<TreeNode>();
		TreeNode currNode = node.leftmostLeaf;
		while (currNode != node.rightmostLeaf) {
			if (!currNode.isLeaf()) // internal node?
				currNode = currNode.leftmostLeaf; // descend to minimal leaf
			leaves.add(currNode);
			currNode = currNode.preorderNext;
		}
		leaves.add(node.rightmostLeaf);
		return leaves;
	}
}

/** Comparator class for Strings */
class NameComparator implements Comparator<TreeNode> {
	/** collator object used for string comparison. */
	Collator myCollator = Collator.getInstance(Locale.US);

	/** String comparator, uses {@link Collator} comparator. */
	public int compare(TreeNode o1, TreeNode o2) {
		String s1 = ((TreeNode) o1).getName();
		String s2 = ((TreeNode) o2).getName();
		return myCollator.compare(s1, s2);
	}

}
