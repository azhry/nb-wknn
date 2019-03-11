/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Azhary Arliansyah
 */
public class Console {
    
    public static void printMap(Map<String, Integer> m) {
        m.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }
    
    public static void printMapIntegerDouble(Map<Integer, Double> m) {
        m.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }
    
    public static void printListEntry(List<Map.Entry<String, Double>> l) {
        for (Map.Entry<String, Double> m : l) {
            System.out.println(m.getKey() + " - " + m.getValue().toString());
        }
    }
    
    public static void printListEntryIntegerDouble(
            List<Map.Entry<Integer, Double>> l) {
        for (Map.Entry<Integer, Double> m : l) {
            System.out.println(m.getKey() + " - " + m.getValue().toString());
        }
    }
    
}
