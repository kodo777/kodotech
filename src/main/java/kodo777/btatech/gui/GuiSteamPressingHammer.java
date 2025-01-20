package kodo777.btatech.gui;

import kodo777.btatech.container.ContainerSteamPressingHammer;
import kodo777.btatech.tileentity.TileEntitySteamPressingHammer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.util.helper.Textures;
import net.minecraft.core.player.inventory.IInventory;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;

public class GuiSteamPressingHammer extends GuiContainer {
    private TileEntitySteamPressingHammer steamPressingHammerInventory;

    private BufferedImage guiIMG = Textures.readImage(getClass().getResourceAsStream("/assets/kodotech/textures/gui/gui_steam_pressing_hammer.png"));
    private int guiTexture = Minecraft.getMinecraft(Minecraft.class).renderEngine.allocateAndSetupTexture(guiIMG);

    public GuiSteamPressingHammer(IInventory inventoryplayer, TileEntitySteamPressingHammer tileentitysteampressinghammer) {
        super(new ContainerSteamPressingHammer(inventoryplayer, tileentitysteampressinghammer));
        this.steamPressingHammerInventory = tileentitysteampressinghammer;
    }

    protected void drawGuiContainerForegroundLayer() {
        int argb = 0xFFFFFFFF;
        this.fontRenderer.drawString("Steam Pressing Hammer", 6, 5, argb);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 92, argb);
    }

    public void drawGuiContainerBackgroundLayer(float f) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(guiTexture);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        if (this.steamPressingHammerInventory.isBurning()) {
            int l = this.steamPressingHammerInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(j + 48, k + 36 + 12 - l, 176, 12 - l, 14, l + 2);
            int i1 = this.steamPressingHammerInventory.getCookProgressScaled(24);
            this.drawTexturedModalRect(j + 92, k + 34, 176, 14, i1 + 1, 16);
        }

    }
}
