package com.strypel.overfear.items.tapeInfo;

import com.strypel.overfear.utils.RandomUtils;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TapeInfos {
    private static List<TapeInfo> infoTapes =  List.of(
            new TapeInfo("noise_tape_1",null,List.of(
                    Component.nullToEmpty("Text!")
            ),null),
            new TapeInfo("noise_tape_2",null,null,null)
    );
    public static TapeInfo getTapeInfoById(String id){
        for(TapeInfo info : TapeInfos.infoTapes){
            if(info.getId().equals(id)){
                return info;
            }
        }
        return null;
    }
    public static TapeInfo getRandomTapeInfo(){
        return TapeInfos.infoTapes.get((int) RandomUtils.rnd(0,TapeInfos.infoTapes.size()));
    }
    public static List<TapeInfo> getTapeInfos(){
        return TapeInfos.infoTapes;
    }
}
