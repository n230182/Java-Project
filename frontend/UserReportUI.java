package frontend;

import javax.swing.*;
import java.awt.event.*;
import backend.UserReport;

public class UserReportUI implements ActionListener {

    JFrame f;
    JTextField[] t;
    JButton submit;
    JTextArea area;
    JScrollPane sp;  // scroll pane variable

    public UserReportUI() {

        f = new JFrame("Report");
        f.setSize(400, 450); // increased height for layout
        f.setLayout(null);

        String[] labels = {"Name", "Mobile", "Injury", "City", "Priority"};
        t = new JTextField[5];

        for(int i = 0; i < 5; i++){
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(50, 30 + i*40, 100, 30);
            f.add(lbl);

            t[i] = new JTextField();
            t[i].setBounds(150, 30 + i*40, 150, 30);
            f.add(t[i]);
        }

        // Submit button moved above scroll pane
        submit = new JButton("Submit");
        submit.setBounds(120, 230, 120, 30); 
        f.add(submit);

        // Scrollable text area below submit
        area = new JTextArea();
        area.setEditable(false);
        sp = new JScrollPane(area);
        sp.setBounds(50, 270, 300, 120); 
        f.add(sp);

        submit.addActionListener(this);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == submit){

            // Using inputs exactly as entered (no trim)
            String name = t[0].getText();
            String mobile = t[1].getText();
            String injury = t[2].getText();
            String city = t[3].getText();
            String priority = t[4].getText();

            if(name.isEmpty() || mobile.isEmpty() || injury.isEmpty() || city.isEmpty() || priority.isEmpty()){
                JOptionPane.showMessageDialog(f,"Please fill all fields");
                return;
            }

            UserReport r = new UserReport(name, mobile, injury, city, "NA", priority);
            r.saveToFile();

            // Display nearest NGOs properly
            String ngos = UserReport.getNGOByCity(city);
            area.setText("Nearby NGOs: \n" + ngos);

            JOptionPane.showMessageDialog(f, "Report Submitted");
        }
    }
}