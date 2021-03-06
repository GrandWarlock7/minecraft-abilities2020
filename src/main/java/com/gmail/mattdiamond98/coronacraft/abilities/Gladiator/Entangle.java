package com.gmail.mattdiamond98.coronacraft.abilities.Gladiator;

import com.gmail.mattdiamond98.coronacraft.abilities.AbilityStyle;
import com.gmail.mattdiamond98.coronacraft.CoronaCraft;
import com.gmail.mattdiamond98.coronacraft.abilities.Summoner.CustomEntity;
import com.gmail.mattdiamond98.coronacraft.util.AbilityUtil;
import com.tommytony.war.Team;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static com.gmail.mattdiamond98.coronacraft.util.AbilityUtil.notInSpawn;

public class Entangle extends AbilityStyle {

    public Entangle() {
        super("Entangle", new String[] {
                "Bind your opponent, preventing",
                "them from moving."
        }, 0);
    }

    /***
     * @param p the player using the ability
     * @param args arg[0] instanceof FishHook
     * @return the cooldown
     */
    public int execute(Player p, Object... args) {
        FishHook hook = (FishHook) args[0];
        List<Player> enemies = AbilityUtil.getEnemies(p);
        for (Player enemy : enemies) {
            if (enemy.getLocation().distanceSquared(hook.getLocation()) < 2.5 * 2.5 && notInSpawn(enemy)) {
                new PotionEffect(PotionEffectType.SLOW, 8 * 20, 3).apply(enemy);
                enemy.getLocation().getWorld().playEffect(enemy.getEyeLocation(), Effect.STEP_SOUND, Material.COBWEB);
                enemy.sendMessage(ChatColor.RED + "You have been entangled.");
            }
        }
        for(Entity e:hook.getNearbyEntities(2.5, 2.5, 2.5)){
            if(e instanceof CustomEntity&& Team.getNonPlayerEntityTeam(e)!=Team.getTeamByPlayerName(p.getName())){
                new PotionEffect(PotionEffectType.SLOW, 8 * 20, 3).apply((LivingEntity) e);
                e.getLocation().getWorld().playEffect(((LivingEntity) e).getEyeLocation(), Effect.STEP_SOUND, Material.COBWEB);

            }
        }
        return 7 * CoronaCraft.ABILITY_TICK_PER_SECOND;
    }
}
