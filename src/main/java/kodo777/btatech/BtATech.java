package kodo777.btatech;

import kodo777.btatech.block.BlockSteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.RecipeHelper;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.mixin.accessors.BlockAccessor;


public class BtATech implements ModInitializer {
    public static final String MOD_ID = "btatech";
    public static final int BLOCK_ID = 2000;
    public static final int ITEM_ID = 140;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return BtATech.MOD_ID + "." + name;
    }

    public static final Item stoneGear = new Item(ITEM_ID + 1).setIconCoord(19, 0).setItemName(name("stone.gear"));
    public static final Item carbonEnrichedSteelIngot = new Item(ITEM_ID + 2).setIconCoord(19, 1).setItemName(name("carbon.enriched.steel.ingot"));
    public static final Item ashOfShame = new Item(ITEM_ID + 3).setIconCoord(19, 2).setItemName(name("ash.of.shame"));

    public static final Block steamBoilerIdle = BlockHelper.createBlock(MOD_ID,
            new BlockSteamBoiler(BLOCK_ID + 1, false),
            "steam.boiler.idle",
            "steam_boiler_top.png", "steam_boiler_bottom.png", "steam_boiler.png", "steam_boiler_side.png", "steam_boiler_side.png", "steam_boiler_side.png",
            Block.soundStoneFootstep,
            3f,
            2f,
            0f);
    static {
        ((BlockAccessor) steamBoilerIdle).callSetImmovable();
    }
    public static final Block steamBoilerActive = BlockHelper.createBlock(MOD_ID,
            new BlockSteamBoiler(BLOCK_ID + 2, true),
            "steam.boiler.active",
            "steam_boiler_top.png", "steam_boiler_bottom.png", "steam_boiler_active.png", "steam_boiler_side.png", "steam_boiler_side.png", "steam_boiler_side.png",
            Block.soundStoneFootstep,
            3f,
            2f,
            1f).setNotInCreativeMenu();
    static {
        ((BlockAccessor) steamBoilerActive).callSetImmovable();
    }


    @Override
    public void onInitialize() {
        LOGGER.info("BtATech initialized.");

        RecipeHelper.Crafting.createRecipe(steamBoilerIdle, 1, new Object[]{"AAA", "BCB", "DED", 'A', Block.brickClay, 'B', Item.ingotGold, 'C', Block.furnaceStoneIdle, 'D', Block.blockGold, 'E', stoneGear});
        RecipeHelper.Crafting.createRecipe(stoneGear, 1, new Object[]{"#A#", "A#A", "#A#", 'A', Block.cobbleStone});
        RecipeHelper.Blasting.createRecipe(carbonEnrichedSteelIngot, Item.ingotSteel);
        RecipeHelper.Blasting.createRecipe(ashOfShame, carbonEnrichedSteelIngot);

        TextureHelper.addTextureToItems(MOD_ID, "stone_gear.png",19, 0);
        TextureHelper.addTextureToItems(MOD_ID, "carbon_enriched_steel_ingot.png", 19, 1);
        TextureHelper.addTextureToItems(MOD_ID, "ash_of_shame.png", 19, 2);

        EntityHelper.createTileEntity(TileEntitySteamBoiler.class, "tileEntitySteamBoiler");

        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler.png", 31, 0);
        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_active.png", 31, 1);
        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_side.png", 31, 2);
        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_top.png", 31, 3);
    }
}
