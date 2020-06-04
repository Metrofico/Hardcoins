package me.metrofico.hardcoins.listener;


import me.metrofico.hardcoins.Jecon;
import me.metrofico.hardcoins.config.ConfigStruct;
import me.metrofico.hardcoins.db.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Login implements Listener {

    private final Database db;
    private final ConfigStruct config;

    public Login(Jecon jecon) {
        db = jecon.getDb();
        config = jecon.getConfigStruct();

        jecon.getServer().getPluginManager().registerEvents(this, jecon);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (config.isCreateAccountOnJoin()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    db.createPlayerAccount(player);
                }
            }.runTaskAsynchronously(Jecon.getPlugin(Jecon.class));
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    db.createPlayerAccount(player, 0);
                }
            }.runTaskAsynchronously(Jecon.getPlugin(Jecon.class));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent event) {
        try {
            db.removeCacheAccount(event.getPlayer().getName());
        } catch (Exception e) {

        }
    }
}
