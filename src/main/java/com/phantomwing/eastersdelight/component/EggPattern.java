package com.phantomwing.eastersdelight.component;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EggPattern implements StringRepresentable {
    STRIPES(0, "stripes"),
    HALF_DIPPED(1, "half_dipped"),
    SPLIT(2, "split"),
    HALF_STRIPES(3, "half_stripes"),
    WAVES(4, "waves"),
    DOTS(5, "dots");

    public static final StringRepresentable.StringRepresentableCodec<EggPattern> CODEC = StringRepresentable.fromEnum(EggPattern::values);

    private final int id;
    private final String name;

    EggPattern(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
