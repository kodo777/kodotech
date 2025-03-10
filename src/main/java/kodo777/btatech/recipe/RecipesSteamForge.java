package kodo777.btatech.recipe;

import kodo777.btatech.BtATech;
import net.minecraft.core.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RecipesSteamForge {
    private static final RecipesSteamForge smeltingBase = new RecipesSteamForge();
    private Map smeltingList = new HashMap();

    public static final RecipesSteamForge smelting() {
        return smeltingBase;
    }

    private RecipesSteamForge() {
        this.addSmelting(BtATech.plateIron.id, new ItemStack(BtATech.gearIron));
        this.addSmelting(BtATech.plateSteel.id, new ItemStack(BtATech.gearSteel));
        this.addSmelting(BtATech.plateCarbonEnrichedSteel.id, new ItemStack(BtATech.gearCarbonEnrichedSteel));
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
