package net.kly.bydesign.mixin.client;

import net.kly.bydesign.ByDesignClient;
import net.kly.bydesign.client.model.FireworkRocketEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FireworkRocketEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FireworkRocketEntityRenderer.class)
public class FireworkRocketEntityRendererMixin extends EntityRenderer<FireworkRocketEntity> {

    public FireworkRocketEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    private static final Identifier TEXTURE = new Identifier("bydesign", "textures/entity/projectiles/firework_rocket.png");
    private final FireworkRocketEntityModel model = new FireworkRocketEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ByDesignClient.FIREWORK_ROCKET_MODEL_LAYER));

    /**
     * @author Kly
     * @reason Changed how fireworks render
     */
    @Overwrite
    public void render(FireworkRocketEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrices.push();

        if (entity.wasShotAtAngle()) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        } else {
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
        }

        matrices.translate(0.0, -1.5, 0.0);

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(getTexture(entity)));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, light);
    }


    /**
     * @author Kly
     * @reason Switched texture
     */
    @Overwrite
    public Identifier getTexture(FireworkRocketEntity entity) {
        return TEXTURE;
    }
}
