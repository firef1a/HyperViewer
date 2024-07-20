package dev.fire.firemod.viewer;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.devutils.Base64Utils;
import dev.fire.firemod.devutils.GzipUtils;
import dev.fire.firemod.devutils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.Packet;
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
import java.util.Base64;

public class FunctionFinder {
    public ArrayList<Vec3i> checkedBlocks;
    public ArrayList<Vec3i> queueBlocks;
    public boolean isModifying;
    public FunctionFinder() {
        this.checkedBlocks = new ArrayList<>();
        this.queueBlocks = new ArrayList<>();
        this.isModifying = false;
    }

    public static void handlePacket(Packet packet) throws IOException {
        if (Firemod.MC.currentScreen == null && Firemod.MC.getNetworkHandler() != null) {
            if (packet instanceof ScreenHandlerSlotUpdateS2CPacket slot) {

                var nbt = slot.getStack().getNbt();

                 if (nbt != null){
                    var display = nbt.getCompound("display");
                    if (display != null) {
                        if (display.contains("Name", NbtElement.STRING_TYPE)) {
                            var bukkitvalues = nbt.getCompound("PublicBukkitValues");
                            if (bukkitvalues != null) {
                                if (bukkitvalues.contains("hypercube:codetemplatedata", NbtElement.STRING_TYPE)) {
                                    String hypercube = bukkitvalues.getString("hypercube:codetemplatedata").replace("'", "\"").toString();

                                    HCV codeValues = getHCVfromJSON(hypercube);

                                    String compressedCode = codeValues.code;
                                    String decompressedCode = GzipUtils.decompress(Base64Utils.decodeBase64Bytes(compressedCode));
                                    Firemod.functionDataManager.addFunction(decompressedCode);

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
        return Firemod.gson.fromJson(rawJson, HCV.class);

    }

    public void tick() {
        if (Firemod.MC.player != null && Firemod.MC.player.networkHandler != null && Firemod.MC.world != null) {
            //Thread newThread = new Thread(() -> {
            //
            //});
            //newThread.start();
            Vec3d playerPos = Firemod.MC.player.getPos();

            for (int cx = -4; cx < 4; cx++) {
                for (int cy = -4; cy < 4; cy++) {
                    for (int cz = -4; cz < 4; cz++) {
                        Vec3i blockPos = vecDoubleToVecInt(playerPos).add(cx,cy,cz);
                        if (!checkedBlocks.contains(blockPos)) {
                            Block blockState = Firemod.MC.world.getBlockState(new BlockPos(blockPos.getX(),blockPos.getY(),blockPos.getZ())).getBlock();
                            if (blockState == Blocks.LAPIS_BLOCK || blockState == Blocks.DIAMOND_BLOCK || blockState == Blocks.EMERALD_BLOCK || blockState == Blocks.GOLD_BLOCK) {
                                queueBlocks.add(blockPos);
                                checkedBlocks.add(blockPos);
                            }
                        }
                    }
                }
            }

            if (!queueBlocks.isEmpty()) {
                sendPacket(new ClientCommandC2SPacket(Firemod.MC.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));

                for (Vec3i eachBlock : queueBlocks) {
                    String func = "["+eachBlock.getX()+", {"+eachBlock.getY()+"}, {"+eachBlock.getZ()+"}]";
                    BlockPos blockPos = new BlockPos(eachBlock);
                    Firemod.LOGGER.info("FOUND FUNCTION: " + func);

                    sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(playerPos, Direction.WEST, blockPos, false), 1));
                }
                queueBlocks.clear();
                sendPacket(new ClientCommandC2SPacket(Firemod.MC.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));

            }

        }
    }

    public void sendPacket(Packet packet){
        Firemod.MC.player.networkHandler.sendPacket(packet);
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
