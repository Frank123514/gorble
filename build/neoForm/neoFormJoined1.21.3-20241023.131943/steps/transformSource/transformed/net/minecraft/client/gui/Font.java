package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringDecomposer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class Font implements net.neoforged.neoforge.client.extensions.IFontExtension {
    private static final float EFFECT_DEPTH = 0.01F;
    private static final Vector3f SHADOW_OFFSET = new Vector3f(0.0F, 0.0F, 0.03F);
    public static final int ALPHA_CUTOFF = 8;
    public final int lineHeight = 9;
    public final RandomSource random = RandomSource.create();
    private final Function<ResourceLocation, FontSet> fonts;
    final boolean filterFishyGlyphs;
    private final StringSplitter splitter;
    /** Neo: enables linear filtering on text */
    public boolean enableTextTextureLinearFiltering = false;

    public Font(Function<ResourceLocation, FontSet> fonts, boolean filterFishyGlyphs) {
        this.fonts = fonts;
        this.filterFishyGlyphs = filterFishyGlyphs;
        this.splitter = new StringSplitter(
            (p_92722_, p_92723_) -> this.getFontSet(p_92723_.getFont()).getGlyphInfo(p_92722_, this.filterFishyGlyphs).getAdvance(p_92723_.isBold())
        );
    }

    FontSet getFontSet(ResourceLocation fontLocation) {
        return this.fonts.apply(fontLocation);
    }

    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    public String bidirectionalShaping(String text) {
        try {
            Bidi bidi = new Bidi(new ArabicShaping(8).shape(text), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException arabicshapingexception) {
            return text;
        }
    }

    public int drawInBatch(
        String text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords
    ) {
        if (this.isBidirectional()) {
            text = this.bidirectionalShaping(text);
        }

        return this.drawInternal(text, x, y, color, dropShadow, pose, bufferSource, displayMode, backgroundColor, packedLightCoords, true);
    }

    public int drawInBatch(
        Component text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords
    ) {
        return this.drawInBatch(text, x, y, color, dropShadow, pose, bufferSource, displayMode, backgroundColor, packedLightCoords, true);
    }

    public int drawInBatch(
        Component text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords,
        boolean inverseDepth
    ) {
        return this.drawInternal(
            text.getVisualOrderText(), x, y, color, dropShadow, pose, bufferSource, displayMode, backgroundColor, packedLightCoords, inverseDepth
        );
    }

    public int drawInBatch(
        FormattedCharSequence text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords
    ) {
        return this.drawInternal(text, x, y, color, dropShadow, pose, bufferSource, displayMode, backgroundColor, packedLightCoords, true);
    }

    public void drawInBatch8xOutline(
        FormattedCharSequence text,
        float x,
        float y,
        int color,
        int backgroundColor,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        int packedLightCoords
    ) {
        int i = adjustColor(backgroundColor);
        Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(
            bufferSource, 0.0F, 0.0F, i, false, pose, Font.DisplayMode.NORMAL, packedLightCoords
        );

        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j != 0 || k != 0) {
                    float[] afloat = new float[]{x};
                    int l = j;
                    int i1 = k;
                    text.accept((p_168661_, p_168662_, p_168663_) -> {
                        boolean flag = p_168662_.isBold();
                        FontSet fontset = this.getFontSet(p_168662_.getFont());
                        GlyphInfo glyphinfo = fontset.getGlyphInfo(p_168663_, this.filterFishyGlyphs);
                        font$stringrenderoutput.x = afloat[0] + (float)l * glyphinfo.getShadowOffset();
                        font$stringrenderoutput.y = y + (float)i1 * glyphinfo.getShadowOffset();
                        afloat[0] += glyphinfo.getAdvance(flag);
                        return font$stringrenderoutput.accept(p_168661_, p_168662_.withColor(i), p_168663_);
                    });
                }
            }
        }

        font$stringrenderoutput.renderCharacters();
        Font.StringRenderOutput font$stringrenderoutput1 = new Font.StringRenderOutput(
            bufferSource, x, y, adjustColor(color), false, pose, Font.DisplayMode.POLYGON_OFFSET, packedLightCoords
        );
        text.accept(font$stringrenderoutput1);
        font$stringrenderoutput1.finish(x);
    }

    private static int adjustColor(int color) {
        return (color & -67108864) == 0 ? ARGB.opaque(color) : color;
    }

    private int drawInternal(
        String text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords,
        boolean inverseDepth
    ) {
        color = adjustColor(color);
        Matrix4f matrix4f = new Matrix4f(pose);
        if (dropShadow) {
            this.renderText(text, x, y, color, true, pose, bufferSource, displayMode, backgroundColor, packedLightCoords, inverseDepth);
            matrix4f.translate(SHADOW_OFFSET);
        }

        x = this.renderText(text, x, y, color, false, matrix4f, bufferSource, displayMode, backgroundColor, packedLightCoords, inverseDepth);
        return (int)x + (dropShadow ? 1 : 0);
    }

    private int drawInternal(
        FormattedCharSequence text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords,
        boolean inverseDepth
    ) {
        color = adjustColor(color);
        Matrix4f matrix4f = new Matrix4f(pose);
        if (dropShadow) {
            this.renderText(text, x, y, color, true, pose, bufferSource, displayMode, backgroundColor, packedLightCoords, inverseDepth);
            matrix4f.translate(SHADOW_OFFSET);
        }

        x = this.renderText(text, x, y, color, false, matrix4f, bufferSource, displayMode, backgroundColor, packedLightCoords, inverseDepth);
        return (int)x + (dropShadow ? 1 : 0);
    }

    private float renderText(
        String text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords,
        boolean inverseDepth
    ) {
        Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(
            bufferSource, x, y, color, backgroundColor, dropShadow, pose, displayMode, packedLightCoords, inverseDepth
        );
        StringDecomposer.iterateFormatted(text, Style.EMPTY, font$stringrenderoutput);
        return font$stringrenderoutput.finish(x);
    }

    private float renderText(
        FormattedCharSequence text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        MultiBufferSource bufferSource,
        Font.DisplayMode displayMode,
        int backgroundColor,
        int packedLightCoords,
        boolean inverseDepth
    ) {
        Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(
            bufferSource, x, y, color, backgroundColor, dropShadow, pose, displayMode, packedLightCoords, inverseDepth
        );
        text.accept(font$stringrenderoutput);
        return font$stringrenderoutput.finish(x);
    }

    /**
     * Returns the width of this string. Equivalent of FontMetrics.stringWidth(String s).
     */
    public int width(String text) {
        return Mth.ceil(this.splitter.stringWidth(text));
    }

    public int width(FormattedText text) {
        return Mth.ceil(this.splitter.stringWidth(text));
    }

    public int width(FormattedCharSequence text) {
        return Mth.ceil(this.splitter.stringWidth(text));
    }

    public String plainSubstrByWidth(String text, int maxWidth, boolean tail) {
        return tail ? this.splitter.plainTailByWidth(text, maxWidth, Style.EMPTY) : this.splitter.plainHeadByWidth(text, maxWidth, Style.EMPTY);
    }

    public String plainSubstrByWidth(String text, int maxWidth) {
        return this.splitter.plainHeadByWidth(text, maxWidth, Style.EMPTY);
    }

    public FormattedText substrByWidth(FormattedText text, int maxWidth) {
        return this.splitter.headByWidth(text, maxWidth, Style.EMPTY);
    }

    /**
     * Returns the height (in pixels) of the given string if it is wordwrapped to the given max width.
     */
    public int wordWrapHeight(String text, int maxWidth) {
        return 9 * this.splitter.splitLines(text, maxWidth, Style.EMPTY).size();
    }

    public int wordWrapHeight(FormattedText text, int maxWidth) {
        return 9 * this.splitter.splitLines(text, maxWidth, Style.EMPTY).size();
    }

    public List<FormattedCharSequence> split(FormattedText text, int maxWidth) {
        return Language.getInstance().getVisualOrder(this.splitter.splitLines(text, maxWidth, Style.EMPTY));
    }

    public boolean isBidirectional() {
        return Language.getInstance().isDefaultRightToLeft();
    }

    public StringSplitter getSplitter() {
        return this.splitter;
    }

    @Override public Font self() { return this; }

    @OnlyIn(Dist.CLIENT)
    public static enum DisplayMode {
        NORMAL,
        SEE_THROUGH,
        POLYGON_OFFSET;
    }

    @OnlyIn(Dist.CLIENT)
    class StringRenderOutput implements FormattedCharSink {
        final MultiBufferSource bufferSource;
        private final boolean dropShadow;
        private final float dimFactor;
        private final int color;
        private final int backgroundColor;
        private final Matrix4f pose;
        private final Font.DisplayMode mode;
        private final int packedLightCoords;
        private final boolean inverseDepth;
        float x;
        float y;
        private final List<BakedGlyph.GlyphInstance> glyphInstances;
        @Nullable
        private List<BakedGlyph.Effect> effects;

        private void addEffect(BakedGlyph.Effect effect) {
            if (this.effects == null) {
                this.effects = Lists.newArrayList();
            }

            this.effects.add(effect);
        }

        public StringRenderOutput(
            MultiBufferSource bufferSource,
            float x,
            float y,
            int color,
            boolean dropShadow,
            Matrix4f pose,
            Font.DisplayMode mode,
            int packedLightCoords
        ) {
            this(bufferSource, x, y, color, 0, dropShadow, pose, mode, packedLightCoords, true);
        }

        public StringRenderOutput(
            MultiBufferSource buferSource,
            float x,
            float y,
            int color,
            int backgroundColor,
            boolean dropShadow,
            Matrix4f pose,
            Font.DisplayMode displayMode,
            int packedLightCoords,
            boolean inverseDepth
        ) {
            this.glyphInstances = new ArrayList<>();
            this.bufferSource = buferSource;
            this.x = x;
            this.y = y;
            this.dropShadow = dropShadow;
            this.dimFactor = dropShadow ? 0.25F : 1.0F;
            this.color = ARGB.scaleRGB(color, this.dimFactor);
            this.backgroundColor = backgroundColor;
            this.pose = pose;
            this.mode = displayMode;
            this.packedLightCoords = packedLightCoords;
            this.inverseDepth = inverseDepth;
        }

        /**
         * Accepts a single code point from a {@link net.minecraft.util.FormattedCharSequence}.
         * @return {@code true} to accept more characters, {@code false} to stop traversing the sequence.
         *
         * @param positionInCurrentSequence Contains the relative position of the
         *                                  character in the current sub-sequence. If
         *                                  multiple formatted char sequences have been
         *                                  combined, this value will reset to {@code 0}
         *                                  after each sequence has been fully consumed.
         */
        @Override
        public boolean accept(int positionInCurrentSequence, Style style, int codePoint) {
            FontSet fontset = Font.this.getFontSet(style.getFont());
            GlyphInfo glyphinfo = fontset.getGlyphInfo(codePoint, Font.this.filterFishyGlyphs);
            BakedGlyph bakedglyph = style.isObfuscated() && codePoint != 32 ? fontset.getRandomGlyph(glyphinfo) : fontset.getGlyph(codePoint);
            boolean flag = style.isBold();
            TextColor textcolor = style.getColor();
            int i = textcolor != null ? ARGB.color(ARGB.alpha(this.color), ARGB.scaleRGB(textcolor.getValue(), this.dimFactor)) : this.color;
            float f = glyphinfo.getAdvance(flag);
            float f1 = positionInCurrentSequence == 0 ? this.x - 1.0F : this.x;
            if (!(bakedglyph instanceof EmptyGlyph)) {
                float f2 = flag ? glyphinfo.getBoldOffset() : 0.0F;
                float f3 = this.dropShadow ? glyphinfo.getShadowOffset() : 0.0F;
                this.glyphInstances.add(new BakedGlyph.GlyphInstance(this.x + f3, this.y + f3, i, bakedglyph, style, f2));
            }

            float f4 = this.dropShadow ? 1.0F : 0.0F;
            if (style.isStrikethrough()) {
                this.addEffect(new BakedGlyph.Effect(f1 + f4, this.y + f4 + 4.5F, this.x + f4 + f, this.y + f4 + 4.5F - 1.0F, this.getOverTextEffectDepth(), i));
            }

            if (style.isUnderlined()) {
                this.addEffect(new BakedGlyph.Effect(f1 + f4, this.y + f4 + 9.0F, this.x + f4 + f, this.y + f4 + 9.0F - 1.0F, this.getOverTextEffectDepth(), i));
            }

            this.x += f;
            return true;
        }

        float finish(float x) {
            BakedGlyph bakedglyph = null;
            if (this.backgroundColor != 0) {
                BakedGlyph.Effect bakedglyph$effect = new BakedGlyph.Effect(
                    x - 1.0F, this.y + 9.0F, this.x, this.y - 1.0F, this.getUnderTextEffectDepth(), this.backgroundColor
                );
                bakedglyph = Font.this.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
                VertexConsumer vertexconsumer = this.bufferSource.getBuffer(bakedglyph.renderType(this.mode, Font.this.enableTextTextureLinearFiltering));
                bakedglyph.renderEffect(bakedglyph$effect, this.pose, vertexconsumer, this.packedLightCoords);
            }

            this.renderCharacters();
            if (this.effects != null) {
                if (bakedglyph == null) {
                    bakedglyph = Font.this.getFontSet(Style.DEFAULT_FONT).whiteGlyph();
                }

                VertexConsumer vertexconsumer1 = this.bufferSource.getBuffer(bakedglyph.renderType(this.mode, Font.this.enableTextTextureLinearFiltering));

                for (BakedGlyph.Effect bakedglyph$effect1 : this.effects) {
                    bakedglyph.renderEffect(bakedglyph$effect1, this.pose, vertexconsumer1, this.packedLightCoords);
                }
            }

            return this.x;
        }

        void renderCharacters() {
            for (BakedGlyph.GlyphInstance bakedglyph$glyphinstance : this.glyphInstances) {
                BakedGlyph bakedglyph = bakedglyph$glyphinstance.glyph();
                VertexConsumer vertexconsumer = this.bufferSource.getBuffer(bakedglyph.renderType(this.mode, Font.this.enableTextTextureLinearFiltering));
                bakedglyph.renderChar(bakedglyph$glyphinstance, this.pose, vertexconsumer, this.packedLightCoords);
            }
        }

        private float getOverTextEffectDepth() {
            return this.inverseDepth ? 0.01F : -0.01F;
        }

        private float getUnderTextEffectDepth() {
            return this.inverseDepth ? -0.01F : 0.01F;
        }
    }
}
