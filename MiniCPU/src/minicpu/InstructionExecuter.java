/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicpu;

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
        String op = instruction.substring(0, 4);
        String reg = instruction.substring(4, 8);
        String number = instruction.substring(8);
        switch (op) {
            case Instruction.LOAD:
                cpu.getAc().setData(cpu.getMemory().getValue(BinaryConversor.toInteger(reg)));
                break;
            case Instruction.STORE:
                cpu.getMemory().setValue(BinaryConversor.toInteger(reg), cpu.getAc().getData());
                break;
            case Instruction.MOV:
                cpu.getMemory().setValue(BinaryConversor.toInteger(reg), "00000000".concat(number));
                break;
            case Instruction.ADD:
                cpu.getAc().setData("00000000".concat(
                        BinaryConversor.toBinary2(Integer.toString(
                                BinaryConversor.toInteger2(cpu.getAc().getData().substring(8)) + BinaryConversor.toInteger2(cpu.getMemory().getValue(BinaryConversor.toInteger(reg)).substring(8))
                        ), 7))
                );
                break;
            case Instruction.SUB:
                cpu.getAc().setData("00000000".concat(
                        BinaryConversor.toBinary2(Integer.toString(
                                BinaryConversor.toInteger2(cpu.getAc().getData().substring(8)) - BinaryConversor.toInteger2(cpu.getMemory().getValue(BinaryConversor.toInteger(reg)).substring(8))
                        ), 7))
                );
                break;
        }
    }
}
