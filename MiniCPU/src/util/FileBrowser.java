/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Luism
 */
public class FileBrowser {
    
    public FileBrowser() {
        
    }
    
    public File browse(javax.swing.JFrame view) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileFilter assemblerFilter = (FileFilter) new FileTypeFilter(".asm", "Assembler Code File");
        fileChooser.addChoosableFileFilter(assemblerFilter);
        int result = fileChooser.showOpenDialog(view);
        File selectedFile = null;
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                selectedFile = fileChooser.getSelectedFile();
                break;
            case JFileChooser.CANCEL_OPTION:
                break;
            default:
                break;
        }
        return selectedFile;
    }
    
    public String extractFileInfo(File file) throws IOException {
        FileInputStream tokensFile = new FileInputStream(file);
        byte[] dataBytes = new byte[(int) file.length()];
        tokensFile.read(dataBytes);
        String data = new String(dataBytes, "UTF-8");
        return data;
    }
}
