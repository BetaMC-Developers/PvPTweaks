package org.betamc.pvptweaks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPTweaks extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ProjectileListener(), this);
        Bukkit.getLogger().info("[PvPTweaks] Version " + getDescription().getVersion() + " enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[PvPTweaks] Version " + getDescription().getVersion() + " disabled.");
    }

}
