package uwu.femboypeek.itemspegger.formatters;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantmentFormatter {
    private static final MiniMessage mm = MiniMessage.miniMessage();
    private Map<Enchantment, Integer> enchantments;

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void saveEnchantments(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasEnchants()) {
                this.enchantments = meta.getEnchants();
            }
        }
    }

    public List<Component> formatEnchantments() {
        List<Component> enchantmentsList = new ArrayList<>();
        if (enchantments != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();
                String enchantName = enchantment.getKey().getKey().replace("_", " ");
                Component enchantmentComp = mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F <#D4D9D8>" + enchantName
                        + "</i:false> <i:false><gradient:#9540FC:#FF00D9>" + level + "</gradient></i:false>");
                enchantmentsList.add(enchantmentComp);
            }
        }
        return enchantmentsList;
    }

    public void hideEnchantments(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasEnchants()) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
    }
}