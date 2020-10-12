package cf.cicigames.aeroclientapi.enums;

import java.awt.*;

public enum WaypointColor {
  WHITE(-1), // = 255 255 255
  KOTH(-66536), // = 254 252 24
  RALLY(-16776961); // = 0 0 255
  
  private int color;
  
  WaypointColor(int color) {
    this.color = color;
  }

  public int getColor() {
    return this.color;
  }

  public Color getRGB() {
    return new Color(getColor());

  }
}
