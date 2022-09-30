package net.kly.bydesign.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ArrowEntityModel extends Model {

    private final ModelPart root;

    public ArrowEntityModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData arrow = modelPartData.addChild("arrow", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(0.5F, -4.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 17).cuboid(-0.5F, -15.0F, -1.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 17).cuboid(-0.5F, -15.0F, 0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(1.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-1.5F, -4.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-2.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-0.5F, -3.0F, -2.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-0.5F, -4.0F, -1.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-0.5F, -3.0F, 1.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-0.5F, -4.0F, 0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
