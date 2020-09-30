/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu.instruction;

import util.BinaryConversor;

/**
 *
 * @author Luism
 */
public class Number extends InstructionPart {
    public static final int NumberBitCantRepresentation = 7;

    public Number(String str) {
        super(str);
        this.setBitCantRepresentation(NumberBitCantRepresentation);
    }
    
    public Number(String str, String binRepresentation, int bitCantRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(bitCantRepresentation);
    }
    
    public Number(String str, String binRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(NumberBitCantRepresentation);
    }
    
    /**
     * Allows to decode the number part of an instruction, it means, converting the number in binary.
     * @return String with the converted data.
     */
    @Override
    public String decode() {
        String result = "";
        int number = Integer.parseInt(this.getInstructionString());
        if (number < 0) {
            result += "1";
            result += BinaryConversor.toBinary(this.getInstructionString().substring(1), this.getBitCantRepresentation());
        } else {
            result += "0";
            result += BinaryConversor.toBinary(this.getInstructionString(), this.getBitCantRepresentation());
        }
        return result;
    }
    
    
}
