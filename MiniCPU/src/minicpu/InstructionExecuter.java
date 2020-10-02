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
     */
    public static void executeInstruction(CPU cpu, String instruction) {
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
                cpu.getMemory().setValue(BinaryConversor.toInteger(inst.getAddressing()), "00000000".concat(inst.getNumber()));
                break;
            case BinaryInstruction.ADD:
                cpu.setAc("00000000".concat(
                        BinaryConversor.toBinary2(Integer.toString(
                                BinaryConversor.toInteger2(cpu.getAc().substring(8)) + BinaryConversor.toInteger2(cpu.getMemory().getValue(BinaryConversor.toInteger(inst.getAddressing())).substring(8))
                        ), 7))
                );
                break;
            case BinaryInstruction.SUB:
                cpu.setAc("00000000".concat(
                        BinaryConversor.toBinary2(Integer.toString(
                                BinaryConversor.toInteger2(cpu.getAc().substring(8)) - BinaryConversor.toInteger2(cpu.getMemory().getValue(BinaryConversor.toInteger(inst.getAddressing())).substring(8))
                        ), 7))
                );
                break;
        }
    }
}
