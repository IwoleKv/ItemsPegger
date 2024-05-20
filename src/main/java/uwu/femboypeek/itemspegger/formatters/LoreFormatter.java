package uwu.femboypeek.itemspegger.formatters;

import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreFormatter {
    private List<Component> lore;
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public List<Component> getLore() {
        return lore;
    }

    public void saveLore(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                boolean hasHidden = meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);

                if (hasHidden) {
                    this.lore = strippedLore(meta.getLore());
                } else {
                    this.lore = meta.lore();
                }
            }
        }
    }

    private List<Component> strippedLore(List<String> originalLore) {
        List<Component> limitedLore = new ArrayList<>();
        boolean isWithinSection = false;

        for (String line : originalLore) {
            if (line.contains("ᴡɪᴇᴅᴢᴀ")) {
                isWithinSection = true;
                continue;
            }
            if (line.contains("ᴀᴛʀʏʙᴜᴛʏ")) {
                break;
            }
            if (isWithinSection) {
                limitedLore.add(Component.text(line));
            }
        }
        return limitedLore;
    }

    public List<Component> formattedLore(List<Component> enchantmentComp,
                                         List<Component> loreComp, List<Component> attributeComp) {
        List<Component> result = new ArrayList<>();

        if (!enchantmentComp.isEmpty()) {
            result.add(Component.text(""));
            result.add(mm.deserialize(" <#9540FC><i:false><bold>ᴇɴᴄʜᴀɴᴛʏ</bold>"));
            result.add(mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F"));
            result.addAll(enchantmentComp);
        }

        if (loreComp != null && !loreComp.isEmpty()) {
            result.add(Component.text(""));
            result.add(mm.deserialize(" <#FFFF55><i:false><bold>ᴡɪᴇᴅᴢᴀ</bold>"));
            result.add(mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F"));
            for (Component comp : loreComp) {
                Component symbol =
                        mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F<reset> ");
                result.add(symbol.append(comp));
            }
        }

        if (!attributeComp.isEmpty()) {
            result.add(Component.text(""));
            result.add(mm.deserialize(" <#55FF55><i:false><bold>ᴀᴛʀʏʙᴜᴛʏ</bold>"));
            result.add(mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F"));
            result.addAll(attributeComp);
        }

        return result;
    }
}