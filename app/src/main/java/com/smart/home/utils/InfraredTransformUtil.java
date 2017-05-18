package com.smart.home.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2017/5/17.
 */

public class InfraredTransformUtil {

    public static String hex2dec(String irData)  // 返回值:将前4个数去掉，空格变为逗号，第一个数为载波频率
    {
        List<String> list = new ArrayList<String>(Arrays.asList(irData.split(" ")));
        list.remove(0);
        int frequency = Integer.parseInt(list.remove(0), 16);
        list.remove(0);
        list.remove(0);
        for (int i = 0; i < list.size(); i++)
        {
            list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
        }
        frequency = (int) (1000000 / (frequency * 0.241246));
        list.add(0, Integer.toString(frequency));
        irData = "";
        for (String s : list)
        {
            irData += s + ",";
        }
        return irData;
    }


    public static String count2duration(String countPattern)
    {
        List<String> list = new ArrayList<String>(Arrays.asList(countPattern.split(",")));
        int frequency = Integer.parseInt(list.get(0));
        int pulses = 1000000/frequency;
        int count;
        int duration;
        for (int i = 1; i < list.size(); i++)
        {
            count = Integer.parseInt(list.get(i));
            duration = count * pulses;
            list.set(i, Integer.toString(duration));
        }

        String durationPattern = "";
        for (String s : list)
        {
            durationPattern += s + ",";
        }
        return durationPattern;
    }

    //格力空调编码
    public static int[] hex2time(String hex){
        List<String> list = new ArrayList<>(Arrays.asList(hex.split(" ")));

        String patternStr = "";
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals("S")){
                patternStr += "9000 4500 ";

            }else if(list.get(i).equals("0")){
                patternStr += "600 600 ";
            }else if(list.get(i).equals("1")){
                patternStr += "600 1600 ";
            }else if(list.get(i).equals("C")){
                patternStr += "600 20000 ";
            }
        }
        String[] patternArray = patternStr.split(" ");
        int [] pattern = new int[patternArray.length];
        for (int i = 0; i < patternArray.length; i++){
            pattern[i] = Integer.parseInt(patternArray[i]);
        }
        return pattern;
    }
}
