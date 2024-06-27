package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.model.hw02.Card;


/**
 * Tests for the Models.
 */
public class ExamplarExtendedModelTests {

  WhiteheadKlondike whKlondike;
  LimitedDrawKlondike ldKlondike;
  List<Card> deck;


  @Before
  public void initializeData() {
    whKlondike = new WhiteheadKlondike();
    ldKlondike = new LimitedDrawKlondike(2);
    deck = whKlondike.getDeck();
  }

  private List<Card> makeDeck(List<String> perfectDeck) {
    List<Card> newDeck = new ArrayList<Card>();
    for (String str : perfectDeck) {
      for (Card card : deck) {
        if (str.equals(card.toString())) {
          newDeck.add(card);
        }
      }
    }
    return newDeck;
  }

  List<String> deck1 = new ArrayList<String>(Arrays.asList("A♣", "2♣", "A♠", "2♠"));
  // (♣, ♠, ♡, ♢).

  @Test(expected = IllegalStateException.class)
  public void testSuitMoveException() {
    List<String> myDeck = new ArrayList<String>(Arrays.asList("A♡", "A♣",
            "2♣", "2♣", "A♣", "2♡", "A♢", "2♢"));
    List<Card> deck = this.makeDeck(myDeck);
    whKlondike.startGame(deck, false, 3, 1);
    whKlondike.movePile(0, 1, 1);
  }

  @Test
  public void testCardsInfiniteShow() {
    List<Card> deck = this.makeDeck(deck1);
    ldKlondike.startGame(deck, false, 1, 1);
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    ldKlondike.discardDraw();
    Assert.assertTrue(ldKlondike.getDrawCards().isEmpty());
  }


  @Test(expected = IllegalStateException.class)
  public void testMoveToCascadeWrongSuit() {
    List<String> myDeck = new ArrayList<String>(Arrays.asList("A♣", "A♡",
            "2♣", "2♠", "A♠", "3♣", "3♡", "3♠", "2♡"));
    List<Card> deck = this.makeDeck(myDeck);
    whKlondike.startGame(deck, false, 3, 1);
    whKlondike.movePile(0, 1, 1);
    whKlondike.movePile(1, 2, 2);
  }

  @Test
  public void testMoveToEmptyCascade() {
    List<String> myDeck = new ArrayList<String>(Arrays.asList("A♣", "A♡", "2♣", "2♣",
            "A♣", "2♡", "A♢", "2♢"));
    List<Card> deck = this.makeDeck(myDeck);
    whKlondike.startGame(deck, false, 3, 1);
    whKlondike.movePile(0, 1, 1);
    whKlondike.movePile(2, 1, 0);
    whKlondike.getCardAt(0, 0);
    Assert.assertEquals(1,1);
  }
  
  @Test(expected = IllegalStateException.class)
  public void testExceptionDD() {
    List<Card> deck = this.makeDeck(deck1);
    ldKlondike.startGame(deck, false, 1, 1);
    for (int i = 0; i < 10; i++) {
      ldKlondike.discardDraw();
    }
    Assert.assertTrue(ldKlondike.getDrawCards().isEmpty());
  }

}