package cs3500.klondike.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cs3500.klondike.model.hw02.RegularCard;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.Suits;

/**
 * An abstract class that represents the different versions of the game.
 */

//TODO: implement these in the basic klondike

public abstract class AbstractKlondike implements cs3500.klondike.model.hw02.KlondikeModel {

  //the draw cards in a klondike game
  public final List<Card> drawCards;

  //the cascades in a klondike game
  protected final List<List<Card>> cascade;

  //the foundation in a klondike game
  private final List<List<Card>> foundation;

  //the number of draw cards in the game
  public int numDraw;

  //boolean of whether the game is started or not
  private boolean gameStarted;

  /**
   * makes the model for the game.
   */
  public AbstractKlondike() {
    this.drawCards = new ArrayList<>();
    this.cascade = new ArrayList<>();
    this.foundation = new ArrayList<>();
    this.gameStarted = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> gameDeck = new ArrayList<>();

    for (Suits suit : Suits.values()) {
      for (int i = 1; i <= 13; i++) {
        gameDeck.add(new RegularCard(suit, i));
      }
    }
    return gameDeck;
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


  //todo: fix maybe?
  @Override
  public void movePile(int srcPile, int numCards, int destPile)
          throws IllegalStateException {
    if (srcPile >= cascade.size() || srcPile < 0
            || destPile < 0 || destPile >= cascade.size() || srcPile == destPile) {
      throw new IllegalArgumentException("Invalid move ");
    }

    if (numCards > cascade.get(srcPile).size() || numCards <= 0) {
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

  //todo: fix maybe?
  @Override
  public void moveDraw(int destPile)
          throws IllegalStateException {
    if (destPile < 0 || destPile >= cascade.size()) {
      throw new IllegalArgumentException("Destination pile number is invalid");
    }
    if (drawCards.isEmpty()) {
      throw new IllegalStateException("No cards to draw");
    }
    moveToCascade(destPile, drawCards, drawCards.get(0));
    if (drawCards.size() >= numDraw) {
      drawCards.get(numDraw - 1).changeVisibility(true);
    }
  }

  //todo: fix maybe?
  @Override
  public void moveToFoundation(int srcPile, int foundationPile)
          throws IllegalStateException {
    if (srcPile < 0 || srcPile >= getNumPiles()) {
      throw new IllegalArgumentException("Invalid source pile index.");
    }
    if (cascade.get(srcPile).isEmpty()) {
      throw new IllegalStateException("The source pile is empty");
    }
    moveFoundation(cascade.get(srcPile).get(cascade.get(srcPile).size() - 1),
            foundationPile, cascade.get(srcPile));
    if (!cascade.get(srcPile).isEmpty()) {
      cascade.get(srcPile).get(cascade.get(srcPile).size() - 1).changeVisibility(true);
    }
  }

  //todo: put into basic klondike
  @Override
  public void moveDrawToFoundation(int foundationPile) {
    if (drawCards.isEmpty()) {
      throw new IllegalStateException("There are no draw cards");
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
  public boolean isGameOver()
          throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore()
          throws IllegalStateException {
    int gameScore = 0;
    for (List<Card> pile : foundation) {
      if (!pile.isEmpty()) {
        gameScore = gameScore + pile.get(pile.size() - 1).getValue();
      }
    }
    return gameScore;
  }

  @Override
  public int getPileHeight(int pileNum)
          throws IllegalStateException {
    if (pileNum >= cascade.size() || pileNum < 0) {
      throw new IllegalArgumentException("Pile does not exist");
    }
    return cascade.get(pileNum).size();
  }

  @Override
  public boolean isCardVisible(int pileNum, int card)
          throws IllegalArgumentException, IllegalStateException {
    if (pileNum < 0 || pileNum >= cascade.size() || card < 0
            || card >= getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Can't find card");
    }
    return getCardAt(pileNum, card).getVisibility();
  }

  @Override
  public Card getCardAt(int pileNum, int card)
          throws IllegalArgumentException {
    if (card < 0 || pileNum < 0 || pileNum >= cascade.size() ||
            card >= getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Invalid input");
    }
    if (!(this.cascade.get(pileNum).get(card).getVisibility())) {
      throw new IllegalArgumentException("Card is not visible");
    }
    return this.cascade.get(pileNum).get(card);
  }

  @Override
  public Card getCardAt(int foundationPile) throws IllegalStateException {
    if (foundationPile < 0 || foundationPile >= foundation.size()) {
      throw new IllegalArgumentException("Invalid input");
    }
    if (foundation.get(foundationPile).isEmpty()) {
      return null;
    }
    return foundation.get(foundationPile).get(foundation.get(foundationPile).size() - 1);
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    List<Card> dCards = new ArrayList<>();
    if (0 < getNumDraw()) {
      dCards = drawCards.subList(0, drawCards.size());
    }
    else {
      dCards = drawCards.subList(0, getNumDraw());
    }
    return dCards;
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    return foundation.size();
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


  protected String getCardColor(Card card) {
    String suit = getCardSuit(card);
    String color = "";
    if (Objects.equals(suit, "♣") || Objects.equals(suit, "♠")) {
      color = "black";
    }
    if (Objects.equals(suit, "♡") || Objects.equals(suit, "♢")) {
      color = "red";
    }
    return color;
  }

  protected void moveToCascade(int destPile, List<Card> srcPile,
                               Card compare) {
    List<Card> destinationPile = cascade.get(destPile);
    validateCascadeMove(destinationPile, srcPile, compare);
    if (srcPile.equals(drawCards)) {
      Card cardToMove = srcPile.remove(0);
      destinationPile.add(cardToMove);
    } else {
      List<Card> move = new ArrayList<>(srcPile.subList(srcPile.size() - 1,
              srcPile.size()));
      destinationPile.addAll(move);
      srcPile.subList(srcPile.size() - 1, srcPile.size()).clear();
    }
  }


  protected void validateCascadeMove(List<Card> destinationPile, List<Card> srcPile,
                                     Card compare) {
    if (destinationPile.isEmpty()) {
      if (compare.getValue() != 13) {
        throw new IllegalStateException("Must be a King");
      }
    }
    if (!destinationPile.isEmpty()) {
      Card destTopCard = destinationPile.get(destinationPile.size() - 1);
      if ((getCardColor(compare).equals(getCardColor(destTopCard))) ||
              (compare.getValue() != destTopCard.getValue() - 1)) {
        throw new IllegalStateException("Wrong color");
      }
    }
  }

  protected void moveFoundation(Card card, int foundPile, List<Card> sourcePile) {
    if (foundPile < 0 || foundPile >= foundation.size()) {
      throw new IllegalArgumentException("Invalid input");
    }
    List<Card> pile = foundation.get(foundPile);
    if (pile.isEmpty() && card.getValue() != 1) {
      throw new IllegalStateException("must be an ace");
    } else if (!pile.isEmpty()) {
      if (!(getCardSuit(getCardAt(foundPile)).equals(getCardSuit(card)))
              || (getCardAt(foundPile).getValue() != card.getValue() - 1)) {
        throw new IllegalStateException("invalid move");
      }
    }
    if (sourcePile.equals(drawCards)) {
      sourcePile.remove(0);
    } else {
      sourcePile.remove(sourcePile.size() - 1);
    }
    pile.add(card);
  }
}

