package kodo777.btatech.recipe;

import kodo777.btatech.BtATech;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RecipesSteamBoiler {
    private static final RecipesSteamBoiler smeltingBase = new RecipesSteamBoiler();
    private Map smeltingList = new HashMap();

    public static final RecipesSteamBoiler smelting() {
        return smeltingBase;
    }

    private RecipesSteamBoiler() {
        this.addSmelting(Item.bucketWater.id, new ItemStack(BtATech.bucketSteam));
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
