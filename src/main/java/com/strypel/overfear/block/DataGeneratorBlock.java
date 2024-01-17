package com.strypel.overfear.block;

import com.strypel.overfear.block.entity.AnalogCraftingTableEntity;
import com.strypel.overfear.block.entity.DataGeneratorEntity;
import com.strypel.overfear.block.entity.util.TickableBlockEntity;
import com.strypel.overfear.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class DataGeneratorBlock extends Block implements EntityBlock, IDirectionalBlock {
    public static final EnumProperty<DataGeneratorPart> PART = EnumProperty.create("part", DataGeneratorPart.class);
    public static final BooleanProperty SECOND_PART = BooleanProperty.create("second_part");
    public DataGeneratorBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART,DataGeneratorPart.FOOT).setValue(SECOND_PART,Boolean.FALSE));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,PART,SECOND_PART);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,
                context.getHorizontalDirection().getClockWise().getClockWise());
    }


    //@Override
    //public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
    //    super.setPlacedBy(worldIn, pos, state, p_49850_, p_49851_);
    //    if (!worldIn.isClientSide) {
    //        BlockPos blockpos = getOtherPos(pos, state);
    //        worldIn.setBlock(blockpos, state.setValue(PART, DataGeneratorPart.HEAD).setValue(SECOND_PART,Boolean.TRUE), 3);
    //        worldIn.blockUpdated(pos, Blocks.AIR);
    //        state.updateNeighbourShapes(worldIn, pos, 3);
    //        Block block = worldIn.getBlockState(blockpos).getBlock();
    //        if(DataGeneratorBlock.isHead(worldIn,blockpos)){
    //            worldIn.getBlockState(blockpos).setValue(SECOND_PART,Boolean.TRUE);
    //            System.out.println("true 1" + worldIn.getBlockState(blockpos).getValue(SECOND_PART));
    //        }
    //    }
    //}
    private static Direction getDirectionToOther(DataGeneratorPart type, @NotNull Direction facing) {
        return type == DataGeneratorPart.FOOT ? facing : facing.getCounterClockWise();
    }
    @NotNull
    @Override
    public BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        //if (facing == getDirectionToOther(stateIn.getValue(PART), stateIn.getValue(FACING))) {
        //    return facingState.getBlock() == this && facingState.getValue(PART) != stateIn.getValue(PART) ? Blocks.AIR.defaultBlockState() : Blocks.AIR.defaultBlockState();
        //} else {
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        //}
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Block.box(-16, 0, 0, 16, 16, 16);
            case SOUTH ->  Block.box(0, 0, 0, 32, 16, 16);
            case WEST ->  Block.box(0, 0, 0, 16, 16, 32);
            default -> Block.box(0, 0, -16, 16, 16, 16);
        };
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        for (BlockPos testPos : BlockPos.betweenClosed(pos,
                pos.relative(state.getValue(FACING).getCounterClockWise(), 1))) {
            if (!testPos.equals(pos) && !world.getBlockState(testPos).isAir())
                return false;
        }

        return true;
    }

    @Override
    public InteractionResult use(BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(pLevel.getBlockEntity(pos) instanceof DataGeneratorEntity entity){
            entity.triggerAnim("data_generator_controller", "onuse");
        }
        BlockEntity be = pLevel.getBlockEntity(pos);
        if(!(be instanceof DataGeneratorEntity blockEntity))
            return InteractionResult.PASS;
        if(pLevel.isClientSide)
            return InteractionResult.SUCCESS;

        if(player instanceof ServerPlayer sPlayer){
            sPlayer.openMenu(blockEntity,pos);
        }
        return InteractionResult.SUCCESS;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return TickableBlockEntity.getTickerHelper(p_153212_);
    }
    public @NotNull BlockPos getOtherPos(@NotNull BlockPos pos, @NotNull BlockState state) {
        return pos.relative(state.getValue(FACING).getCounterClockWise());
    }
    public static boolean isHead(@NotNull BlockGetter world, @NotNull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof DataGeneratorBlock && state.getValue(PART) == DataGeneratorPart.HEAD;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return ModBlockEntities.DATA_GENERATOR.get().create(p_153215_,p_153216_);
    }
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state_2, boolean p_60519_) {
        if(!level.isClientSide){
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof DataGeneratorEntity){
                ItemStackHandler inventory = ((DataGeneratorEntity) be).getInventory();
                for (int i = 0; i < inventory.getSlots(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    var itemEntity = new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),stack);
                    level.addFreshEntity(itemEntity);
                }
            }
        }
        super.onRemove(state, level, pos, state_2, p_60519_);
    }
    public enum DataGeneratorPart implements StringRepresentable {
        HEAD("head"),
        FOOT("foot");

        private final String name;

        DataGeneratorPart(String name) {
            this.name = name;
        }

        @NotNull
        public String getSerializedName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }
    }
}
