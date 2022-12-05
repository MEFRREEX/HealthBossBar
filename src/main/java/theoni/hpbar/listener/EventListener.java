package theoni.hpbar.listener;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.DummyBossBar;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar.Builder;
import theoni.hpbar.Main;

import theoni.hpbar.BossBarManager;

public class EventListener implements Listener {

    Main plugin;
    Config config;
    Config mobs;
    private HashMap<UUID, Long> lastHit = new HashMap<UUID, Long>();

    public EventListener(Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.mobs = new Config(new File(plugin.getDataFolder() + "/mobs.yml"), Config.YAML);
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // Mob filter
        if (mobs.getBoolean("enable")) {
            if (mobs.getString("type").equals("blacklist")) {   
                if (mobs.getIntegerList("mobs").contains(event.getEntity().getNetworkId())) return;
            } else if (mobs.getString("type").equals("whitelist")) {
                if (!mobs.getIntegerList("mobs").contains(event.getEntity().getNetworkId())) return;
            }
        }

        // Creating a boss bar
        if (event.getDamager() instanceof Player) {
            Entity entity = event.getEntity();
            Player damager = (Player) event.getDamager();
            Float health = entity.getHealth();
            Float maxhealth = (float) entity.getMaxHealth();

            float length = health * ((health % maxhealth) / 4);
            DummyBossBar bar = new Builder(damager)
                .text(config.getString("bossbar-text")
                    .replace("{name}", entity.getName())
                    .replace("{tag}", entity.getNameTag())
                    .replace("{health}", health.toString())
                    .replace("{maxhealth}", maxhealth.toString()))
                .color(BossBarColor.valueOf(config.getString("color")))
                .length(length)
                .build();
            damager.createBossBar(bar);
            BossBarManager.removeBossBar(damager);
            BossBarManager.addBossBar(damager, bar);
            lastHit.put(damager.getUniqueId(), System.currentTimeMillis());

            // Removal of the boss bar after the time specified in the config.
            (new Timer()).schedule(new TimerTask() {
                public void run() {
                    if (System.currentTimeMillis() - lastHit.get(damager.getUniqueId()) >= config.getInt("bossbar-remove-time") * 1000) {
                        BossBarManager.removeBossBar(damager);
                        lastHit.remove(damager.getUniqueId());
                    }
                }
            }, config.getInt("bossbar-remove-time") * 1000);
        }
    }
}
