package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainUI implements ActionListener {

    JFrame f;
    JButton ngo, user;

    public MainUI() {

        f = new JFrame("Animal Injury Reporting System");
        f.setSize(500, 400);
        f.setLayout(null);

        JLabel title = new JLabel("Animal Injury Reporting System");
        title.setBounds(100, 50, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        ngo = new JButton("Register/Login NGO");
        ngo.setBounds(150, 150, 200, 40);

        user = new JButton("Report Animal Injury/Login");
        user.setBounds(150, 220, 200, 40);

        f.add(title);
        f.add(ngo);
        f.add(user);

        ngo.addActionListener(this);
        user.addActionListener(this);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ngo) {
            new NGOMenu();
        }

        else if (e.getSource() == user) {
            new UserMenu();
        }
    }

    public static void main(String[] args) {
        new MainUI();
    }
}