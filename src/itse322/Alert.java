
package itse322;

import javax.swing.JOptionPane;

/**
 *
 * @author youse
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
