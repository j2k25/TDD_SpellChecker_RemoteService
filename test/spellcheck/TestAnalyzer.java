package spellcheck;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.nio.channels.NoConnectionPendingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestAnalyzer {
  Analyzer analyzer;
  SpellChecker spellChecker;

  @BeforeEach
  void setUp() {
    analyzer = new Analyzer();
    spellChecker = Mockito.mock(SpellChecker.class);
    analyzer.setSpellChecker(spellChecker);
  }

  @Test
  void canary() {
    assertTrue(true);
  }

  @Test
  void checkOneCorrectWord() {
    when(spellChecker.isSpellingCorrect("hello")).thenReturn(true);

    AnalysisResult actual = analyzer.analyze("hello");

    assertEquals("hello", actual.wordResult);
    assertEquals(1, actual.numberOfWords);
    assertEquals(0, actual.numberOfTypos);
    assertEquals(0, actual.errorPercent);
  }

  @Test
  void checkTwoCorrectWords() {
    when(spellChecker.isSpellingCorrect("hello")).thenReturn(true);
    when(spellChecker.isSpellingCorrect("there")).thenReturn(true);

    AnalysisResult actual = analyzer.analyze("hello there");

    assertEquals("hello there", actual.wordResult);
    assertEquals(2, actual.numberOfWords);
    assertEquals(0, actual.numberOfTypos);
    assertEquals(0, actual.errorPercent);
  }

  @Test
  void checkTwoCorrectWordsWithExtraSpace() {
    when(spellChecker.isSpellingCorrect("hello")).thenReturn(true);
    when(spellChecker.isSpellingCorrect("there")).thenReturn(true);

    AnalysisResult actual = analyzer.analyze("hello  there");

    assertEquals("hello there", actual.wordResult);
    assertEquals(2, actual.numberOfWords);
    assertEquals(0, actual.numberOfTypos);
    assertEquals(0, actual.errorPercent);
  }

  @Test
  void checkOneCorrectAndOneIncorrectWords() {
    when(spellChecker.isSpellingCorrect("hello")).thenReturn(true);
    when(spellChecker.isSpellingCorrect("thre")).thenReturn(false);

    AnalysisResult actual = analyzer.analyze("hello thre");

    assertEquals("hello __thre__", actual.wordResult);
    assertEquals(2, actual.numberOfWords);
    assertEquals(1, actual.numberOfTypos);
    assertEquals(50, actual.errorPercent);
  }

  @Test
  public void checkEmptyString() {
    when(spellChecker.isSpellingCorrect("")).thenReturn(true);

    AnalysisResult actual = analyzer.analyze("");

    assertEquals("", actual.wordResult);
    assertEquals(0, actual.numberOfWords);
    assertEquals(0, actual.numberOfTypos);
    assertEquals(0, actual.errorPercent);
  }

  @Test()
  void checkOneCorrectWordWithClientFailureError() {
    when(spellChecker.isSpellingCorrect("hello")).thenThrow(new RuntimeException("Network Error"));

    AnalysisResult actual = analyzer.analyze("hello");

    assertEquals("??hello??", actual.wordResult);
    assertEquals(1, actual.numberOfWords);
    assertEquals(0, actual.numberOfTypos);
    assertEquals(0, actual.errorPercent);
  }

  @Test
  public void checkOneCorrectWordWithServiceFailureError() {
    when(spellChecker.isSpellingCorrect("hello")).thenThrow(new NoConnectionPendingException());

    AnalysisResult actual = analyzer.analyze("hello");

    assertEquals("??hello??", actual.wordResult);
    assertEquals(1, actual.numberOfWords);
    assertEquals(0, actual.numberOfTypos);
    assertEquals(0, actual.errorPercent);
  }

  @Test
  public void checkSentenceWithFirstWordCorrectSecondEncounterServiceFailureAndLastWordIncorrect() {
    when(spellChecker.isSpellingCorrect("hello")).thenReturn(true);
    when(spellChecker.isSpellingCorrect("you")).thenThrow(new NoConnectionPendingException());
    when(spellChecker.isSpellingCorrect("thre")).thenReturn(false);

    AnalysisResult actual = analyzer.analyze("hello you thre");

    assertEquals("hello ??you?? __thre__", actual.wordResult);
    assertEquals(3, actual.numberOfWords);
    assertEquals(1, actual.numberOfTypos);
    assertEquals(33, actual.errorPercent);
  }

}
