package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager {
    public final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException{
        if(storage.list().isEmpty()){
            throw new NoVideoAvailableException();
        }

        Collections.sort(storage.list(), new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int temp = (int)(o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
                if(temp == 0){
                    int costForMillisecond1 = (int)((o1.getAmountPerOneDisplaying() / o1.getDuration()) * 1000);
                    int costForMillisecond2 = (int)((o2.getAmountPerOneDisplaying() / o2.getDuration()) * 1000);
                    return costForMillisecond1 - costForMillisecond2;
                }else{
                    return temp;
                }
            }
        });

        int arrayVideoLength = storage.list().size();
        int maxMoneyForVideo = 0;
        int luckyCombination = 0;
        for(int i = 0; i < ( 1 << arrayVideoLength ); i++){
            int tempVideoLength = 0;
            int tempMoneyForVideo = 0;
            for(int j = 0; j < arrayVideoLength; j++){
                int mask = 1 << j;
                if((i & mask) > 0){
                    tempVideoLength += storage.list().get(j).getDuration();
                    tempMoneyForVideo += storage.list().get(j).getAmountPerOneDisplaying();
                }
            }
            if(tempVideoLength <= timeSeconds  && tempMoneyForVideo > maxMoneyForVideo ){
                maxMoneyForVideo = tempMoneyForVideo;
                luckyCombination = i;
            }
        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(storage.list(), maxMoneyForVideo, timeSeconds));
        showVideos(luckyCombination, arrayVideoLength);
    }

    private void showVideos(int luckyCombination, int arrayVideoLength){

        for(int i = 0; i < arrayVideoLength; i++){
            if((luckyCombination & 1 << i) > 0){
                Advertisement advertisement = storage.list().get(i);
                int costForMillisecond = (int)(1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration());

                String message = String.format("%s is displaying... %d, %d", advertisement.getName(), advertisement.getAmountPerOneDisplaying(), costForMillisecond);
                ConsoleHelper.writeMessage(message);
                advertisement.revalidate();
            }
        }
    }
}
