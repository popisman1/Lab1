package se.hig.aod.lab1;

/**
 * Implementation av ett binärt sökträd (BST).
 */

public class BinarySearchTree<T extends Comparable<? super T>> implements SearchableDataStructure<T> {

	// Returnerar antalet element i trädet.
	@Override
	public int size() {
		return size;
	}

	// Lägger till ett nytt element i trädet
	@Override
	public void addElement(T newElement) {
		if (newElement == null) {
			throw new IllegalArgumentException("Element cannot be null");
		}
		root = addRecursive(root, newElement);
		size++;
	}

	private TreeNode<T> addRecursive(TreeNode<T> node, T element) {
		if (node == null) {
			return new TreeNode<>(element);
		}

		int cmp = element.compareTo(node.data);

		if (cmp < 0) {
			node.left = addRecursive(node.left, element);
		} else {
			node.right = addRecursive(node.right, element);
		}

		return node;
	}

	// Returnerar element på given postition
	@Override
	public T getElementAt(int position) {
		if (position < 0 || position >= size) {
			throw new IndexOutOfBoundsException("Position: " + position);
		}
		Counter c = new Counter();
		return getElementAtRecursive(root, position, c);
	}

	private static class Counter {
		int value = 0;
	}

	private T getElementAtRecursive(TreeNode<T> node, int position, Counter c) {
		if (node == null) {
			return null;
		}

		T leftResult = getElementAtRecursive(node.left, position, c);
		if (leftResult != null) {
			return leftResult;
		}

		if (c.value == position) {
			return node.data;
		}
		c.value++;

		return getElementAtRecursive(node.right, position, c);
	}

	// Skriver ut trädets innehåll i sorterad ordning
	@Override
	public void printData() {
		System.out.println(toString());

	}

	// Kontrollerar om elementet finns i trädet
	@Override
	public boolean containsElement(T element) {
		return searchElement(element) != null;
	}

	// Söker efter element i trädet
	@Override
	public T searchElement(T elementToFind) {
		if (elementToFind == null) {
			return null;
		}
		return searchRecursive(root, elementToFind);
	}

	private T searchRecursive(TreeNode<T> node, T target) {
		if (node == null) {
			return null;
		}

		int cmp = target.compareTo(node.data);

		if (cmp == 0) {
			return node.data;
		} else if (cmp < 0) {
			return searchRecursive(node.left, target);
		} else {
			return searchRecursive(node.right, target);
		}
	}

	// Returnerar en sträng av trädet
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		inorder(root, sb);
		return sb.toString();
	}

	private void inorder(TreeNode<T> node, StringBuilder sb) {
		if (node == null) {
			return;
		}

		inorder(node.left, sb);
		sb.append(node.data);
		inorder(node.right, sb);
	}

	private static class TreeNode<E> {
		E data;
		TreeNode<E> left;
		TreeNode<E> right;

		TreeNode(E data) {
			this.data = data;
		}
	}

	private TreeNode<T> root;

	private int size;

	// Skapar ett tomt binärt sökträd
	public BinarySearchTree() {
		root = null;
		size = 0;
	}
}
