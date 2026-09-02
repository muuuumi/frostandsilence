package com.tidal.frostandsilence.entity.custom;

import com.tidal.frostandsilence.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;


public class PenguinEntity extends PathfinderMob {
    public final AnimationState idleAnimationState = new AnimationState();

    public PenguinEntity(Level world) {
        this(ModEntities.PENGUIN, world);
    }

    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createCubeAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.TEMPT_RANGE, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TemptGoal(this, 1, Ingredient.of(Items.BLUE_ICE), false, 2f));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this,1f));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }
}
