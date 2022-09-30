package net.kly.bydesign.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class FireworkRocketEntityModel extends Model {

    private final ModelPart root;

    public FireworkRocketEntityModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData firework_rocket_head = modelPartData.addChild("firework_rocket_head", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        ModelPartData cubes = firework_rocket_head.addChild("cubes", ModelPartBuilder.create()
                .uv(8, 0).cuboid(-0.5F, -0.5F, -1.5F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 9).cuboid(0.5F, 0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(5, 6).cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
