package com.strypel.overfear.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.strypel.overfear.Overfear;
import com.strypel.overfear.menu.DataGeneratorMenu;
import com.strypel.overfear.network.PacketHandler;
import com.strypel.overfear.network.SetPointerSpeedDataGeneratorEntityPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;

import java.text.DecimalFormat;

public class DataGeneratorScreen  extends AbstractContainerScreen<DataGeneratorMenu> {

    public static ResourceLocation TEXTURE = new ResourceLocation(Overfear.MODID,"textures/gui/data_generator_test.png");
    public static ResourceLocation TEXTURE_OV_1 = new ResourceLocation(Overfear.MODID,"textures/gui/data_generator_overlay_1.png");
    private static final WidgetSprites ARROW_LEFT_SPRITES = new WidgetSprites(new ResourceLocation(Overfear.MODID,"data_generator/arrow_left"), new ResourceLocation(Overfear.MODID,"data_generator/arrow_left_r"));
    private static final WidgetSprites ARROW_RIGHT_SPRITES = new WidgetSprites(new ResourceLocation(Overfear.MODID,"data_generator/arrow_right"), new ResourceLocation(Overfear.MODID,"data_generator/arrow_right_r"));

    private DataGeneratorMenu menuT;
    private final int imageWidth;
    private final int imageHeight;
    float vhs_a = 118;

    public DataGeneratorScreen(DataGeneratorMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_,p_97742_,p_97743_);
        this.menuT = p_97741_;
        this.imageWidth = 176;
        this.imageHeight = 262;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ImageButton(this.leftPos + 39,this.topPos - 60 + 55,6,11,ARROW_LEFT_SPRITES,(p_93751_ -> {
            int sub = this.menuT.getPointerSpeed() - 1;
            PacketHandler.sendToServer(new SetPointerSpeedDataGeneratorEntityPacket(this.menuT.getBlockEntity().getBlockPos(), sub));
            this.minecraft.setScreen(this);
        })));
        addRenderableWidget(new ImageButton(this.leftPos + 85,this.topPos - 60 + 55,6,11,ARROW_RIGHT_SPRITES,(p_93751_ -> {
            int add = this.menuT.getPointerSpeed() + 1;
            PacketHandler.sendToServer(new SetPointerSpeedDataGeneratorEntityPacket(this.menuT.getBlockEntity().getBlockPos(), add));
            this.minecraft.setScreen(this);
        })));
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float v) {
        super.render(p_283479_, p_283661_, p_281248_, v);
        renderTooltip(p_283479_,p_283661_,p_281248_);
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float k, int xm, int ym) {
        renderTransparentBackground(guiGraphics);
        guiGraphics.blit(TEXTURE,this.leftPos,this.topPos - 60,0,0,this.imageWidth,this.imageHeight,512,512);
        boolean t = this.menuT.tapeSlotIsFull();
        if(t){
            guiGraphics.blit(TEXTURE,this.leftPos + 34,this.topPos - 60 + 156,176,0,47,4,512,512);
        }
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        renderScaledText(guiGraphics,this.menuT.getBlockEntity().pointer_speed > 0 ? "+"+this.menuT.getBlockEntity().pointer_speed : String.valueOf(this.menuT.getBlockEntity().pointer_speed),this.leftPos + 65,this.topPos - 60 + 58,FastColor.ARGB32.color(255,29,182,24),0.7F,true);
        renderScaledText(guiGraphics,"scanning efficiency: " + decimalFormat.format(this.menuT.getBlockEntity().processingEfficiency() * 100) + "%",this.leftPos + 39,this.topPos - 60 + 70,FastColor.ARGB32.color(255,29,182,24),0.42F);
        renderScaledText(guiGraphics,"x position: " + this.menuT.getCur_s(),this.leftPos + 39,this.topPos - 60 + 75,FastColor.ARGB32.color(255,29,182,24),0.42F);
        renderScaledText(guiGraphics,"voltage: " + "12.3V",this.leftPos + 39,this.topPos - 60 + 80,FastColor.ARGB32.color(255,29,182,24),0.42F);

        String status = "insert tape";
        if(this.menuT.tapeSlotIsFull()){
            if(this.menuT.getBlockEntity().scannedPercentage() >= 100){
                status = "completed";
            } else {
                status = "scanning...";
            }
        }
        //
        renderScaledText(guiGraphics,"Modifications:",this.leftPos + 116,this.topPos - 60 + 25,FastColor.ARGB32.color(255,29,182,24),0.56F,true);
        renderScaledText(guiGraphics,"Tape head: " + "standard",this.leftPos + 97,this.topPos - 60 + 33,FastColor.ARGB32.color(255,29,182,24),0.38F);
        renderScaledText(guiGraphics,"Radar: " + "standard",this.leftPos + 97,this.topPos - 60 + 38,FastColor.ARGB32.color(255,29,182,24),0.38F);

        renderScaledText(guiGraphics,"Status: " + status,this.leftPos + 97,this.topPos - 60 + 73,FastColor.ARGB32.color(255,29,182,24),0.42F);
        if(this.menuT.tapeSlotIsFull()){
            renderScaledText(guiGraphics,"Scanned: "+ decimalFormat.format(this.menuT.getBlockEntity().scannedPercentage()) + "%",this.leftPos + 97,this.topPos - 60 + 78,FastColor.ARGB32.color(255,29,182,24),0.42F);
        }
        renderScaledText(guiGraphics,"(C) FSMS 1986",this.leftPos + 102,this.topPos - 60 + 114,FastColor.ARGB32.color(255,29,182,24),0.42F);

        renderPointer(guiGraphics, (float) this.menuT.getCur_s() / 100,this.menuT.getBlockEntity().processingEfficiency());

        guiGraphics.pose().pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
        guiGraphics.pose().translate(0D,0D,200D);
        guiGraphics.blit(TEXTURE_OV_1,this.leftPos,this.topPos - 60,0,0,this.imageWidth,this.imageHeight,512,512);
        guiGraphics.pose().popPose();

        renderVHSBand(guiGraphics);
    }
    protected void renderScaledText(GuiGraphics guiGraphics,String text,int x,int y,int color,float scale,boolean centred){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale,scale,scale);
        if(centred){
            guiGraphics.drawCenteredString(this.font, text, (int) (x * (1 / scale)), (int) (y * (1 / scale)), color);
        } else {
            guiGraphics.drawString(this.font, text, (int) (x * (1 / scale)), (int) (y * (1 / scale)), color);
        }
        guiGraphics.pose().popPose();
    }
    protected void renderScaledText(GuiGraphics guiGraphics,String text,int x,int y,int color,float scale){
        this.renderScaledText(guiGraphics,text,x,y,color,scale,false);
    }

    protected void renderPointer(GuiGraphics guiGraphics, float p,float pEff){
        guiGraphics.pose().pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        float bright = (float) (pEff + 0.5);
        RenderSystem.setShaderColor(bright,bright,bright,1.0f);
        guiGraphics.blit(TEXTURE, (int) (this.leftPos + 41 + (45 * p)),this.topPos - 60 + 38,224,0,3,10,512,512);
        guiGraphics.pose().popPose();
    }
    protected void renderVHSBand(GuiGraphics guiGraphics){
        //21,118
        if(this.vhs_a > 21){
            this.vhs_a -= (float) (0.02314) * 30;
        } else {
            this.vhs_a = 118;
        }
        guiGraphics.pose().pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
        guiGraphics.pose().translate(0,0,200D);
        guiGraphics.blit(TEXTURE_OV_1,this.leftPos + 36, (int) (this.topPos - 60 + this.vhs_a),294,0,101,3,512,512);
        guiGraphics.pose().popPose();
    }

    @Override
    public void removed() {
        super.removed();
        this.menuT.getBlockEntity().triggerAnim("data_generator_controller", "onoff");
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int p_282681_, int p_283686_) {
    }
}
