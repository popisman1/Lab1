package se.hig.aod.lab1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Utför empiriska studier av sökning i ett binärt sökträd (BST).
 */
public class BSTStudy {

	// Studie 1 N-värden
	private static final int[] N_VALUES_STUDY1 = { 10_000, 20_000, 40_000, 80_000, 160_000, 320_000, 640_000, 1_280_000,
			2_560_000 };

	// Studie 2 N-värden
	private static final int[] N_VALUES_STUDY2 = { 10_000, 20_000 };

	private static final int SEARCH_COUNT = 2_500_000;
	private static final int RUNS_PER_N = 10;

	/**
	 * Startar Studie 1 och Studie 2.
	 */
	public static void main(String[] args) throws Exception {

		String path = "data/unique_integers.txt";

		List<Integer> elementsToSearchFor = loadListFromFile(path, SEARCH_COUNT);

		System.out.println("STUDIE 1 (osorterad data)");
		runStudy(path, elementsToSearchFor, N_VALUES_STUDY1, false);

		System.out.println();
		System.out.println("STUDIE 2 (sorterad data)");
		runStudy(path, elementsToSearchFor, N_VALUES_STUDY2, true);
	}

	/**
	 * Kör en empirisk studie för givna N-värden.
	 */
	private static void runStudy(String path, List<Integer> elementsToSearchFor, int[] nValues, boolean sortedData)
			throws IOException {

		long[] meanTimes = new long[nValues.length];

		for (int i = 0; i < nValues.length; i++) {
			int n = nValues[i];

			long sum = 0;
			for (int run = 0; run < RUNS_PER_N; run++) {

				// Skapa nytt tomt BST varje körning
				SearchableDataStructure<Integer> bst = new BinarySearchTree<>();

				// Läs in N element
				List<Integer> dataList = loadListFromFile(path, n);

				// Studie 1: shuffle, Studie 2: sort
				if (sortedData) {
					Collections.sort(dataList);
				} else {
					Collections.shuffle(dataList);
				}

				// Fyll BST med data
				for (Integer x : dataList) {
					bst.addElement(x);
				}

				// Mät tid för att söka i BST efter 2 500 000 element
				long t1 = System.currentTimeMillis();
				for (Integer target : elementsToSearchFor) {
					bst.containsElement(target);
				}
				long exeTime = System.currentTimeMillis() - t1;

				sum += exeTime;
				System.out.printf("N=%d run=%d time(ms)=%d%n", n, (run + 1), exeTime);
			}

			long mean = sum / RUNS_PER_N;
			meanTimes[i] = mean;
			System.out.printf(">>> N=%d MEAN(ms)=%d%n%n", n, mean);
		}

		// Skriv tabell med kvoter
		System.out.println("N\tMean(ms)\tQuota");
		for (int i = 0; i < nValues.length; i++) {
			String quota = (i == 0) ? "-" : String.format("%.3f", (meanTimes[i] * 1.0) / meanTimes[i - 1]);
			System.out.printf("%d\t%d\t\t%s%n", nValues[i], meanTimes[i], quota);
		}
	}

	/**
	 * Läser 'size' antal heltal från filen i 'path' och returnerar dem som en
	 * lista.
	 */
	private static List<Integer> loadListFromFile(String path, int size) throws FileNotFoundException, IOException {

		List<Integer> list = new ArrayList<>(size);
		int cnt = 0;

		try (Scanner in = new Scanner(new FileReader(path))) {
			while (cnt < size && in.hasNextLine()) {
				String line = in.nextLine();
				try {
					list.add(Integer.parseInt(line));
					cnt++;
				} catch (NumberFormatException nfe) {
					System.err.printf("Not an integer while reading from data file \"%s\": %s (ignoring)%n", path,
							nfe.getLocalizedMessage());
				}
			}
		}

		if (cnt != size) {
			System.err.printf("Didn't read %d integers, only %d.%n", size, cnt);
		}
		return list;
	}
}