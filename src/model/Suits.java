package cs3500.klondike.model.hw02;

/**
 * represents the suits as enums.
 */
public enum Suits {
  Clubs("♣"), SPADES("♠") , HEARTS("♡"),DIAMONDS("♢");
  private final String symbol;

  Suits(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
