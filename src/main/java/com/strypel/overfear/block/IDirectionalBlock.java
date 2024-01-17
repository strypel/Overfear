package com.strypel.overfear.block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
public interface IDirectionalBlock {
    DirectionProperty FACING = BlockStateProperties.FACING;
}
