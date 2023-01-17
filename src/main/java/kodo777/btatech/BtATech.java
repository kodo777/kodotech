package kodo777.btatech;

import kodo777.btatech.block.BlockSteamBoiler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.RecipeHelper;
import turniplabs.halplibe.helper.TextureHelper;


public class BtATech implements ModInitializer {
    public static final String MOD_ID = "btatech";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return BtATech.MOD_ID + "." + name;
    }

    public static final Item stoneGear = new Item(140).setIconCoord(19, 0).setItemName(name("stone.gear"));

    public static final Block steamBoiler = BlockHelper.createBlock(
            new BlockSteamBoiler(1001 + 14, Material.rock),
            name("steam.boiler"),
            31,0,
            Block.soundStoneFootstep,
            3f,
            2f,
            0f);


    @Override
    public void onInitialize() {
        LOGGER.info("BtATech initialized.");

        RecipeHelper.Crafting.createRecipe(steamBoiler, 1, new Object[]{"AAA", "BCB", "DED", 'A', Block.brickClay, 'B', Item.ingotGold, 'C', Block.furnaceStoneIdle, 'D', Block.blockGold, 'E', stoneGear});
        RecipeHelper.Crafting.createRecipe(stoneGear, 1, new Object[]{"#A#", "A#A", "#A#", 'A', Block.cobbleStone});

        TextureHelper.addTextureToItems(MOD_ID, "stone_gear.png",19, 0);

        TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler.png", 31, 0);
        TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_side.png", 31, 1);
        TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_top.png", 31, 2);
    }
}
