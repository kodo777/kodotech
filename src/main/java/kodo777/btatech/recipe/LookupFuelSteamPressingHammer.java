package kodo777.btatech.recipe;

import kodo777.btatech.BtATech;
import net.minecraft.src.LookupFuelFurnace;

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
        this.fuelList = new HashMap();
        this.addFuelEntry(BtATech.bucketSteam.itemID, 1600);
        this.addFuelEntry(BtATech.bottleSteam.itemID, 400);
    }
}
