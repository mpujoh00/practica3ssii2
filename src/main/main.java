/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DAO.CategoriasDAO;
import DAO.TrabajadorDAO;
import clases.Categorias;
import clases.Empresas;
import java.util.Scanner;
import clases.Trabajadorbbdd;
import excel.excel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import xml.xml;

/**
 *
 * @author maybeitsmica
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        excel exc = new excel();
        /*exc.corrigeNifs();
        exc.corrigeDigitosDeControl();
        exc.iban();*/
        
        exc.hojaTrienios();
        
        exc.close();
    
        xml xml = new xml();
        xml.creaFicheroErrores();
        //xml.creaFicheroErroresCCC();
        
    }
    
}