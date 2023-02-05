package kodo777.btatech.gui;

import kodo777.btatech.container.ContainerSteamBoiler;
import kodo777.btatech.tileentity.TileEntitySteamBoiler;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.helper.Textures;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;

public class GuiSteamBoiler extends GuiContainer {
    private final TileEntitySteamBoiler steamBoilerInventory;

    private final BufferedImage guiIMG = Textures.readImage(getClass().getResourceAsStream("/assets/btatech/gui/gui_steam_boiler.png"));
    private final int guiTexture = Minecraft.getMinecraft().renderEngine.allocateAndSetupTexture(guiIMG);

    public GuiSteamBoiler(InventoryPlayer inventoryplayer, TileEntitySteamBoiler tileentitysteamboiler) {
        super(new ContainerSteamBoiler(inventoryplayer, tileentitysteamboiler));
        this.steamBoilerInventory = tileentitysteamboiler;
    }

    protected void drawGuiContainerForegroundLayer() {
        int argb = 0xFFFFFFFF;
        this.fontRenderer.drawString("Steam Boiler", 6, 5, argb);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 92, argb);
    }

    public void drawGuiContainerBackgroundLayer(float f) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(guiTexture);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        if (this.steamBoilerInventory.isBurning()) {
            int l = this.steamBoilerInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(j + 56, k + 36 + 12 - l, 176, 12 - l, 14, l + 2);
            int i1 = this.steamBoilerInventory.getCookProgressScaled(24);
            this.drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
        }

    }
}
