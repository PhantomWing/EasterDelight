package com.phantomwing.eastersdelight.util;

import com.phantomwing.eastersdelight.EastersDelight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class ItemUtils {
    public static ResourceLocation getResourceLocation(ItemLike item) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }

    public static String getName(ItemLike item) {
        return getResourceLocation(item).getPath();
    }

    public static String getNameWithPrefix(ItemLike item, String prefix) {
        return (prefix != null && !prefix.isEmpty() ? (prefix + "_") : "") + getName(item);
    }

    public static String getNameWithSuffix(ItemLike item, String suffix) {
        return getName(item) + (suffix != null && !suffix.isEmpty() ? ("_" + suffix) : "");
    }

    public static String getNamespace(ItemLike item) {
        return getResourceLocation(item).getNamespace();
    }

    public static String getNameWithNamespace(ItemLike item) {
        ResourceLocation rl = getResourceLocation(item);
        return rl.getNamespace() + ":" + rl.getPath();
    }

    public static ResourceLocation getScopedResourceLocation(ItemLike item, String scope) {
        String namespace = getNamespace(item);
        return ResourceLocation.fromNamespaceAndPath(namespace, scope + "/" + getName(item));
    }

    public static ResourceLocation getScopedResourceLocationWithPrefix(ItemLike item, String scope, String prefix) {
        String namespace = getNamespace(item);
        return ResourceLocation.fromNamespaceAndPath(namespace, scope + "/" + getNameWithPrefix(item, prefix));
    }

    public static ResourceLocation getScopedResourceLocationWithSuffix(ItemLike item, String scope, String suffix) {
        String namespace = getNamespace(item);
        return ResourceLocation.fromNamespaceAndPath(namespace, scope + "/" + getNameWithSuffix(item, suffix));
    }

    public static ResourceLocation getItemResourceLocation(ItemLike item) {
        return getScopedResourceLocationWithSuffix(item, "item", "");
    }

    public static ResourceLocation getItemResourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(EastersDelight.MOD_ID, "item/" + name);
    }

    public static ResourceLocation getItemResourceLocationWithPrefix(ItemLike item, String prefix) {
        return getScopedResourceLocationWithPrefix(item, "item", prefix);
    }

    public static ResourceLocation getItemResourceLocationWithSuffix(ItemLike item, String suffix) {
        return getScopedResourceLocationWithSuffix(item, "item", suffix);
    }
}
