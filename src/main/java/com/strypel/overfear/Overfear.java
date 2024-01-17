package com.strypel.overfear;

import com.strypel.overfear.block.client.DataGeneratorBlockEntityRenderer;
import com.strypel.overfear.client.gui.DataGeneratorScreen;
import com.strypel.overfear.client.gui.overlays.OFOverlayHandler;
import com.strypel.overfear.core.ModBlockEntities;
import com.strypel.overfear.core.ModEntities;
import com.strypel.overfear.core.ModMenus;
import com.strypel.overfear.core.RegistryManager;
import com.strypel.overfear.entity.client.SpectatorRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Overfear.MODID)
public class Overfear
{
    public static final String MODID = "overfear";
    private static OFOverlayHandler OF_OVERLAY_HANDLER;
    public Overfear() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryManager.setupRegistries(modbus);
    }

    public static OFOverlayHandler getOverlayHandler() {
        return OF_OVERLAY_HANDLER;
    }

    @Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            //MenuScreens.register(ModMenuTypes.ANALOG_CRAFTING_TABLE.get(), AnalogCraftingTableScreen::new);
            event.enqueueWork(() ->{
                if(Overfear.OF_OVERLAY_HANDLER == null){
                    Overfear.OF_OVERLAY_HANDLER = new OFOverlayHandler();
                    MinecraftForge.EVENT_BUS.register(Overfear.OF_OVERLAY_HANDLER);
                }
                MenuScreens.register(ModMenus.DATA_GENERATOR_MENU.get(), DataGeneratorScreen::new);
            });
            EntityRenderers.register(ModEntities.SPECTATOR.get(), SpectatorRender::new);
        }
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.DATA_GENERATOR.get(),context -> new DataGeneratorBlockEntityRenderer());
        }
    }
}
