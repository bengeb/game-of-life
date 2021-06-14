package GameOfLife;

import java.util.Arrays;

public class Ruleset {
  private boolean[] begin;
  private boolean[] stay;

  public Ruleset(boolean[] beginIn, boolean[] stayIn) {
    this.begin = beginIn;
    this.stay = stayIn;
  }

  // Decide whether the given number of neighbors will create a cell
  // count: The number of cells neighboring a particular cell
  // return: Whether a new cell should be created
  public boolean begin(int count) {
    return begin[count];
  }

  // Decide whether the given number of neighbors will keep a cell alive
  // count: The number of cells neighboring a particular cell
  // return: Whether the cell should be remain alive
  public boolean stay(int count) {
    return stay[count];
  }
}