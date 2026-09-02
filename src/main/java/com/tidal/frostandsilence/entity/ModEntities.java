package com.tidal.frostandsilence.entity;

import com.tidal.frostandsilence.FrostAndSilence;
import com.tidal.frostandsilence.entity.custom.PenguinEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;


public class ModEntities {
    public static final EntityType<PenguinEntity> PENGUIN = register(
            "penguin",
            EntityType.Builder.<PenguinEntity>of(PenguinEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 1F)
    );
    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(FrostAndSilence.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        FrostAndSilence.LOGGER.info("Registering EntityTypes for " + FrostAndSilence.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(PENGUIN, PenguinEntity.createCubeAttributes());
    }
}
