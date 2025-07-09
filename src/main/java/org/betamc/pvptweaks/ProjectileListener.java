package org.betamc.pvptweaks;

import net.minecraft.server.EntityPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class ProjectileListener implements Listener {

    @EventHandler(priority = Event.Priority.High, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent ev) {
        if (!(ev instanceof EntityDamageByEntityEvent))
            return;

        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) ev;

        if (!(event.getDamager() instanceof Fish) &&
            !(event.getDamager() instanceof Snowball) &&
            !(event.getDamager() instanceof Egg))
            return;
        if (!(event.getEntity() instanceof Player))
            return;

        Projectile projectile = (Projectile) event.getDamager();
        Player player = (Player) event.getEntity();

        if (!(projectile.getShooter() instanceof Player))
            return;

        doKnockback(projectile, player);
    }

    private void doKnockback(Projectile projectile, Player player) {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        if ((float) nmsPlayer.noDamageTicks > (float) nmsPlayer.maxNoDamageTicks / 2.0f)
            return;

        nmsPlayer.lastDamage = 0;
        nmsPlayer.noDamageTicks = nmsPlayer.maxNoDamageTicks;
        nmsPlayer.hurtTicks = nmsPlayer.ae = 10;
        nmsPlayer.world.a(nmsPlayer, (byte) 2);
        nmsPlayer.velocityChanged = true;

        Vector velProjectile = projectile.getVelocity();
        Vector velPlayer = player.getVelocity();
        Vector velocity = velPlayer.clone().subtract(velProjectile).normalize().multiply(-0.66);
        velocity.setY(Math.min(velPlayer.getY() + 0.52, 0.52));

        player.setVelocity(velocity);
    }

}
