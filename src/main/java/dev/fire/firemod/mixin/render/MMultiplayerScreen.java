package dev.fire.firemod.mixin.render;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.CodeScreen;
import dev.fire.firemod.screen.widget.BlendableTexturedButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.block.BlockModelRenderer;


@Mixin(MultiplayerScreen.class)
public class MMultiplayerScreen extends Screen {
    @Unique
    private final Identifier identifier_main = new Identifier(Firemod.MOD_ID + ":scripts");
    @Unique
    private final Identifier identifier_main_highlight = new Identifier(Firemod.MOD_ID + ":scripts_highlight");

    protected MMultiplayerScreen(Text title) {
        super(title);
    }
    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    private void init(CallbackInfo ci) {
        Firemod.LOGGER.info("testing");
        this.addDrawableChild(new BlendableTexturedButtonWidget(100, 5, 20, 20, identifier_main, identifier_main_highlight, (button) -> {
            CodeScreen screen = new CodeScreen(Text.literal("hi"), this);
            Firemod.MC.setScreen(screen);
        }));
    }
}
