package kodo777.btatech;

import kodo777.btatech.block.*;
import kodo777.btatech.item.*;
import kodo777.btatech.tileentity.*;

import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.*;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BtATech implements ModInitializer, RecipeEntrypoint {
    public static final String MOD_ID = "kodotech";
    public static final int BLOCK_ID = 1970;
    public static int blockId = BLOCK_ID;
    public static final int ITEM_ID = 19900;
    public static int itemId = ITEM_ID;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item stoneGear = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/stone_gear")
        .build(new Item("stone.gear", itemId++));
    public static final Item carbonEnrichedSteelIngot = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/carbon_enriched_steel_ingot")
        .build(new Item("carbon.enriched.steel.ingot", itemId++));
    public static final Item ashOfShame = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/ash_of_shame")
        .build(new Item("ash.of.shame", itemId++));
    public static final ItemBucketSteam bucketSteam = (ItemBucketSteam) new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/bucket_steam")
        .setStackSize(1)
        .setContainerItem(() -> Item.bucket)
        .build(new ItemBucketSteam("bucket.steam", itemId++));
    public static final Item bottle = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/bottle")
        .setStackSize(4)
        .build(new Item("bottle", itemId++));
    public static final Item bottleSteam = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/bottle_steam")
        .setStackSize(4)
        .build(new Item("bottle.steam", itemId++));
    public static final Item plateIron = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/iron_plate")
        .build(new Item("plate.iron", itemId++));
    public static final Item plateSteel = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/steel_plate")
        .build(new Item("plate.steel", itemId++));
    public static final Item plateCarbonEnrichedSteel = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/carbon_enriched_steel_plate")
        .build(new Item("plate.carbon.enriched.steel", itemId++));
    public static final Item gearIron = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/iron_gear")
        .build(new Item("gear.iron", itemId++));
    public static final Item gearSteel = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/steel_gear")
        .build(new Item("gear.steel", itemId++));
    public static final Item gearCarbonEnrichedSteel = new ItemBuilder(MOD_ID)
        .setIcon("kodotech:item/carbon_enriched_steel_gear")
        .build(new Item("gear.carbon.enriched.steel", itemId++));

    public static final Block steamBoilerIdle = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelHorizontalRotation<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_boiler", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new BlockSteamBoiler("steam.boiler.idle", blockId++, false));

    public static final Block steamBoilerActive = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelHorizontalRotation<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_boiler_active", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new BlockSteamBoiler("steam.boiler.active", blockId++, true));
            //.setNotInCreativeMenu();

    public static final Block steamPressingHammerIdle = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelHorizontalRotation<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_pressing_hammer", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new BlockSteamPressingHammer("steam.pressing.hammer.idle", blockId++, false));

    public static final Block steamPressingHammerActive = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelHorizontalRotation<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_pressing_hammer_active", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new BlockSteamPressingHammer("steam.pressing.hammer.active", blockId++, true));
            //.setNotInCreativeMenu();

    public static final Block steamMachineHull = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelStandard<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new Block("steam.machine.hull", blockId++, Material.stone));

    public static final Block steamForgeIdle = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelHorizontalRotation<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_forge", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new BlockSteamForge("steam.forge.idle", blockId++, false));

    public static final Block steamForgeActive = new BlockBuilder(MOD_ID)
            .setBlockModel(b -> new BlockModelHorizontalRotation<>(b).withTextures("kodotech:block/steam_boiler_top", "kodotech:block/steam_boiler_bottom", "kodotech:block/steam_forge_active", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side", "kodotech:block/steam_boiler_side"))
            .setBlockSound(BlockSounds.STONE)
            .setHardness(3f)
            .setResistance(2f)
            .build(new BlockSteamForge("steam.forge.active", blockId++, true));
            //.setNotInCreativeMenu();

    @Override
    public void onInitialize() {
        LOGGER.info("BtATech initialized.");
    }

    @Override
    public void onRecipesReady() {
        new RecipeBuilderShaped(MOD_ID, "AAA", "BCB", "DED")
            .addInput('A', Item.nethercoal)
            .addInput('B', stoneGear)
            .addInput('C', Block.furnaceStoneIdle)
            .addInput('D', Item.dustRedstone)
            .addInput('E', steamMachineHull)
            .create("steamBoiler", new ItemStack(steamBoilerIdle));

        new RecipeBuilderShaped(MOD_ID, "AAA", "B B", "CDC")
            .addInput('A', Block.brickClay)
            .addInput('B', stoneGear)
            .addInput('C', Item.dustRedstone)
            .addInput('D', Block.blockGold)
            .create("steamMachineHull", new ItemStack(steamMachineHull));

        new RecipeBuilderShaped(MOD_ID, "AAA", "BCB", "DED")
            .addInput('A', Block.blockIron)
            .addInput('B', stoneGear)
            .addInput('C', Item.ingotSteel)
            .addInput('D', Item.dustRedstone)
            .addInput('E', steamMachineHull)
            .create("steamPressingHammer", new ItemStack(steamPressingHammerIdle));

        new RecipeBuilderShaped(MOD_ID, "AAA", "BCB", "DED")
            .addInput('A', plateSteel)
            .addInput('B', stoneGear)
            .addInput('C', Block.blockSteel)
            .addInput('D', Item.dustRedstone)
            .addInput('E', steamMachineHull)
            .create("steamForge", new ItemStack(steamForgeIdle));

        new RecipeBuilderShaped(MOD_ID, " A ", "A A", " A ")
            .addInput('A', Block.cobbleStone)
            .create("stoneGear", new ItemStack(stoneGear));

        new RecipeBuilderShaped(MOD_ID, "   ", "A A", " A ")
            .addInput('A', Block.glass)
            .create("bottle", new ItemStack(bottle));

        new RecipeBuilderShaped(MOD_ID, "ABB", "BB ", "   ")
            .addInput('A', bucketSteam)
            .addInput('B', bottle)
            .create("bottleSteam", new ItemStack(bottleSteam, 4));

        new RecipeBuilderShaped(MOD_ID, "ABB", "BB ", "   ")
            .addInput('A', Item.bucket)
            .addInput('B', bottleSteam)
            .create("bottleSteam", new ItemStack(bucketSteam));

        RecipeBuilder.BlastFurnace(MOD_ID).setInput(Item.ingotSteel).create("carbonEnrichedSteelIngot", new ItemStack(carbonEnrichedSteelIngot));
        RecipeBuilder.BlastFurnace(MOD_ID).setInput(carbonEnrichedSteelIngot).create("ashOfShame", new ItemStack(ashOfShame));

        EntityHelper.createTileEntity(TileEntitySteamBoiler.class, "tileEntitySteamBoiler");
        EntityHelper.createTileEntity(TileEntitySteamPressingHammer.class, "tileEntitySteamPressingHammer");
        EntityHelper.createTileEntity(TileEntitySteamForge.class, "tileEntitySteamForge");
    }

    @Override
    public void initNamespaces() {
        RecipeBuilder.initNameSpace(MOD_ID);
        RecipeBuilder.getRecipeNamespace(MOD_ID);
    }
}
