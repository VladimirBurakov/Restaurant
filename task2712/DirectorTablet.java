package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {
    public void printAdvertisementProfit(){
        Map<Date, Double> map = StatisticManager.getInstance().getAdvertisementData();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Double totalSum = 0.0;
        Double sum = 0.0;
        String date = null;
        for(Map.Entry<Date, Double> entry: map.entrySet()){
            if(date == null){
                date = simpleDateFormat.format(entry.getKey());
                sum = entry.getValue();
                totalSum += entry.getValue();
            }else if(date.equals(simpleDateFormat.format(entry.getKey()))){
                sum += entry.getValue();
                totalSum += entry.getValue();
            }else{
                if(sum > 0.0){
                    ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"%s - %.02f", date, sum));
                }
                date = simpleDateFormat.format(entry.getKey());
                sum = entry.getValue();
                totalSum += entry.getValue();
            }
        }
        if(sum > 0.0){
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"%s - %.02f", date, sum));
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"Total - %.2f%n", totalSum));
        }
    }

    public void printCookWorkloading(){
        Map<Date, Map<String, Integer>> map = StatisticManager.getInstance().getCookData();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Map<String, Integer> resultMap = new TreeMap<>();
        int sum = 0;
        String cookName = null;
        String date = null;
        for(Map.Entry<Date, Map<String, Integer>> entry1: map.entrySet()){
            if(date == null){
                date = simpleDateFormat.format(entry1.getKey());
                for(Map.Entry<String, Integer> entry2: entry1.getValue().entrySet()){
                    cookName = entry2.getKey();
                    sum = entry2.getValue();
                    resultMap.put(cookName, sum);
                }
            }else if(date.equals(simpleDateFormat.format(entry1.getKey()))){
                for(Map.Entry<String, Integer> entry2: entry1.getValue().entrySet()){
                    if(resultMap.containsKey(entry2.getKey())){
                        cookName = entry2.getKey();
                        sum = entry2.getValue() + resultMap.get(cookName);
                        resultMap.put(cookName, sum);
                    }else{
                        cookName = entry2.getKey();
                        sum = entry2.getValue();
                        resultMap.put(cookName, sum);
                    }
                }
            }else if(!(date.equals(simpleDateFormat.format(entry1.getKey())))){
                if(!resultMap.isEmpty()){
                    ConsoleHelper.writeMessage(String.format("%s%n", date));
                    for(Map.Entry<String, Integer> entry : resultMap.entrySet()){
                        ConsoleHelper.writeMessage(String.format("%s - %d min", entry.getKey(), entry.getValue()));
                    }
                    System.out.println("");
                    resultMap.clear();

                    date = simpleDateFormat.format(entry1.getKey());
                    for(Map.Entry<String, Integer> entry2: entry1.getValue().entrySet()){
                        cookName = entry2.getKey();
                        sum = entry2.getValue();
                        resultMap.put(cookName, sum);
                    }
                }
            }
        }
        ConsoleHelper.writeMessage(String.format("%s", date));
        for(Map.Entry<String, Integer> entry : resultMap.entrySet()){
            ConsoleHelper.writeMessage(String.format("%s - %d min", entry.getKey(), entry.getValue()));
        }
        resultMap.clear();
    }
    public void printActiveVideoSet(){

    }
    public void printArchivedVideoSet(){

    }
}
