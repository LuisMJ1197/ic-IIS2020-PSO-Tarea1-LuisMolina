/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import minicpu.CPU;
import minicpu.Program;
import minicpu.Register;
import util.BinaryConversor;
import util.FileBrowser;
import view.MainFrame;
import view.MemoryRegisterPanel;

/**
 * Controls the view and the button actions. Also, updates the memory view for showing memory registers
 * values.
 * @author Luism
 */
public class MainFrameController implements ActionListener {
    private MainFrame view;
    private String[] data;
    private CPU cpu;
    private Program loadedProgram = null;
    private MemoryRegisterPanel[] memoryRegisters;
    
    /**
     * Constructor method
     */
    public MainFrameController() {
        this.cpu = new CPU();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "openFile":
                this.browseFile();
                break;
            case "executeProgram":
                this.executeProgram();
                break;
            case "executeNextInstruction":
                this.executeNextInstruction();
                break;
            default:
                break;
        }
    }
    
    /**
     * Opens a File Browser to select the file for execution.
     * It also filters the file's extension.
     */
    private void browseFile() {
        FileBrowser fileBrowser = new FileBrowser();
        File file = fileBrowser.browse(this.view);
        if (file == null) return;
        try {
            String dataR = fileBrowser.extractFileInfo(file);
            this.data = dataR.split("\n"); //TODO: Delete blank 
            this.loadedProgram = new Program(this.data);
            this.cpu.loadProgram(this.loadedProgram);
            loadProgramView(this.loadedProgram);
            this.view.executeButton.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this.view, "It was an error opening the file.");
            this.view.executeButton.setEnabled(false);
        }
    }
    
    /**
     * Load the memoryPanel with value 0. This only is executed when the program runs. 
     */
    private void loadMemoryView() {
        this.memoryRegisters = new MemoryRegisterPanel[this.cpu.getMemorySize()];
        for (int i = 0; i < this.cpu.getMemorySize(); i++) {
            this.memoryRegisters[i] = new MemoryRegisterPanel();
            String dir = "";
            if (i <= 9) {
                dir += "0";
            }
            dir += i;
            this.memoryRegisters[i].dirLabel.setText("[" + dir + "]");
            this.memoryRegisters[i].asmCodeLabel.setText("");
            this.memoryRegisters[i].binaryCodeLabel.setText("0000 0000 00000000");
            this.view.memoryMainPane.add(this.memoryRegisters[i]);
        }
    }
    
    /**
     * Load the instructions in memory, graphically.
     * @param program The program that contains the list of instructions.
     */
    private void loadProgramView(Program program) {
        int initPos = program.getInitPosition();
        for (int instructionPos = 0; instructionPos < program.getProgramSize(); instructionPos++) {
            String str = program.getInstructions()[instructionPos].getBinRepresentation();
            JScrollBar vertical = this.view.memoryPanel.getVerticalScrollBar();
            vertical.setValue( 22 * program.getInitPosition());
            this.memoryRegisters[initPos].binaryCodeLabel.setText(String.format("%s %s %s", str.substring(0, 4), str.substring(4, 8), str.substring(8)));
            this.memoryRegisters[initPos].asmCodeLabel.setText(program.getInstructions()[instructionPos].getInstructionString());
            initPos++;
        }
    }
    
    /**
     * Calls the executeProgram method of cpu, enables the run button and updates the view (pc register, ac registers, and others)
     */
    private void executeProgram() {
        this.cpu.executeProgram(this.loadedProgram.getId());
        this.view.nextButton.setEnabled(true);
        this.updateView(false);
    }
    
    /**
     * Calls the executeNextInstruction of cpu. If that returns false, the program won't allow to press the nextButton (when it finalizes)
     */
    private void executeNextInstruction() {
        if (!this.cpu.executeNextInstruction(this.loadedProgram.getId())) {
            this.view.nextButton.setEnabled(false);
        }
        this.updateView(false);
    }
    
    /**
     * Updates the view with the value of all registers, this is executed when the program (asm) is executed and when the next
     * instruction is executed.
     * @param bin True if the values will be presented in binary or False in decimal.
     */
    private void updateView (boolean bin) {
        if (bin) {
            this.view.pcReg.setText(this.cpu.getPc().getData());
            this.view.acReg.setText(this.cpu.getAc().getData());
            
            this.view.axReg.setText(this.cpu.getMemory().getValue(Register.AX));
            this.view.bxReg.setText(this.cpu.getMemory().getValue(Register.BX));
            this.view.cxReg.setText(this.cpu.getMemory().getValue(Register.CX));
            this.view.dxReg.setText(this.cpu.getMemory().getValue(Register.DX));
        } else {
            this.view.pcReg.setText(Integer.toString(BinaryConversor.toInteger(this.cpu.getPc().getData())));
            this.view.acReg.setText(Integer.toString(BinaryConversor.toInteger2(this.cpu.getAc().getData().substring(8))));
            
            this.view.axReg.setText(Integer.toString(BinaryConversor.toInteger2(this.cpu.getMemory().getValue(Register.AX).substring(8))));
            this.view.bxReg.setText(Integer.toString(BinaryConversor.toInteger2(this.cpu.getMemory().getValue(Register.BX).substring(8))));
            this.view.cxReg.setText(Integer.toString(BinaryConversor.toInteger2(this.cpu.getMemory().getValue(Register.CX).substring(8))));
            this.view.dxReg.setText(Integer.toString(BinaryConversor.toInteger2(this.cpu.getMemory().getValue(Register.DX).substring(8))));
        }
        
        this.view.irReg.setText(this.cpu.getIr().getData());
        String ax = this.cpu.getMemory().getValue(Register.AX);
        this.memoryRegisters[Register.AX].binaryCodeLabel.setText(String.format("%s %s %s", ax.substring(0, 4), ax.substring(4, 8), ax.substring(8)));

        String bx = this.cpu.getMemory().getValue(Register.AX);
        this.memoryRegisters[Register.AX].binaryCodeLabel.setText(String.format("%s %s %s", bx.substring(0, 4), bx.substring(4, 8), bx.substring(8)));

        String cx = this.cpu.getMemory().getValue(Register.AX);
        this.memoryRegisters[Register.AX].binaryCodeLabel.setText(String.format("%s %s %s", cx.substring(0, 4), cx.substring(4, 8), cx.substring(8)));

        String dx = this.cpu.getMemory().getValue(Register.AX);
        this.memoryRegisters[Register.AX].binaryCodeLabel.setText(String.format("%s %s %s", dx.substring(0, 4), dx.substring(4, 8), dx.substring(8)));
            
        int memoryPos = BinaryConversor.toInteger(this.cpu.getPc().getData());
        for (MemoryRegisterPanel regPane: this.memoryRegisters) {
            regPane.setBackground(new java.awt.Color(240, 240, 240));
        }
        this.memoryRegisters[memoryPos].setBackground(java.awt.Color.LIGHT_GRAY);
    }
    
    /**
     * Starts the view frame.
     */
    public void startView() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        this.view = new MainFrame();
        MainFrame view = this.view;
        this.view.openFileButton.addActionListener(this);
        this.view.executeButton.addActionListener(this);
        this.view.nextButton.addActionListener(this);
        this.loadMemoryView();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                view.setVisible(true);
            }
        });
    }
}
