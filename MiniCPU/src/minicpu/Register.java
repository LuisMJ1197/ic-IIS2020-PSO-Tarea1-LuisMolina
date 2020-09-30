/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;

/**
 * Register class to create Register object with their memory position and data.
 * @author Luism
 */
public class Register {
    public static final int AX = 1;
    public static final int BX = 2;
    public static final int CX = 3;
    public static final int DX = 4;
    
    private int dir = -1;
    private String data = "0000000000000000";
    
    public Register(int dir) {
        this.dir = dir;
    }
    
    public Register() {
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
