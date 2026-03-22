package frontend;
import javax.swing.*;
import java.awt.event.*;
import backend.NGO;

public class NGORegisterUI implements ActionListener {
    JFrame f;
    JTextField[] t;
    JButton submit;
    public NGORegisterUI(){
        f = new JFrame("Register NGO");
        f.setSize(400, 500);
        f.setLayout(null);
        String[] labels = {"Email","Password","Name","Mobile","GovID","State","District","City","Pincode"};
        t = new JTextField[9];
        for(int i=0;i<9;i++){
            f.add(new JLabel(labels[i])).setBounds(50,30+i*40,100,30);
            t[i] = new JTextField();
            t[i].setBounds(150,30+i*40,150,30);
            f.add(t[i]);
        }
        submit = new JButton("Register");
        submit.setBounds(120,400,120,30);
        f.add(submit);
        submit.addActionListener(this);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submit){
            String email = t[0].getText().trim();
            String password = t[1].getText().trim();
            String name = t[2].getText().trim();
            String mobile = t[3].getText().trim();
            String govId = t[4].getText().trim();
            String state = t[5].getText().trim();
            String district = t[6].getText().trim();
            String city = t[7].getText().trim();
            String pincode = t[8].getText().trim();
            if(email.isBlank() || password.isBlank() || name.isBlank() || mobile.isBlank() ||
               govId.isBlank() || state.isBlank() || district.isBlank() || city.isBlank() || pincode.isBlank()){
                JOptionPane.showMessageDialog(f, "Please fill all fields!");
                return;
            }
            if(!validateEmail(email)){
                JOptionPane.showMessageDialog(f,"Invalid Email!");
                return;
            }
            if(!validatePassword(password)){
                JOptionPane.showMessageDialog(f,"Password must be at least 8 characters and include a letter, a digit, and a special character!");
                return;
            }
            if(!validateUser(name)){
                JOptionPane.showMessageDialog(f,"Name can only contain letters and spaces!");
                return;
            }
            if(!validateMobile(mobile)){
                JOptionPane.showMessageDialog(f,"Mobile number must be 10 digits!");
                return;
            }
            NGO ngo = new NGO(email, password, name, mobile, govId, state, district, city, pincode);
            ngo.saveToFile();
            JOptionPane.showMessageDialog(f,"Registered Successfully!");
        }
    }

    public boolean validateEmail(String email){
        int at = email.length() - email.replace("@","").length();
        int dot = email.length() - email.replace(".","").length();
        if((email.indexOf('@') <= 0 || email.indexOf('@') == email.length()-1) ||
           (email.indexOf('@') > email.lastIndexOf('.')) || at != 1 || dot < 1){
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password){
        if(password.length() < 8) return false;
        boolean hasLetter=false, hasDigit=false, hasSpecial=false;
        for(char ch:password.toCharArray()){
            if(Character.isLetter(ch)) hasLetter=true;
            else if(Character.isDigit(ch)) hasDigit=true;
            else hasSpecial=true;
        }
        return hasLetter && hasDigit && hasSpecial;
    }

    public boolean validateUser(String user){
        for(char ch:user.toCharArray()){
            if(!Character.isLetter(ch) && ch!=' ') return false;
        }
        return true;
    }

    public boolean validateMobile(String mobile){
        if(mobile.length() != 10) return false;
        for(char ch:mobile.toCharArray()){
            if(!Character.isDigit(ch)) return false;
        }
        return true;
    }
}