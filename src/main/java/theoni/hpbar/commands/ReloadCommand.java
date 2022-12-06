package theoni.hpbar.commands;

import java.io.File;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.Config;
import theoni.hpbar.Main;

public class ReloadCommand extends Command {

    Main plugin;
    Config config;
    Config mobs;

    public ReloadCommand(Main plugin) {
        super("hpbar", "Health boss bar");
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.mobs = new Config(new File(plugin.getDataFolder() + "/mobs.yml"), Config.YAML);

        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newEnum("action", new CommandEnum("Action", "help", "reload", "info", "about"))
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", "/hpbar help"));
            return false;
        }
        switch (args[0]) {
            case "reload":
                if (sender.hasPermission("healthbossbar.reload")) {
                    sender.sendMessage("§eReloading config.yml and mobs.yml...");
                    config.reload();
                    mobs.reload();
                    sender.sendMessage("§eReload complete.");
                } else {
                    sender.sendMessage("§cYou do not have permission for this action");
                }
                break;
            case "about":
            case "info":
                if (sender.hasPermission("healthbossbar.about")) {
                    sender.sendMessage("§eThis plugin was written for free distribution and can be downloaded at §7https://cloudburstmc.org/resources/health-boss-bar.861/.\n§fDeveloper: MEFRREEXX");
                } else {
                    sender.sendMessage("§cYou do not have permission for this action");
                }
                break;
            case "help":
                sender.sendMessage("§fHealth boss bar commands:\n§f/hpbar reload §8- §7Reload all configs\n§f/hpbar about §8- §7About the plugin");
                break;
        }
        return false;
    }
}
