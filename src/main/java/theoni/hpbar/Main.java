package theoni.hpbar;

import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import theoni.hpbar.listener.*;
import theoni.hpbar.commands.*;

public class Main extends PluginBase implements Listener {

    public void onEnable() {
        this.saveDefaultConfig();
        this.saveResource("mobs.yml");
        this.getServer().getPluginManager().registerEvents((Listener)new EventListener(this), (Main)this);
        this.getServer().getCommandMap().register("help", (Command) new ReloadCommand(this));
    }
}
