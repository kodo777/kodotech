package kodo777.btatech.recipe;

import kodo777.btatech.BtATech;
import net.minecraft.core.crafting.LookupFuelFurnace;

import java.util.HashMap;

public class LookupFuelSteamPressingHammer extends LookupFuelFurnace {
    private static final LookupFuelSteamPressingHammer fuelBase = new LookupFuelSteamPressingHammer();

    public void addFuelEntry(int id, int fuelYield) {
        this.fuelList.put(id, fuelYield);
    }

    public static LookupFuelSteamPressingHammer fuelSteamPressingHammer() {
        return fuelBase;
    }

    protected LookupFuelSteamPressingHammer() {
        this.addFuelEntry(BtATech.bucketSteam.id, 1600);
        this.addFuelEntry(BtATech.bottleSteam.id, 400);
    }
}
