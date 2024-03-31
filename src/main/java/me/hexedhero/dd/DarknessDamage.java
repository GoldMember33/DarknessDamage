package me.hexedhero.dd;

import java.util.logging.Level;
import me.hexedhero.dd.commands.MainCommand;
import me.hexedhero.dd.listeners.ChangeHandListener;
import me.hexedhero.dd.listeners.LightnessListener;
import me.hexedhero.dd.tasks.DamageChecker;
import me.hexedhero.dd.tasks.MessageChecker;
import me.hexedhero.dd.utils.DataManager;
import me.hexedhero.dd.utils.MetricsLite;
import me.hexedhero.dd.utils.RegistrationHelper;
import me.hexedhero.dd.utils.StringHelper;
import me.hexedhero.dd.utils.VersionHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class DarknessDamage extends JavaPlugin {
    private static DarknessDamage INSTANCE;
    private final StringHelper stringHelper = new StringHelper();
    private final VersionHelper versionHelper = new VersionHelper();
    private final RegistrationHelper registrationHelper = new RegistrationHelper();
    private DataManager dataManager = null;
    BukkitTask damageChecker;
    BukkitTask messageChecker;

    public static DarknessDamage getInstance() {
        return INSTANCE;
    }

    public StringHelper getStringHelper() {
        return this.stringHelper;
    }

    public VersionHelper getVersionHelper() {
        return this.versionHelper;
    }

    public RegistrationHelper getRegistrationHelper() {
        return this.registrationHelper;
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public void onEnable() {
        long enableStartTime = System.nanoTime();
        INSTANCE = this;
        this.getStringHelper().tellConsole(Level.INFO, "Registering config...");
        this.saveDefaultConfig();
        this.dataManager = new DataManager();
        this.getStringHelper().tellConsole(Level.INFO, "Registering commands...");
        this.getRegistrationHelper().registerCommands(new Command[]{new MainCommand()});
        this.getStringHelper().tellConsole(Level.INFO, "Registering listeners...");
        this.getRegistrationHelper().registerEvents(new Listener[]{new LightnessListener(), new ChangeHandListener()});
        this.getStringHelper().tellConsole(Level.INFO, "Starting tasks...");
        this.damageChecker = Bukkit.getScheduler().runTaskTimer(getInstance(), new DamageChecker(), (long)this.getConfig().getInt("Damage Task Timer"), (long)this.getConfig().getInt("Damage Task Timer"));
        this.messageChecker = Bukkit.getScheduler().runTaskTimer(getInstance(), new MessageChecker(), (long)this.getConfig().getInt("Message Task Timer"), (long)this.getConfig().getInt("Message Task Timer"));
        new MetricsLite(getInstance(), 9847);
        this.getStringHelper().tellConsole(Level.INFO, "&aDone and enabled in %time%ms".replace("%time%", this.getStringHelper().nanosToMillis(System.nanoTime() - enableStartTime)));
    }

    public void onDisable() {
        long disableStartTime = System.nanoTime();
        this.getStringHelper().tellConsole(Level.INFO, new String[]{"&aDone and disabled in %time%ms".replace("%time%", this.getStringHelper().nanosToMillis(System.nanoTime() - disableStartTime)), "&aIf you liked the plugin, don't forget to give a &e★★★★★ &arating!", "&aThank you and have a great day!"});
    }

    public void reloadPlugin() {
        this.reloadConfig();
        this.damageChecker.cancel();
        this.messageChecker.cancel();
        this.damageChecker = Bukkit.getScheduler().runTaskTimer(getInstance(), new DamageChecker(), (long)this.getConfig().getInt("Damage Task Timer"), (long)this.getConfig().getInt("Damage Task Timer"));
        this.messageChecker = Bukkit.getScheduler().runTaskTimer(getInstance(), new MessageChecker(), (long)this.getConfig().getInt("Message Task Timer"), (long)this.getConfig().getInt("Message Task Timer"));
        this.dataManager = new DataManager();
    }
}