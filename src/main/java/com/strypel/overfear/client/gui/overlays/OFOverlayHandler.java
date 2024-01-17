package com.strypel.overfear.client.gui.overlays;

import com.strypel.overfear.Overfear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Overfear.MODID)
public class OFOverlayHandler {
    private OFOverlay overlay;
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void RenderSpyglassOverlay(RenderGuiOverlayEvent event) {
        if(overlay != null && event.getGuiGraphics() != null){
            GuiGraphics graphics = event.getGuiGraphics();
            if(Minecraft.getInstance().player != null && Minecraft.getInstance().player.isAlive()){
                overlay.render(graphics,0,0,0);
            } else {
                Minecraft.getInstance().getSoundManager().stop();
                this.overlay = null;
            }
        }
    }

    public void setOverlay(OFOverlay overlay) {
        this.overlay = overlay;
        if(this.overlay != null){
            this.overlay.init();
        }
    }

    public OFOverlay getOverlay() {
        return this.overlay;
    }
}
