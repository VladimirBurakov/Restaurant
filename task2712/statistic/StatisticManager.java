package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {
    private static StatisticManager statisticManager;
    private StatisticManager.StatisticStorage statisticStorage = this.new StatisticStorage();
    private Set<Cook> cooks = new HashSet();
    private StatisticManager() {
    }
    public static StatisticManager getInstance(){
        if(statisticManager == null){
            statisticManager = new StatisticManager();
        }
        return statisticManager;
    }
    public void register(EventDataRow data){
        statisticStorage.put(data);
    }
    /*public void registerMockData(){     // fake Data, remove this
        register(new CookedOrderEventDataRow("one", "Vasya", 300, new GregorianCalendar(2020, 2, 30, 1, 1).getTime()));
        register(new CookedOrderEventDataRow("one", "Amigos", 300, new GregorianCalendar(2020, 2, 30, 2, 2).getTime()));
        register(new CookedOrderEventDataRow("one", "Amigos", 300, new GregorianCalendar(2020, 2, 30, 3, 3).getTime()));
        register(new CookedOrderEventDataRow("one", "Vasya", 300, new GregorianCalendar(2020, 2, 30, 4, 4).getTime()));
        register(new CookedOrderEventDataRow("one", "Ben", 300, new GregorianCalendar(2020, 2, 30, 5, 5).getTime()));

        register(new CookedOrderEventDataRow("one", "Ben", 300, new GregorianCalendar(2020, 2, 29, 1, 1).getTime()));
        register(new CookedOrderEventDataRow("one", "Ben", 300, new GregorianCalendar(2020, 2, 29, 2, 2).getTime()));
        register(new CookedOrderEventDataRow("one", "Vasya", 300, new GregorianCalendar(2020, 2, 29, 3, 3).getTime()));

        register(new CookedOrderEventDataRow("one", "Vasya", 300, new GregorianCalendar(2020, 2, 27, 1, 1).getTime()));
        register(new CookedOrderEventDataRow("one", "Vasya", 300, new GregorianCalendar(2020, 2, 27, 2,2).getTime()));
    }*/
    public void register(Cook cook){
        cooks.add(cook);
    }
    public Map<Date, Double> getAdvertisementData(){
        Map<Date, Double> map = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> selectedVideosData = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);

        for(EventDataRow row: selectedVideosData){
            VideoSelectedEventDataRow videoSelectedEventDataRow = (VideoSelectedEventDataRow)row;
            //calendar мог бы быть
            if(!map.containsKey(videoSelectedEventDataRow.getDate())){
                map.put(videoSelectedEventDataRow.getDate(), videoSelectedEventDataRow.getAmount() / 100.0);
            }else{
                Double amount = map.get(videoSelectedEventDataRow.getDate());
                amount += videoSelectedEventDataRow.getAmount() / 100.0;
                map.put(videoSelectedEventDataRow.getDate(), amount);
            }
        }
        return map;
    }

    public Map<Date, Map<String, Integer>> getCookData(){
        Map<Date, Map<String, Integer>> map = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> cookedOrderData = statisticStorage.getStorage().get(EventType.COOKED_ORDER);

        for(EventDataRow cook: cookedOrderData){
            CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) cook;
            //calendar мог бы быть
            if(!map.containsKey(cookedOrderEventDataRow.getDate())){
                Map<String, Integer> cookMap = new TreeMap<>();
                cookMap.put(cookedOrderEventDataRow.getCookName(), (int)Math.ceil(cookedOrderEventDataRow.getTime() / 60.0));
                map.put(cookedOrderEventDataRow.getDate(), cookMap);
            }

        }
        return map;
    }

    private class StatisticStorage{
        private Map<EventType, List<EventDataRow>> storage = new TreeMap<>();
        StatisticStorage(){
            for(EventType type: EventType.values()){
                storage.put(type, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data){
            storage.get(data.getType()).add(data);
        }
        public Map<EventType, List<EventDataRow>> getStorage(){
            return storage;
        }
    }
}
