/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu.instruction;

import java.util.regex.Pattern;
import util.BinaryConversor;

/**
 *
 * @author Luism
 */
public class ASMInstruction extends Instruction {
    public static final String[] LOAD = {"LOAD", BinaryInstruction.LOAD};
    public static final String[] STORE = {"STORE", BinaryInstruction.STORE};
    public static final String[] MOV = {"MOV", BinaryInstruction.MOV};
    public static final String[] ADD = {"ADD", BinaryInstruction.ADD};
    public static final String[] SUB = {"SUB", BinaryInstruction.SUB};
    
    public static final String[] AX = {"AX", BinaryInstruction.AX};
    public static final String[] BX = {"BX", BinaryInstruction.BX};
    public static final String[] CX = {"CX", BinaryInstruction.CX};
    public static final String[] DX = {"DX", BinaryInstruction.DX};
    
    public static final String[][] ADDRESING_MODES = {AX, BX, CX, DX};
    public static final String[][] OPERATOR_MODES = {LOAD, STORE, MOV, ADD, SUB};
    
    private static final String[] PATTERNS = {
            "(MOV|mov)\\s[A-Da-d](X|x),(\\s)?(0|-?[1-9][0-9]*)",
            "(STORE|store)\\s[A-Da-d](X|x)",
            "(LOAD|load)\\s[A-Da-d](X|x)",
            "(ADD|add)\\s[A-Da-d](X|x)",
            "(SUB|sub)\\s[A-Da-d](X|x)",
        };
    private Instruction binRepresentation;
    
    public ASMInstruction(String instructionString) {
        super(instructionString);
    }
    
    @Override
    public String decode() throws Exception {
        if (this.validateInstruction()) {
            String[] data = new String[3];
            data[2] = "00000000";
            if (this.getInstructionString().contains(",")) {
                data[2] = this.decodeNumber(this.getInstructionString().split(",")[1].trim());
                data[1] = this.decodeAddressing(this.getInstructionString().split(",")[0].split(" ")[1]);
                data[0] = this.decodeOp(this.getInstructionString().split(",")[0].split(" ")[0]);
            } else {
                data[1] = this.decodeAddressing(this.getInstructionString().split(" ")[1]);
                data[0] = this.decodeOp(this.getInstructionString().split(" ")[0]);
            }
            this.binRepresentation = new BinaryInstruction(String.format("%s%s%s", data[0], data[1], data[2]));
            return this.binRepresentation.getInstructionString();
        } else {
            return null;
        }
    }

    public Instruction getBinRepresentation() {
        return binRepresentation;
    }
    
    private boolean validateInstruction() {
        for (String pattern: ASMInstruction.PATTERNS) {
            if (Pattern.matches(pattern, this.instructionString)) {
                return true;
            }
        }
        return false;
    }
    
        /**
     * Decodes a number by creating a Number object and executing its decode method.
     * @param str String representing the number.
     * @return A String with the number in binary.
     */
    private String decodeNumber(String str) throws Exception {
        try {
            if (Math.abs(Integer.parseInt(str)) <= (Math.pow(2, BinaryInstruction.LITERAL_NUMBER_BITS_EXTENSION) - 1)) {
                return BinaryConversor.toBinary2(str, 8);
            } else {
                throw new Exception("Number out of range exception at " + this.instructionString);
            }
        } catch (NumberFormatException e) {
            throw new Exception("Number format exception at " + this.instructionString);
        }
    }
    
    /**
     * Decodes an addressing mode by searching and existing Addressing mode in static values of this class.
     * @param str String representing the addressing mode.
     * @return A String with the register in binary.
     */
    private String decodeAddressing(String str) {
        for (String[] addressingMode: ASMInstruction.ADDRESING_MODES) {            
            if (addressingMode[0].toLowerCase().equals(str.toLowerCase())) {
                return addressingMode[1];
            }
        }
        return null;
    }
    
    /**
     * Decodes an operator mode by searching and existing operator mode in static values of this class.
     * @param str String representing the operator.
     * @return A String with the operator representation in binary.
     */
    private String decodeOp(String str) {
        for (String[] operatorMode: ASMInstruction.OPERATOR_MODES) {            
            if (operatorMode[0].toLowerCase().equals(str.toLowerCase())) {
                return operatorMode[1];
            }
        }
        return null;
    }

}
