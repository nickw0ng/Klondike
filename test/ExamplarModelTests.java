package cs3500.klondike;

import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the example tests for the basicKlondike.
 */

public class ExamplarModelTests {
  /**
   * (♣, ♠, ♡, ♢).
   * Things that I am testing:
   * The deck is invalid
   * The deck to be properly shuffles and dealing randomly
   * Can not keep deaing when the max number of cards have been delt and thers no more left
   *
   */
  KlondikeModel klondike;
  List<Card> deck;


  @Before
  public void setUpData() {
    klondike = new BasicKlondike();
    deck = klondike.getDeck();
  }

  private List<Card> rigDeck(List<String> perfectDeck) {
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

  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveToDest() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♣", "2♣", "A♠", "2♢", "A♡", "2♡", "A♢", "2♠"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.movePile(1, 1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidEmptyDraw() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♡", "2♣", "2♠", "A♣", "A♠", "2♡", "A♢", "2♢"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.movePile(0, 1, 1);
    klondike.moveDraw(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidTurnedCard() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("3♣", "2♡",
                    "A♣", "A♠", "2♣", "2♠", "A♡", "3♡", "3♠"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.movePile(1, 2, 0);
  }


  @Test(expected = IllegalStateException.class)
  public void testInvalidAceFoundation() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♡", "A♣", "2♠", "2♣", "A♠", "2♡", "A♢", "2♢"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.moveToFoundation(1, 0);
  }

  //test the wrong suit to the foundation pile
  @Test(expected = IllegalStateException.class)
  public void test1() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♣", "3♣", "2♢", "A♢",
                    "2♠", "3♠", "A♠", "3♢", "2♣"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.moveToFoundation(0, 1);
    klondike.moveDrawToFoundation(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidSameSuit() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("2♣", "A♠", "A♡", "A♣", "A♢", "2♢", "2♡", "2♠"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 2, 1);
    klondike.moveDraw(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveNoCardFoundation() {
    List<String> deck = new ArrayList<String>(Arrays.asList("A♣", "2♢", "A♢", "2♠", "A♠", "2♣"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.moveDrawToFoundation(0);
  }

  //moving a 3 to an empty cascade
  @Test(expected = IllegalStateException.class)
  public void testProject() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♣", "2♢", "3♢",
                    "A♢", "2♠", "3♠", "A♠", "2♣", "3♣"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.moveToFoundation(0, 0);
    klondike.moveToFoundation(1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testKingToEmpty() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♡", "A♣", "2♠", "2♣", "A♠", "2♡", "A♢", "2♢"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
    klondike.movePile(0, 1, 1);
    klondike.moveDraw(0);
  }

  @Test
  public void testDrawRec() {
    List<String> deck = new ArrayList<String>(Arrays.asList("A♣", "2♢", "A♢", "2♣"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 2, 1);
    klondike.discardDraw();
    Assert.assertEquals(deckEx.get(3), klondike.getDrawCards().get(0));
  }



}
