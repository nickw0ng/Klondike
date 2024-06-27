package view;

import java.io.IOException;

import model.hw02.Card;
import model.hw02.KlondikeModel;

/**
 * Game of klondike.
 */
public class KlondikeTextualView implements TextualView {
  private final KlondikeModel model;
  private Appendable appendable;

  public KlondikeTextualView(KlondikeModel model) {
    this.model = model;
  }

  /**
   *  represents toString in the textual view.
   * @return the output.
   */
  public String toString() {
    StringBuilder output = new StringBuilder();

    output.append("Draw: ");
    for (Card card : model.getDrawCards()) {
      output.append(card.toString()).append(", ");
    }
    if (!model.getDrawCards().isEmpty()) {
      output.setLength(output.length() - 2);
    }
    output.append("\n");

    output.append("Foundation: ");
    for (int loc = 0; loc < model.getNumFoundations(); loc++) {
      if (model.getCardAt(loc) == null) {
        output.append("<none>, ");
      } else {
        output.append(model.getCardAt(loc).toString()).append(", ");
      }
    }

    output.setLength(output.length() - 2);
    output.append("\n");
    int numRows = model.getNumRows();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < model.getNumPiles(); j++) {
        int pileHeight = model.getPileHeight(j);
        if (i < pileHeight) {
          if (model.isCardVisible(j, i)) {
            output.append(String.format("%3s", model.getCardAt(j, i).toString()));
          } else {
            output.append("  ?");
          }
        }
      }
      output.append("\n");
    }


    return output.toString();
  }

  public KlondikeTextualView(KlondikeModel model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() throws IOException {
    appendable.append(this.toString());
  }
}