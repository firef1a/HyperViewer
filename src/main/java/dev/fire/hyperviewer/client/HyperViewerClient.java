package dev.fire.hyperviewer.client;

import dev.fire.hyperviewer.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class HyperViewerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();


    }
}