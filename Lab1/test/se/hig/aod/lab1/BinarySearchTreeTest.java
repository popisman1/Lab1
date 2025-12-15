package se.hig.aod.lab1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

	private BinarySearchTree<Integer> tree;

	@BeforeEach
	void setUp() {
		tree = new BinarySearchTree<>();
	}

	@Test
	void emptyTree() {
		assertEquals(0, tree.size());
		assertEquals("", tree.toString());
	}

	@Test
	void addOneElement() {
		tree.addElement(3);
		assertEquals(1, tree.size());
		assertEquals("3", tree.toString());
	}

	@Test
	void addMultipleElements() {
		tree.addElement(3);
		tree.addElement(6);
		tree.addElement(1);
		tree.addElement(9);
		tree.addElement(2);

		assertEquals(5, tree.size());
		assertEquals("12369", tree.toString());
	}

	@Test
	void containsElement() {
		tree.addElement(5);
		tree.addElement(7);

		assertTrue(tree.containsElement(5));
		assertTrue(tree.containsElement(7));
		assertFalse(tree.containsElement(1));
		assertNull(tree.searchElement(10));
	}

}
