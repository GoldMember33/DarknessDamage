package me.hexedhero.dd.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.hexedhero.dd.DarknessDamage;
import org.bukkit.Material;
import org.bukkit.World;

public class DataManager {
    public List<String> ENABLED_WORLDS;
    public List<String> COMMANDS_ENTER;
    public List<String> COMMANDS_LEAVE;

    public int DAMAGE_TASK_TIMER;
    public int LIGHT_RANGE_MIN;
    public int LIGHT_RANGE_MAX;
    public int DAMAGE;
    public int DAMAGE_Y_LEVEL_MIN;
    public int DAMAGE_Y_LEVEL_MAX;
    public boolean DAMAGE_INCREASES;
    public boolean DAMAGE_KILLS;
    public boolean IGNORE_LIGHT_SOURCES_IN_HAND;

    public int LIGHT_LEVEL_TO_DEEP_DARKNESS_MIN;

    public int LIGHT_LEVEL_TO_DEEP_DARKNESS_MAX;

    public String ACTION_BAR_MESSAGE_DARKNESS;

    public List<Material> LIGHT_SOURCES_MATERIALS;
    public Map<UUID, Integer> lastDamages = new HashMap();

    public DataManager() {
        String[] ranges = DarknessDamage.getInstance().getConfig().getString("Light Range").split("-");
        this.ENABLED_WORLDS = DarknessDamage.getInstance().getConfig().getStringList("Enabled Worlds");
        this.DAMAGE_TASK_TIMER = DarknessDamage.getInstance().getConfig().getInt("Damage Task Timer");
        this.LIGHT_RANGE_MIN = Integer.parseInt(ranges[0]);
        this.LIGHT_RANGE_MAX = Integer.parseInt(ranges[1]);
        this.DAMAGE = DarknessDamage.getInstance().getConfig().getInt("Damage");
        String[] levels = DarknessDamage.getInstance().getConfig().getString("Damage Y Level").split("\\|");
        this.DAMAGE_Y_LEVEL_MIN = Integer.parseInt(levels[0]);
        this.DAMAGE_Y_LEVEL_MAX = Integer.parseInt(levels[1]);
        this.DAMAGE_INCREASES = DarknessDamage.getInstance().getConfig().getBoolean("Damage Increases");
        this.DAMAGE_KILLS = DarknessDamage.getInstance().getConfig().getBoolean("Damage Kills");
        this.ACTION_BAR_MESSAGE_DARKNESS = DarknessDamage.getInstance().getConfig().getString("Action Bar Message Darkness");
        String[] lightLevelsDeepDarkness = DarknessDamage.getInstance().getConfig().getString("Light Level To Deep Darkness").split("-");
        this.LIGHT_LEVEL_TO_DEEP_DARKNESS_MIN = Integer.parseInt(lightLevelsDeepDarkness[0]);
        this.LIGHT_LEVEL_TO_DEEP_DARKNESS_MAX = Integer.parseInt(lightLevelsDeepDarkness[1]);
        this.IGNORE_LIGHT_SOURCES_IN_HAND = DarknessDamage.getInstance().getConfig().getBoolean("Ignore Light Sources In Hand.Enabled");
        List<Material> materials = new ArrayList<>();
        Iterator var3 = DarknessDamage.getInstance().getConfig().getStringList("Ignore Light Sources In Hand.Valid Light Sources").iterator();

        while(var3.hasNext()) {
            String string = (String)var3.next();
            materials.add(Material.valueOf(string));
        }

        this.LIGHT_SOURCES_MATERIALS = materials;
        this.COMMANDS_ENTER = DarknessDamage.getInstance().getConfig().getStringList("Commands.Enter");
        this.COMMANDS_LEAVE = DarknessDamage.getInstance().getConfig().getStringList("Commands.Leave");
    }

    public boolean isInLightRange(int number) {
        return number >= this.LIGHT_RANGE_MIN && number <= this.LIGHT_RANGE_MAX;
    }

    public boolean isInEnabledWorld(World world) {
        return this.ENABLED_WORLDS.contains("*") ? true : this.ENABLED_WORLDS.contains(world.getName());
    }
}