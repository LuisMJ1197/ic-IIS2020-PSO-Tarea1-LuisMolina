/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu.instruction;

/**
 * Abstract class for creating instruction parts with its original String, binary representation and the bits quantity representation.
 * @author Luism
 */
public abstract class InstructionPart {
    private String instructionString;
    private String binRepresentation;
    private int bitCantRepresentation;

    public InstructionPart(String str) {
        this.instructionString = str;
    }

    public String getInstructionString() {
        return instructionString;
    }

    public void setInstructionString(String instructionString) {
        this.instructionString = instructionString;
    }

    public String getBinRepresentation() {
        return binRepresentation;
    }

    public void setBinRepresentation(String binRepresentation) {
        this.binRepresentation = binRepresentation;
    }

    public int getBitCantRepresentation() {
        return bitCantRepresentation;
    }

    public void setBitCantRepresentation(int bitCantRepresentation) {
        this.bitCantRepresentation = bitCantRepresentation;
    }
    
    public abstract String decode();
    
}
