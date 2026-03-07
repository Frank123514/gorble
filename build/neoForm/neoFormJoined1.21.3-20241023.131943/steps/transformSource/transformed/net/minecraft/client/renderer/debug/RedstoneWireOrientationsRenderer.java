package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.network.protocol.common.custom.RedstoneWireOrientationsDebugPayload;
import net.minecraft.world.level.redstone.Orientation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class RedstoneWireOrientationsRenderer implements DebugRenderer.SimpleDebugRenderer {
    public static final int TIMEOUT = 200;
    private final Minecraft minecraft;
    private final List<RedstoneWireOrientationsDebugPayload> updatedWires = Lists.newArrayList();

    RedstoneWireOrientationsRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void addWireOrientations(RedstoneWireOrientationsDebugPayload payload) {
        this.updatedWires.add(payload);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, double camX, double camY, double camZ) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.lines());
        long i = this.minecraft.level.getGameTime();
        Iterator<RedstoneWireOrientationsDebugPayload> iterator = this.updatedWires.iterator();

        while (iterator.hasNext()) {
            RedstoneWireOrientationsDebugPayload redstonewireorientationsdebugpayload = iterator.next();
            long j = i - redstonewireorientationsdebugpayload.time();
            if (j > 200L) {
                iterator.remove();
            } else {
                for (RedstoneWireOrientationsDebugPayload.Wire redstonewireorientationsdebugpayload$wire : redstonewireorientationsdebugpayload.wires()) {
                    Vector3f vector3f = redstonewireorientationsdebugpayload$wire.pos()
                        .getBottomCenter()
                        .subtract(camX, camY - 0.1, camZ)
                        .toVector3f();
                    Orientation orientation = redstonewireorientationsdebugpayload$wire.orientation();
                    ShapeRenderer.renderVector(poseStack, vertexconsumer, vector3f, orientation.getFront().getUnitVec3().scale(0.5), -16776961);
                    ShapeRenderer.renderVector(poseStack, vertexconsumer, vector3f, orientation.getUp().getUnitVec3().scale(0.4), -65536);
                    ShapeRenderer.renderVector(poseStack, vertexconsumer, vector3f, orientation.getSide().getUnitVec3().scale(0.3), -256);
                }
            }
        }
    }
}
