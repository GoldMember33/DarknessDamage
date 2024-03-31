package me.hexedhero.dd.tasks;

import java.util.Iterator;
import me.hexedhero.dd.DarknessDamage;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class DamageChecker implements Runnable {

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

            int multiplier = 1;
            if (DarknessDamage.getInstance().getDataManager().lastDamages.containsKey(player.getUniqueId())) {
                multiplier += DarknessDamage.getInstance().getDataManager().lastDamages.get(player.getUniqueId()) / DarknessDamage.getInstance().getDataManager().DAMAGE;
            }

            if (player.getLocation().getBlockY() >= DarknessDamage.getInstance().getDataManager().DAMAGE_Y_LEVEL_MIN && player.getLocation().getBlockY() <= DarknessDamage.getInstance().getDataManager().DAMAGE_Y_LEVEL_MAX) {
                int damageToDeal = DarknessDamage.getInstance().getDataManager().DAMAGE * multiplier;
                if (player.getHealth() <= (double)damageToDeal && !DarknessDamage.getInstance().getDataManager().DAMAGE_KILLS) {
                    if (player.getHealth() != 1.0D) {
                        player.setHealth(1.0D);
                        player.playEffect(EntityEffect.HURT);
                    }

                    DarknessDamage.getInstance().getDataManager().lastDamages.remove(player.getUniqueId());
                } else {

                    player.damage(damageToDeal);
                    if (DarknessDamage.getInstance().getDataManager().DAMAGE_INCREASES) {
                        DarknessDamage.getInstance().getDataManager().lastDamages.put(player.getUniqueId(), damageToDeal);
                    }
                }
            }
        }
    }
}