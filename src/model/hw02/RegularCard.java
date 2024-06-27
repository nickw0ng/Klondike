package cs3500.klondike.model.hw02;

/**
 * Represents a playing card.
 */

public class RegularCard implements Card {

  private final Suits suit;
  private final int value;
  private boolean isVisible;

  /**
   * represents a card.
   *
   * @param suit  is the suit of the card.
   * @param value the card value.
   */
  public RegularCard(Suits suit, int value) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Invalid card number");
    }
    this.suit = suit;
    this.value = value;
    this.isVisible = false;
  }

  @Override
  public void changeVisibility(Boolean vis) {
    this.isVisible = vis;
  }

  @Override
  public boolean getVisibility() {
    return this.isVisible;
  }

  @Override
  public int getValue() {
    return this.value;
  }

  public Suits getSuit() {
    return suit;
  }

  /**
   * To string for the cards.
   * @return the tostring card.
   */
  public String toString() {
    switch (value) {
      case 1:
        return "A" + suit.toString();
      case 11:
        return "J" + suit.toString();
      case 12:
        return "Q" + suit.toString();
      case 13:
        return "K" + suit.toString();
      default:
        return Integer.toString(value) + suit.toString();
    }
  }
}

