package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message){
        System.out.println(message);
    }
    public static String readString() throws IOException {
        return reader.readLine();
    }
    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishList = new ArrayList<>();
        writeMessage(Dish.allDishesToString());
        writeMessage("Введите название блюда.");
        String dish = readString();
        while(!dish.equals("exit")){
            if(Dish.allDishesToString().contains(dish)){
                dishList.add(Dish.valueOf(dish));
                writeMessage("Введите название блюда.");
                dish = readString();
            }else if(!Dish.allDishesToString().contains(dish)){
                writeMessage("Такого блюда нет, выбирите ещё раз.");
                dish = readString();
            }
        }
        return dishList;
    }
}
