package model.hw04;

import model.hw02.BasicKlondike;
import model.hw02.KlondikeModel;

/**
 * Klondike Creator class that creates the different versions of the klondike game.
 */
public class KlondikeCreator {


  /**
   * The three different types of games represented by an enum.
   */
  public enum GameType {
    LIMITED,WHITEHEAD, BASIC,
  }

  /**
   * Makes the model of the klondike game type.
   * @param version the version of the game to be played.
   * @return the model made.
   */
  public static KlondikeModel createGame(GameType version) {
    return createGame(version, 2);
  }

  /**
   * Makes the model of the klondike game type.
   * @param version the version of the game to be played.
   * @param redraws the amount of redraws to be used.
   * @return the model made.
   */
  public static KlondikeModel createGame(GameType version, int redraws) {
    switch (version) {
      case BASIC:
        return new BasicKlondike();
      case LIMITED:
        return new LimitedDrawKlondike(redraws);
      case WHITEHEAD:
        return new WhiteheadKlondike();
      default:
        throw new IllegalArgumentException("Invalid Game Type: " + version);
    }
  }
}
