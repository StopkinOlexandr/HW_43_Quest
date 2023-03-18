import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {

  private Hero mainHero; // mainHero - главный персонаж игры
//  private List<Room> rooms = new ArrayList<>();

  private Room current;
  private Map<String, Command> commands = new HashMap<>();
  public QuestMap roomMap = new QuestMap();

  public Game() {

    roomMap.setRowSize(2);
    roomMap.setColumnSize(3);
    roomMap.createMap();
    roomMap.setQuestRoom(0, 1, new Room("Зал"));
    roomMap.setQuestRoom(1, 2, new Room("Кухня"));
    roomMap.setQuestRoom(1, 0, new Room("Туалет"));
    roomMap.setQuestRoom(1, 1, new Room("Коридор"));
    current = roomMap.getQuestRoom(0, 1);
//    System.out.println(current.getTitle());
//    roomMap.getCoordinates(current.getTitle());

    commands.put("вперёд", Command.FORWARD);
    commands.put("вперед", Command.FORWARD); // так можно, но тогда её два раза выведет help()
    commands.put("назад", Command.BACK);
    commands.put("влево", Command.LEFT);
    commands.put("вправо", Command.RIGHT);
    commands.put("выход", Command.EXIT);
    commands.put("справка", Command.HELP);
  }

  public boolean checkMove(Command command) {
    int[] coordinates = roomMap.getCoordinates(current.getTitle());
    int row = coordinates[0];
    int column = coordinates[1];
    boolean movePossible = false;

    switch (command) {
      case FORWARD -> {
        if (row + 1 < roomMap.getRowSize()) {
          if (!roomMap.getQuestRoom(row + 1, column).getTitle().equals("empty")) {
            movePossible = true;
          }
        }
      }
      case BACK -> {
        if (row - 1 >= 0) {
          if (!roomMap.getQuestRoom(row - 1, column).getTitle().equals("empty")) {
            movePossible = true;
          }
        }
      }
      case LEFT -> {
        if (column + 1 < roomMap.getColumnSize()) {
          if (!roomMap.getQuestRoom(row, column + 1).getTitle().equals("empty")) {
            movePossible = true;
          }
        }
      }
      case RIGHT -> {
        if (column - 1 >= 0) {
          if (!roomMap.getQuestRoom(row, column - 1).getTitle().equals("empty")) {
            movePossible = true;
          }
        }
      }
    }
    return movePossible;
  }

  public void start(BufferedReader br) throws IOException {
    System.out.println("=== Текстовый квест v 0.1.0 ===");

    System.out.print("Введите имя: ");
    String name = br.readLine();
    mainHero = new Hero(name);
    mainCycle(br); // запускаем основной игровой цикл
  }

  private Command readCommand(BufferedReader br) throws IOException {
    System.out.print("Введите команду: ");
    String command = br.readLine().toLowerCase();
    while (!commands.containsKey(command)) {
      System.out.print("Введите корректную команду: ");
      command = br.readLine();
    }
    return commands.get(command);
  }

  private void mainCycle(BufferedReader br) throws IOException {

    boolean playing = true; // "продолжать ли игровой цикл"
    while (playing) {
      Command command = readCommand(br);
      switch (command) {
        case FORWARD -> {
          if (!checkMove(command)) {
            System.out.println("Вы не можете пойти вперёд");
          } else {
            int[] coordinates = roomMap.getCoordinates(current.getTitle());
            ++coordinates[0];
            current = roomMap.getQuestRoom(coordinates[0], coordinates[1]);
            mainHero.move();
            String newRoom = current.getTitle();
            System.out.println("Вы шагнули вперёд");
            System.out.println("Теперь вы попали в " + newRoom);
          }
        }
        case BACK -> {
          if (!checkMove(command)) {
            System.out.println("Вы не можете пойти назад");
          } else {
            int[] coordinates = roomMap.getCoordinates(current.getTitle());
            --coordinates[0];
            current = roomMap.getQuestRoom(coordinates[0], coordinates[1]);
            mainHero.move();
            String newRoom = current.getTitle();
            System.out.println("Вы шагнули назад");
            System.out.println("Теперь вы попали в " + newRoom);
          }
        }
        case LEFT -> {
          if (!checkMove(command)) {
            System.out.println("Вы не можете пойти влево");
          } else {
            int[] coordinates = roomMap.getCoordinates(current.getTitle());
            ++coordinates[1];
            current = roomMap.getQuestRoom(coordinates[0], coordinates[1]);
            mainHero.move();
            String newRoom = current.getTitle();
            System.out.println("Вы шагнули влево");
            System.out.println("Теперь вы попали в " + newRoom);
          }
        }
        case RIGHT -> {
          if (!checkMove(command)) {
            System.out.println("Вы не можете пойти вправо");
          } else {
            int[] coordinates = roomMap.getCoordinates(current.getTitle());
            --coordinates[1];
            current = roomMap.getQuestRoom(coordinates[0], coordinates[1]);
            mainHero.move();
            String newRoom = current.getTitle();
            System.out.println("Вы шагнули вправо");
            System.out.println("Теперь вы попали в " + newRoom);
          }
        }
        case EXIT -> {
          System.out.println("До свидания!");
          System.out.printf("Герой по имени %s сделал %d шагов%n", mainHero.getName(),
              mainHero.getSteps());
          playing = false;
        }
        case HELP -> help();
      }
    }
  }

  public void help() { // вывод справки
    System.out.println("Возможные команды:");
    for (String command : commands.keySet()) {
      if (!command.contains("ё")) {
        System.out.println("- " + command);
      }
    }
  }
}
