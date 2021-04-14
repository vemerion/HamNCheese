package coffeecatrailway.hamncheese.common.item.crafting;

import coffeecatrailway.hamncheese.HNCMod;
import coffeecatrailway.hamncheese.common.item.AbstractSandwichItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author CoffeeCatRailway
 * Created: 2/04/2021
 */
public abstract class AbstractSandwichRecipe extends SpecialRecipe
{
    private final ITag.INamedTag<Item> bunTag;
    private final Supplier<? extends IItemProvider> defaultItem;

    public AbstractSandwichRecipe(ResourceLocation id, ITag.INamedTag<Item> bunTag, Supplier<? extends IItemProvider> defaultItem)
    {
        super(id);
        this.bunTag = bunTag;
        this.defaultItem = defaultItem;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world)
    {
        ItemStack bunStack = this.hasBun(inventory);
        if (!bunStack.isEmpty())
        {
            AbstractSandwichItem sandwich = (AbstractSandwichItem) this.assemble(inventory).getItem();
            int maxBunCount = sandwich.sandwichProperties.hasTwoBuns() ? 2 : 1;
            int bunCount = 0;
            int ingredientCount = 0;
            boolean hasNeededItem = this.getNeededItem() == null;
            int neededItemCount = 0;

            for (int i = 0; i < inventory.getContainerSize(); i++)
            {
                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty())
                {
                    if (!stack.isEdible())
                        return false;
                    else
                    {
                        if (stack.getItem() == this.getNeededItem())
                        {
                            neededItemCount++;
                            hasNeededItem = neededItemCount <= 1;
                        } else if (this.bunTag.contains(stack.getItem()))
                            bunCount++;
                        else
                            ingredientCount++;
                    }
                }
            }
            return bunCount == maxBunCount && ingredientCount > 0 && ingredientCount <= HNCMod.SERVER_CONFIG.maxSandwichIngredientCraftCount.get() && hasNeededItem;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInventory inventory)
    {
        ItemStack bunStack = this.hasBun(inventory);
        if (!bunStack.isEmpty())
        {
            int neededItemCount = 0;
            ItemStack sandwich = new ItemStack(this.defaultItem.get());
            for (int i = 0; i < inventory.getContainerSize(); i++)
            {
                ItemStack ingredient = inventory.getItem(i);
                if (ingredient.getItem() == this.getNeededItem())
                    neededItemCount++;
                if (ingredient.getItem() != this.getNeededItem() && neededItemCount <= 1)
                    if (!ingredient.isEmpty() && ingredient.isEdible() && !this.bunTag.contains(ingredient.getItem()))
                        sandwich = AbstractSandwichItem.addIngredient(sandwich, ingredient);
            }
            return sandwich;
        }
        return bunStack;
    }

    private ItemStack hasBun(CraftingInventory inventory)
    {
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (this.bunTag.contains(stack.getItem()))
            {
                if (stack.getItem() instanceof AbstractSandwichItem)
                {
                    ListNBT ingredients = stack.getOrCreateTag().getList(AbstractSandwichItem.TAG_INGREDIENTS, Constants.NBT.TAG_COMPOUND);
                    if (ingredients.size() <= 0)
                        return stack;
                }
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width >= 2 && height >= 2;
    }

    @Nullable
    protected Item getNeededItem()
    {
        return null;
    }
}
