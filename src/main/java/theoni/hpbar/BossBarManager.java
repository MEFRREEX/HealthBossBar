package theoni.hpbar;

import cn.nukkit.Player;
import cn.nukkit.utils.DummyBossBar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBarManager {
    
    private static Map<UUID, Long> bossBars = new HashMap<>();

    public static void createOrUpdateBar(Player player, DummyBossBar bossBar) {
        if (hasBar(player)) {
            updateBar(player, bossBar);
        } else {
            createBar(player, bossBar);
        }
    }

    public static void createBar(Player player, DummyBossBar bossBar) {
        bossBars.put(player.getUniqueId(), bossBar.getBossBarId());
        player.createBossBar(bossBar);
    }


    public static void updateBar(Player player, DummyBossBar bossBar) {
        if (hasBar(player)) {
            removeBar(player);
            createBar(player, bossBar);
        }
    }



    public static void removeBar(Player player) {
        if (hasBar(player)) {
            DummyBossBar bossBar = getBar(player);
            bossBar.destroy();
            bossBars.remove(player.getUniqueId());
        }
    }

    public static void removeBar(Player player, DummyBossBar bossBar) {
        if (hasBar(player)) {
            bossBar.destroy();
            bossBars.remove(player.getUniqueId());
        }
    }


    public static boolean hasBar(Player player) {
        return bossBars.containsKey(player.getUniqueId());
    }

    public static DummyBossBar getBar(Player player) {
        Long id = bossBars.get(player.getUniqueId());
        return player.getDummyBossBar(id);
    }

}