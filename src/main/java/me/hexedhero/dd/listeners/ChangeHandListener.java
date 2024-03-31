package me.hexedhero.dd.listeners;

import me.hexedhero.dd.DarknessDamage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class ChangeHandListener implements Listener {
    @EventHandler(
            priority = EventPriority.NORMAL,
            ignoreCancelled = true
    )
    public void a(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if (newItem != null && DarknessDamage.getInstance().getDataManager().LIGHT_SOURCES_MATERIALS.contains(newItem.getType())) {
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(DarknessDamage.getInstance().getStringHelper().colorize("&r")));
            DarknessDamage.getInstance().getDataManager().lastDamages.remove(event.getPlayer().getUniqueId());
        }

    }
}