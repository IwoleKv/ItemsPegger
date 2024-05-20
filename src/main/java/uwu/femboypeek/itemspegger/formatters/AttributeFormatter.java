package uwu.femboypeek.itemspegger.formatters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AttributeFormatter {
    private static final MiniMessage mm = MiniMessage.miniMessage();
    private Map<Attribute, Collection<AttributeModifier>> attributes;

    public Map<Attribute, Collection<AttributeModifier>> getAttributes() {
        return attributes;
    }

    public void saveAttributes(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasAttributeModifiers()) {
                this.attributes = meta.getAttributeModifiers().asMap();
            }
        }
    }

    public List<Component> formatAttributes() {
        List<Component> attributesList = new ArrayList<>();
        if (attributes != null) {
            for (Map.Entry<Attribute, Collection<AttributeModifier>> entry :
                    attributes.entrySet()) {
                Attribute attribute = entry.getKey();
                Collection<AttributeModifier> modifiers = entry.getValue();
                for (AttributeModifier modifier : modifiers) {
                    String attributeName = getAttributeName(attribute);
                    double attributeValue = modifier.getAmount();
                    String operation = getAttributeOperation(modifier);
                    Component attributeComp =
                            mm.deserialize("<i:false><#FFFFFF>\uD873\uDD9F <#D4D9D8>"
                                    + attributeName + "<i:false> <gradient:#44F777:#00D6FF>+"
                                    + attributeValue + operation + "</gradient></i:false>");
                    attributesList.add(attributeComp);
                }
            }
        }
        return attributesList;
    }

    private String getAttributeName(Attribute attribute) {
        switch (attribute) {
            case GENERIC_MAX_HEALTH:
                return "Max Health";
            case GENERIC_FOLLOW_RANGE:
                return "Follow Range";
            case GENERIC_KNOCKBACK_RESISTANCE:
                return "Knockback Resistance";
            case GENERIC_MOVEMENT_SPEED:
                return "Movement Speed";
            case GENERIC_FLYING_SPEED:
                return "Flying Speed";
            case GENERIC_ATTACK_DAMAGE:
                return "Attack Damage";
            case GENERIC_ATTACK_KNOCKBACK:
                return "Attack Knockback";
            case GENERIC_ATTACK_SPEED:
                return "Attack Speed";
            case GENERIC_ARMOR:
                return "Armor";
            case GENERIC_ARMOR_TOUGHNESS:
                return "Armor Toughness";
            case GENERIC_LUCK:
                return "Luck";
            case HORSE_JUMP_STRENGTH:
                return "Horse Jump Strength";
            case ZOMBIE_SPAWN_REINFORCEMENTS:
                return "Zombie Spawn Reinforcements";
            default:
                return attribute.name();
        }
    }

    private String getAttributeOperation(AttributeModifier modifier) {
        switch (modifier.getOperation()) {
            case ADD_NUMBER:
                return "";
            case ADD_SCALAR:
                return "%";
            case MULTIPLY_SCALAR_1:
                return "%";
            default:
                return "";
        }
    }
    public void hideAttributes(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasAttributeModifiers()) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }
    }
}