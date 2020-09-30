/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu.instruction;

/**
 * Instruction class with all attributes of InstructionPart and all the parts of the instruction.
 * It allows to save all instruction parts into one accessible class.
 * @author Luism
 */
public class Instruction extends InstructionPart {
    public static final String LOAD = "0001";
    public static final String STORE = "0010";
    public static final String MOV = "0011";
    public static final String ADD = "0100";
    public static final String SUB = "0101";
    
    public static InstructionPart[] availableOperators = {
        new Operator("LOAD", "0001"),
        new Operator("STORE", "0010"),
        new Operator("MOV", "0011"),
        new Operator("ADD", "0100"),
        new Operator("SUB", "0101")
    };
    public static InstructionPart[] availableRegisters = {
        new Register("AX", "0001"),
        new Register("BX", "0010"),
        new Register("CX", "0011"),
        new Register("DX", "0100")
    };
    public static final int InstructionBitCantRepresentation = 16;
    private InstructionPart operator = null;
    private InstructionPart register = null;
    private InstructionPart number = null;
    
    public Instruction(String str) {
        super(str);
        this.setBitCantRepresentation(InstructionBitCantRepresentation);
    }
    
    public Instruction(String str, String binRepresentation, int bitCantRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(bitCantRepresentation);
    }
    
    public Instruction(String str, String binRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(InstructionBitCantRepresentation);
    }
    
    /**
     * Allows to decode a whole instruction by decoding its parts, it means, converting the instruction
     * parts into binary code.
     * @return String with the converted data.
     */
    @Override
    public String decode() {
        String[] data = new String[3];
        data[2] = "00000000";
        if (this.getInstructionString().contains(",")) {
            data[2] = this.decodeNumber(this.getInstructionString().split(",")[1].trim());
            data[1] = this.decodeDir(this.getInstructionString().split(",")[0].split(" ")[1]);
            data[0] = this.decodeOp(this.getInstructionString().split(",")[0].split(" ")[0]);
        } else {
            data[1] = this.decodeDir(this.getInstructionString().split(" ")[1]);
            data[0] = this.decodeOp(this.getInstructionString().split(" ")[0]);
        }
        this.setBinRepresentation(String.format("%s%s%s", data[0], data[1], data[2]));
        return this.getBinRepresentation();
    }
    
    /**
     * Decodes a number by creating a Number object and executing its decode method.
     * @param str String representing the number.
     * @return A String with the number in binary.
     */
    private String decodeNumber(String str) {
        this.number = new Number(str);
        return this.number.decode();
    }
    
    /**
     * Decodes an addressing mode by searching and existing Addressing mode in static values of this class.
     * @param str String representing the addressing mode.
     * @return A String with the register in binary.
     */
    private String decodeDir(String str) {
        for (InstructionPart reg: Instruction.availableRegisters) {            
            if (reg.getInstructionString().toLowerCase().equals(str.toLowerCase())) {
                this.register = reg;
                return this.register.getBinRepresentation();
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
        for (InstructionPart op: Instruction.availableOperators) {
            if (op.getInstructionString().toLowerCase().equals(str.toLowerCase())) {
                this.operator = op;
                return this.operator.getBinRepresentation();
            }
        }
        return null;
    }
}
