package coffeecatrailway.hamncheese.common.block;

import coffeecatrailway.hamncheese.registry.HNCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

/**
 * @author CoffeeCatRailway
 * Created: 11/04/2021
 */
public class TomatoPlantBlock extends AbstractDoublePlantBlock
{
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 9);

    private static final VoxelShape[] SHAPES_TOP = new VoxelShape[]{
            Block.box(0d, 0d, 0d, 16d, 1d, 16d),
            Block.box(0d, 0d, 0d, 16d, 1d, 16d),
            Block.box(0d, 0d, 0d, 16d, 5d, 16d),
            Block.box(0d, 0d, 0d, 16d, 10d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d)
    };
    private static final VoxelShape[] SHAPES_BOTTOM = new VoxelShape[]{
            Block.box(0d, 0d, 0d, 16d, 8d, 16d),
            Block.box(0d, 0d, 0d, 16d, 15d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d),
            Block.box(0d, 0d, 0d, 16d, 16d, 16d)
    };

    public TomatoPlantBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected IntegerProperty getAgeProperty()
    {
        return AGE;
    }

    @Override
    protected int getMaxAge()
    {
        return 9;
    }

    @Override
    protected VoxelShape[] getTopShapes()
    {
        return SHAPES_TOP;
    }

    @Override
    protected VoxelShape[] getBottomShapes()
    {
        return SHAPES_BOTTOM;
    }

    @Override
    protected boolean placeableOn(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    protected boolean needsFertileLand()
    {
        return true;
    }

    @Override
    protected Item getPickBlock(BlockState state)
    {
        return HNCItems.TOMATO_SEEDS.get();
    }

    @Override
    protected void bonemeal(int age, ServerWorld world, BlockPos pos, BlockState state)
    {
        boolean isLower = state.getValue(HALF) == DoubleBlockHalf.LOWER;
        world.setBlock(isLower ? pos.above() : pos, this.getStateForAge(age, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
        world.setBlock(isLower ? pos : pos.below(), this.getStateForAge(age, DoubleBlockHalf.LOWER), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState p_196243_4_, boolean p_196243_5_)
    {
        if (world.getBlockState(pos).getBlock() == this)
            return;
        if (world.getBlockState(pos.above()).getBlock() == this)
            world.destroyBlock(pos.above(), true);
        else if (world.getBlockState(pos.below()).getBlock() == this)
            world.destroyBlock(pos.below(), true);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && this.isYoung(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getRawBrightness(pos, 0) >= 9)
        {
            float growthSpeed = this.getGrowthSpeed(world, pos);
            if (ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25f / growthSpeed) + 1) == 0))
            {
                int age = state.getValue(AGE) + 1;
                if (age > 1)
                    world.setBlock(pos.above(), this.getStateForAge(age, DoubleBlockHalf.UPPER), Constants.BlockFlags.BLOCK_UPDATE);
                world.setBlock(pos, this.getStateForAge(age, DoubleBlockHalf.LOWER), Constants.BlockFlags.BLOCK_UPDATE);
                ForgeHooks.onCropsGrowPost(world, pos, state);
            }
        }
    }
}
