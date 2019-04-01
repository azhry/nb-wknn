/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Control.NWKNN;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author acer
 */
public class NWKNNTest {
    public static NWKNN nwknn;
    
    public NWKNNTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        nwknn = new NWKNN();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testEuclideanDistance() throws NoSuchMethodException, 
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        List<Double> x1 = new ArrayList<>();
        x1.add(10.0);
        x1.add(3.0);
        
        List<Double> x2 = new ArrayList<>();
        x2.add(7.0);
        x2.add(7.0);
        
        Method method = NWKNN.class.getDeclaredMethod("euclideanDistance",
                List.class, List.class);
        method.setAccessible(true);
        double result = (double)method.invoke(nwknn, x1, x2);
        assertEquals(5.0, result, 0);
        
        x1.clear();
        x2.clear();
        
        x1.add(2.0);
        x1.add(13.0);
        x1.add(2.0);
        x1.add(4.0);
        x1.add(2.0);
        x1.add(3.0);
        x1.add(2.0);
        
        x2.add(2.0);
        x2.add(14.0);
        x2.add(1.0);
        x2.add(4.0);
        x2.add(1.0);
        x2.add(1.0);
        x2.add(2.0);
        
        result = (double)method.invoke(nwknn, x1, x2);
        assertEquals(2.6457513110645907, result, 2);
    }
    
}
