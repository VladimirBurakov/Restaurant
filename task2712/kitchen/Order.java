package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;
    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getTotalCookingTime(){
        int temp = 0;
        for(Dish dish: dishes){
            temp += dish.getDuration();
        }
        return temp;
    }

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        if(dishes == null){
            return "";
        }
        for(Dish dish: dishes){
            stringJoiner.add(dish.name());
        }
        return String.format("Your order: %s of %s, cooking time %dmin", stringJoiner.toString(), tablet.toString(), getTotalCookingTime());
    }
}
