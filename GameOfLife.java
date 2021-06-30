package GameOfLife;
import java.util.*;
import java.io.*;
public class GameOfLife {
  static final String[] FORMS = {"GOL", "HLIF", "ASIM", "2X2", "DANI", "AMOE", "MOVE", "PGOL", "DMOE", "34", "LLIF", "STAN", "SEED", "MAZE", "COAG", "WALL", "GNAR", "REPL", "MYST", "CORL"};
  static final String[] DESCRIPTIONS = {"Game of Life (default)", "High Life", "Assimilation", "2x2", "Day and Night", "Amoeba", "Move", "Pseudo Life", "Diamoeba", "3-4 Life", "Long Life", "Stains", "Seeds", "Maze", "Coagulations", "Walled Cities", "Gnarl", "Replicator", "Mystery", "Coral"};
  static final String[] STAY = {"23", "23", "4567", "125", "34678", "1358", "245", "238", "5678", "34", "5", "235678", "", "12345", "235678", "2345", "1", "1357", "05678", "45678"};
  static final String[] BEGIN = {"3", "36", "345", "36", "3678", "357", "368", "357", "35678", "34", "345", "3678", "2", "3", "378", "45678", "1", "1357", "3458", "3"};
  static final char EMPTY = '.';
  static final char ALIVE = 'o';
  static final char BIRTH = 'B';
  static final char DEATH = 'D';

  // Print all implemented rule variations
  static void printHelp() {
    for (int i = 0; i < FORMS.length; i++) {
      System.out.println(FORMS[i] + ": " + DESCRIPTIONS[i] + "; stay " + STAY[i] + ", begin " + BEGIN[i]);
    }
  }

  // Print the proper command-line input syntax for this program
  static void printUsage() {
    System.out.println("Usage: GameOfLife input.txt [type] [speed]");
    System.out.println("input.txt is the file to read the initial state of the playing field from.");
    System.out.println("[type] is the abbreviated rule variation to use; GOL by default.");
    System.out.println("[speed] is, if positive, the number of milliseconds to wait between cycles; 200 by default.");
    System.out.println("If [speed] is negative, its absolute value represents the number of cycles to compute between each console print.");
    System.out.println("Alternatively, 'GameOfLife help' lists all rule variants.");
  }

  // Read the contents of the given file and put them into a char array
  // filename: The name of the file to read from
  // return: The contents of the file
  static char[][] read(String filename) {
    ArrayList<String> contents = new ArrayList<String>(0);

    try {
      Scanner file = new Scanner(new File(filename));
      while (file.hasNextLine()) {
        contents.add(file.nextLine());
      }
      file.close();
    } catch (FileNotFoundException err) {
      System.out.println(err);
      return null;
    }

    char[][] field = new char[contents.size()][contents.get(0).length()];
    for (int i = 0; i < contents.size(); i++) {
      for (int j = 0; j < contents.get(0).length(); j++) {
        field[i][j] = contents.get(i).charAt(j);
      }
    }

    return field;
  }

  // Construct a Ruleset with the appropriate rules for the given rule variation
  // index: The index of the rule variant in FORMS; by default, 0 for GOL
  // return: A new Ruleset object
  static Ruleset makeRuleset(int index) {
    boolean stay[] = {false, false, false, false, false, false, false, false, false};
    boolean begin[] = {false, false, false, false, false, false, false, false, false};

    for (int i = 0; i < STAY[index].length(); i++) {
      stay[STAY[index].charAt(i) - 48] = true;
    }
    for (int i = 0; i < BEGIN[index].length(); i++) {
      begin[BEGIN[index].charAt(i) - 48] = true;
    }

    return new Ruleset(begin, stay);
  }

  // Count the number of living cells neighboring, but not including, the given cell
  // field: The playing field
  // int row: The row of the given cell
  // int col: The column of the given cell
  static int countNeighbors(char[][] field, int row, int col) {
    int count = 0;
    int width = field[0].length;
    int height = field.length;
    for (int r = row + height - 1; r < row + height + 2; r++) {
      for (int c = col + width - 1; c < col + width + 2; c++) {
        if ((r % height != row || c % width != col) &&
        (field[r % height][c % width] == ALIVE || field[r % height][c % width] == DEATH)) {
          count++;
        }
      }
    }
    return count;
  }

  // Run through one cycle of the game
  // field: The playing field
  // rule: The Ruleset used to decide what each cell should do
  // return: Whether the field changed at all this cycle
  static boolean cycle(char[][] field, Ruleset rule) {
    boolean change = false;

    for (int row = 0; row < field.length; row++) {
      for (int col = 0; col < field[0].length; col++) {
        if (field[row][col] == EMPTY && rule.begin(countNeighbors(field, row, col))) {
          field[row][col] = BIRTH;
          change = true;
        }
        if (field[row][col] == ALIVE && !rule.stay(countNeighbors(field, row, col))) {
          field[row][col] = DEATH;
          change = true;
        }
      }
    }
    for (int row = 0; row < field.length; row++) {
      for (int col = 0; col < field[0].length; col++) {
        if (field[row][col] == BIRTH) {
          field[row][col] = ALIVE;
        }
        if (field[row][col] == DEATH) {
          field[row][col] = EMPTY;
        }
      }
    }

    return change;
  }

  // Print the grid of lifeforms in its current state
  // field: The playing field
  // cycle: The number of cycles elapsed so far
  static void printField(char[][] field, int cycle) {
    System.out.println("Cycle " + cycle);
    for (int i = 0; i < field.length; i++) {
      for (int j = 0; j < field[0].length; j++) {
        System.out.print(field[i][j]);
      }
      System.out.println();
    }
  }

  // Set up the playing field and loop over it with the cycle() function
  // args: The command line arguments
  public static void main(String args[]) {
    if (args.length < 1 || args.length > 3) {
      printUsage();
      return;
    }

    if (args.length > 1 && args[1].toLowerCase().equals("help")) {
      printHelp();
      return;
    }

    // Set the Game of Life rules to use according to command line input
    int ruleIndex = 0;
    if (args.length > 1) {
      ruleIndex = java.util.Arrays.asList(FORMS).indexOf(args[1].toUpperCase());
      if (ruleIndex < 0) {
        ruleIndex = 0;
      }
    }
    Ruleset rule = makeRuleset(ruleIndex);

    char[][] field = read(args[0]);
    if (field == null) {
      return;
    }

    int speed = 200;
    int turbo = 1;
    if (args.length > 2 && Integer.parseInt(args[2]) >= 0) {
      speed = Integer.parseInt(args[2]);
    } else if (args.length > 2 && Integer.parseInt(args[2]) < 0) {
      speed = 0;
      turbo = Math.abs(Integer.parseInt(args[2]));
    }

    System.out.println("--- " + DESCRIPTIONS[ruleIndex] + " (stay " + STAY[ruleIndex] + ", begin " + BEGIN[ruleIndex] + ") ---");
    int cycle = 0;
    printField(field, cycle);
    while (cycle(field, rule)) {
      cycle++;
      try {Thread.sleep(speed);} catch(InterruptedException err){}
      if (cycle % turbo == 0) {
        // Use an ANSI control sequence to position the cursor
        System.out.print(String.format("\033[%dA", field.length + 1));
        printField(field, cycle);
      }
    }
    System.out.print(String.format("\033[%dA", field.length + 1));
    printField(field, cycle);
    System.out.println("System has reached equilibrium.");
  }
}
