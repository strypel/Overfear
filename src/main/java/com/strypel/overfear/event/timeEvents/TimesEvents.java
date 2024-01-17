package com.strypel.overfear.event.timeEvents;

import java.util.ArrayList;
import java.util.List;

public class TimesEvents {
    private static List<TimeEvent> timeEvents = new ArrayList<>();

    public static void registerEvent(TimeEvent event){
        timeEvents.add(event);
    }

    protected static void unRegisterEvent(TimeEvent event){
        timeEvents.remove(event);
    }
}
