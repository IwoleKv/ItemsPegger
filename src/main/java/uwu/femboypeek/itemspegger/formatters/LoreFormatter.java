package uwu.femboypeek.itemspegger.formatters;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
                    this.lore = strippedLore(meta.lore());
                } else {
                    this.lore = meta.lore();
                }
            }
        }
    }

    private List<Component> strippedLore(List<Component> originalLore) {
        List<Component> limitedLore = new ArrayList<>();
        boolean isWithinSection = false;

        for (Component component : originalLore) {
            String line = mm.serialize(component);

            if (line.contains("ᴡɪᴇᴅᴢᴀ")) {
                isWithinSection = true;
                continue;
            }
            if (line.contains("ᴀᴛʀʏʙᴜᴛʏ")) {
                break;
            }
            if (isWithinSection) {
                limitedLore.add(component);
            }
        }
        return limitedLore;
    }

    public List<Component> formattedLore(List<Component> enchantmentComp, List<Component> loreComp, List<Component> attributeComp, boolean strip) {
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
            if (!strip) {
                result.add(mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F"));
                for (Component comp : loreComp) {
                    Component symbol = mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F<reset> ");
                    result.add(symbol.append(comp));
                }
            } else {
                result.addAll(loreComp);
            }
        }

        if (!attributeComp.isEmpty()) {
            if (!strip) {
                result.add(Component.text(""));
            }
            result.add(mm.deserialize(" <#55FF55><i:false><bold>ᴀᴛʀʏʙᴜᴛʏ</bold>"));
            result.add(mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F"));
            result.addAll(attributeComp);
        }

        return result;
    }
}