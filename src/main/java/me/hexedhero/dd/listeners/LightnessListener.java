package me.hexedhero.dd.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.hexedhero.dd.DarknessDamage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class LightnessListener implements Listener {
    private final Map<UUID, LightnessListener.CurrentState> playerStates = new HashMap();

    @EventHandler(
            priority = EventPriority.NORMAL,
            ignoreCancelled = true
    )
    public void a(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int playersLightLevel = player.getLocation().getBlock().getLightLevel();
        if (DarknessDamage.getInstance().getDataManager().isInLightRange(playersLightLevel)) {
            this.playerStates.put(player.getUniqueId(), LightnessListener.CurrentState.DARK);
            if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR) && !player.hasPermission("darknessdamage.bypass") && DarknessDamage.getInstance().getDataManager().isInEnabledWorld(player.getWorld())) {
                this.triggerCommands(player, LightnessListener.CurrentState.DARK);
            }
        } else {
            this.playerStates.put(player.getUniqueId(), LightnessListener.CurrentState.LIGHT);
            if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR) && !player.hasPermission("darknessdamage.bypass") && DarknessDamage.getInstance().getDataManager().isInEnabledWorld(player.getWorld())) {
                this.triggerCommands(player, LightnessListener.CurrentState.LIGHT);
            }
        }

    }

    @EventHandler(
            priority = EventPriority.NORMAL,
            ignoreCancelled = true
    )
    public void a(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isValid() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR) && !player.hasPermission("darknessdamage.bypass") && DarknessDamage.getInstance().getDataManager().isInEnabledWorld(player.getWorld())) {
            int playersLightLevel = event.getTo().getBlock().getLightLevel();
            if (DarknessDamage.getInstance().getDataManager().isInLightRange(playersLightLevel) && ((LightnessListener.CurrentState)this.playerStates.get(player.getUniqueId())).equals(LightnessListener.CurrentState.LIGHT)) {
                this.playerStates.put(event.getPlayer().getUniqueId(), LightnessListener.CurrentState.DARK);
                if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
                    this.triggerCommands(player, LightnessListener.CurrentState.DARK);
                }
            } else if (!DarknessDamage.getInstance().getDataManager().isInLightRange(playersLightLevel) && ((LightnessListener.CurrentState)this.playerStates.get(player.getUniqueId())).equals(LightnessListener.CurrentState.DARK)) {
                this.playerStates.put(event.getPlayer().getUniqueId(), LightnessListener.CurrentState.LIGHT);
                if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
                    this.triggerCommands(player, LightnessListener.CurrentState.LIGHT);
                }

                if (DarknessDamage.getInstance().getDataManager().lastDamages.containsKey(player.getUniqueId())) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(DarknessDamage.getInstance().getStringHelper().colorize("&r")));
                    DarknessDamage.getInstance().getDataManager().lastDamages.remove(player.getUniqueId());
                }
            }

        }
    }

    private void triggerCommands(Player player, LightnessListener.CurrentState state) {
        List<String> commands = state.equals(LightnessListener.CurrentState.DARK) ? DarknessDamage.getInstance().getDataManager().COMMANDS_ENTER : DarknessDamage.getInstance().getDataManager().COMMANDS_LEAVE;
        Iterator var4 = commands.iterator();

        while(var4.hasNext()) {
            String command = (String)var4.next();
            if (!command.trim().isEmpty()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
        }

    }

    public static enum CurrentState {
        LIGHT,
        DARK;
    }
}