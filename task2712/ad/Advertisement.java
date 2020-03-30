package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content;
    private String name;
    private long initialAmount;  //начальная сумма, стоимость всей рекламы в копейках
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;


    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        amountPerOneDisplaying = hits != 0 ? initialAmount / hits : 0;
    }

    public String getName() {
        return name;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public int getDuration() {
        return duration;
    }

    public void revalidate() throws NoVideoAvailableException{
        if(hits <= 0){
            throw new UnsupportedOperationException();
        }
        hits--;
    }
}
