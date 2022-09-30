package net.kly.bydesign.mixin.client;

import net.kly.bydesign.ByDesignClient;
import net.kly.bydesign.client.model.ArrowEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ProjectileEntityRenderer.class)
public abstract class ProjectileEntityRendererMixin<T extends PersistentProjectileEntity> extends EntityRenderer<T> {

    public ProjectileEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    private static final Identifier ARROW_TEXTURE = new Identifier("bydesign", "textures/entity/projectiles/arrow.png");
    private static final Identifier SPECTRAL_ARROW_TEXTURE = new Identifier("bydesign", "textures/entity/projectiles/spectral_arrow.png");

    private final ArrowEntityModel model = new ArrowEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ByDesignClient.ARROW_MODEL_LAYER));

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrices.push();

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));

        float s = entity.shake - tickDelta;
        if (s > 0.0F) {
            float t = -MathHelper.sin(s * 3.0F) * s;
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(t));
        }

        matrices.translate(0.0, -0.75, 0.0);

        Identifier texture;
        if (entity instanceof SpectralArrowEntity) {
            texture = SPECTRAL_ARROW_TEXTURE;
        } else {
            texture = ARROW_TEXTURE;
        }

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(texture));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, light);
    }
}
