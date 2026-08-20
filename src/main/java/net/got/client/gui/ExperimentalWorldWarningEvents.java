package net.got.client.gui;

import net.got.GotMod;
import net.got.mixin.client.ConfirmScreenAccessor;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

/**
 * Auto-confirms the vanilla "Warning! These settings are using experimental
 * features" dialog that Minecraft shows whenever the selected world preset
 * isn't one of its hardcoded WorldPresets.NORMAL/FLAT/... constants - which is
 * unavoidable for GoT's custom "got:knownworld" preset (see WorldPresetsMixin
 * and CreateWorldScreenMixin), even though the preset's actual generator
 * settings are otherwise vanilla-safe.
 *
 * We deliberately match on the dialog's title text rather than blanket-
 * accepting every ConfirmScreen, so we don't accidentally auto-confirm
 * unrelated prompts (e.g. "Delete World?") that also happen to use
 * ConfirmScreen.
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = GotMod.MODID)
public final class ExperimentalWorldWarningEvents {

    private static final String EXPERIMENTAL_TITLE_KEY =
            "selectWorld.import_worldgen_settings.experimental.title";

    @SubscribeEvent
    public static void onScreenOpening(ScreenEvent.Opening event) {
        if (!(event.getNewScreen() instanceof ConfirmScreen confirm)) {
            return;
        }

        String expectedTitle = Component.translatable(EXPERIMENTAL_TITLE_KEY).getString();
        if (!confirm.getTitle().getString().equals(expectedTitle)) {
            return;
        }

        ((ConfirmScreenAccessor) confirm).got$getCallback().accept(true);
        event.setNewScreen(null);
    }
}
