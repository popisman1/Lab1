package se.hig.aod.lab1;

/**
 * Interface för en sökbar datastruktur.
 */

public interface SearchableDataStructure<T> {

	public int size();

	public void addElement(T newElement);

	public T getElementAt(int position);

	public void printData();

	public boolean containsElement(T element);

	public T searchElement(T elementToFind);

	public String toString();
}
