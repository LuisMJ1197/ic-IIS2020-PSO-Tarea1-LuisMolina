/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;
import java.util.Random;
import minicpu.instruction.Instruction;

/**
 * Class for Memory objects with memory registers and size.
 * @author Luism
 */
public class Memory {
    public static final int AX = 1;
    public static final int BX = 2;
    public static final int CX = 3;
    public static final int DX = 4;
    public static final int MEMORY_SIZE = 100;
    public static final int MIN_MEMORY_SAVE_POSITION = 50;
    private int size;
    private String[] registers;
    
    public Memory() {
        this.size = MEMORY_SIZE;
        this.initMemory();
    }
    
    public int getSize() {
        return this.size;
    }
    
    /**
     * Initializes the memory array and all needed registers.
     */
    private void initMemory() {
        this.registers = new String[100];
        for (int i = 0; i < this.size; i++) {
            this.registers[i] = "0000000000000000";
        }
    }

    /**
     * Loads a program in memory according to a random initial position.
     * @param program Program for loading
     */
    public void loadProgram(Program program) {
        int position = this.saveMemory(program.getProgramSize());
        program.setInitPosition(position);
        for (Instruction ins: program.getInstructions()) {
            this.registers[position] = ins.decode();
            position++;
        }
    }
    
    /**
     * It calculates a random number between MIN_MEMORY_SAVE_POSITION and the MEMORY_SIZE, considering the program size.
     * @param size
     * @return 
     */
    private int saveMemory(int size) {
        return new Random().nextInt((Memory.MEMORY_SIZE - size) - Memory.MIN_MEMORY_SAVE_POSITION) + Memory.MIN_MEMORY_SAVE_POSITION;
    }

    /**
     * Sets a value (binary) in a memory register at pos registerPos
     * @param registerPos Position of memory 
     * @param value Value for saving
     */
    public void setValue(int registerPos, String value) {
        this.registers[registerPos] = value;
    }
    
    /**
     * Returns the value of a memory register at registerPos
     * @param registerPos Memory position
     * @return Value of the register (binary)
     */
    public String getValue(int registerPos) {
        return this.registers[registerPos];
    }

    /**
     * Clears all memory registers, set them to 0 (binary 16 bits).
     */
    public void clear() {
        for (int i = 0; i < this.registers.length; i++) {
            this.registers[i] = "0000000000000000";
        }
    }
}
