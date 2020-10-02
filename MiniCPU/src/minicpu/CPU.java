/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;

import java.util.ArrayList;
import minicpu.instruction.Instruction;
import util.BinaryConversor;

/**
 * The CPU class allows to create CPU objects with their respective memory and registers.
 * Also, contains a list of processes.
 * @author Luism
 */
public class CPU {
    private String ac;
    private String pc;
    private String ir;
    private Memory memory;
    private ArrayList<Program> processes = new ArrayList<>();
    
    public CPU() {
        this.ac = "0000000000000000";
        this.pc = "0000000000000000";
        this.ir = "0000000000000000";
        this.memory = new Memory();
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getIr() {
        return ir;
    }

    public void setIr(String ir) {
        this.ir = ir;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
    
    public int getMemorySize() {
        return this.memory.getSize();
    }
    
    /**
     * Loads a program in memory.
     * @param program Program for loading
     */
    public void loadProgram(Program program) {
        this.clearAll();
        this.memory.loadProgram(program);
        this.processes.clear();
        this.processes.add(program);
    }

    /**
     * Executes a program, updates the pc and ir registers.
     * @param id Program id
     */
    public void executeProgram(int id) {
        Program program = this.findProcess(id);
        if (program != null) {
            this.pc = BinaryConversor.toBinary(Integer.toString(program.getInitPosition()), 16);
            this.ir = program.getInstructions()[0].decode();
        }
    }
    /**
     * Restarts all the registers and memory.
     */
    private void clearAll() {
        this.pc = BinaryConversor.toBinary("0", 16);
        this.ir = BinaryConversor.toBinary("0", 16);
        this.ac = BinaryConversor.toBinary("0", 16);
        this.memory.clear();
    }
    
    /**
     * Executes the actual instruction and move to next one. Updates the pc and ir.
     * @param id Program id
     * @return True if the program finalizes, False otherwise.
     */
    public boolean executeNextInstruction(int id) {
        Program program = this.findProcess(id);
        if (program != null) {
            int intPc = BinaryConversor.toInteger(this.pc);
            InstructionExecuter.executeInstruction(this, this.ir);
            intPc += 1;
            if (intPc < program.getInitPosition() + program.getProgramSize()) {
                this.pc = BinaryConversor.toBinary(Integer.toString(intPc), 16);
                this.ir = this.memory.getValue(intPc);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    
    /**
     * Finds a program by id.
     * @param id Program id
     * @return Program object with same id
     */
    private Program findProcess(int id) {
        for (Program program: this.processes) {
            if (program.getId() == id) {
                return program;
            }
        }
        return null;
    }
}
