package net.aeroclient.aeroclientapi.enums;

import java.awt.*;

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

  public Color getRGB() {
    return new Color(getColor());

  }
}
