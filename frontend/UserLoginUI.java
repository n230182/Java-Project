package frontend;

import javax.swing.*;
import java.awt.event.*;
import backend.UserReport;

public class UserLoginUI implements ActionListener {

    JFrame f;
    JTextField[] t;
    JButton view;
    JTextArea area;

    public UserLoginUI() {

        f = new JFrame("View Report");
        f.setSize(400, 450);
        f.setLayout(null);

        String[] labels = {"Name:", "Mobile:"};
        t = new JTextField[2];

        for(int i = 0; i < 2; i++){
            f.add(new JLabel(labels[i])).setBounds(50, 50 + i*50, 100, 30);

            t[i] = new JTextField();
            t[i].setBounds(150, 50 + i*50, 150, 30);
            f.add(t[i]);
        }

        view = new JButton("View Report");
        view.setBounds(120, 150, 150, 30);

        area = new JTextArea();
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(50, 200, 300, 180);

        f.add(view);
        f.add(sp);

        view.addActionListener(this);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == view){

            String result = UserReport.getUserReport(
                    t[0].getText(), t[1].getText());

            if(result.equals("No report found.")){
                area.setText(result);
                return;
            }

            if(result.contains("Status: Resolved")){
                String[] parts = result.split("City:");
                String report = parts[0];
                String city = parts[1].trim();

                String ngo = UserReport.getNGOByCity(city);

                area.setText(report + "\n\nNearby NGOs\n" + ngo);
            }
        }
    }
}