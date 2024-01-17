package com.strypel.overfear.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AreaUtils {
    public static BlockPos LevelAreaRandomPoint(BlockPos b1, BlockPos b2, Level level){
        Vec3 point_1 = b1.getCenter();
        Vec3 point_2 = b2.getCenter();
        if(point_2.x < point_1.x){
            double point_1_old = point_1.x;
            point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
            point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
        }
        if(point_2.y < point_1.y){
            double point_1_old = point_1.y;
            point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
            point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
        }
        if(point_2.z < point_1.z){
            double point_1_old = point_1.z;
            point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
            point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
        }

        int nX = (int) (point_1.x + (int)(Math.random() * ((point_2.x - point_1.x) + 1)));
        int nZ = (int) (point_1.z + (int)(Math.random() * ((point_2.z - point_1.z) + 1)));
        int nY = (int) point_2.y;
        short tryes = 0;
        int h = (int) point_2.y;
        while(h > point_1.y){
            if(h > point_1.y + 1 && !(level.getBlockState(new BlockPos(nX,h - 1,nZ)).getBlock() instanceof LiquidBlock)){
                if(level.getBlockState(new BlockPos(nX,h,nZ)).getBlock() instanceof AirBlock
                        && level.getBlockState(new BlockPos(nX,h + 1,nZ)).getBlock() instanceof AirBlock
                        && !(level.getBlockState(new BlockPos(nX,h - 1,nZ)).getBlock() instanceof AirBlock)
                        && !(level.getBlockState(new BlockPos(nX,h - 1,nZ)).getBlock() instanceof LeavesBlock)){
                    nY = h;
                    break;
                }
                h--;
            } else {
                if(tryes < 500){
                    tryes++;
                    nX = (int) (point_1.x + (int)(Math.random() * ((point_2.x - point_1.x) + 1)));
                    nZ = (int) (point_1.z + (int)(Math.random() * ((point_2.z - point_1.z) + 1)));
                    h = (int) point_2.y;
                } else {
                    break;
                }
            }
        }
        BlockPos toReturn = new BlockPos(nX,nY,nZ);
        return toReturn;
    }
    public static BlockPos getNearestBlockFromArea(Block block, BlockPos b1, BlockPos b2, Level level){
        Vec3 point_1 = b1.getCenter();
        Vec3 point_2 = b2.getCenter();
        if(point_2.x < point_1.x){
            double point_1_old = point_1.x;
            point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
            point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
        }
        if(point_2.y < point_1.y){
            double point_1_old = point_1.y;
            point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
            point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
        }
        if(point_2.z < point_1.z){
            double point_1_old = point_1.z;
            point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
            point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
        }
        int cube_y = (int) (point_2.y - point_1.y);
        int cube_x = (int) (point_2.x - point_1.x);
        int cube_z = (int) (point_2.z - point_1.z);
        BlockState[][][] blocks = new BlockState[cube_y][cube_x][cube_z];
        for (int y = 0; y < cube_y; y++) {
            for (int x = 0; x < cube_x; x++) {
                for (int z = 0; z < cube_z; z++) {
                    blocks[y][x][z] = level.getBlockState(new BlockPos((int) (point_1.x + x),(int) (point_1.y + y),(int) (point_1.z + z) ));
                    //System.out.print(blocks[y][x][z].getBlock().getName().getString() + "p:" + new BlockPos((int) (point_1.x + x),(int) (point_1.y + y), (int) (point_1.z + z)) + " ");
                }
                //System.out.println();
            }
            //System.out.println();
            //System.out.println();
        }
        BlockPos toReturn = null;
        for (int y = 0; y < cube_y; y++) {
            for (int x = 0; x < cube_x; x++) {
                for (int z = 0; z < cube_z; z++) {
                    if(blocks[y][x][z].is(block)){
                        toReturn = new BlockPos((int) (point_1.x + x),(int) (point_1.y + y),(int) (point_1.z + z) );
                    }
                }
            }
        }
        return toReturn;
    }
    public static BlockPos getNearestDoorFromArea(BlockPos b1, BlockPos b2, Level level){
        Vec3 point_1 = b1.getCenter();
        Vec3 point_2 = b2.getCenter();
        if(point_2.x < point_1.x){
            double point_1_old = point_1.x;
            point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
            point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
        }
        if(point_2.y < point_1.y){
            double point_1_old = point_1.y;
            point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
            point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
        }
        if(point_2.z < point_1.z){
            double point_1_old = point_1.z;
            point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
            point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
        }
        int cube_y = (int) (point_2.y - point_1.y);
        int cube_x = (int) (point_2.x - point_1.x);
        int cube_z = (int) (point_2.z - point_1.z);
        BlockState[][][] blocks = new BlockState[cube_y][cube_x][cube_z];
        for (int y = 0; y < cube_y; y++) {
            for (int x = 0; x < cube_x; x++) {
                for (int z = 0; z < cube_z; z++) {
                    blocks[y][x][z] = level.getBlockState(new BlockPos((int) (point_1.x + x),(int) (point_1.y + y),(int) (point_1.z + z) ));
                    //System.out.print(blocks[y][x][z].getBlock().getName().getString() + "p:" + new BlockPos((int) (point_1.x + x),(int) (point_1.y + y), (int) (point_1.z + z)) + " ");
                }
                //System.out.println();
            }
            //System.out.println();
            //System.out.println();
        }
        BlockPos toReturn = null;
        for (int y = 0; y < cube_y; y++) {
            for (int x = 0; x < cube_x; x++) {
                for (int z = 0; z < cube_z; z++) {
                    if(blocks[y][x][z].getBlock() instanceof DoorBlock){
                        toReturn = new BlockPos((int) (point_1.x + x),(int) (point_1.y + y),(int) (point_1.z + z) );
                    }
                }
            }
        }
        return toReturn;
    }
}
