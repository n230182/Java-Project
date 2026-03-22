package frontend;
import javax.swing.*;
import java.awt.event.*;

public class NGOMenu implements ActionListener{
    JFrame f;
    JButton register, login;
    public NGOMenu(){
        f = new JFrame("NGO Menu");
        f.setSize(400, 300);
        f.setLayout(null);
        register = new JButton("Register NGO");
        register.setBounds(120, 80, 150, 40);
        login = new JButton("Login NGO");
        login.setBounds(120, 150, 150, 40);
        f.add(register);
        f.add(login);
        register.addActionListener(this);
        login.addActionListener(this);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == register){
            new NGORegisterUI();
        }
        else if(e.getSource() == login){
            new NGOLoginUI();
        }
    }
}