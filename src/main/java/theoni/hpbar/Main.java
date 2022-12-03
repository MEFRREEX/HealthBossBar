package theoni.hpbar;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import theoni.hpbar.listener.*;

public class Main extends PluginBase implements Listener {

    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents((Listener)new EventListener(this), (Main)this);
    }
}
