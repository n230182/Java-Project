package frontend;
import javax.swing.*;
import java.awt.event.*;
import backend.UserReport;

public class UserReportUI implements ActionListener{
    JFrame f;
    JTextField[] t;
    JButton submit;
    JTextArea area;
    JScrollPane sp;

    public UserReportUI(){
        f = new JFrame("Report Animal Injury");
        f.setSize(450, 550);
        f.setLayout(null);
        String[] labels = {
            "Name", "Mobile", "Injury",
            "City", "District", "State",
            "Pincode", "Priority"
        };
        t = new JTextField[8];
        for(int i = 0; i < 8; i++){
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(50, 30 + i*40, 100, 30);
            f.add(lbl);
            t[i] = new JTextField();
            t[i].setBounds(160, 30 + i*40, 180, 30);
            f.add(t[i]);
        }
        submit = new JButton("Submit");
        submit.setBounds(150, 360, 120, 30);
        f.add(submit);
        area = new JTextArea();
        area.setEditable(false);
        sp = new JScrollPane(area);
        sp.setBounds(50, 410, 330, 100);
        f.add(sp);
        submit.addActionListener(this);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submit){
            for(int i = 0; i < 8; i++){
                if(t[i].getText().isBlank()){
                    JOptionPane.showMessageDialog(f, "Please fill all fields");
                    return;
                }
            }
            if(!validateUser(t[0].getText().trim())){
                JOptionPane.showMessageDialog(f, "Name can contain only letters and spaces!");
                return;
            }
            if(!validateMobile(t[1].getText().trim())){
                JOptionPane.showMessageDialog(f, "Mobile must be 10 digits!");
                return;
            }
            if(!validatePincode(t[6].getText().trim())){
                JOptionPane.showMessageDialog(f, "Pincode must be 6 digits!");
                return;
            }
            String priority = t[7].getText().trim();
            if(!priority.equalsIgnoreCase("Emergency") && !priority.equalsIgnoreCase("Normal")){
                JOptionPane.showMessageDialog(f, "Priority must be 'Emergency' or 'Normal'");
                return;
            }
            UserReport r = new UserReport(
                    t[0].getText().trim(),
                    t[1].getText().trim(),
                    t[2].getText().trim(),
                    t[3].getText().trim(),
                    t[4].getText().trim(),
                    t[5].getText().trim(),
                    t[6].getText().trim(),
                    priority
            );
            r.saveToFile();
            String ngos = UserReport.getNGOByLocation(
                t[3].getText().trim(), // city
                t[4].getText().trim(), // district
                t[5].getText().trim(), // state
                t[6].getText().trim()  // pincode
            );
            area.setText(ngos);
            JOptionPane.showMessageDialog(f, "Report Submitted Successfully");
        }
    }

    public boolean validateUser(String user){
        for(char ch : user.toCharArray()){
            if(!Character.isLetter(ch) && ch != ' ') return false;
        }
        return true;
    }

    public boolean validateMobile(String mobile){
        if(mobile.length() != 10) return false;
        for(char ch : mobile.toCharArray()){
            if(!Character.isDigit(ch)) return false;
        }
        return true;
    }

    public boolean validatePincode(String pincode){
        if(pincode.length() != 6) return false;
        for(char ch : pincode.toCharArray()){
            if(!Character.isDigit(ch)) return false;
        }
        return true;
    }
}