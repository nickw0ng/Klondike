package model.hw04;

import java.util.ArrayList;

import model.hw02.Card;

/**
 * This is a stub implementation of the {@link cs3500.klondike.model.hw02.KlondikeModel} interface.
 * You may assume that the actual implementation of LimitedDrawKlondike will have a one-argument
 * constructor, and that all the methods below will be implemented.  You may not make
 * any other assumptions about the implementation of this class (e.g. what fields it might have,
 * or helper methods, etc.).
 */
public final class LimitedDrawKlondike extends AbstractKlondike {

  private final int redraw;

  /**
   * The limited klondike version of the game.
   * @param redraw amount of times to redraw.
   */
  public LimitedDrawKlondike(int redraw) {
    super();
    if (redraw < 0) {
      throw new IllegalArgumentException("Invalid redraws");
    }
    else {
      this.redraw = redraw;
    }
  }

  /**
   * creates the draw piles in the limited klondike.
   * @param deck the deck to create pile.
   */
  public void createDrawPile(ArrayList<Card> deck) {
    for (int numRedraws = 0; numRedraws <= redraw; numRedraws++) {
      drawCards.addAll(deck);
    }
  }

  @Override
  public void discardDraw() throws IllegalStateException {
    if (drawCards.isEmpty()) {
      throw new IllegalStateException("Nothing in draw pile");
    }
    drawCards.remove(0);
    if (drawCards.size() >= numDraw) {
      drawCards.get(drawCards.size() - 1).changeVisibility(false);
      drawCards.get(numDraw - 1).changeVisibility(true);
    }
  }

  public boolean isCardVisable(int pileNum, int card) {
    return false;
  }

}
