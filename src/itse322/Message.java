package itse322;

import javax.swing.JOptionPane;

/**
 *
 * مشروع مادة جافا المتقدمة
 * 
 * 216180392 - يوسف عبد الكريم بريكة
 * 216180296 - علي جمال الدين بن موسى
 * 
 */

public class Message {
     public static void showWarning(String string) {
        JOptionPane.showMessageDialog(null, string, "تحذير", JOptionPane.WARNING_MESSAGE);
    }
    public static void showSuccess(String string) {
        JOptionPane.showMessageDialog(null, string, "نجاح", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showError(String string) {
        JOptionPane.showMessageDialog(null, string, "خطأ", JOptionPane.ERROR_MESSAGE);
    }
    
}
