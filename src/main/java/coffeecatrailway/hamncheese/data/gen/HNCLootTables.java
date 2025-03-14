package coffeecatrailway.hamncheese.data.gen;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.block.PineapplePlantBlock;
import coffeecatrailway.hamncheese.registry.HNCBlocks;
import coffeecatrailway.hamncheese.registry.HNCEntities;
import coffeecatrailway.hamncheese.registry.HNCItems;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.*;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeLootTableProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2021
 */
public class HNCLootTables extends ForgeLootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = ImmutableList.of(
            Pair.of(ChestProvider::new, LootParameterSets.CHEST),
            Pair.of(EntityProvider::new, LootParameterSets.ENTITY),
            Pair.of(BlockProvider::new, LootParameterSets.BLOCK));

    public static final ResourceLocation PLAINS_CHEF_RESTAURANT = HNCMod.getLocation("chests/village/village_chef_restaurant");

    public HNCLootTables(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return this.tables;
    }

    private static class ChestProvider extends ChestLootTables
    {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
        {
            registry.accept(PLAINS_CHEF_RESTAURANT, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(new RandomValueRange(3, 8))
                            .add(ItemLootEntry.lootTableItem(HNCItems.KNIFE.get()).setWeight(2))
                            .add(ItemLootEntry.lootTableItem(HNCItems.CURDLER.get()).setWeight(2))
                            .add(ItemLootEntry.lootTableItem(HNCItems.GRIND_STONES.get()).setWeight(2))
                            .add(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE.get()).setWeight(5)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 5))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.TOMATO.get()).setWeight(5)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 5))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE_PLANT.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 8))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.TOMATO_SEEDS.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 8))))
                            .add(ItemLootEntry.lootTableItem(Items.BREAD).setWeight(8)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 4))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.BREAD_SLICE.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 8))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.BLOCK_OF_CHEESE.get()).setWeight(8)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 8))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.CHEESE_SLICE.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 4))))
                            .add(ItemLootEntry.lootTableItem(Items.EMERALD).setWeight(2)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 4))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.ROCK_SALT.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(2, 8))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.FLOUR.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(2, 8))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.FOOD_SCRAPS.get()).setWeight(8)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 8))))
                            .add(ItemLootEntry.lootTableItem(HNCItems.CRACKED_EGG.get()).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 5))))
                            .add(ItemLootEntry.lootTableItem(Items.EGG).setWeight(10)
                                    .apply(SetCount.setCount(new RandomValueRange(1, 10))))
                    ));
        }
    }

    private static class EntityProvider extends EntityLootTables
    {
        @Override
        protected void addTables()
        {
            this.add(HNCEntities.MOUSE.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.MOUSE.get())
                                    .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityLootTables.ENTITY_ON_FIRE))))));
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities()
        {
            return ForgeRegistries.ENTITIES.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && HNCMod.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }

    private static class BlockProvider extends BlockLootTables
    {
        @Override
        protected void addTables()
        {
            this.add(HNCBlocks.PINEAPPLE_PLANT.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE.get())
                                    .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.PINEAPPLE_PLANT.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.UPPER)
                                                    .hasProperty(PineapplePlantBlock.AGE, 4)))
                                    .otherwise(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE_PLANT.get()))))
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.PINEAPPLE_PLANT.get())
                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.PINEAPPLE_PLANT.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(PineapplePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                            .hasProperty(PineapplePlantBlock.AGE, 4))))
                    .apply(ExplosionDecay.explosionDecay()));

            this.add(HNCBlocks.TOMATO_PLANT.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.TOMATO.get())
                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3))
                                    .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.TOMATO_PLANT.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                                    .hasProperty(PineapplePlantBlock.AGE, 9)))
                                    .otherwise(ItemLootEntry.lootTableItem(HNCItems.TOMATO_SEEDS.get()))))
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCItems.TOMATO_SEEDS.get())
                                    .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, .5714286f, 3)))
                            .when(BlockStateProperty.hasBlockStateProperties(HNCBlocks.TOMATO_PLANT.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(PineapplePlantBlock.AGE, 9))))
                    .apply(ExplosionDecay.explosionDecay()));

            this.dropSelf(HNCBlocks.OAK_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.BIRCH_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.SPRUCE_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.JUNGLE_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.ACACIA_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.DARK_OAK_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.CRIMSON_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.WARPED_CHOPPING_BOARD.get());

            this.dropSelf(HNCBlocks.STONE_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.POLISHED_BLACKSTONE_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.GOLD_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.IRON_CHOPPING_BOARD.get());

            this.dropSelf(HNCBlocks.FIR_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.REDWOOD_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.CHERRY_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.MAHOGANY_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.JACARANDA_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.PALM_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.WILLOW_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.DEAD_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.MAGIC_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.UMBRAN_CHOPPING_BOARD.get());
            this.dropSelf(HNCBlocks.HELLBARK_CHOPPING_BOARD.get());

            this.add(HNCBlocks.PIZZA_OVEN.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(new ConstantRange(1))
                            .add(ItemLootEntry.lootTableItem(HNCBlocks.PIZZA_OVEN.get())
                                    .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))
                            .when(SurvivesExplosion.survivesExplosion())));
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && HNCMod.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }
}
