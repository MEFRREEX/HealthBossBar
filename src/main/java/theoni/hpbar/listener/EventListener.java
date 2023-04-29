package theoni.hpbar.listener;

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


import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class EventListener implements Listener {

    private Config config;
    private Config mobs;
    private HashMap<Player, Long> lastHit = new HashMap<Player, Long>();

    public EventListener(Main main) {
        this.config = main.getConfig();
        this.mobs = new Config(main.getDataFolder() + "/mobs.yml", Config.YAML);
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        // Mob filter
        if (mobs.getBoolean("enable")) {
            if (mobs.getString("type").equals("blacklist")) {   
                if (mobs.getIntegerList("mobs").contains(event.getEntity().getNetworkId())) {
                    return;
                }
            } else if (mobs.getString("type").equals("whitelist")) {
                if (!mobs.getIntegerList("mobs").contains(event.getEntity().getNetworkId())) {
                    return;
                }
            }
        }

        // Creating a boss bar
        if (event.getDamager() instanceof Player) {

            Entity entity = event.getEntity();
            Player damager = (Player) event.getDamager();
            
            Float health = entity.getHealth();
            Float maxhealth = (float) entity.getMaxHealth();

            int length = (int) Main.toPercentage(health - event.getFinalDamage(), 0, maxhealth);

            DummyBossBar bar = new Builder(damager)
                .text(config.getString("bossbar-text")
                    .replace("{name}", entity.getName())
                    .replace("{tag}", entity.getNameTag())
                    .replace("{health}", health.toString())
                    .replace("{maxhealth}", maxhealth.toString()))
                .color(BossBarColor.valueOf(config.getString("color").toUpperCase()))
                .length(length >= 0 ? length : 0)
                .build();

            BossBarManager.createOrUpdateBar(damager, bar);;
            lastHit.put(damager, System.currentTimeMillis());

            // Removal of the boss bar after the time specified in the config.
            new Timer().schedule(new TimerTask() {

                public void run() {
                    if (System.currentTimeMillis() - lastHit.get(damager) >= config.getInt("bossbar-remove-time") * 1000) {
                        BossBarManager.removeBar(damager);
                        lastHit.remove(damager);
                    }
                }

            }, config.getInt("bossbar-remove-time") * 1000);
        }
    }
}
