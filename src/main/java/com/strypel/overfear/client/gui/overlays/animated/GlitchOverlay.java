package com.strypel.overfear.client.gui.overlays.animated;

import com.mojang.blaze3d.systems.RenderSystem;
import com.strypel.overfear.Overfear;
import com.strypel.overfear.core.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.util.List;
import java.util.Random;

public class GlitchOverlay extends AnimatedOFOverlay {
    public static ResourceLocation background = new ResourceLocation(Overfear.MODID, "textures/gui/overlay/animated/glitch_01/g_01.png");
    private double t = 0;
    public boolean s_true = false;
    double a = 1;
    boolean a_flag = false;
    double s_speed_cs = 1.7314;
    double s_speed_os = 2.0314;
    protected double time;
    boolean end = false;
    private float a2 = 0.043F;

    public GlitchOverlay(Component title, double time) {
        super(title);
        this.time = time;
    }

    @Override
    protected void init() {
        super.init();
        int sizeX = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int sizeY = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        addAnimation(new Animation(Component.nullToEmpty("glitch_01"),List.of(
                new ResourceLocation(Overfear.MODID,"textures/gui/overlay/animated/glitch_01/g_01.png"),
                new ResourceLocation(Overfear.MODID,"textures/gui/overlay/animated/glitch_01/g_02.png"),
                new ResourceLocation(Overfear.MODID,"textures/gui/overlay/animated/glitch_01/g_03.png"),
                new ResourceLocation(Overfear.MODID,"textures/gui/overlay/animated/glitch_01/g_04.png"),
                new ResourceLocation(Overfear.MODID,"textures/gui/overlay/animated/glitch_01/g_05.png")
        ),0,0,sizeX,sizeY,sizeX,sizeY));
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.GLITCH_01.get(), 1.0F));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int p_253973_, int p_254325_, float p_254004_) {
        super.render(guiGraphics, p_253973_, p_254325_, p_254004_);
        // render black square
        this.t += 0.0002314;
        if(this.s_true){
          guiGraphics.pose().pushPose();
          RenderSystem.enableDepthTest();
          RenderSystem.depthMask(true);
          RenderSystem.enableBlend();
          RenderSystem.defaultBlendFunc();
          RenderSystem.setShader(GameRenderer::getPositionTexShader);
          RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f,1.0f);
          guiGraphics.fill(0,0,Minecraft.getInstance().getWindow().getGuiScaledWidth(),Minecraft.getInstance().getWindow().getGuiScaledHeight(), FastColor.ARGB32.color((int) this.a,0,0,0));
          if(this.a < 200 && !this.a_flag){
              this.a += this.s_speed_cs;
          } else {
              this.a_flag = true;
          }
          if(this.a_flag){
              if(this.a > 0){
                  this.a -= this.s_speed_os;
              } else {
                  this.s_true = false;
                  this.a_flag = false;
              }
          }
          guiGraphics.pose().popPose();
        } else {
            Random random = new Random();
            if(random.nextDouble() < 0.0019428373) {
                this.s_true = true;
                this.s_speed_cs = rnd(0.3314,0.9314);
                this.s_speed_os = rnd(0.7314,1.4314);
            }
        }
        if(((this.t * 100) / (this.time * 100)) * 100 >= 60) {
            this.a2 -= 0.0000068F;
        }
        //render glitch
        guiGraphics.pose().pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, a2);
        renderAnimation("glitch_01",guiGraphics,0,0,12,true);
        guiGraphics.pose().popPose();
        if(this.t > this.time){
            this.end = true;
            Overfear.getOverlayHandler().setOverlay(null);
        }
    }
    public static double rnd(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

}
