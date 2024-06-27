package cs3500.klondike.controller;

import model.hw02.Card;
import model.hw02.KlondikeModel;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * the klondike controller.
 */
public class KlondikeTextualController implements cs3500.klondike.controller.KlondikeController {
  private final Readable readable;
  private final Appendable appendable;

  /**
   * instantiates readable and appendable.
   *
   * @param r instantiate readable.
   * @param a instantiate appendable.
   */
  public KlondikeTextualController(Readable r, Appendable a) {
    if ((r == null) || (a == null)) {
      throw new IllegalArgumentException("Readable or appendable is null");
    }
    this.appendable = a;
    this.readable = r;
  }

  /**
   * the play game aspect of the controller that interacts with the code and view.
   *
   * @param model The game of solitaire to be played.
   * @param deck The deck of cards to be used.
   * @param shuffle Whether to shuffle the deck or not.
   * @param numPiles How many piles should be in the initial deal.
   * @param numDraw How many draw cards should be visible.
   */
  public void playGame(KlondikeModel model, List<Card> deck,
                       boolean shuffle, int numPiles, int numDraw) {
    Scanner sc = new Scanner(readable);
    boolean quit = false;


    while (!quit) { //continue until the user quits

      String userInstruction = sc.next(); //take an instruction name
      switch (userInstruction) {
        case "mpp": //move pile
          try {
            int arg1 = sc.nextInt();
            int arg2 = sc.nextInt();
            int arg3 = sc.nextInt();
            model.movePile(arg1, arg2, arg3);
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "md": //move draw
          try {
            int arg1 = sc.nextInt();
            model.moveDraw(arg1);
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "mpf": //move to foundation
          try {
            int arg1 = sc.nextInt();
            int arg2 = sc.nextInt();
            model.moveToFoundation(arg1, arg2);
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "mdf": // move draw to foundation
          try {
            int arg1 = sc.nextInt();
            model.moveDrawToFoundation(arg1);
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "dd": //discard draw
          try {
            model.discardDraw();
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "q": //quit
        case "quit": //quit
          quit = true;
          break;
        default:
          writeMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
          if (sc.hasNext(".*q.*")) {
            quit = true;
          }

      }
    }
  }

  private void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }




}
