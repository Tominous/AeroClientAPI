package net.aeroclient.aeroclientapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TitleType {
  TITLE("title"),
  SUBTITLE("subtitle"),
  TOOLTIP("tooltip");

  private String type;

}