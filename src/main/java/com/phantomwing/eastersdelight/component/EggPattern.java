package com.phantomwing.eastersdelight.component;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EggPattern implements StringRepresentable {
    STRIPES(0, "stripes"),
    STRIPES_2(1, "stripes_2"),
    STRIPES_3(2, "stripes_3"),
    DIPPED(3, "dipped"),
    SPLIT(4, "split"),
    WAVES(5, "waves"),
    DOTS(6, "dots"),
    PETALS(7, "petals"),
    CREEPER(8, "creeper"),
    HEART(9, "heart"),
    BLOCKS(10, "blocks");

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
