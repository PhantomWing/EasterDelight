package com.phantomwing.eastersdelight.component;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EggPattern implements StringRepresentable {
    STRIPES(0, "stripes"),
    STRIPES_2(1, "stripes_2"),
    DIPPED(2, "dipped"),
    SPLIT(3, "split"),
    WAVES(4, "waves"),
    DOTS(5, "dots"),
    PETALS(6, "petals"),
    CREEPER(7, "creeper"),
    HEART(8, "heart"),
    BLOCKS(9, "blocks");

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
