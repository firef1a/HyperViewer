package dev.fire.hyperviewer.client;

import dev.fire.hyperviewer.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;


@Environment(EnvType.CLIENT)
public class HyperViewerClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    // The KeyBinding declaration and registration are commonly executed here statically

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();


    }
}