package com.strypel.overfear.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.strypel.overfear.Overfear;
import com.strypel.overfear.menu.AnalogCraftingTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AnalogCraftingTableScreen extends AbstractContainerScreen<AnalogCraftingTableMenu> {
    public static ResourceLocation TEXTURE = new ResourceLocation(Overfear.MODID,"textures/gui/analog_crafting_table.png");
    private AnalogCraftingTableMenu menu;
    public AnalogCraftingTableScreen(AnalogCraftingTableMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        this.menu = p_97741_;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float k, int xm, int ym) {
        renderTransparentBackground(p_283065_);
        p_283065_.blit(TEXTURE,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
        int i = this.menu.getProgress();
        double a = i * 22 / 100;
        int d = this.menu.getData();
        double da = d * 22 / 64;
        double dp = d * 100 / 64;
        p_283065_.blit(TEXTURE,this.leftPos + 90,this.topPos + 22,176,3, (int) a,15);
        p_283065_.blit(TEXTURE,this.leftPos + 90,this.topPos + 64,176,0, (int) da,3);
        if(xm >= this.leftPos + 90 && xm <= this.leftPos + 90 + 22){
            if(ym >= this.topPos + 47 && ym <= this.topPos + 66){
                p_283065_.pose().pushPose();
                RenderSystem.enableDepthTest();
                p_283065_.pose().translate(0,0,800D);
                p_283065_.blit(TEXTURE,xm,ym,0,166, 120,32);
                p_283065_.drawString(this.font,"Data: "+(int)dp+"%",xm + 5,ym + 5,16777215);
                p_283065_.pose().popPose();
            }
        }
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
        renderTooltip(p_283479_,p_283661_,p_281248_);
    }
}
