package net.got.client.gui;

import net.got.network.CompleteIntroPayload;
import net.got.network.SetCharacterNamePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

/**
 * Black-screen "waking up" character-creation intro.
 *
 * MODE FULL (a brand new player's first login): waking line -> who-are-you
 * line -> name entry box -> hands off into FactionSelectionScreen.
 *
 * MODE FINAL_ONLY (opened by FactionSelectionScreen right after confirming a
 * house, or on relogin if the player picked a faction but never finished):
 * just the closing "Very well." line, then marks character creation done.
 * The player is already standing in the knownworld the whole time — this
 * only ever runs on a save generated with the knownworld preset — so there's
 * no teleport, just a black screen over the world they're already in.
 *
 * No cinematic, no camera lock, no buttons — "Proceed" is just a fading
 * reminder line, same font as everything else, and a click anywhere on the
 * screen (outside the name box, while it's up) advances to the next line.
 */
public final class IntroScreen extends Screen {

    public enum Mode { FULL, FINAL_ONLY }

    private static final long BLINK_DURATION      = 1400;
    private static final long LINE_FADE_MS        = 500;
    private static final long PROCEED_DELAY_MS    = 900;

    private static final String WAKING_LINE       = "...oh, you're awake...";
    private static final String WHO_ARE_YOU_LINE   = "So tell me, stranger, who are you?";
    private static final String FINAL_LINE        = "Very well.";
    private static final String PROCEED_LINE      = "Proceed";

    private static final int COL_TEXT    = 0xFFFFFF;
    private static final int COL_PROCEED = 0xAAAAAA;

    private final Mode mode;
    private int stepIndex = 0;

    private long stepStartMillis;
    private EditBox nameBox;

    private boolean proceedVisible = false;

    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    public IntroScreen(Mode mode) {
        super(Component.literal("..."));
        this.mode = mode;
    }

    @Override
    protected void init() {
        super.init();
        stepStartMillis = System.currentTimeMillis();
        lastLayoutWidth = width;
        lastLayoutHeight = height;
        buildForStep();
    }

    private void buildForStep() {
        clearWidgets();
        nameBox = null;

        if (mode == Mode.FULL && stepIndex == 2) {
            nameBox = new EditBox(font, width / 2 - 100, height / 2 + 14, 200, 20,
                    Component.literal("Name"));
            nameBox.setMaxLength(24);
            addRenderableWidget(nameBox);
            setInitialFocus(nameBox);
        }
    }

    private String currentLine() {
        if (mode == Mode.FINAL_ONLY) return FINAL_LINE;
        return switch (stepIndex) {
            case 0 -> WAKING_LINE;
            default -> WHO_ARE_YOU_LINE;
        };
    }

    private boolean isBlinkStep() {
        return mode == Mode.FULL && stepIndex == 0;
    }

    private boolean isNameStep() {
        return mode == Mode.FULL && stepIndex == 2;
    }

    private boolean canProceed() {
        if (!proceedVisible) return false;
        if (isNameStep()) return nameBox != null && !nameBox.getValue().trim().isEmpty();
        return true;
    }

    private void advance() {
        if (mode == Mode.FINAL_ONLY) {
            ClientPacketDistributor.sendToServer(new CompleteIntroPayload());
            onClose();
            return;
        }

        if (stepIndex == 2) {
            String name = nameBox.getValue().trim();
            if (name.isEmpty()) return;
            ClientPacketDistributor.sendToServer(new SetCharacterNamePayload(name));
            Minecraft.getInstance().setScreen(new FactionSelectionScreen());
            return;
        }

        stepIndex++;
        stepStartMillis = System.currentTimeMillis();
        buildForStep();
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
        if (nameBox != null && nameBox.isMouseOver(event.x(), event.y())) {
            return super.mouseClicked(event, doubleClick);
        }
        if (canProceed()) {
            advance();
            return true;
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (width != lastLayoutWidth || height != lastLayoutHeight) {
            buildForStep();
            lastLayoutWidth = width;
            lastLayoutHeight = height;
        }

        long elapsed = System.currentTimeMillis() - stepStartMillis;
        int screenW = width;
        int screenH = height;

        // background: flicker on the very first step, solid black otherwise
        int bg;
        if (isBlinkStep() && elapsed < BLINK_DURATION) {
            float t = (float) elapsed / BLINK_DURATION;
            float flutter = (float) Math.abs(Math.sin(t * 3.0 * Math.PI)) * (1f - t);
            int g = clampByte((int) (flutter * 40f));
            bg = 0xFF000000 | (g << 16) | (g << 8) | g;
        } else {
            bg = 0xFF000000;
        }
        gfx.fill(0, 0, screenW, screenH, bg);

        long textDelay = isBlinkStep() ? BLINK_DURATION : 0;
        if (elapsed < textDelay) {
            proceedVisible = false;
            return;
        }

        float lineAlpha = clamp01((float) (elapsed - textDelay) / LINE_FADE_MS);
        int textColor = (clampByte((int) (lineAlpha * 255f)) << 24) | COL_TEXT;
        String line = currentLine();
        gfx.drawCenteredString(font, line, screenW / 2, screenH / 2 - 10, textColor);

        proceedVisible = (elapsed - textDelay) >= PROCEED_DELAY_MS;

        // widgets (just the name box, when present) render after our custom text
        super.render(gfx, mouseX, mouseY, partialTick);

        if (proceedVisible && (nameBox == null || !nameBox.getValue().trim().isEmpty())) {
            long pulseElapsed = elapsed - textDelay - PROCEED_DELAY_MS;
            float pulse = 0.5f + 0.5f * (float) Math.abs(Math.sin(pulseElapsed / 500.0));
            int proceedColor = (clampByte((int) (pulse * 255f)) << 24) | COL_PROCEED;
            gfx.drawCenteredString(font, PROCEED_LINE, screenW / 2, screenH - 40, proceedColor);
        }
    }

    @Override public boolean shouldCloseOnEsc() { return false; }
    @Override public boolean isPauseScreen()    { return true;  }

    @Override
    public void renderBackground(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {}

    private static int clampByte(int v) { return Math.max(0, Math.min(255, v)); }
    private static float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }
}
