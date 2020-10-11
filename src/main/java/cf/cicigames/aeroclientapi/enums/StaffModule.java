package cf.cicigames.aeroclientapi.enums;

public enum StaffModule {
  XRAY("X-Ray", "xray"),
  BUNNYHOP("Bunnyhop", "bunnyhop"),
  NAMETAGS("Nametags", "nametags"),
  NOCLIP("Noclip", "noclip");

  private String displayName;
  
  private String packetName;
  
  StaffModule(String displayName, String packetName) {
    this.displayName = displayName;
    this.packetName = packetName;
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public String getPacketName() {
    return this.packetName;
  }
}
