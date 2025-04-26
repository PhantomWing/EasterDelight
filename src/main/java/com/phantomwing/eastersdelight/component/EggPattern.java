package com.phantomwing.eastersdelight.component;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

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

    private static final IntFunction<EggPattern> BY_ID = ByIdMap.continuous(EggPattern::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.StringRepresentableCodec<EggPattern> CODEC = StringRepresentable.fromEnum(EggPattern::values);
    public static final StreamCodec<ByteBuf, EggPattern> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, EggPattern::getId);

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
