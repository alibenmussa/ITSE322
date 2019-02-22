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

public class Alert {
     public static void viewWarningMessage(String string) {
        JOptionPane.showMessageDialog(null, string, "تحذير", JOptionPane.WARNING_MESSAGE);
    }
    public static void viewSuccessMessage(String string) {
        JOptionPane.showMessageDialog(null, string, "نجاح", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void viewErrorMessage(String string) {
        JOptionPane.showMessageDialog(null, string, "خطأ", JOptionPane.ERROR_MESSAGE);
    }
    
}
