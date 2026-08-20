package net.got.mixin.client;

import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.util.BooleanConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes the private {@code BooleanConsumer} callback stored on {@link ConfirmScreen}
 * so we can programmatically "click" Yes/No without needing to inject any logic into
 * vanilla code. This is an accessor-only mixin (no method bodies are touched), which
 * makes it far less likely to break across Minecraft versions than hooking the actual
 * world-creation gating logic that produced this screen in the first place.
 *
 * NOTE: the field name "callback" below is inferred from typical Mojang mapping
 * conventions for this class and hasn't been checked against your 1.21.11 mappings
 * jar. If the mixin fails to apply (Mixin will throw at launch, not at compile time,
 * since @Accessor targets are resolved by name/type at runtime), open ConfirmScreen's
 * constructor in your IDE, find the BooleanConsumer field it assigns to, and swap the
 * string below to match.
 */
@OnlyIn(Dist.CLIENT)
@Mixin(ConfirmScreen.class)
public interface ConfirmScreenAccessor {

    @Accessor("callback")
    BooleanConsumer got$getCallback();
}
