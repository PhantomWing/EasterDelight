package com.phantomwing.eastersdelight.util;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class ItemUtils {
    public static Identifier getIdentifier(ItemLike item) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }

    public static String getName(ItemLike item) {
        return getIdentifier(item).getPath();
    }

    public static String getNameWithPrefix(ItemLike item, String prefix) {
        return (prefix != null && !prefix.isEmpty() ? (prefix + "_") : "") + getName(item);
    }

    public static String getNameWithSuffix(ItemLike item, String suffix) {
        return getName(item) + (suffix != null && !suffix.isEmpty() ? ("_" + suffix) : "");
    }

    public static String getNamespace(ItemLike item) {
        return getIdentifier(item).getNamespace();
    }

    public static String getNameWithNamespace(ItemLike item) {
        Identifier rl = getIdentifier(item);
        return rl.getNamespace() + ":" + rl.getPath();
    }

    public static Identifier getScopedIdentifier(ItemLike item, String scope) {
        String namespace = getNamespace(item);
        return Identifier.fromNamespaceAndPath(namespace, scope + "/" + getName(item));
    }

    public static Identifier getScopedIdentifierWithPrefix(ItemLike item, String scope, String prefix) {
        String namespace = getNamespace(item);
        return Identifier.fromNamespaceAndPath(namespace, scope + "/" + getNameWithPrefix(item, prefix));
    }

    public static Identifier getScopedIdentifierWithSuffix(ItemLike item, String scope, String suffix) {
        String namespace = getNamespace(item);
        return Identifier.fromNamespaceAndPath(namespace, scope + "/" + getNameWithSuffix(item, suffix));
    }

    public static Identifier getItemIdentifier(ItemLike item) {
        return getScopedIdentifierWithSuffix(item, "item", "");
    }

    public static Identifier getItemIdentifier(String name) {
        return Identifier.fromNamespaceAndPath(EastersDelight.MOD_ID, "item/" + name);
    }

    public static Identifier getItemIdentifierWithPrefix(ItemLike item, String prefix) {
        return getScopedIdentifierWithPrefix(item, "item", prefix);
    }

    public static Identifier getItemIdentifierWithSuffix(ItemLike item, String suffix) {
        return getScopedIdentifierWithSuffix(item, "item", suffix);
    }
}
