/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;

import minicpu.instruction.ASMInstruction;
import minicpu.instruction.Instruction;

/**
 * Service class for converting a program lines into Instruction objects.
 * @author Luism
 */
public class ProgramLoader {
    
    /**
     * Loads a program by converting the String array of code lines into an Instruction object array.
     * @param program Array of String code lines.
     * @return Instruction array.
     * @throws java.lang.Exception
     */
    public static Instruction[] loadProgram(String[] program) throws Exception {
        Instruction instructions[];
        int length = program.length;
        instructions = new Instruction[length];
        int i = 0;
        for (String str: program) {
            instructions[i] = new ASMInstruction(str);   
            if (instructions[i].decode() == null) {
                throw new Exception(String.format("Error reading instruction: %s.", str));
            }
            i++;
        }
        return instructions;
    }
}
