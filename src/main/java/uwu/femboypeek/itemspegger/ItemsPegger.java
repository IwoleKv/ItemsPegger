package uwu.femboypeek.itemspegger;

import org.bukkit.plugin.java.JavaPlugin;
import uwu.femboypeek.itemspegger.commands.SetLoreCommand;

public final class ItemsPegger extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    getCommand("setlore").setExecutor(new SetLoreCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
