import java.util.ArrayList;
import java.util.List;

public class QuestMap {
  private List<List<Room>> questMap = new ArrayList<>();
  private int rowSize;
  private int columnSize;

  public int getRowSize() {
    return rowSize;
  }

  public void setRowSize(int rowSize) {
    this.rowSize = rowSize;
  }

  public int getColumnSize() {
    return columnSize;
  }

  public void setColumnSize(int columnSize) {
    this.columnSize = columnSize;
  }

  public void setQuestRoom(int row, int column, Room room) {
    this.questMap.get(row).set(column, room);
  }

  public Room getQuestRoom(int row, int column) {
    return questMap.get(row).get(column);
  }

  public int[] getCoordinates(String title) {
    int[] coordinates = {0, 0};
    for (int i = 0; i < getRowSize(); ++i) {
      for (int j = 0; j < getColumnSize(); ++j) {
        if (questMap.get(i).get(j).getTitle().equals(title)) {
          coordinates[0] = i;
          coordinates[1] = j;
        }
      }
    }
    return coordinates;
  }

  public QuestMap() {
  }

  public void createMap() {
    for (int i = 0; i < getRowSize(); ++i) {
      List<Room> rowRooms = new ArrayList<>();
      for (int j = 0; j < getColumnSize(); ++j) {
        rowRooms.add(new Room("empty"));
      }
      questMap.add(rowRooms);
    }
  }
}
