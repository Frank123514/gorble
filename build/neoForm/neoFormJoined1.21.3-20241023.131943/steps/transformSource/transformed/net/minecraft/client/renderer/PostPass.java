package net.minecraft.client.renderer;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class PostPass {
    private final String name;
    private final CompiledShaderProgram shader;
    private final ResourceLocation outputTargetId;
    private final List<PostChainConfig.Uniform> uniforms;
    private final List<PostPass.Input> inputs = new ArrayList<>();

    public PostPass(String name, CompiledShaderProgram shader, ResourceLocation outputTargetId, List<PostChainConfig.Uniform> uniforms) {
        this.name = name;
        this.shader = shader;
        this.outputTargetId = outputTargetId;
        this.uniforms = uniforms;
    }

    public void addInput(PostPass.Input input) {
        this.inputs.add(input);
    }

    public void addToFrame(FrameGraphBuilder frameGraphBuilder, Map<ResourceLocation, ResourceHandle<RenderTarget>> targets, Matrix4f projectionMatrix) {
        FramePass framepass = frameGraphBuilder.addPass(this.name);

        for (PostPass.Input postpass$input : this.inputs) {
            postpass$input.addToPass(framepass, targets);
        }

        ResourceHandle<RenderTarget> resourcehandle = targets.computeIfPresent(
            this.outputTargetId, (p_362663_, p_363989_) -> framepass.readsAndWrites((ResourceHandle<RenderTarget>)p_363989_)
        );
        if (resourcehandle == null) {
            throw new IllegalStateException("Missing handle for target " + this.outputTargetId);
        } else {
            framepass.executes(() -> {
                RenderTarget rendertarget = resourcehandle.get();
                RenderSystem.viewport(0, 0, rendertarget.width, rendertarget.height);

                for (PostPass.Input postpass$input1 : this.inputs) {
                    postpass$input1.bindTo(this.shader, targets);
                }

                this.shader.safeGetUniform("OutSize").set((float)rendertarget.width, (float)rendertarget.height);

                for (PostChainConfig.Uniform postchainconfig$uniform : this.uniforms) {
                    Uniform uniform = this.shader.getUniform(postchainconfig$uniform.name());
                    if (uniform != null) {
                        uniform.setFromConfig(postchainconfig$uniform.values(), postchainconfig$uniform.values().size());
                    }
                }

                rendertarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                rendertarget.clear();
                rendertarget.bindWrite(false);
                RenderSystem.depthFunc(519);
                RenderSystem.setShader(this.shader);
                RenderSystem.backupProjectionMatrix();
                RenderSystem.setProjectionMatrix(projectionMatrix, ProjectionType.ORTHOGRAPHIC);
                BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                bufferbuilder.addVertex(0.0F, 0.0F, 500.0F);
                bufferbuilder.addVertex((float)rendertarget.width, 0.0F, 500.0F);
                bufferbuilder.addVertex((float)rendertarget.width, (float)rendertarget.height, 500.0F);
                bufferbuilder.addVertex(0.0F, (float)rendertarget.height, 500.0F);
                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                RenderSystem.depthFunc(515);
                RenderSystem.restoreProjectionMatrix();
                rendertarget.unbindWrite();

                for (PostPass.Input postpass$input2 : this.inputs) {
                    postpass$input2.cleanup(targets);
                }

                this.restoreDefaultUniforms();
            });
        }
    }

    private void restoreDefaultUniforms() {
        for (PostChainConfig.Uniform postchainconfig$uniform : this.uniforms) {
            String s = postchainconfig$uniform.name();
            Uniform uniform = this.shader.getUniform(s);
            ShaderProgramConfig.Uniform shaderprogramconfig$uniform = this.shader.getUniformConfig(s);
            if (uniform != null && shaderprogramconfig$uniform != null && !postchainconfig$uniform.values().equals(shaderprogramconfig$uniform.values())) {
                uniform.setFromConfig(shaderprogramconfig$uniform);
            }
        }
    }

    public CompiledShaderProgram getShader() {
        return this.shader;
    }

    @OnlyIn(Dist.CLIENT)
    public interface Input {
        void addToPass(FramePass pass, Map<ResourceLocation, ResourceHandle<RenderTarget>> targets);

        void bindTo(CompiledShaderProgram shaderProgram, Map<ResourceLocation, ResourceHandle<RenderTarget>> targets);

        default void cleanup(Map<ResourceLocation, ResourceHandle<RenderTarget>> targets) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static record TargetInput(String samplerName, ResourceLocation targetId, boolean depthBuffer, boolean bilinear) implements PostPass.Input {
        private ResourceHandle<RenderTarget> getHandle(Map<ResourceLocation, ResourceHandle<RenderTarget>> targets) {
            ResourceHandle<RenderTarget> resourcehandle = targets.get(this.targetId);
            if (resourcehandle == null) {
                throw new IllegalStateException("Missing handle for target " + this.targetId);
            } else {
                return resourcehandle;
            }
        }

        @Override
        public void addToPass(FramePass pass, Map<ResourceLocation, ResourceHandle<RenderTarget>> targets) {
            pass.reads(this.getHandle(targets));
        }

        @Override
        public void bindTo(CompiledShaderProgram shaderProgram, Map<ResourceLocation, ResourceHandle<RenderTarget>> targets) {
            ResourceHandle<RenderTarget> resourcehandle = this.getHandle(targets);
            RenderTarget rendertarget = resourcehandle.get();
            rendertarget.setFilterMode(this.bilinear ? 9729 : 9728);
            shaderProgram.bindSampler(this.samplerName + "Sampler", this.depthBuffer ? rendertarget.getDepthTextureId() : rendertarget.getColorTextureId());
            shaderProgram.safeGetUniform(this.samplerName + "Size").set((float)rendertarget.width, (float)rendertarget.height);
        }

        @Override
        public void cleanup(Map<ResourceLocation, ResourceHandle<RenderTarget>> targets) {
            if (this.bilinear) {
                this.getHandle(targets).get().setFilterMode(9728);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static record TextureInput(String samplerName, AbstractTexture texture, int width, int height) implements PostPass.Input {
        @Override
        public void addToPass(FramePass p_361843_, Map<ResourceLocation, ResourceHandle<RenderTarget>> p_362022_) {
        }

        @Override
        public void bindTo(CompiledShaderProgram p_366520_, Map<ResourceLocation, ResourceHandle<RenderTarget>> p_363141_) {
            p_366520_.bindSampler(this.samplerName + "Sampler", this.texture.getId());
            p_366520_.safeGetUniform(this.samplerName + "Size").set((float)this.width, (float)this.height);
        }
    }
}
