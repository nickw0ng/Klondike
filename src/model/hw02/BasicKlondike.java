package cs3500.klondike.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is a stub implementation of the {@link cs3500.klondike.model.hw02.KlondikeModel}
 * interface. You may assume that the actual implementation of BasicKlondike will have a
 * zero-argument (i.e. default) constructor, and that all the methods below will be
 * implemented.  You may not make any other assumptions about the implementation of this
 * class (e.g. what fields it might have, or helper methods, etc.).
 *
 * <p>Once you've implemented all the constructors and methods on your own, you can
 * delete the placeholderWarning() method.
 */
public class BasicKlondike implements cs3500.klondike.model.hw02.KlondikeModel {

  String placeholderMessage = "If you got this to run, your tests compile correctly"
          + " against the data definitions.";

  private boolean gameStarted = false;
  private List<Card> deck; // remaining cards
  private List<List<Card>> cascade; // cascade list of lists of cards
  // the top most card is shown, just need to show that during the toString method

  private List<List<Card>> foundation;
  private List<Card> drawCards;

  private int score; // ongoing score of game
  private int numPiles;
  private int numDraw;
  private int maxNumHeight;

  /**
   * Creates all the cards for the deck.
   */
  public BasicKlondike() {
    this.score = 0;
    this.deck = new ArrayList<>();
    for (int i = 1; i < 14; i++) {
      this.deck.add(new RegularCard(Suits.HEARTS, i));
    }
    for (int i = 1; i < 14; i++) {
      this.deck.add(new RegularCard(Suits.DIAMONDS, i));
    }
    for (int i = 1; i < 14; i++) {
      this.deck.add(new RegularCard(Suits.Clubs, i));
    }
    for (int i = 1; i < 14; i++) {
      this.deck.add(new RegularCard(Suits.SPADES, i));
    }
  }

  @Override
  public List<Card> getDeck() {
    return this.deck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw)
          throws IllegalArgumentException {
    this.numDraw = numDraw;
    ArrayList<Card> deck2 = new ArrayList<>(deck);

    if (!isValidDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck.");
    }

    if (gameStarted) {
      throw new IllegalStateException("The game has already started.");
    }
    gameStarted = true;

    if (numPiles < 1 || numDraw < 0 || numPiles + numDraw > deck.size()) {
      throw new IllegalArgumentException("Invalid number of piles or draw count.");
    }

    ArrayList<ArrayList<Card>> cardSuits = this.suitsEqual(this.sortSuits(deck2));

    for (ArrayList<Card> suit : cardSuits) {
      ArrayList<Integer> cardNums = new ArrayList<>();
      this.foundation.add(new ArrayList<>());
      for (Card card : suit) {
        cardNums.add(card.getValue());
      }
      int i = 1;
      for (Card card : suit) {
        if (!(cardNums.contains(i))) {
          throw new IllegalArgumentException("invalid");
        }
        i = i + 1;
      }
    }
    drawCards.clear();
    cascade.clear();
    createPiles(deck2, shuffle, numPiles, numDraw);
  }

  /**
   * checks if it is a valid deck.
   */
  private boolean isValidDeck(List<Card> deck) {
    if (deck == null) {
      return false;
    }
    ArrayList<Card> deck2 = new ArrayList<>(deck);
    for (Card card : deck2) {
      if (card == null) {
        return false;
      }
    }
    return true;
  }

  private void createPiles(ArrayList<Card> exDeck, boolean shuffle, int numPiles, int numDraw) {
    if (shuffle) {
      Collections.shuffle(exDeck);
    }

    for (int i = 0; i < numPiles; i++) {
      cascade.add(new ArrayList<>());
    }
    for (int pIndex = 0; pIndex < numPiles; pIndex++) {
      for (int cIndex = pIndex; cIndex < numPiles; cIndex++) {
        if (exDeck.isEmpty()) {
          throw new IllegalArgumentException("Invalid amount of cards");
        } else {
          cascade.get(cIndex).add(exDeck.remove(0));
        }
        makeVisibility(cIndex, pIndex);
      }
    }
    createDrawPile(exDeck);
    int i = 0;
    while (drawCards.size() > i && i < numDraw) {
      drawCards.get(i).changeVisibility(true);
      i++;
    }
  }

  private void createDrawPile(ArrayList<Card> deck) {
    drawCards.addAll(deck);
  }

  private void makeVisibility(int cIndex, int pIndex) {
    if (pIndex == cIndex) {
      cascade.get(cIndex).get(pIndex).changeVisibility(true);
    }
  }
  
  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    if (srcPile < 0 || srcPile >= cascade.size()
            || destPile < 0 || destPile >= cascade.size() || srcPile == destPile) {
      throw new IllegalArgumentException("Invalid move ");
    }

    if (numCards <= 0 || numCards > cascade.get(srcPile).size()) {
      throw new IllegalArgumentException("Invalid card move");
    }
    List<Card> sourcePile = cascade.get(srcPile);
    List<Card> destinationPile = cascade.get(destPile);
    int startIndex = sourcePile.size() - (numCards - 1);

    if (!(sourcePile.get(startIndex).getVisibility())) {
      throw new IllegalStateException("Cannot move a face down card");
    }
    for (int i = startIndex; i < sourcePile.size(); i++) {
      Card card = sourcePile.remove(i);
      destinationPile.add(card);
    }
  }

  @Override
  public void moveDraw(int destPile) throws IllegalStateException {
    if (destPile < 0 || destPile >= cascade.size()) {
      throw new IllegalArgumentException("Destination pile number is invalid");
    }
    /*if (visibleDrawCards.isEmpty()) {
      throw new IllegalStateException("No draw cards left");
    }

    moveToCascade(destPile, visibleDrawCards, 1, visibleDrawCards.get(0));
    if (visibleDrawCards.add().size() >= numPiles) {
      visibleDrawCards.get(numPiles - 1).changeVisibility(true); }*/

  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile) {
    // Check if the provided indices are valid
    if (srcPile < 0 || srcPile >= getNumPiles()) {
      throw new IllegalArgumentException("Invalid source pile index.");
    }
    if (cascade.get(srcPile).isEmpty()) {
      throw new IllegalStateException("The source pile is empty");
    }

    /*foundationMove(getCardAt(srcPile, 0), foundationPile, cascade.get(srcPile));
    if (!cascade.get(srcPile).isEmpty()) {
      cascade.get(srcPile).getClass(0).changeVisibility(true); }
  }*/

  }

  @Override
  public void moveDrawToFoundation(int foundationPile) throws IllegalStateException {
    if (drawCards.isEmpty()) {
      throw new IllegalStateException("No cards in draw");
    }
    moveFoundation(drawCards.get(0), foundationPile, drawCards);
    if (drawCards.size() >= numDraw) {
      drawCards.get(numDraw - 1).changeVisibility(true);
    }
  }

  @Override
  public void discardDraw()
          throws IllegalStateException {
    if (drawCards.isEmpty()) {
      throw new IllegalStateException("Nothing in draw piles");
    }
    Card moveCard = drawCards.remove(0);
    drawCards.add(moveCard);
    if (drawCards.size() >= numDraw) {
      drawCards.get(drawCards.size() - 1).changeVisibility(false);
      drawCards.get(numDraw - 1).changeVisibility(true);
    }
  }

  @Override
  public int getNumRows() {
    List<Integer> size = new ArrayList<>();
    for (List<Card> pile : cascade) {
      size.add(pile.size());
    }
    int cas = size.get(0);
    for (int number : size) {
      if (number > cas) {
        cas = number;
      }
    }
    return cas;
  }

  @Override
  public int getNumPiles() {
    return cascade.size();
  }

  @Override
  public int getNumDraw() {
    return numDraw;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return score;
  }

  @Override
  public int getPileHeight(int pileNum) throws IllegalStateException {
    if (pileNum < 0 || pileNum >= cascade.size()) {
      throw new IllegalArgumentException("Can't get height");
    }
    return cascade.get(pileNum).size();
  }

  @Override
  public boolean isCardVisible(int pileNum, int card) throws IllegalStateException {
    if (pileNum < 0 || pileNum >= cascade.size() || card < 0
            || card >= getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Can not find card");
    }
    return getCardAt(pileNum, card).getVisibility();
  }

  @Override
  public Card getCardAt(int pileNum, int card) throws IllegalStateException {
    if (pileNum < 0 || pileNum >= cascade.size() || card < 0
            || card >= getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Card can not be acquired");
    }
    return this.cascade.get(pileNum).get(card);
  }

  @Override
  public Card getCardAt(int foundationPile) throws IllegalStateException {
    if (foundationPile < 0 || foundationPile > foundation.size()) {
      throw new IllegalArgumentException("Foundation no found");
    }
    return foundation.get(foundationPile).get(0);
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    List<Card> drawCard = new ArrayList<Card>();
    if (drawCards.size() < getNumDraw()) {
      drawCard = drawCards.subList(0, drawCards.size());
    } else {
      drawCard = drawCards.subList(0, getNumDraw());
    }
    return drawCard;
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    return foundation.size();
  }

  public boolean isCardVisable(int pileNum, int card) {
    return false;
  }

  protected ArrayList<ArrayList<Card>> suitsEqual(ArrayList<ArrayList<Card>> suits)
          throws IllegalArgumentException {
    ArrayList<ArrayList<Card>> deck = new ArrayList<>(suits);
    for (ArrayList<Card> suit : deck) {
      if (suit.isEmpty()) {
        suits.remove(suit);
      }
    }

    for (ArrayList<Card> suit : suits) {
      for (ArrayList<Card> otherSuit : suits) {
        if (suit.size() != otherSuit.size()) {
          throw new IllegalArgumentException("All suits must have the same number of cards");
        }
      }
    }

    return suits;
  }

  protected String getCardSuit(Card card) {
    return card.toString().substring(card.toString().length() - 1);
  }

  protected ArrayList<ArrayList<Card>> sortSuits(List<Card> deck) throws IllegalArgumentException,
          IllegalStateException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    ArrayList<Card> clubCard = new ArrayList<>();
    ArrayList<Card> spadeCard = new ArrayList<>();
    ArrayList<Card> heartCard = new ArrayList<>();
    ArrayList<Card> diamondCard = new ArrayList<>();
    ArrayList<ArrayList<Card>> suits = new ArrayList<>(Arrays.asList(clubCard,
            spadeCard, heartCard, diamondCard));
    for (Card card : deck) {
      String suit = getCardSuit(card);
      switch (suit) {
        case "♣":
          clubCard.add(card);
          break;
        case "♠":
          spadeCard.add(card);
          break;
        case "♡":
          heartCard.add(card);
          break;
        case "♢":
          diamondCard.add(card);
          break;
        default:
          throw new IllegalArgumentException("No matching suit");
      }
    }
    return suits;
  }


  private void moveFoundation(Card card, int foundPile, List<Card> sourcePile) {
    if (foundPile < 0 || foundPile >= foundation.size()) {
      throw new IllegalArgumentException("Invalid foundation pile number");
    }
    List<Card> foundationPile = foundation.get(foundPile);
    if ((card.getValue() != 1) && (!foundationPile.isEmpty())) {
      if ((card.getValue() != 1) && (!foundationPile.isEmpty())) {
        throw new IllegalStateException("Must be an ace to move to an empty foundation pile");
      }
      sourcePile.remove(0);
      foundationPile.add(0, card);
    }
  }
}

