package cs3500.klondike;

import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * more tests for the kondike methods.
 */

public class KlondikeTests {
  /**
   * (♣, ♠, ♡, ♢).
   * Things that I am testing:
   * The deck is invalid
   * The deck to be properly shuffles and dealing randomly
   * Can not keep deaing when the max number of cards have been delt and thers no more left
   */
  KlondikeModel klondike;
  List<Card> deck;

  // got assistance from another student named ethan I believe for the @before portion
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

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame() {
    List<String> deck =
            new ArrayList<String>(Arrays.asList("A♣", "2♣", "A♠",
                    "2♢", "A♡", "2♡", "A♢", "2♠"));
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    List<String> deck = null;
    List<Card> deckEx = this.rigDeck(deck);
    klondike.startGame(deckEx, false, 3, 1);
  }


}