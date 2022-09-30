package net.kly.bydesign;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.kly.bydesign.client.model.ArrowEntityModel;
import net.kly.bydesign.client.model.FireworkRocketEntityModel;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class ByDesignClient implements ClientModInitializer {

    public static final EntityModelLayer FIREWORK_ROCKET_MODEL_LAYER = new EntityModelLayer(new Identifier("bydesign", "firework_rocket"), "firework_rocket");
    public static final EntityModelLayer ARROW_MODEL_LAYER = new EntityModelLayer(new Identifier("bydesign", "arrow"), "arrow");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(FIREWORK_ROCKET_MODEL_LAYER, FireworkRocketEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ARROW_MODEL_LAYER, ArrowEntityModel::getTexturedModelData);

        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("spectral_arrow"), (stack, world, entity, seed) -> entity != null && entity.getArrowType(stack).isOf(Items.SPECTRAL_ARROW) ? 1.0F : 0.0F);
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("spectral_arrow"), (stack, world, entity, seed) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.hasProjectile(stack, Items.SPECTRAL_ARROW) ? 1.0F : 0.0F);
    }
}
