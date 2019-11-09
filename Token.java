/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

/**
 *
 * @author SKYNET
 */
public class Token implements Symbols{
    
    private Symbol token;
    private Object atributo;
    
    public Token(Symbol token){
        this.token = token;
    }
    
    public Symbol getToken() {
        return token;
    }
    
    public void setToken(Symbol token) {
        this.token = token;
    }
    
    @Override
    public String toString(){
        return token.toString();
    }
}
