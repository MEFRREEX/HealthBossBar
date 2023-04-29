package theoni.hpbar;

import cn.nukkit.plugin.PluginBase;
import theoni.hpbar.listener.EventListener;
import theoni.hpbar.commands.HealthBossBarCommand;

public class Main extends PluginBase {

    public void onEnable() {
        this.saveDefaultConfig();
        this.saveResource("mobs.yml");
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        this.getServer().getCommandMap().register("Health Boss Bar", new HealthBossBarCommand(this));
    }

    public static double toPercentage(double value, double minValue, double maxValue) {
        return (value - minValue) / (maxValue - minValue) * 100;
    }
}
