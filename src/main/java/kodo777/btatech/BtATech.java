package kodo777.btatech;

import kodo777.btatech.block.BlockSteamBoiler;
import kodo777.btatech.block.BlockSteamPressingHammer;
import kodo777.btatech.item.ItemBucketSteam;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.src.*;
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
    public static final ItemBucketSteam bucketSteam = (ItemBucketSteam) new ItemBucketSteam(ITEM_ID + 4).setIconCoord(19, 3).setItemName(name("bucket.steam")).setMaxStackSize(1).setContainerItem(Item.bucket);
    public static final Item bottle = new Item(ITEM_ID + 5).setIconCoord(19, 4).setItemName(name("bottle")).setMaxStackSize(4);
    public static final Item bottleSteam = new Item(ITEM_ID + 6).setIconCoord(19, 5).setItemName(name("bottle.steam")).setMaxStackSize(4);
    public static final Item plateIron = new Item(ITEM_ID + 7).setIconCoord(19, 6).setItemName(name("plate.iron"));
    public static final Item plateSteel = new Item(ITEM_ID + 8).setIconCoord(19, 7).setItemName(name("plate.steel"));
    public static final Item plateCarbonEnrichedSteel = new Item(ITEM_ID + 9).setIconCoord(19, 8).setItemName(name("plate.carbon.enriched.steel"));

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

    public static final Block steamPressingHammerIdle = BlockHelper.createBlock(MOD_ID,
            new BlockSteamPressingHammer(BLOCK_ID + 3, false),
            "steam.pressing.hammer.idle",
            "steam_boiler_top.png", "steam_boiler_bottom.png", "steam_pressing_hammer.png", "steam_boiler_side.png", "steam_boiler_side.png", "steam_boiler_side.png",
            Block.soundStoneFootstep,
            3f,
            2f,
            0f);
    static {
        ((BlockAccessor) steamPressingHammerIdle).callSetImmovable();
    }

    public static final Block steamPressingHammerActive = BlockHelper.createBlock(MOD_ID,
            new BlockSteamPressingHammer(BLOCK_ID + 4, true),
            "steam.pressing.hammer.active",
            "steam_boiler_top.png", "steam_boiler_bottom.png", "steam_pressing_hammer_active.png", "steam_boiler_side.png", "steam_boiler_side.png", "steam_boiler_side.png",
            Block.soundStoneFootstep,
            3f,
            2f,
            1f).setNotInCreativeMenu();
    static {
        ((BlockAccessor) steamPressingHammerActive).callSetImmovable();
    }

    public static final Block steamMachineHull = BlockHelper.createBlock(MOD_ID,
            new Block(BLOCK_ID + 5, Material.rock),
            "steam.machine.hull",
            "steam_boiler_top.png", "steam_boiler_bottom.png", "steam_boiler_side.png",
            Block.soundStoneFootstep,
            3f,
            2f,
            0f);

    @Override
    public void onInitialize() {
        LOGGER.info("BtATech initialized.");

        RecipeHelper.Crafting.createRecipe(steamBoilerIdle, 1, new Object[]{"AAA", "BCB", "DED", 'A', Item.nethercoal, 'B', stoneGear, 'C', Block.furnaceStoneIdle, 'D', Item.dustRedstone, 'E', steamMachineHull});
        RecipeHelper.Crafting.createRecipe(steamMachineHull, 1, new Object[]{"AAA", "B#B", "CDC", 'A', Block.brickClay, 'B', stoneGear, 'C', Item.dustRedstone, 'D', Block.blockGold});
        RecipeHelper.Crafting.createRecipe(steamPressingHammerIdle, 1, new Object[]{"AAA", "BCB", "DED", 'A', Block.blockIron, 'B', stoneGear, 'C', Item.ingotSteel, 'D', Item.dustRedstone, 'E', steamMachineHull});
        RecipeHelper.Crafting.createRecipe(stoneGear, 1, new Object[]{"#A#", "A#A", "#A#", 'A', Block.cobbleStone});
        RecipeHelper.Crafting.createRecipe(bottle, 4, new Object[]{"###", "A#A", "#A#", 'A', Block.glass});
        RecipeHelper.Crafting.createRecipe(bottleSteam, 4, new Object[]{"ABB", "BB#", "###", 'A', bucketSteam, 'B', bottle});
        RecipeHelper.Crafting.createRecipe(bucketSteam, 1, new Object[]{"ABB", "BB#", "###", 'A', Item.bucket, 'B', bottleSteam});
        RecipeHelper.Blasting.createRecipe(carbonEnrichedSteelIngot, Item.ingotSteel);
        RecipeHelper.Blasting.createRecipe(ashOfShame, carbonEnrichedSteelIngot);

        TextureHelper.addTextureToItems(MOD_ID, "stone_gear.png",19, 0);
        TextureHelper.addTextureToItems(MOD_ID, "carbon_enriched_steel_ingot.png", 19, 1);
        TextureHelper.addTextureToItems(MOD_ID, "ash_of_shame.png", 19, 2);
        TextureHelper.addTextureToItems(MOD_ID, "bucket_steam.png", 19, 3);
        TextureHelper.addTextureToItems(MOD_ID, "bottle.png", 19, 4);
        TextureHelper.addTextureToItems(MOD_ID, "bottle_steam.png", 19, 5);
        TextureHelper.addTextureToItems(MOD_ID, "iron_plate.png", 19, 6);
        TextureHelper.addTextureToItems(MOD_ID, "steel_plate.png", 19, 7);
        TextureHelper.addTextureToItems(MOD_ID, "carbon_enriched_steel_plate.png", 19, 8);

        EntityHelper.createTileEntity(TileEntitySteamBoiler.class, "tileEntitySteamBoiler");
        EntityHelper.createTileEntity(TileEntitySteamPressingHammer.class, "tileEntitySteamPressingHammer");

        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler.png", 31, 0);
        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_active.png", 31, 1);
        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_side.png", 31, 2);
        //TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_top.png", 31, 3);
    }
}
