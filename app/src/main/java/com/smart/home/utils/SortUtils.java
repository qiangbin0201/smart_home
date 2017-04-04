package com.smart.home.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by admin on 16/5/27.
 */
public class SortUtils {

    public static <T, V> Map<T, V> sortMap(Map<T, V> maps) {
        if (maps == null || maps.size() == 0)
            return null;

        Map<T, V> sortedMap = new TreeMap<>((Comparator<T>) new Comparator<T>() {
            @Override
            public int compare(T key1, T key2) {
                if (key1 instanceof String) {
                    return ((String) key1).compareTo((String) key2);

                } else if (key1 instanceof Integer) {
                    return ((Integer) key1).compareTo((Integer) key2);

                }

                return key1.equals(key2) ? 0 : 1;


            }
        });
        sortedMap.putAll(maps);
        return sortedMap;
    }
}
