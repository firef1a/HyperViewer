package dev.fire.hyperviewer.viewer;

import dev.fire.hyperviewer.HyperViewer;
import dev.fire.hyperviewer.devutils.Base64Utils;
import dev.fire.hyperviewer.devutils.GzipUtils;
import dev.fire.hyperviewer.devutils.MathUtils;
import dev.fire.hyperviewer.screen.utils.FunctionEntry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class FunctionFinder {
    public ArrayList<Vec3i> checkedBlocks;
    public ArrayList<Vec3i> queueBlocks;
    public HashMap<FunctionEntry, Vec3i> functionLocationMap;
    public boolean isModifying;
    public static int outstandingRequests;
    public FunctionFinder() {
        this.checkedBlocks = new ArrayList<>();
        this.queueBlocks = new ArrayList<>();
        this.functionLocationMap = new HashMap<>();
        this.isModifying = false;
        outstandingRequests = 0;
    }

    public static void handlePacket(Packet packet) throws IOException {
        if (HyperViewer.MC.currentScreen == null && HyperViewer.MC.getNetworkHandler() != null && HyperViewer.MC.player.isCreative()) {
            if (packet instanceof ScreenHandlerSlotUpdateS2CPacket slot) {

                var nbt = slot.getStack().getNbt();

                 if (nbt != null){
                    var display = nbt.getCompound("display");
                    if (display != null) {
                        if (display.contains("Name", NbtElement.STRING_TYPE)) {
                            var bukkitvalues = nbt.getCompound("PublicBukkitValues");
                            if (bukkitvalues != null) {
                                if (bukkitvalues.contains("hypercube:codetemplatedata", NbtElement.STRING_TYPE)) {
                                    if (outstandingRequests > 0) {
                                        outstandingRequests--;

                                        sendPacket(new CreativeInventoryActionC2SPacket(slot.getSlot(), ItemStack.EMPTY));
                                        String hypercube = bukkitvalues.getString("hypercube:codetemplatedata").replace("'", "\"").toString();

                                        HCV codeValues = getHCVfromJSON(hypercube);

                                        String compressedCode = codeValues.code;
                                        String decompressedCode = GzipUtils.decompress(Base64Utils.decodeBase64Bytes(compressedCode));
                                        HyperViewer.functionDataManager.addFunction(decompressedCode);
                                        //new TeleportConfirmC2SPacket()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public static class HCV {
        String author;
        String name;
        int version;
        String code;
    }

    public static HCV getHCVfromJSON(String rawJson) {
        return HyperViewer.gson.fromJson(rawJson, HCV.class);

    }

    public void tick() {
        if (HyperViewer.MC.player != null && HyperViewer.MC.player.networkHandler != null && HyperViewer.MC.world != null && HyperViewer.MC.player.isCreative() && HyperViewer.functionDataManager.enableCodeViewer) {

            //Thread newThread = new Thread(() -> {
            //
            //});
            //newThread.start();
            Vec3d playerPos = HyperViewer.MC.player.getPos();

            if (!queueBlocks.isEmpty()) {
                sendPacket(new ClientCommandC2SPacket(HyperViewer.MC.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));

                for (Vec3i eachBlock : queueBlocks) {
                    String func = "["+eachBlock.getX()+", {"+eachBlock.getY()+"}, {"+eachBlock.getZ()+"}]";
                    BlockPos blockPos = new BlockPos(eachBlock);
                    HyperViewer.LOGGER.info("FOUND FUNCTION: " + func);

                    outstandingRequests++;
                    sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(getVec3dFromBlock(blockPos), Direction.UP, blockPos, false), 10));
                    //Firemod.MC.interactionManager.interactBlock(Firemod.MC.player, Hand.MAIN_HAND, new BlockHitResult(playerPos, Direction.UP, blockPos, true));

                }
                queueBlocks.clear();
                sendPacket(new ClientCommandC2SPacket(HyperViewer.MC.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));

            }

            for (int cx = -4; cx < 4; cx++) {
                for (int cy = -4; cy < 4; cy++) {
                    for (int cz = -4; cz < 4; cz++) {
                        Vec3i blockPos = vecDoubleToVecInt(playerPos).add(cx,cy,cz);
                        if (!checkedBlocks.contains(blockPos)) {
                            BlockPos bp = new BlockPos(blockPos.getX(),blockPos.getY(),blockPos.getZ());
                            Block blockState = HyperViewer.MC.world.getBlockState(bp).getBlock();
                            if (blockState == Blocks.LAPIS_BLOCK || blockState == Blocks.DIAMOND_BLOCK || blockState == Blocks.EMERALD_BLOCK || blockState == Blocks.GOLD_BLOCK) {
                                queueBlocks.add(blockPos);
                                checkedBlocks.add(blockPos);

                                HyperViewer.MC.player.teleport(blockPos.getX(),blockPos.getY(),blockPos.getZ());
                            }
                        }
                    }
                }
            }

        }
    }

    public static void sendPacket(Packet packet){
        HyperViewer.MC.player.networkHandler.sendPacket(packet);
    }
    public Vec3d getVec3dFromBlock(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }
    public Vec3i vecDoubleToVecInt(Vec3d pos) {
        Vec3d r = MathUtils.align(pos);
        return new Vec3i((int) r.getX(),(int) r.getY(),(int) r.getZ());
    }
    public boolean compareVec3d(Vec3d pos1, Vec3d pos2) {
        if (pos1 != null && pos2 != null) {
            if (pos1.getX() == pos2.getX()) {
                if (pos1.getY() == pos2.getY()) {
                    if (pos1.getZ() == pos2.getZ()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



}
