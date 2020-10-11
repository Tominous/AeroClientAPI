package cf.cicigames.aeroclientapi.enums;

public enum WaypointColor {
  WHITE(-1),
  KOTH(-66536),
  RALLY(-16776961);
  
  private int color;
  
  WaypointColor(int color) {
    this.color = color;
  }
  
  public int getColor() {
    return this.color;
  }
}
