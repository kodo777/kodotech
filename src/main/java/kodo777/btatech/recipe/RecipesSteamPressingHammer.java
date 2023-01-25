package kodo777.btatech.recipe;

import kodo777.btatech.BtATech;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipesSteamPressingHammer {
    private static final RecipesSteamPressingHammer smeltingBase = new RecipesSteamPressingHammer();
    private Map smeltingList = new HashMap();

    public static final RecipesSteamPressingHammer smelting() {
        return smeltingBase;
    }

    private RecipesSteamPressingHammer() {
        this.addSmelting(Item.ingotIron.itemID, new ItemStack(BtATech.plateIron));
        this.addSmelting(Item.ingotSteel.itemID, new ItemStack(BtATech.plateSteel));
        this.addSmelting(BtATech.carbonEnrichedSteelIngot.itemID, new ItemStack(BtATech.plateCarbonEnrichedSteel));
    }


    public void addSmelting(int i, ItemStack itemstack) {
        this.smeltingList.put(i, itemstack);
    }

    public ItemStack getSmeltingResult(int i) {
        return (ItemStack)this.smeltingList.get(i);
    }

    public Map getSmeltingList() {
        return this.smeltingList;
    }
}
