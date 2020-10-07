package cf.cicigames.aeroclientapi.enums;

import java.util.Arrays;

public enum ServerRule {
  VOICE_ENABLED("voiceEnabled", Boolean.class),
  MINIMAP_STATUS("minimapStatus", String.class),
  SERVER_HANDLES_WAYPOINTS("serverHandlesWaypoints", Boolean.class),
  COMPETITIVE_GAMEMODE("competitiveGame", Boolean.class);
  
  private String rule;
  
  private Class value;
  
  ServerRule(String rule, Class clazz) {
    this.rule = rule;
    this.value = clazz;
  }
  
  public static ServerRule getRule(String s) {
    return Arrays.<ServerRule>stream(values()).filter(rule -> rule.getRule().equalsIgnoreCase(s))
      
      .findFirst().orElse((ServerRule)null);
  }
  
  public String getRule() {
    return this.rule;
  }
  
  public Class getValue() {
    return this.value;
  }
}
