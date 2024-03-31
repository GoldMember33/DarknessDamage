package me.hexedhero.dd.utils;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.hexedhero.dd.DarknessDamage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StringHelper {
    private final Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");

    public void tellConsole(Level level, String message) {
        if (!message.trim().isEmpty()) {
            message = this.colorize(message);
            if (!DarknessDamage.getInstance().getVersionHelper().isPaper()) {
                message = ChatColor.stripColor(message);
            }

            DarknessDamage.getInstance().getLogger().log(level, message);
        }
    }

    public void tellConsole(Level level, String... message) {
        String[] var3 = message;
        int var4 = message.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            this.tellConsole(level, string);
        }

    }

    public void tellPlayer(Player player, String message) {
        if (!message.trim().isEmpty()) {
            player.sendMessage(this.colorize(message));
        }
    }

    public void tellPlayer(Player player, String... message) {
        String[] var3 = message;
        int var4 = message.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            this.tellPlayer(player, string);
        }

    }

    public void tellSender(CommandSender sender, String message) {
        if (sender instanceof Player) {
            this.tellPlayer((Player)sender, message);
        } else {
            this.tellConsole(Level.INFO, message);
        }

    }

    public void tellSender(CommandSender sender, String... message) {
        String[] var3 = message;
        int var4 = message.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            this.tellSender(sender, string);
        }

    }

    public void tellSenderAndConsole(CommandSender sender, String message) {
        this.tellSender(sender, message);
        if (sender instanceof Player) {
            this.tellConsole(Level.INFO, message);
        }

    }

    public void tellSenderAndConsole(CommandSender sender, String... message) {
        String[] var3 = message;
        int var4 = message.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String string = var3[var5];
            this.tellSenderAndConsole(sender, string);
        }

    }

    public String colorize(String message) {
        if (message != null && message != "") {
            if (DarknessDamage.getInstance().getVersionHelper().getMajorVersionNumber() >= 16) {
                for(Matcher matcher = this.hexPattern.matcher(message); matcher.find(); matcher = this.hexPattern.matcher(message)) {
                    ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
                    String before = message.substring(0, matcher.start());
                    String after = message.substring(matcher.end());
                    message = before + hexColor + after;
                }
            }

            return ChatColor.translateAlternateColorCodes('&', message);
        } else {
            return message;
        }
    }

    public String nanosToMillis(long nanoseconds) {
        return (new DecimalFormat("0.00")).format((double)nanoseconds / 1000000.0D);
    }
}