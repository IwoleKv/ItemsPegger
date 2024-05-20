package uwu.femboypeek.itemspegger.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import uwu.femboypeek.itemspegger.formatters.AttributeFormatter;
import uwu.femboypeek.itemspegger.formatters.EnchantmentFormatter;
import uwu.femboypeek.itemspegger.formatters.LoreFormatter;

import java.util.List;

public class SetLoreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType().isAir()) {
            player.sendMessage("Nie masz itemu");
            return true;
        }

        EnchantmentFormatter enchantmentFormatter = new EnchantmentFormatter();
        LoreFormatter loreFormatter = new LoreFormatter();
        AttributeFormatter attributeFormatter = new AttributeFormatter();

        enchantmentFormatter.saveEnchantments(itemInHand);
        loreFormatter.saveLore(itemInHand);
        attributeFormatter.saveAttributes(itemInHand);

        List<Component> enchantmentComp = enchantmentFormatter.formatEnchantments();
        List<Component> loreComp = loreFormatter.getLore();
        List<Component> attributeComp = attributeFormatter.formatAttributes();

        List<Component> finalLore = loreFormatter.formattedLore(enchantmentComp, loreComp, attributeComp);

        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (itemMeta != null) {
            itemMeta.lore(finalLore);
            itemInHand.setItemMeta(itemMeta);

            enchantmentFormatter.hideEnchantments(itemInHand);
            attributeFormatter.hideAttributes(itemInHand);
        }

        return true;
    }
}