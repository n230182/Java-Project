package frontend;
import javax.swing.*;
import java.awt.event.*;

public class UserMenu implements ActionListener {
    JFrame f;
    JButton report, login;

    public UserMenu(){
        f = new JFrame("User Menu");
        f.setSize(400, 300);
        f.setLayout(null);
        report = new JButton("Submit Report");
        report.setBounds(120, 80, 150, 40);
        login = new JButton("View Report");
        login.setBounds(120, 150, 150, 40);
        f.add(report);
        f.add(login);
        report.addActionListener(this);
        login.addActionListener(this);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == report){
            new UserReportUI();
        }
        else if(e.getSource() == login){
            new UserLoginUI();
        }
    }
}