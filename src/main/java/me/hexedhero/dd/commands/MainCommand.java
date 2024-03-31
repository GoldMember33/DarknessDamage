package me.hexedhero.dd.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import me.hexedhero.dd.DarknessDamage;
import me.hexedhero.dd.utils.StringHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MainCommand extends Command {
    public MainCommand() {
        super("darknessdamage");
        this.setAliases(Arrays.asList("dd"));
        this.setDescription("Plugin main command");
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("darknessdamage.command.reload")) {
                    this.sendDefaultMessage(sender);
                    return true;
                }

                StringHelper stringHelper = DarknessDamage.getInstance().getStringHelper();
                DarknessDamage.getInstance().reloadPlugin();
                stringHelper.tellSender(sender, "&c[DarknessDamage] &aReloaded plugin!");
            }

            return false;
        } else {
            this.sendDefaultMessage(sender);
            return true;
        }
    }

    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> list = new ArrayList();
        if (args.length == 1) {
            list.add("reload");
        }

        return (List)(args[args.length - 1].isEmpty() ? list : (List)list.stream().filter((string) -> {
            return string.toLowerCase().startsWith(args[args.length - 1].toLowerCase());
        }).collect(Collectors.toList()));
    }

    private void sendDefaultMessage(CommandSender sender) {
        DarknessDamage.getInstance().getStringHelper().tellSender(sender, new String[]{"&c[DarknessDamage] &fCommand Help", "&c[DarknessDamage] &7/darknessdamage - Command help", "&c[DarknessDamage] &7/darknessdamage reload - Reload the plugin"});
    }
}
