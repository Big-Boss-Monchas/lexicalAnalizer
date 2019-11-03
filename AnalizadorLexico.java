/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

import java.io.*;
        
/**
 *
 * @author SKYNET
 */
public class AnalizadorLexico implements Symbols{

    /**
     * @param args the command line arguments
     */
    private BufferedReader fileInput;   // entrada de archivo
    private PrintWriter fileOutput;     // salida del archivo
    int lineNumber;
    String readLine;
    int index;
    String[] identifierVector = new String[150];
    private int identifierNumber;
    private Token currentToken;
    private char character;
    private int attribute;
    private String lexeme;
    
    AnalizadorLexico(){
        lineNumber = 0;
        index = 0;
        readLine = null;
        identifierNumber = 0;
        attribute = -1;
    }
    
    public void compile(){
        openFileInput();
        openFileOutput();
        
        character = getNextChar();
    }
    
    public char getNextChar() {
        if (readLine != null && index < readLine.length()) {
            return readLine.charAt(index++);
        }
        
        try {
            readLine = fileInput.readLine();
            index = 0;
            if (readLine != null) {
                fileOutput.println(++lineNumber + ": " + readLine);
                return '\n';
            }
            else {
                return '\0';
            }
        } catch(NullPointerException npe) {
            return '\0';
        } catch(EOFException eofe) {
            return '\0';
        } catch(IOException ioe) {
            System.err.println("Error reading file character.");
            return '\0';
        } 
    }
    
    public void openFileInput(){
        try{
            File myFile = new File("input.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileReader readmyFile = new FileReader(myFile);
            fileInput =  new BufferedReader(readmyFile);
        } catch(FileNotFoundException ex){
            System.err.println("The file does not exists.");
        } catch(IOException ex){
            System.err.println("Error opening the file.");
        }
    }
    
    public void openFileOutput(){
        try{
            File outputFile = new File("output.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter writemyFile = new FileWriter(outputFile);
            BufferedWriter bufwriteFile = new BufferedWriter(writemyFile);
            fileOutput = new PrintWriter(bufwriteFile);
        } catch(IOException ex){
            System.err.println("Error creating the new file.");
        }
    }
    
    public void closeFileInput() {
        try {
            if (fileInput != null) {
                fileInput.close();
            }
        } catch(IOException ex) {
            System.err.println("Error closing the file.");
        }
    }
    
    public void closeFileOutput() {
        if (fileOutput != null) {
            fileOutput.close();
        }
    }
    
    public static void main(String[] args) {
        AnalizadorLexico myLexicalAnalizer = new AnalizadorLexico();
        myLexicalAnalizer.compile();
    }
}
