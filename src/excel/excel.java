/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clases.Categorias;

/**
 *
 * @author Micaela Pujol Higueras
 * @author Silvia Matilla García
 */
public class excel {
    
    String rutaArchivo = "./src/resources/SistemasInformacionII.xlsx";
    XSSFWorkbook excel;
    FileInputStream archivo;
    
    public excel(){
        
        try{ // leo el archivo excel
            archivo = new FileInputStream(new File(rutaArchivo));
            excel = new XSSFWorkbook(archivo);               
        }
        catch(Exception e){
            e.printStackTrace();
        }              
    }
    
    public void corrigeNifs(){
        
        XSSFSheet hoja = excel.getSheetAt(0);        
        Iterator<Row> iteradorFilas = hoja.iterator();
        
        Row fila;
                
        for(int i = 1; i < hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            Cell celda = fila.getCell(7); // selecciona la casilla correspondiente al NIF/NIE
                                
            if(celda != null && celda.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celda.toString()) && !filaVacia(fila)){
                
                String res = validaLetra(celda.getStringCellValue());
                
                if(res != "inc" && res != null){ // modifica los nif/nie erróneos
                    
                    celda.setCellValue(celda.getStringCellValue().substring(0,8).concat(res));
                    
                }
            }
        }
    }
    
    private static String validaLetra(String nif){
        
        String res = null;
        String letra = null;
        
        if(nif.length() != 9){
            res = "inc";
        }
        else{
            String firstChar = nif.substring(0,1);
            String n = null;
            
            if(firstChar.equals("X")){
                n = "0";
                nif = n.concat(nif.substring(1));
            }
            else if(firstChar.equals("Y")){
                n = "1";
                nif = n.concat(nif.substring(1));
            }
            else if(firstChar.equals("Z")){
                n = "2";
                nif = n.concat(nif.substring(1));
            }
            
            String numNif = nif.substring(0,8);
            
            int num = Integer.parseInt(numNif);
            int resto = num%23;
            
            switch(resto){
                case 0:
                    letra = "T";
                    break;
                case 1:
                    letra = "R";
                    break;
                case 2:
                    letra = "W";
                    break;
                case 3:
                    letra = "A";
                    break;
                case 4:
                    letra = "G";
                    break;
                case 5:
                    letra = "M";
                    break;
                case 6:
                    letra = "Y";
                    break;
                case 7:
                    letra = "F";
                    break;
                case 8:
                    letra = "P";
                    break;
                case 9:
                    letra = "D";
                    break;
                case 10:
                    letra = "X";
                    break;
                case 11:
                    letra = "B";
                    break;
                case 12:
                    letra = "N";
                    break;
                case 13:
                    letra = "J";
                    break;
                case 14:
                    letra = "Z";
                    break;
                case 15:
                    letra = "S";
                    break;
                case 16:
                    letra = "Q";
                    break;
                case 17:
                    letra = "V";
                    break;
                case 18:
                    letra = "H";
                    break;
                case 19:
                    letra = "L";
                    break;
                case 20:
                    letra = "C";
                    break;
                case 21:
                    letra = "K";
                    break;
                case 22:
                    letra = "E";
                    break;
            }
            
            if(!nif.substring(8).equals(letra)){
                res = letra;
            }
        }
        
        return res;
    }
    
    public static boolean filaVacia(Row fila){
        
        if (fila == null) {
            return true;
        }
        if (fila.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = fila.getFirstCellNum(); cellNum < fila.getLastCellNum(); cellNum++) {
            Cell celda = fila.getCell(cellNum);
            if (celda != null && celda.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celda.toString())) {
                return false;
            }
        }
        return true;
    } 
    
    
    // HASHMAP
    
     public ArrayList<Categorias> hojaCategorias(){ //hoja 2
        
        XSSFSheet hoja = excel.getSheetAt(1);
        
        Row fila;
        
        //definir un HashMap
        //HashMap<String, Float> categorias = new HashMap<String, Float>();
        ArrayList<Categorias> categorias = new ArrayList<>(hoja.getLastRowNum()-1);
        
        for(int i = 1; i <= hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            Categorias cat = new Categorias(i, fila.getCell(0).toString(), Double.parseDouble(fila.getCell(1).toString()),
            		Double.parseDouble(fila.getCell(2).toString()));
            
            categorias.add(cat);
        
            //insertar valores "key"-"value" al HashMap
            //categorias.put(fila.getCell(0).toString(), Float.parseFloat(fila.getCell(1).toString()));
            //categorias.put(fila.getCell(0).toString(), Float.parseFloat(fila.getCell(2).toString()));
        } 
        
        for(Categorias cat: categorias) {
        	
        	System.out.println(cat.toString());
        }
        
        return categorias;
    }
    
    public HashMap<Double, Double> hojaRetenciones(){ //hoja 3
        
        XSSFSheet hoja = excel.getSheetAt(2);
        
        Row fila;
        
        //definir un HashMap
        HashMap<Double, Double> retenciones = new HashMap<Double, Double>();
                
        for(int i = 1; i <= hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            //insertar valores "key"-"value" al HashMap
            retenciones.put(Double.parseDouble(fila.getCell(0).toString()), Double.parseDouble(fila.getCell(1).toString()));            
        }
                
        for(Double key: retenciones.keySet()) {
        	
        	System.out.println(key + ", " + retenciones.get(key));
        }
        
        return retenciones;
        
    }
    
    public HashMap<String, Double> hojaValores(){ //hoja 4
        
        XSSFSheet hoja = excel.getSheetAt(3);
        
        Row fila;
        
        //definir un HashMap
        HashMap<String, Double> valores = new HashMap<String, Double>();
                
        for(int i = 0; i <= hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            //insertar valores "key"-"value" al HashMap
            valores.put(fila.getCell(0).toString(), Double.parseDouble(fila.getCell(1).toString()));
        }
        
        for(String key: valores.keySet()) {
        	
        	System.out.println(key + ", " + valores.get(key));
        }
        
        return valores;
    }
    
    public HashMap<Double, Double> hojaTrienios(){ //hoja 5
        
        XSSFSheet hoja = excel.getSheetAt(4);
        
        Row fila;
        
        //definir un HashMap
        HashMap<Double, Double> trienios = new HashMap<Double, Double>();
                
        for(int i = 1; i <= hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            //insertar valores "key"-"value" al HashMap
            trienios.put(Double.parseDouble(fila.getCell(0).toString()), Double.parseDouble(fila.getCell(1).toString()));
        }
        
        for(Double key: trienios.keySet()) {
        	
        	System.out.println(key + ", " + trienios.get(key));
        }
        
        return trienios;
    }
    
    //DÍGITOS DE CONTROL
    
    public void corrigeDigitosDeControl(){
        
        XSSFSheet hoja = excel.getSheetAt(0);
        Iterator<Row> iteradorFilas = hoja.iterator();
        
        Row fila;
        
        for(int i = 1; i < hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            Cell celda = fila.getCell(9); //selecciona la casilla correspondiente al CCC

            if(celda != null && celda.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celda.toString()) && !filaVacia(fila)){
                
                String res = validaDigitosDeControl(celda.getStringCellValue());
                
                if(res != "inc" && res != null){ //modifica los dígitos de control erróneos
                    
                    //celda.setCellValue(res);
                }
            }
        }
    }
    
    private static String validaDigitosDeControl(String ccc){
        
        String res = null;
        String primerDigito = null;
        String segundoDigito = null;
        
        if(ccc.length() != 20){
            res = "inc";
        }
        else{
            
            String primerasPosiciones = ccc.substring(0,8);
            String cadena1 = "00".concat(primerasPosiciones);
            String cadena2 = ccc.substring(10,20);

            int[] factores = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
            
            int productos1 = Integer.parseInt(cadena1.substring(0,1)) * factores[0];
            int productos2 = Integer.parseInt(cadena2.substring(0,1)) * factores[0];

            for(int i = 1; i < 10; i++){
                    
                productos1 =  productos1 + Integer.parseInt(cadena1.substring(i,i+1)) * factores[i];
                productos2 =  productos2 + Integer.parseInt(cadena2.substring(i,i+1)) * factores[i];
            }
            
            int resto1 = productos1%11;
            int resto2 = productos2%11;
            
            primerDigito = Integer.toString(11-resto1);
            segundoDigito = Integer.toString(11-resto2);
            
            if(primerDigito.equals("10")){
                primerDigito = "1";
            }
            else if(primerDigito.equals("11")){
                primerDigito = "0";
            }
            
            if(segundoDigito.equals("10")){
                segundoDigito = "1";
            }
            else if(segundoDigito.equals("11")){
                segundoDigito = "0";
            }
            
            //comparo los dígitos de control obtenidos con los originales
            if(!ccc.substring(8,9).equals(primerDigito) || !ccc.substring(9,10).equals(segundoDigito)){
                res = primerasPosiciones.concat(primerDigito).concat(segundoDigito).concat(cadena2);
            }
        }
        return res;
    }
    
    //IBAN
    
    public void iban(){
        
        XSSFSheet hoja = excel.getSheetAt(0);
        Iterator<Row> iteradorFilas = hoja.iterator();
        
        Row fila;
        
        for(int i = 1; i < hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            Cell celdaCCC = fila.getCell(9); //selecciona la casilla correspondiente al CCC
            Cell celdaPais = fila.getCell(10); //selecciona la casilla correspondiente al pais
            Cell celdaIban = fila.getCell(11); //selecciona la casilla correspondiente a la columna L en la que se añadirá el IBAN
            
            if(celdaCCC != null && celdaCCC.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celdaCCC.toString()) && !filaVacia(fila)){
                if(celdaPais != null && celdaPais.getCellType() != CellType.BLANK && StringUtils.isNoneBlank(celdaPais.toString()) && !filaVacia(fila)){
                    
                    String iban = calculaIban(celdaCCC.getStringCellValue(), celdaPais.getStringCellValue());

                    //celdaIban.setCellValue(iban);
                    
                }
            }
        } 
    }
    
    public String calculaIban(String ccc, String pais){

        String codigo = pais.concat("00").concat(ccc);
        
        codigo = ccc.concat(pais).concat("00");
        
        String[] letras = pais.split("");
        
        String codigoletras = transformarNumero(letras[0]).concat(transformarNumero(letras[1]));
        
        codigo = ccc.concat(codigoletras).concat("00");
                
        java.math.BigInteger codigoCompleto = new java.math.BigInteger(codigo);
        java.math.BigInteger num = new java.math.BigInteger("97");
        
        java.math.BigInteger resto = codigoCompleto.mod(num);
                
        java.math.BigInteger num1 = new java.math.BigInteger("98");
        java.math.BigInteger diferencia = num1.subtract(resto);
        
        String digitos = diferencia.toString();
        
        if(digitos.length() != 2){
            digitos = "0".concat(digitos);
        }
        
        String iban = pais.concat(digitos).concat(ccc);
        System.out.println("IBAN: " + iban);
        
        return iban;
    }
    
    public String transformarNumero(String letra){
        
        String num = null;
        
        switch(letra){
                case "A":
                    num = "10";
                    break;
                case "B":
                    num = "11";
                    break;
                case "C":
                    num = "12";
                    break;
                case "D":
                    num = "13";
                    break;
                case "E":
                    num = "14";
                    break;
                case "F":
                    num = "15";
                    break;
                case "G":
                    num = "16";
                    break;
                case "H":
                    num = "17";
                    break;
                case "I":
                    num = "18";
                    break;
                case "J":
                    num = "19";
                    break;
                case "K":
                    num = "20";
                    break;
                case "L":
                    num = "21";
                    break;
                case "M":
                    num = "22";
                    break;
                case "N":
                    num = "23";
                    break;
                case "O":
                    num = "24";
                    break;
                case "P":
                    num = "25";
                    break;
                case "Q":
                    num = "26";
                    break;
                case "R":
                    num = "27";
                    break;
                case "S":
                    num = "28";
                    break;
                case "T":
                    num = "29";
                    break;
                case "U":
                    num = "30";
                    break;
                case "V":
                    num = "31";
                    break;
                case "W":
                    num = "32";
                    break;
                case "X":
                    num = "33";
                    break;
                case "Y":
                    num = "34";
                    break;
                case "Z":
                    num = "35";
                    break;
        }    
        return num;
    }
    
    //DIRECCIONES DE CORREO ELECTRÓNICO
    
    public void email(){
        
        XSSFSheet hoja = excel.getSheetAt(0);
        Iterator<Row> iteradorFilas = hoja.iterator();
        
        Row fila;
        
        ArrayList<String> usuarios = new ArrayList();
        
        for(int i = 1; i < hoja.getLastRowNum(); i++){
            
            fila = hoja.getRow(i);
            
            Cell celdaA1 = fila.getCell(5); //selecciona la casilla correspondiente al apellido 1
            Cell celdaA2 = fila.getCell(6); //selecciona la casilla correspondiente al apellido 2
            Cell celdaN = fila.getCell(4); //selecciona la casilla correspondiente al nombre
            Cell celdaE = fila.getCell(1); //selecciona la casilla correspondiente al nombre de la empresa
            Cell celdaEmail = fila.getCell(8); //selecciona la casilla correspondiente al correo electrónico
                        
            if(celdaA1 != null && celdaA1.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celdaA1.toString()) && !filaVacia(fila)){
                if(celdaN != null && celdaN.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celdaN.toString()) && !filaVacia(fila)){
                    if(celdaE != null && celdaE.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celdaE.toString()) && !filaVacia(fila)){
                        if(celdaA2 != null && celdaA2.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celdaA2.toString()) && !filaVacia(fila)){
                            
                            String email = generaEmail(celdaA1.getStringCellValue(), celdaA2.getStringCellValue(), celdaN.getStringCellValue(), celdaE.getStringCellValue(),usuarios);
                            
                            //celdaEmail.setCellValue(email);
                            
                        }
                        else{
                            String email = generaEmail(celdaA1.getStringCellValue(),celdaN.getStringCellValue(),celdaE.getStringCellValue(),usuarios);
                            
                            //celdaEmail.setCellValue(email);
                        }
                    }
                }  
            }
        }
    }
    
    public String generaEmail(String apellido1, String apellido2, String nombre, String empresa, ArrayList<String> usuarios){
        
        String[] surname1 = apellido1.split("");
        String[] name = nombre.split("");
        
        String usuario = null;
        
        if(apellido2 != null){   
            
            String[] surname2 = apellido2.split("");
            
            usuario = surname1[0].concat(surname2[0]).concat(name[0]);
        }
        else{
            
            usuario = surname1[0].concat(name[0]);
        }
                
        String num = repeticion(usuarios, usuario);
        
        usuarios.add(usuario);
        
        String email = usuario.concat(num).concat("@").concat(empresa).concat(".es");
        System.out.println("email: " + email);
        
        return email;
    }
    
    public String generaEmail(String apellido1, String nombre, String empresa, ArrayList<String> usuarios){
        
        String[] surname = apellido1.split("");
        String[] name = nombre.split("");
        
        String usuario = null;
        
        usuario = surname[0].concat(name[0]);
                
        String num = repeticion(usuarios, usuario);
        
        usuarios.add(usuario);
        
        String email = usuario.concat(num).concat("@").concat(empresa).concat(".es");
        System.out.println("email: " + email);
        
        return null;
    }
    
    public String repeticion(ArrayList<String> usuarios, String usuario){
        
        int contador = 0;
        
        for(int i = 0; i < usuarios.size(); i++){
            
            if(usuario.equals(usuarios.get(i))){
                
                contador++;
            }
        }
        
        String num = Integer.toString(contador);
        
        if(contador < 10){
            
            num = "0".concat(num);  
        }
        
        return num;
    }
    
    public void close(){
        
        try{
            this.archivo.close();
            FileOutputStream output = new FileOutputStream(new File(rutaArchivo));        
            excel.write(output);
            output.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }        
    }
    
    public XSSFWorkbook getExcel(){
        
        return this.excel;
    }
}
