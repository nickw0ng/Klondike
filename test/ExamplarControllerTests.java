package cs3500.klondike;


import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.view.KlondikeTextualView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * test for the controller.
 */

public class ExamplarControllerTests {

  BasicKlondike klon;
  KlondikeController controller;
  List<Card> deck;
  KlondikeTextualView kview;
  StringBuilder out;
  private Readable readable;
  private Appendable appendable;
  private boolean f = false;


  @Before

  public void initializeData() {
    klon = new BasicKlondike();
    deck = klon.getDeck();
    kview = new KlondikeTextualView(klon);
    out = new StringBuilder();
  }


  private List<Card> rigDeck(List<String> perfectDeck) {
    List<Card> newDeck = new ArrayList<Card>();
    if (perfectDeck == null) {
      newDeck.add(null);
    } else {
      for (String str : perfectDeck) {
        for (Card card : deck) {
          if (str.equals(card.toString())) {
            newDeck.add(card);
          }
        }
      }
    }
    return newDeck;
  }

  List<String> deckNull = null;
  List<String> deckEmpty =
          new ArrayList<String>(Arrays.asList(""));

  List<String> deck1 = new ArrayList<String>(
          Arrays.asList("2♡", "2♣", "A♣", "3♣", "A♡", "3♡"));
  List<String> deck2 =
          new ArrayList<String>(Arrays.asList("2♣", "A♡", "A♣", "A♢", "A♠", "2♡", "2♠", "2♢"));


  @Test
  public void testStartWithQuit() {
    StringReader in = new StringReader("q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck1);
    controller.playGame(klon, deckTest, false, 2, 1);
    Assert.assertTrue(out.toString().contains("State of game when quit:"));
  }


  @Test
  public void testSpecialCharacterInInput() {
    StringReader in = new StringReader("? 1 1 q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck2);
    controller.playGame(klon, deckTest, f, 2, 1);
    Assert.assertTrue(out.toString().contains("Invalid move. Play again."));
  }

  @Test
  public void testMoveDrawCheck() {
    StringReader in = new StringReader("md 1 q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck2);
    controller.playGame(klon, deckTest, f, 2, 1);
    Assert.assertEquals("A♢", klon.getCardAt(0, 1).toString());
  }

  @Test
  public void testInvalidLargeMoveDrawInput() {
    StringReader in = new StringReader("md 8 q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck1);
    controller.playGame(klon, deckTest, f, 2, 1);
    Assert.assertTrue(out.toString().contains("Invalid move. Play again."));
  }

  @Test
  public void testInputQuitGame() {
    StringReader in = new StringReader("q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck1);
    controller.playGame(klon, deckTest, f, 2, 1);
    Assert.assertTrue(out.toString().contains("Game quit!\nState of game when quit:"));
  }

  @Test
  public void testCorrectMpp() {
    StringReader in = new StringReader("mpp 2 1 1\n q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck1);
    controller.playGame(klon, deckTest, f, 2, 1);
    Assert.assertEquals("A♣", klon.getCardAt(0, 1).toString());
  }

  @Test
  public void testCharMovePileInput() {
    StringReader in = new StringReader("mpp 2 abcdef 1 1\n q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> test = this.rigDeck(deck1);
    controller.playGame(klon , test, f, 2, 1);
    Assert.assertEquals("A♣", klon.getCardAt(0, 1).toString());
  }

  @Test
  public void testNegativeInvalidMovePile() {
    StringReader in = new StringReader("mpp 1 -3 2 q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    List<Card> deckTest = this.rigDeck(deck2);
    controller.playGame(klon, deckTest, f, 3, 2);
    Assert.assertTrue(out.toString().contains("Invalid move. Play again."));
  }

  @Test
  public void testNumLines() {
    StringReader in = new StringReader("q");
    KlondikeController controller = new KlondikeTextualController(in, out);
    controller.playGame(klon, deck, false, 4, 1);
    String[] size = out.toString().split("\n");
    Assert.assertEquals(16, size.length);
  }
}
