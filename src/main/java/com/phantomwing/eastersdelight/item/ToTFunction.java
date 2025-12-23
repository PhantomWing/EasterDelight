package com.phantomwing.eastersdelight.item;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ToTFunction<T> {
    T byName(String value, @Nullable T fallback);
}