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
public class Register extends InstructionPart {
    public static final int RegisterBitCantRepresentation = 4;

    public Register(String str, String binRepresentation, int bitCantRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(bitCantRepresentation);
    }
    
    public Register(String str, String binRepresentation) {
        super(str);
        this.setBinRepresentation(binRepresentation);
        this.setBitCantRepresentation(RegisterBitCantRepresentation);
    }
    
    @Override
    public String decode() {
        return this.getBinRepresentation();
    }
    
}
