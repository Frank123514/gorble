package net.minecraft.util.datafix.fixes;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class LockComponentPredicateFix extends ItemStackComponentRemainderFix {
    public static final Escaper ESCAPER = Escapers.builder().addEscape('"', "\\\"").addEscape('\\', "\\\\").build();

    public LockComponentPredicateFix(Schema outputSchema) {
        super(outputSchema, "LockComponentPredicateFix", "minecraft:lock");
    }

    @Override
    protected <T> Dynamic<T> fixComponent(Dynamic<T> tag) {
        return fixLock(tag);
    }

    public static <T> Dynamic<T> fixLock(Dynamic<T> tag) {
        Optional<String> optional = tag.asString().result();
        if (optional.isPresent()) {
            Dynamic<T> dynamic = tag.createString("\"" + ESCAPER.escape(optional.get()) + "\"");
            Dynamic<T> dynamic1 = tag.emptyMap().set("minecraft:custom_name", dynamic);
            return tag.emptyMap().set("components", dynamic1);
        } else {
            return tag.emptyMap();
        }
    }
}
