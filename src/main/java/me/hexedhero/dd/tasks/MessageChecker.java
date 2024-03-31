package me.hexedhero.dd.tasks;

import java.util.Iterator;
import me.hexedhero.dd.DarknessDamage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class MessageChecker implements Runnable {

    public void run() {
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(true) {
            Player player;
            byte playersLightLevel;
            do {
                do {
                    do {
                        do {
                            do {
                                if (!var1.hasNext()) {
                                    return;
                                }

                                player = (Player)var1.next();
                            } while(!player.isValid());
                        } while(player.getGameMode().equals(GameMode.CREATIVE));
                    } while(player.getGameMode().equals(GameMode.SPECTATOR));

                    playersLightLevel = player.getLocation().getBlock().getLightLevel();
                } while(!DarknessDamage.getInstance().getDataManager().isInLightRange(playersLightLevel));
            } while(!DarknessDamage.getInstance().getDataManager().isInEnabledWorld(player.getWorld()));

            if (DarknessDamage.getInstance().getDataManager().IGNORE_LIGHT_SOURCES_IN_HAND && (DarknessDamage.getInstance().getDataManager().LIGHT_SOURCES_MATERIALS.contains(player.getInventory().getItemInMainHand().getType()) || DarknessDamage.getInstance().getDataManager().LIGHT_SOURCES_MATERIALS.contains(player.getInventory().getItemInOffHand().getType()))) {
                return;
            }

            if (player.hasPermission("darknessdamage.bypass")) {
                return;
            }

            if (player.getLocation().getBlockY() >= DarknessDamage.getInstance().getDataManager().DAMAGE_Y_LEVEL_MIN && player.getLocation().getBlockY() <= DarknessDamage.getInstance().getDataManager().DAMAGE_Y_LEVEL_MAX) {
                String message = "";
                if (playersLightLevel >= DarknessDamage.getInstance().getDataManager().LIGHT_LEVEL_TO_DEEP_DARKNESS_MIN && playersLightLevel <= DarknessDamage.getInstance().getDataManager().LIGHT_LEVEL_TO_DEEP_DARKNESS_MAX) {
                    message = DarknessDamage.getInstance().getDataManager().ACTION_BAR_MESSAGE_DARKNESS;
                }

                if (!message.isEmpty()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(DarknessDamage.getInstance().getStringHelper().colorize(message)));
                }
            }
        }
    }
}