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
public class Operator extends InstructionPart {
    public static final int OperatorBitCantRepresentation = 4;
    
    public Operator(String str, String binRepresentation, int bitCantRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(bitCantRepresentation);
    }
    
    public Operator(String str, String binRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(OperatorBitCantRepresentation);
    }
    
    @Override
    public String decode() {
        return this.getBinRepresentation();
    }
}
