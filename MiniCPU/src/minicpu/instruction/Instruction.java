/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu.instruction;

/**
 *
 * @author Luism
 */
public abstract class Instruction {
    protected String instructionString;
    protected String operator;
    protected String addressing;
    protected String number;
    
    public Instruction(String instructionString) {
        this.instructionString = instructionString;
    }
    
    public abstract String decode();
    
    public String getInstructionString() {
        return this.instructionString;
    }

    public String getOperator() {
        return operator;
    }

    public String getAddressing() {
        return addressing;
    }

    public String getNumber() {
        return number;
    }
    
    
}
