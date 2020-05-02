/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import excel.excel;
import java.util.ArrayList;
import xml.xml;

/**
 * @author Micaela Pujol Higueras
 * @author Silvia Matilla García
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        
        excel exc = new excel();
        exc.corrigeNifs();
        ArrayList<ArrayList<String>> cuentasErrores = exc.corrigeDigitosDeControl();
        exc.iban();
        exc.email();
        exc.close();
    
        xml xml = new xml();
        xml.creaFicheroErrores();
        xml.creaFicheroErroresCCC(cuentasErrores);
        
    }
    
}