package spellcheck;

import java.io.File;
import java.util.Scanner;
import java.util.StringJoiner;

public class SpellCheckDriver {

	static Scanner fileInput;

	public static void main(String[] args) {
		String fileName = args[0];
		System.out.println("Reading from file: " + fileName);
		String inputString = getTestFromFile(fileName);
		printAnalysisResult(inputString);

	}

	private static String getTestFromFile(String fileName) {
		StringJoiner stringJoiner = new StringJoiner(" ");

		try {
			fileInput = new Scanner(new File(fileName));
			while (fileInput.hasNextLine()) {
				stringJoiner.add(fileInput.nextLine().trim());
			}

			fileInput.close();
		} catch (Exception ignored) {
			System.out.println("Please enter a valid file name");
		}

		return stringJoiner.toString();
	}

	private static void printAnalysisResult(String inputString) {
		Analyzer analyzer = new Analyzer();
		analyzer.setSpellChecker(new AgileCSSpellChecker());
		AnalysisResult theResult = analyzer.analyze(inputString);

		System.out.println(theResult.wordResult);
		System.out.println("Total Words: " + theResult.numberOfWords);
		System.out.println("Typos: " + theResult.numberOfTypos);
		System.out.println("Error Rate: " + theResult.errorPercent + "%");
	}

}
