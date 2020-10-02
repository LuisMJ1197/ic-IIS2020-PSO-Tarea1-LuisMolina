/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;

import minicpu.instruction.Instruction;

/**
 * Program class for object with a set of instructions, program size, initial position and id.
 * @author Luism
 */
public class Program {
    private static int sCantProgram = 0;
    private Instruction[] instructions;
    private int programSize = 0;
    private int initPosition = 0;
    private int id = 0;
    
    public Program(String[] data) throws Exception {
        this.id = ++sCantProgram;
        this.setInstructions(data);
        this.programSize = this.instructions.length;
    }

    public int getId() {
        return this.id;
    }
    
    public int getProgramSize() {
        return this.programSize;
    }
    
    public int getInitPosition() {
        return initPosition;
    }

    public void setInitPosition(int initPosition) {
        this.initPosition = initPosition;
    }
    
    private void setInstructions(String[] data) throws Exception {
        this.instructions = ProgramLoader.loadProgram(data);
    }
    
    public Instruction[] getInstructions() {
        return this.instructions;
    }
}
