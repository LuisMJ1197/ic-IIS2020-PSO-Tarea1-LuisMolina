/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * Service class that allows conversions between decimals and binary numbers.
 * @author Luism
 */
public class BinaryConversor {
    
    /**
     * Converts a numeric string into binary with a bit quantity representation (to fill zeros).
     * @param str numeric string
     * @param bitCantRepresentation bit quantity representation
     * @return Binary string
     */
    public static String toBinary(String str, int bitCantRepresentation) {
        return BinaryConversor.pad(Integer.toBinaryString(Integer.parseInt(str)), '0', bitCantRepresentation);
    }
    
    /**
     * Fill with zeros to complete "cant" number extension of the binary string.
     * @param src Source string
     * @param chr Char to fill with
     * @param cant String extension
     * @return A String with zeros at left to reach cant length.
     */
    public static String pad(String src, char chr, int cant) {
        String res = "";
        for (int i = 0; i < (cant - src.length()); i++) {
            res += chr;
        }
        res += src;
        return res;
    }
    
    /**
     * Converts a binary String into integer number (non negatives)
     * @param binString Binary String
     * @return Integer number representation of the bin string
     */
    public static int toInteger(String binString) {
        return Integer.parseInt(binString, 2);
    }
    
    /**
     * Converts a binary String into integer number (including negatives, first bit)
     * @param binString Binary String
     * @return Integer number representation of the bin string
     */
    public static int toInteger2(String binString) {
        String sign = binString.substring(0, 1);
        String number = binString.substring(1);
        int res = toInteger(number);
        if (sign.equals("1")) {
            res = res * -1;
        }
        return res;
    }
    
    /**
     * Converts a numeric string into binary with a bit quantity representation (to fill zeros). If negative, the first bit is 1.
     * @param str numeric string
     * @param bitCantRepresentation bit quantity representation
     * @return Binary string
     */
    public static String toBinary2(String str, int bitCantRepresentation) {
        String result = "";
        int number = Integer.parseInt(str);
        if (number < 0) {
            result += "1";
            result += BinaryConversor.toBinary(str.substring(1), bitCantRepresentation - 1);
        } else {
            result += "0";
            result += BinaryConversor.toBinary(str, bitCantRepresentation - 1);
        }
        return result;
    }
}
