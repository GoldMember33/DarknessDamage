package me.hexedhero.dd.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import me.hexedhero.dd.DarknessDamage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class RegistrationHelper {
    public void registerCommands(Command... command) {
        Command[] var2 = command;
        int var3 = command.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Command commands = var2[var4];
            StringHelper stringHelper = DarknessDamage.getInstance().getStringHelper();

            try {
                Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);
                CommandMap commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
                commandMap.register(commands.getLabel(), commands);
            } catch (Exception var9) {
                stringHelper.tellConsole(Level.WARNING, "&c&lERROR REGISTERING COMMANDS - PLEASE REPORT THIS ISSUE!");
                var9.printStackTrace();
                stringHelper.tellConsole(Level.WARNING, "");
            }
        }

    }

    public void registerEvents(Listener... listeners) {
        PluginManager pluginManager = DarknessDamage.getInstance().getServer().getPluginManager();
        StringHelper stringHelper = DarknessDamage.getInstance().getStringHelper();
        Listener[] var4 = listeners;
        int var5 = listeners.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Listener listener = var4[var6];

            try {
                pluginManager.registerEvents(listener, DarknessDamage.getInstance());
            } catch (Exception var9) {
                stringHelper.tellConsole(Level.WARNING, "&c&lERROR REGISTERING LISTENERS - PLEASE REPORT THIS ISSUE!");
                var9.printStackTrace();
                stringHelper.tellConsole(Level.WARNING, "");
            }
        }

    }
}
