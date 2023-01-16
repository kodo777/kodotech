package kodo777.btatech;

import net.fabricmc.api.ModInitializer;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.TextureHelper;


public class BtATech implements ModInitializer {
    public static final String MOD_ID = "btatech";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return BtATech.MOD_ID + "." + name;
    }

    public static final Block steamBoiler = BlockHelper.createBlock(new Block(1000, Material.rock), name("steam.boiler"), 31, 2, 17, 6, 31, 0, 31, 1, 31, 1, 31, 1, Block.soundStoneFootstep, 0.5f, 0.5f, 0f);

    @Override
    public void onInitialize() {
        LOGGER.info("BtATech initialized.");

        TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler.png", 31, 0);
        TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_side.png", 31, 1);
        TextureHelper.addTextureToTerrain(MOD_ID, "steam_boiler_top.png", 31, 2);
    }
}
