/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readtags;

/**
 *
 * @author jcarlos2289
 */
public class Tag {
    private String tagName;
    private int count;
    
    public Tag(){
        
    }
    
    public Tag(String name, int cant){
        this.tagName = name;
        this.count = cant;
    }
    
    public String getName(){
        return this.tagName;
    }
    
    public int getCount(){
        return this.count;
    }
    
    public void setName(String name){
        this.tagName = name;
    }
    
    
    public void setCount (int val){
        this.count = val;
    }
    
    public String print(){
        return this.getName() +"-"+String.valueOf(this.getCount())+"\n";
    }
}
