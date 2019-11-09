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
        currentToken = getNextToken();
        while(currentToken.getToken() != Symbol.FILE_END){
            fileOutput.println(currentToken);
            if (currentToken.getToken() == Symbol.IDENTIFIER) {
                fileOutput.println("ID " + lexeme + ", INDEX: " + attribute);
            }
            if (currentToken.getToken() == Symbol.INTEGER) {
                fileOutput.println("VALUE: " + attribute);
            }
            currentToken = getNextToken();
        }
        fileOutput.println(currentToken);
        closeFileInput();
        closeFileOutput();
    }
    
    public Token getNextToken(){
        while(character == ' '){
            character = getNextChar();
        }
        if (isLetter(character)) {
            lexeme = "" + character;
            character = getNextChar();
            while(isLetter(character) || isDigit(character)){
                lexeme += character;
                character = getNextChar();
            }
            int i = search(Symbols.arrayKeyword, lexeme);
            if (i == -1) {
                attribute = searchInsert(lexeme);
                return new Token(Symbol.IDENTIFIER);
            } else {
                return new Token(Symbol.valueOf(arrayKeywordSymbol[i]));
            }  
        } else if (isDigit(character)) {
            int value = character - '0';
            character = getNextChar();
            
            while(isDigit(character)){
                value = value * 10 + character - '0';
                character = getNextChar();
            }
            attribute = value;
            return new Token(Symbol.INTEGER);
        } else if (character == '"') {
            character = getNextChar();
            lexeme = "";
            while(character != '"'){
                lexeme += character;
                character = getNextChar();
            }
            character = getNextChar();
            return new Token(Symbol.STRING);
        }
        
        switch(character){
            case '/':
                character = getNextChar();
                if (character == '*') {
                    character = getNextChar();
                    boolean endComment = false;
                    while(!endComment &&  character != '\0'){
                        while(character != '*' && character != '\0'){
                            character = getNextChar();
                        }
                        if (character == '\0') {
                            System.out.println("No comment end was found.");
                            return new Token(Symbol.FILE_END);
                        }
                        character = getNextChar();
                        if (character == '/') {
                            character = getNextChar();
                            endComment = true;
                            return getNextToken();
                        }
                    }
                    if (character == '\0') {
                        System.out.println("No comment end was found.");
                    }
                }
                if (character == '/') {
                    character = getNextChar();
                    while(character != '\n'){
                        character = getNextChar();
                    }
                    return getNextToken();
                }
                else {
                    return new Token(Symbol.OP_DIVIDE);
                }
            case '=':
                character = getNextChar();
                if (character == '=') {
                    character = getNextChar();
                    return new Token(Symbol.OP_EQUAL);
                } else {
                    return new Token(Symbol.OP_ASSIGN);
                }
            case '+':
                character = getNextChar();
                return new Token(Symbol.OP_PLUS);
            case '-':
                character = getNextChar();
                return new Token(Symbol.OP_MINUS);
            case '*':
                character = getNextChar();
                return new Token(Symbol.OP_MULTIPLY);
            case '%':
                character = getNextChar();
                return new Token(Symbol.OP_MODULO);
            case '>':
                character = getNextChar();
                if (character == '=') {
                    character = getNextChar();
                    return new Token(Symbol.OP_GREATER_EQUAL_THAN);
                } else {
                    return new Token(Symbol.OP_GREATER_THAN);
                }
            case '<':
                character = getNextChar();
                if (character == '=') {
                    character = getNextChar();
                    return new Token(Symbol.OP_LESS_EQUAL_THAN);
                } else {
                    return new Token(Symbol.OP_LESS_THAN);
                }
            case '!':
                character = getNextChar();
                if (character == '=') {
                    character = getNextChar();
                    return new Token(Symbol.OP_NOT_EQUAL);
                }
            case '&':
                character = getNextChar();
                if (character == '&') {
                    character = getNextChar();
                    return new Token(Symbol.OP_AND_BOOLEAN);
                }
            case '|':
                character = getNextChar();
                if (character == '|') {
                    character = getNextChar();
                    return new Token(Symbol.OP_OR_BOOLEAN);
                }
            case '\'':
                character = getNextChar();
                lexeme = "" + character;
                character = getNextChar();
                if (character != '\'') {
                    System.out.println("Wrong ghar literal.");
                    return new Token(Symbol.OTHER);
                }
                character = getNextChar();
                return new Token(Symbol.CHAR);
            case ' ':
                character = getNextChar();
                return getNextToken();
            case '\n':
                character = getNextChar();
                return getNextToken();
            case '\t':
                character = getNextChar();
                return getNextToken();
            case ';':
                character = getNextChar();
                return new Token(Symbol.SEMICOLON);
            case '.':
                character = getNextChar();
                return new Token(Symbol.PERIOD);
            case ',':
                character = getNextChar();
                return new Token(Symbol.COLON);
            case '{':
                character = getNextChar();
                return new Token(Symbol.OPEN_BRACE);
            case '}':
                character = getNextChar();
                return new Token(Symbol.CLOSE_BRACE);
            case '(':
                character = getNextChar();
                return new Token(Symbol.OPEN_PARENTHESIS);
            case ')':
                character = getNextChar();
                return new Token(Symbol.CLOSE_PARENTHESIS);
            case '[':
                character = getNextChar();
                return new Token(Symbol.OPEN_BRACKET);
            case ']':
                character = getNextChar();
                return new Token(Symbol.CLOSE_BRACKET);
            case '\0':
                return new Token(Symbol.FILE_END);
            default:
                System.out.println("(" + lineNumber + ") LEXICAL ERROR: INVALID CHARACTER: " + character);
                character = getNextChar();
                return new Token(Symbol.OTHER);
        }
    }
    
    public boolean isLetter(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ') {
            return true;
        }
        return false;
    }
    
    public boolean isDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }
    
    public int search(String[] table, String str){
        for (int i = 0; i < table.length; i++) {
            if (table[i].equals(str)) {
                return i;
            }
        }
        return -1;
    } 
    
    public int searchInsert(String str) {
        for (int i = 0; i < identifierNumber; i++) {
            if (identifierVector[i].equals(str)) {
                return i;
            }
        }
        identifierVector[identifierNumber] = str;
        return identifierNumber++;
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
            fileInput =  new BufferedReader(new FileReader(myFile));
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
            fileOutput = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(outputFile)));
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
