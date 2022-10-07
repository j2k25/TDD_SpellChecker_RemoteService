package spellcheck;

import java.util.StringJoiner;

class Analyzer {
	private SpellChecker spellChecker;

	public void setSpellChecker(SpellChecker theSpellChecker) {
		spellChecker = theSpellChecker;
	}

	private String padForAString(String word) {
		try {
			return spellChecker.isSpellingCorrect(word) ? "" : "__";
		} catch (Exception ex) {
			return "??";
		}
	}

	public AnalysisResult analyze(String sentenceToBeSpellChecked) {
		if (sentenceToBeSpellChecked.equals("")) {
			return new AnalysisResult("", 0, 0, 0);
		}

		String[] words = sentenceToBeSpellChecked.split("\\s++", 0);
		StringJoiner transformedWords = new StringJoiner(" ");
		int numberOfWords = 0;
		int numberOfTypos = 0;

		for (String word : words) {

			numberOfWords++;

			String padding = padForAString(word);
			transformedWords.add(padding + word + padding);
			numberOfTypos += padding.equals("__") ? 1 : 0;
		}

		int errorPercent = (int) (numberOfTypos * 100.0 / numberOfWords);
		return new AnalysisResult(transformedWords.toString(), numberOfWords, numberOfTypos, errorPercent);
	}

}
