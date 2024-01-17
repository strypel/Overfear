package com.strypel.overfear.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.core.ModAttributes;
import com.strypel.overfear.items.client.ActivityAnalyzerRenderer;
import com.strypel.overfear.items.client.TapeRenderer;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTriggers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class TapeItem extends Item implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public TapeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getPlayer().level().isClientSide){
            Player player = context.getPlayer();
            player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(playerPhase -> {
                int flag = playerPhase.getIntPhase();
                if(!player.isShiftKeyDown()){
                    //playerPhase.addPhase(0.1);
                } else {
                    //playerPhase.subPhase(0.1);
                }
                //if(playerPhase.getIntPhase() != flag){
                //    for (double i = playerPhase.getMin(); i < playerPhase.getMax(); i++) {
                //        if(i != playerPhase.getPhase()){
                //            PhaseTriggers.resetTriggersForPlayer(i,player);
                //        }
                //    }
                //}
                //DecimalFormat decimalFormat = new DecimalFormat( "#.#" );
                //player.displayClientMessage(Component.nullToEmpty("Phase is " + playerPhase.getIntPhase()),true);
            });
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains("data")){
            float data = tag.getFloat("data");
            DecimalFormat decimalFormat = new DecimalFormat( "#.#" );
            tooltip.add(Component.translatable("item.overfear.tape.data").append(Component.literal(": " + (decimalFormat.format(data) + "%"))).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private TapeRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new TapeRenderer();
                }

                return this.renderer;
            }
        });
    }
    
}
