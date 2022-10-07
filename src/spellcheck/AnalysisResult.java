package spellcheck;

class AnalysisResult {
  public final String wordResult;
  public final int numberOfWords;
  public final int numberOfTypos;
  public final int errorPercent;

  AnalysisResult(String theWordResult, int theNumberOfWords, int theNumberOfTypos, int theErrorPercent) {
    wordResult = theWordResult;
    numberOfWords = theNumberOfWords;
    numberOfTypos = theNumberOfTypos;
    errorPercent = theErrorPercent;
  }

}
