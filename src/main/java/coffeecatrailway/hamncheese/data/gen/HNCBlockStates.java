package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.ChoppingBoardBlock;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import coffeecatrailway.hamncheese.common.block.PizzaOvenBlock;
import coffeecatrailway.hamncheese.common.block.TomatoPlantBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

/**
 * @author CoffeeCatRailway
 * Created: 7/04/2021
 */
public class HNCBlockStates extends BlockStateProvider
{
    public HNCBlockStates(DataGenerator gen, ExistingFileHelper existingFileHelper)
    {
        super(gen, HNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        int age;
        VariantBlockStateBuilder.PartialBlockstate pineapplePlant = this.getVariantBuilder(HNCBlocks.PINEAPPLE_PLANT.get()).partialState();
        for (age = 0; age < 5; age++)
        {
            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_bottom_stage_" + age, HNCMod.getLocation("block/pineapple_plant_bottom"))
                    .texture("plant", HNCMod.getLocation("block/pineapple_plant_stage_" + age))).addModel();

            pineapplePlant.with(PineapplePlantBlock.AGE, age).with(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().withExistingParent("block/pineapple_plant_top_stage_" + age, HNCMod.getLocation("block/pineapple_plant_top"))
                    .texture("pineapple", HNCMod.getLocation("block/pineapple_stage_" + age))).addModel();
        }

        VariantBlockStateBuilder.PartialBlockstate tomatoPlant = this.getVariantBuilder(HNCBlocks.TOMATO_PLANT.get()).partialState();
        for (age = 0; age < 10; age++)
        {
            tomatoPlant.with(TomatoPlantBlock.AGE, age).with(TomatoPlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(this.models().crop("block/tomato_plant_bottom_stage_" + age, HNCMod.getLocation("block/tomato_plant_bottom_stage_" + age))).addModel();

            tomatoPlant.with(TomatoPlantBlock.AGE, age).with(TomatoPlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(this.models().crop("block/tomato_plant_top_stage_" + age, HNCMod.getLocation("block/tomato_plant_top_stage_" + age))).addModel();
        }

        this.choppingBoard(HNCBlocks.OAK_CHOPPING_BOARD.get(), "oak", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.BIRCH_CHOPPING_BOARD.get(), "birch", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.SPRUCE_CHOPPING_BOARD.get(), "spruce", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.JUNGLE_CHOPPING_BOARD.get(), "jungle", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.ACACIA_CHOPPING_BOARD.get(), "acacia", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get(), "dark_oak", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.CRIMSON_CHOPPING_BOARD.get(), "crimson", ChoppingBoardType.PLANKS);
        this.choppingBoard(HNCBlocks.WARPED_CHOPPING_BOARD.get(), "warped", ChoppingBoardType.PLANKS);

        this.choppingBoard(HNCBlocks.STONE_CHOPPING_BOARD.get(), "stone", ChoppingBoardType.EMPTY);
        this.choppingBoard(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get(), "polished_blackstone", ChoppingBoardType.EMPTY);
        this.choppingBoard(HNCBlocks.GOLD_CHOPPING_BOARD.get(), "gold", ChoppingBoardType.BLOCK);
        this.choppingBoard(HNCBlocks.IRON_CHOPPING_BOARD.get(), "iron", ChoppingBoardType.BLOCK);

        this.choppingBoard(HNCBlocks.FIR_CHOPPING_BOARD.get(), "fir", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.REDWOOD_CHOPPING_BOARD.get(), "redwood", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.CHERRY_CHOPPING_BOARD.get(), "cherry", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.MAHOGANY_CHOPPING_BOARD.get(), "mahogany", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.JACARANDA_CHOPPING_BOARD.get(), "jacaranda", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.PALM_CHOPPING_BOARD.get(), "palm", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.WILLOW_CHOPPING_BOARD.get(), "willow", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.DEAD_CHOPPING_BOARD.get(), "dead", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.MAGIC_CHOPPING_BOARD.get(), "magic", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.UMBRAN_CHOPPING_BOARD.get(), "umbran", ChoppingBoardType.PLANKS, "biomesoplenty");
        this.choppingBoard(HNCBlocks.HELLBARK_CHOPPING_BOARD.get(), "hellbark", ChoppingBoardType.PLANKS, "biomesoplenty");

        VariantBlockStateBuilder.PartialBlockstate oven = this.getVariantBuilder(HNCBlocks.PIZZA_OVEN.get()).partialState();
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, false).with(PizzaOvenBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(this.models().getExistingFile(HNCMod.getLocation("block/pizza_oven"))).addModel();
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, false).with(PizzaOvenBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(this.models().getExistingFile(HNCMod.getLocation("block/pizza_oven"))).addModel();
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, true).with(PizzaOvenBlock.LIT, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(this.models().getExistingFile(HNCMod.getLocation("block/pizza_oven"))).addModel();
            oven.with(PizzaOvenBlock.HORIZONTAL_FACING, direction).with(PizzaOvenBlock.WATERLOGGED, true).with(PizzaOvenBlock.LIT, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(this.models().getExistingFile(HNCMod.getLocation("block/pizza_oven"))).addModel();
        }
        this.simpleBlockItem(HNCBlocks.PIZZA_OVEN.get(), this.itemModels().getExistingFile(HNCMod.getLocation("block/pizza_oven")));
    }

    private void choppingBoard(ChoppingBoardBlock choppingBoard, String type, ChoppingBoardType choppingBoardType)
    {
        choppingBoard(choppingBoard, type, choppingBoardType, "minecraft");
    }

    private void choppingBoard(ChoppingBoardBlock choppingBoard, String type, ChoppingBoardType choppingBoardType, String planksModId)
    {
        VariantBlockStateBuilder.PartialBlockstate partialState = this.getVariantBuilder(choppingBoard).partialState();
        String path = "block/" + type + "_chopping_board";
        ResourceLocation parent = HNCMod.getLocation("block/chopping_board");
        BlockModelBuilder model = this.models().withExistingParent(path, parent).texture("planks", new ResourceLocation(planksModId, "block/" + choppingBoardType.apply(type)));
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            partialState.with(ChoppingBoardBlock.HORIZONTAL_FACING, direction).with(ChoppingBoardBlock.WATERLOGGED, false)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(model).addModel();
            partialState.with(ChoppingBoardBlock.HORIZONTAL_FACING, direction).with(ChoppingBoardBlock.WATERLOGGED, true)
                    .modelForState().rotationY((int) direction.toYRot()).modelFile(model).addModel();
        }
        this.simpleBlockItem(choppingBoard, this.itemModels().getExistingFile(HNCMod.getLocation(path)));
    }

    enum ChoppingBoardType
    {
        EMPTY(type -> type + ""), PLANKS(type -> type + "_planks"), BLOCK(type -> type + "_block");

        final Function<String, String> textureFactory;

        ChoppingBoardType(Function<String, String> textureFactory)
        {
            this.textureFactory = textureFactory;
        }

        String apply(String type)
        {
            return this.textureFactory.apply(type);
        }
    }
}
