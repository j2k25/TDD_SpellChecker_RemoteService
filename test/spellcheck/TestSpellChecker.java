package spellcheck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestSpellChecker {
  AgileCSSpellChecker spellChecker;

  @BeforeEach
  public void setUp() {
    spellChecker = new AgileCSSpellChecker();
  }

  @Test
  public void serviceReturnsResponseForCall() throws IOException {
    assertEquals("true", spellChecker.callService("testing"));
  }

  @Test
  public void serviceParseTrueResponse() {
    assertTrue(spellChecker.parse("true"));
  }

  @Test
  public void serviceParseFalseResponse() {
    assertFalse(spellChecker.parse("false"));
  }

  @Test
  public void isSpellingCorrectCallsServiceAndParse() throws IOException {
    AgileCSSpellChecker spellCheckerThatWorksProperly = Mockito.spy(new AgileCSSpellChecker());

    assertTrue(spellCheckerThatWorksProperly.isSpellingCorrect("testing"));
    verify(spellCheckerThatWorksProperly).callService("testing");
    verify(spellCheckerThatWorksProperly).parse("true");
  }

  @Test
  public void isSpellingCorrectPassesServiceFailure() {
    AgileCSSpellChecker spellCheckerWithServiceFailure = Mockito.spy(new AgileCSSpellChecker() {
      @Override
      public String callService(String word) throws IOException {
        throw new IOException();
      }
    });

    assertThrows(RuntimeException.class, () -> spellCheckerWithServiceFailure.isSpellingCorrect("testing"));
  }
}