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
public class BinaryInstruction extends Instruction {
    public static final String LOAD = "0001";
    public static final String STORE = "0010";
    public static final String MOV = "0011";
    public static final String ADD = "0100";
    public static final String SUB = "0101";
    
    public static final String AX = "0001";
    public static final String BX = "0010";
    public static final String CX = "0011";
    public static final String DX = "0100";
    public static final int LITERAL_NUMBER_BITS_EXTENSION = 8;
    public static final int REGISTER_NUMBER_BITS_EXTENSION = 16;
    
    public static final int[] OPERATOR_RANGE = {0, 4};
    public static final int[] ADDRESSING_RANGE = {4, 8};
    public static final int[] NUMBER_RANGE = {8, 16};

    public BinaryInstruction(String instructionString) {
        super(instructionString);
    }
    
    @Override
    public String decode() throws Exception {
        this.operator = this.instructionString.substring(BinaryInstruction.OPERATOR_RANGE[0], BinaryInstruction.OPERATOR_RANGE[1]);
        this.addressing = this.instructionString.substring(BinaryInstruction.ADDRESSING_RANGE[0], BinaryInstruction.ADDRESSING_RANGE[1]);
        this.number = this.instructionString.substring(BinaryInstruction.NUMBER_RANGE[0]);
        return this.instructionString;
    }
    
}
