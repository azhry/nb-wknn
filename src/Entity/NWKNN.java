/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author acer
 */
public class NWKNN {
    
    private int K;
    
    public NWKNN(int K) {
        this.K = K;
    }
    
    private Double euclideanDistance(List<Double> x1, List<Double> x2) {
        Double result = 0.00;
        if (x1.size() != x2.size()) {
            throw new Error("You should pass two lists of same size");
        }
        
        Iterator x1Iterator = x1.iterator();
        Iterator x2Iterator = x2.iterator();
        
        while (x1Iterator.hasNext() && x2Iterator.hasNext()) {
            result += Math.pow((double)x1Iterator.next() -
                    (double)x2Iterator.next(), 2);
        }

        return Math.sqrt(result);
    }
    
}
