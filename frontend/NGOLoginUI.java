package frontend;

import javax.swing.*;
import java.awt.event.*;
import backend.NGO;

public class NGOLoginUI implements ActionListener {

    JFrame f;
    JTextField[] t= new JTextField[3]; 
    JButton login, resolve;
    JTextArea area;

    public NGOLoginUI() {

        f = new JFrame("NGO Login");
        f.setSize(500, 500);
        f.setLayout(null);

        String[] labels = {"Email", "Password", "User Number"};

        for(int i = 0; i < 2; i++){
            f.add(new JLabel(labels[i])).setBounds(50, 50 + i*50, 100, 30);

            t[i] = new JTextField();
            t[i].setBounds(150, 50 + i*50, 150, 30);
            f.add(t[i]);
        }

        area = new JTextArea();
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(50,150,400,200);
        f.add(sp);

        f.add(new JLabel(labels[2])).setBounds(50,370,100,30);

        t[2] = new JTextField();
        t[2].setBounds(150,370,150,30);
        f.add(t[2]);

        login = new JButton("Login");
        login.setBounds(320, 100, 100, 30);

        resolve = new JButton("Resolve");
        resolve.setBounds(120, 410, 100, 30);

        f.add(login);
        f.add(resolve);

        login.addActionListener(this);
        resolve.addActionListener(this);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == login){

            String data = NGO.loginNGO(
                    t[0].getText(),t[1].getText()
            );

            if(data != null){

                String[] d = data.split(",");
                String city = d[1];

                area.setText(NGO.getReportsForNGO(city));

            } else {
                JOptionPane.showMessageDialog(f,"Invalid Login");
            }
        }

        else if(e.getSource() == resolve){

            String data = NGO.loginNGO(
                    t[0].getText(),
                    t[1].getText()
            );

            if(data != null){

                String[] d = data.split(",");
                NGO.resolveReport(
                        d[0],d[2],t[2].getText()
                );

                JOptionPane.showMessageDialog(f,"Resolved!");
            }
        }
    }
}