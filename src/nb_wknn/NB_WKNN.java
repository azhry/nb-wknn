/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nb_wknn;

import Entity.ExcelHandler;
import Entity.NWKNN;
import java.io.IOException;

/**
 *
 * @author acer
 */
public class NB_WKNN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
//        NWKNN clf = new NWKNN(3);
        ExcelHandler handler = new ExcelHandler();
        handler.read("/data/data.xlsx", 0);
    }
    
}
