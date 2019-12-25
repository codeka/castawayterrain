package com.codeka.castawayterrain;

import com.google.common.collect.Lists;
//import io.github.cottonmc.cotton.config.ConfigManager;
//import io.github.cottonmc.cotton.config.annotations.ConfigFile;
//import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.List;

//@ConfigFile(name = "CastawayTerrain")
public class CastawayConfig {
  public static final CastawayConfig instance;

  static {
    instance = new CastawayConfig();

//    instance = ConfigManager.loadConfig(CastawayConfig.class);
//    ConfigManager.saveConfig(instance);
  }

 // @Comment("Average distance between volcano islands: lower value = more volcano islands.")
  public int volcano_island_frequency = 1000;

 // @Comment("Size of the volcano island (including surround shallow ocean) in blocks. Changing this will basically scale everything up/down.")
  public int volcano_size = 128;

 // @Comment("Disable ore-gen. Useful for skyblock-style worlds where you want to get resources some other way.")
  public boolean disable_ore_gen = true;

 // @Comment("List of allowed island types. Should be one of 'forest', 'jungle', or 'bamboo'.")
  public List<String> island_types = Lists.newArrayList("forest", "jungle", "bamboo");
}
