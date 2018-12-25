/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nb_wknn;

import Entity.ExcelHandler;
import Entity.NWKNN;
import Entity.Patient;
import Entity.Preprocessor;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author acer
 */
public class NB_WKNN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ExcelHandler excel = new ExcelHandler();
        List<Patient> patients = excel.read("/data/data.xlsx", 2);
        
        NWKNN c = new NWKNN();
        c.fit(patients);
        
        Patient test = new Patient("An. C.G", 'P', 13, 45, 150, 23, 86, 49, 
                "normal");
        String cls = c.predict(test, 3);
        System.out.println(cls);
    }
    
}
