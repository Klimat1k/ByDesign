package net.kly.bydesign.mixin.client;

import net.kly.bydesign.ByDesignClient;
import net.kly.bydesign.client.model.ArrowEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.SpectralArrowEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SpectralArrowEntityRenderer.class)
public class SpectralArrowEntityRendererMixin extends ProjectileEntityRenderer<SpectralArrowEntity> {

    private static final Identifier TEXTURE = new Identifier("bydesign", "textures/entity/projectiles/spectral_arrow.png");
    private final ArrowEntityModel model = new ArrowEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ByDesignClient.ARROW_MODEL_LAYER));

    public SpectralArrowEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(SpectralArrowEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrices.push();

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));

        float s = entity.shake - tickDelta;
        if (s > 0.0F) {
            float t = -MathHelper.sin(s * 3.0F) * s;
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(t));
        }

        matrices.translate(0.0, -0.75, 0.0);

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, light);
    }

    /**
     * @author Kly
     * @reason Switched texture for new 3d model
     */
    @Overwrite
    public Identifier getTexture(SpectralArrowEntity entity) {
        return TEXTURE;
    }
}
