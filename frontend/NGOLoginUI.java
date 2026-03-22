package frontend;
import javax.swing.*;
import java.awt.event.*;
import backend.NGO;

public class NGOLoginUI implements ActionListener {
    JFrame f;
    JTextField[] t = new JTextField[3];
    JButton login, resolve;
    JTextArea area;
    public NGOLoginUI() {
        f = new JFrame("NGO Login");
        f.setSize(500, 520);
        f.setLayout(null);
        String[] labels = {"Email", "Password", "User Mobile"};
        for (int i = 0; i < 2; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(50, 50 + i*50, 100, 30);
            f.add(lbl);
            t[i] = new JTextField();
            t[i].setBounds(150, 50 + i*50, 150, 30);
            f.add(t[i]);
        }
        login = new JButton("Login");
        login.setBounds(320, 100, 100, 30);
        f.add(login);
        area = new JTextArea();
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(50, 150, 400, 220);
        f.add(sp);
        JLabel lbl3 = new JLabel(labels[2]);
        lbl3.setBounds(50, 380, 100, 30);
        f.add(lbl3);
        t[2] = new JTextField();
        t[2].setBounds(150, 380, 150, 30);
        f.add(t[2]);
        resolve = new JButton("Resolve");
        resolve.setBounds(150, 420, 120, 30);
        f.add(resolve);
        login.addActionListener(this);
        resolve.addActionListener(this);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String email = t[0].getText().trim();
        String password = t[1].getText().trim();
        String userMobile = t[2].getText().trim();
        if(!validateEmail(email)){
            JOptionPane.showMessageDialog(f, "Invalid Email!");
            return;
        }
        if(!validatePassword(password)){
            JOptionPane.showMessageDialog(f, "Password must be at least 8 characters and include a letter, a digit, and a special character!");
            return;
        }

        if(e.getSource() == login){
            String data = NGO.loginNGO(email, password);
            if(data != null){
                String[] d = data.split(",");
                area.setText(NGO.getReportsForNGO(d[1], d[2], d[3], d[4]));
            } else {
                JOptionPane.showMessageDialog(f, "Invalid Login");
            }
        }
        else if(e.getSource() == resolve){
            if(!validateMobile(userMobile)){
                JOptionPane.showMessageDialog(f, "User Mobile must be 10 digits!");
                return;
            }
            String data = NGO.loginNGO(email, password);
            if(data != null){
                String[] d = data.split(",");
                NGO.resolveReport(d[0], d[5], userMobile);
                JOptionPane.showMessageDialog(f, "Case Marked as Resolved!");
                area.setText(NGO.getReportsForNGO(d[1], d[2], d[3], d[4]));
            }
            else{
                JOptionPane.showMessageDialog(f, "Invalid Login");
            }
        }
    }

    public boolean validateEmail(String email){
        int at = email.length() - email.replace("@", "").length();
        int dot = email.length() - email.replace(".", "").length();
        if((email.indexOf('@') <= 0 || email.indexOf('@') == email.length()-1) ||
           (email.indexOf('@') > email.lastIndexOf('.')) || at != 1 || dot < 1){
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password){
        if(password.length() < 8) return false;
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for(char ch : password.toCharArray()){
            if(Character.isLetter(ch)) hasLetter = true;
            else if(Character.isDigit(ch)) hasDigit = true;
            else hasSpecial = true;
        }
        return hasLetter && hasDigit && hasSpecial;
    }

    public boolean validateMobile(String mobile){
        if(mobile.length() != 10) return false;
        for(char ch : mobile.toCharArray()){
            if(!Character.isDigit(ch)) return false;
        }
        return true;
    }
}