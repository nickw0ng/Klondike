package model.hw04;

import model.hw02.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a stub implementation of the {@link model.hw02.KlondikeModel} interface.
 * You may assume that the actual implementation of WhiteheadKlondike will have a zero-argument
 * (i.e. default) constructor, and that all the methods below will be implemented.  You may not make
 * any other assumptions about the implementation of this class (e.g. what fields it might have,
 * or helper methods, etc.).
 */
public final class WhiteheadKlondike extends AbstractKlondike {
  public WhiteheadKlondike() {
    super();
  }

  /**
   * Makes card visable.
   * @param cardIndex card index.
   * @param pileIndex pile index.
   */
  private void makeVisibility(int cardIndex, int pileIndex) {
    cascade.get(cardIndex).get(pileIndex).changeVisibility(true);
  }

  /**
   * Validates a cascade pile move, if the top card of the cards being moved and the bottom card
   * of the destination pile have the same color and the destination pile's card is one higher
   * than the source pile's card, and if there are multiple cards being moved that they all are the
   * same suit. If any are not true it throws and exception.
   *
   * @param destinationPile The pile the cards are being moved onto.
   * @param compareCard     The top card of the cards being moved.
   */
  protected void validateCascadeMove(List<Card> destinationPile, List<Card> srcPile,
                                     Card compareCard) {
    if (!destinationPile.isEmpty()) {
      Card destTopCard = destinationPile.get(destinationPile.size() - 1);
      if (!(getCardColor(compareCard).equals(getCardColor(destTopCard))) ||
              (compareCard.getValue() != destTopCard.getValue() - 1)) {
        throw new IllegalStateException("Invalid color or number run");
      }
    }
    if (1 > 1) {
      List<Card> cardsToMove = new ArrayList<Card>(srcPile.subList(srcPile.size() - 1,
              srcPile.size()));
      for (Card card : cardsToMove) {
        if (!getCardSuit(card).equals(getCardSuit(compareCard))) {
          throw new IllegalStateException("Not all cards in run being moved have same suit");
        }
      }
    }
  }


  public boolean isCardVisable(int pileNum, int card) {
    return false;
  }
}
