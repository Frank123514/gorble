package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.CompiledShader;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.VisibleForTesting;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class CompiledShaderProgram implements AutoCloseable {
    private static final AbstractUniform DUMMY_UNIFORM = new AbstractUniform();
    private static final int NO_SAMPLER_TEXTURE = -1;
    private final List<ShaderProgramConfig.Sampler> samplers = new ArrayList<>();
    private final Object2IntMap<String> samplerTextures = new Object2IntArrayMap<>();
    private final IntList samplerLocations = new IntArrayList();
    private final List<Uniform> uniforms = new ArrayList<>();
    private final Map<String, Uniform> uniformsByName = new HashMap<>();
    private final Map<String, ShaderProgramConfig.Uniform> uniformConfigs = new HashMap<>();
    private final int programId;
    @Nullable
    public Uniform MODEL_VIEW_MATRIX;
    @Nullable
    public Uniform PROJECTION_MATRIX;
    @Nullable
    public Uniform TEXTURE_MATRIX;
    @Nullable
    public Uniform SCREEN_SIZE;
    @Nullable
    public Uniform COLOR_MODULATOR;
    @Nullable
    public Uniform LIGHT0_DIRECTION;
    @Nullable
    public Uniform LIGHT1_DIRECTION;
    @Nullable
    public Uniform GLINT_ALPHA;
    @Nullable
    public Uniform FOG_START;
    @Nullable
    public Uniform FOG_END;
    @Nullable
    public Uniform FOG_COLOR;
    @Nullable
    public Uniform FOG_SHAPE;
    @Nullable
    public Uniform LINE_WIDTH;
    @Nullable
    public Uniform GAME_TIME;
    @Nullable
    public Uniform MODEL_OFFSET;

    private CompiledShaderProgram(int programId) {
        this.programId = programId;
        this.samplerTextures.defaultReturnValue(-1);
    }

    public static CompiledShaderProgram link(CompiledShader vertexShader, CompiledShader fragmentShader, VertexFormat vertexFormat) throws ShaderManager.CompilationException {
        int i = GlStateManager.glCreateProgram();
        if (i <= 0) {
            throw new ShaderManager.CompilationException("Could not create shader program (returned program ID " + i + ")");
        } else {
            vertexFormat.bindAttributes(i);
            GlStateManager.glAttachShader(i, vertexShader.getShaderId());
            GlStateManager.glAttachShader(i, fragmentShader.getShaderId());
            GlStateManager.glLinkProgram(i);
            int j = GlStateManager.glGetProgrami(i, 35714);
            if (j == 0) {
                String s = GlStateManager.glGetProgramInfoLog(i, 32768);
                throw new ShaderManager.CompilationException(
                    "Error encountered when linking program containing VS " + vertexShader.getId() + " and FS " + fragmentShader.getId() + ". Log output: " + s
                );
            } else {
                return new CompiledShaderProgram(i);
            }
        }
    }

    public void setupUniforms(List<ShaderProgramConfig.Uniform> uniforms, List<ShaderProgramConfig.Sampler> samplers) {
        RenderSystem.assertOnRenderThread();

        for (ShaderProgramConfig.Uniform shaderprogramconfig$uniform : uniforms) {
            String s = shaderprogramconfig$uniform.name();
            int i = Uniform.glGetUniformLocation(this.programId, s);
            if (i != -1) {
                Uniform uniform = this.parseUniformNode(shaderprogramconfig$uniform);
                uniform.setLocation(i);
                this.uniforms.add(uniform);
                this.uniformsByName.put(s, uniform);
                this.uniformConfigs.put(s, shaderprogramconfig$uniform);
            }
        }

        for (ShaderProgramConfig.Sampler shaderprogramconfig$sampler : samplers) {
            int j = Uniform.glGetUniformLocation(this.programId, shaderprogramconfig$sampler.name());
            if (j != -1) {
                this.samplers.add(shaderprogramconfig$sampler);
                this.samplerLocations.add(j);
            }
        }

        this.MODEL_VIEW_MATRIX = this.getUniform("ModelViewMat");
        this.PROJECTION_MATRIX = this.getUniform("ProjMat");
        this.TEXTURE_MATRIX = this.getUniform("TextureMat");
        this.SCREEN_SIZE = this.getUniform("ScreenSize");
        this.COLOR_MODULATOR = this.getUniform("ColorModulator");
        this.LIGHT0_DIRECTION = this.getUniform("Light0_Direction");
        this.LIGHT1_DIRECTION = this.getUniform("Light1_Direction");
        this.GLINT_ALPHA = this.getUniform("GlintAlpha");
        this.FOG_START = this.getUniform("FogStart");
        this.FOG_END = this.getUniform("FogEnd");
        this.FOG_COLOR = this.getUniform("FogColor");
        this.FOG_SHAPE = this.getUniform("FogShape");
        this.LINE_WIDTH = this.getUniform("LineWidth");
        this.GAME_TIME = this.getUniform("GameTime");
        this.MODEL_OFFSET = this.getUniform("ModelOffset");
    }

    @Override
    public void close() {
        this.uniforms.forEach(Uniform::close);
        GlStateManager.glDeleteProgram(this.programId);
    }

    public void clear() {
        RenderSystem.assertOnRenderThread();
        GlStateManager._glUseProgram(0);
        int i = GlStateManager._getActiveTexture();

        for (int j = 0; j < this.samplerLocations.size(); j++) {
            ShaderProgramConfig.Sampler shaderprogramconfig$sampler = this.samplers.get(j);
            if (!this.samplerTextures.containsKey(shaderprogramconfig$sampler.name())) {
                GlStateManager._activeTexture(33984 + j);
                GlStateManager._bindTexture(0);
            }
        }

        GlStateManager._activeTexture(i);
    }

    public void apply() {
        RenderSystem.assertOnRenderThread();
        GlStateManager._glUseProgram(this.programId);
        int i = GlStateManager._getActiveTexture();

        for (int j = 0; j < this.samplerLocations.size(); j++) {
            String s = this.samplers.get(j).name();
            int k = this.samplerTextures.getInt(s);
            if (k != -1) {
                int l = this.samplerLocations.getInt(j);
                Uniform.uploadInteger(l, j);
                RenderSystem.activeTexture(33984 + j);
                RenderSystem.bindTexture(k);
            }
        }

        GlStateManager._activeTexture(i);

        for (Uniform uniform : this.uniforms) {
            uniform.upload();
        }
    }

    @Nullable
    public Uniform getUniform(String name) {
        RenderSystem.assertOnRenderThread();
        return this.uniformsByName.get(name);
    }

    @Nullable
    public ShaderProgramConfig.Uniform getUniformConfig(String name) {
        return this.uniformConfigs.get(name);
    }

    public AbstractUniform safeGetUniform(String name) {
        Uniform uniform = this.getUniform(name);
        return (AbstractUniform)(uniform == null ? DUMMY_UNIFORM : uniform);
    }

    public void bindSampler(String samplerName, int textureId) {
        this.samplerTextures.put(samplerName, textureId);
    }

    private Uniform parseUniformNode(ShaderProgramConfig.Uniform p_uniform) {
        int i = Uniform.getTypeFromString(p_uniform.type());
        int j = p_uniform.count();
        int k = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
        Uniform uniform = new Uniform(p_uniform.name(), i + k, j);
        uniform.setFromConfig(p_uniform);
        return uniform;
    }

    public void setDefaultUniforms(VertexFormat.Mode mode, Matrix4f frustumMatrix, Matrix4f projectionMatrix, Window window) {
        for (int i = 0; i < 12; i++) {
            int j = RenderSystem.getShaderTexture(i);
            this.bindSampler("Sampler" + i, j);
        }

        if (this.MODEL_VIEW_MATRIX != null) {
            this.MODEL_VIEW_MATRIX.set(frustumMatrix);
        }

        if (this.PROJECTION_MATRIX != null) {
            this.PROJECTION_MATRIX.set(projectionMatrix);
        }

        if (this.COLOR_MODULATOR != null) {
            this.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
        }

        if (this.GLINT_ALPHA != null) {
            this.GLINT_ALPHA.set(RenderSystem.getShaderGlintAlpha());
        }

        FogParameters fogparameters = RenderSystem.getShaderFog();
        if (this.FOG_START != null) {
            this.FOG_START.set(fogparameters.start());
        }

        if (this.FOG_END != null) {
            this.FOG_END.set(fogparameters.end());
        }

        if (this.FOG_COLOR != null) {
            this.FOG_COLOR.set(fogparameters.red(), fogparameters.green(), fogparameters.blue(), fogparameters.alpha());
        }

        if (this.FOG_SHAPE != null) {
            this.FOG_SHAPE.set(fogparameters.shape().getIndex());
        }

        if (this.TEXTURE_MATRIX != null) {
            this.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
        }

        if (this.GAME_TIME != null) {
            this.GAME_TIME.set(RenderSystem.getShaderGameTime());
        }

        if (this.SCREEN_SIZE != null) {
            this.SCREEN_SIZE.set((float)window.getWidth(), (float)window.getHeight());
        }

        if (this.LINE_WIDTH != null && (mode == VertexFormat.Mode.LINES || mode == VertexFormat.Mode.LINE_STRIP)) {
            this.LINE_WIDTH.set(RenderSystem.getShaderLineWidth());
        }

        RenderSystem.setupShaderLights(this);
    }

    @VisibleForTesting
    public void registerUniform(Uniform uniform) {
        this.uniforms.add(uniform);
        this.uniformsByName.put(uniform.getName(), uniform);
    }

    @VisibleForTesting
    public int getProgramId() {
        return this.programId;
    }
}
