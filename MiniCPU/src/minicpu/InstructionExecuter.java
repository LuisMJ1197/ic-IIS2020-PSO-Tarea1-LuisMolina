/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;

import minicpu.instruction.BinaryInstruction;
import minicpu.instruction.Instruction;
import util.BinaryConversor;

/**
 * Service class for executing the instructions according to the operator.
 * @author Luism
 */
public class InstructionExecuter {
    /**
     * Allows to execute a instruction according to the operator.
     * @param cpu CPU object in which registers and memory will be modified according to the instruction.
     * @param instruction Instruction for executing
     * @throws java.lang.Exception
     */
    public static void executeInstruction(CPU cpu, String instruction) throws Exception {
        Instruction inst = new BinaryInstruction(instruction);
        inst.decode();
        switch (inst.getOperator()) {
            case BinaryInstruction.LOAD:
                cpu.setAc(cpu.getMemory().getValue(BinaryConversor.toInteger(inst.getAddressing())));
                break;
            case BinaryInstruction.STORE:
                cpu.getMemory().setValue(BinaryConversor.toInteger(inst.getAddressing()), cpu.getAc());
                break;
            case BinaryInstruction.MOV:
                cpu.getMemory().setValue(BinaryConversor.toInteger(inst.getAddressing()), 
                        inst.getNumber().substring(0, 1).concat("00000000").concat(inst.getNumber().substring(1)));
                break;
            case BinaryInstruction.ADD:
                executeAdd(inst, cpu);
                break;
            case BinaryInstruction.SUB:
                executeSub(inst, cpu);
                break;
        }
    }
    
    /**
     * Validates that the value of a register doesn't exceed the limit.
     * @param number Number for validation
     * @return true if is the number is in accepted range
     */
    public static boolean validateNumberExtension(int number) {
        return (Math.abs(number) <= (Math.pow(2, BinaryInstruction.REGISTER_NUMBER_BITS_EXTENSION) - 1));
    }
    
    /**
     * Executes the add operation
     * @param inst Instruction to be executed
     * @param cpu The CPU object for accesing the registers
     * @throws Exception If there is an overflow
     */
    public static void executeAdd(Instruction inst, CPU cpu) throws Exception {
        int res = BinaryConversor.toInteger2(cpu.getAc()) + 
                        BinaryConversor.toInteger2(cpu.getMemory().getValue(BinaryConversor.toInteger(inst.getAddressing())));
        if (validateNumberExtension(res)) {
            cpu.setAc(
                    BinaryConversor.toBinary2(Integer.toString(res), BinaryInstruction.REGISTER_NUMBER_BITS_EXTENSION)
            );
        } else {
            throw new Exception("Number out of range exception");
        }
    }
    
    /**
     * Executes the sub operation
     * @param inst Instruction to be executed
     * @param cpu The CPU object for accesing the registers
     * @throws Exception If there is an overflow
     */
    public static void executeSub(Instruction inst, CPU cpu) throws Exception {
        int res = BinaryConversor.toInteger2(cpu.getAc()) - 
                        BinaryConversor.toInteger2(cpu.getMemory().getValue(BinaryConversor.toInteger(inst.getAddressing())));
        if (validateNumberExtension(res)) {
            cpu.setAc(
                    BinaryConversor.toBinary2(Integer.toString(res), BinaryInstruction.REGISTER_NUMBER_BITS_EXTENSION)
            );
        } else {
            throw new Exception("Number out of range exception");
        }
    }
}
