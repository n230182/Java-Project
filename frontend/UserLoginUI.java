package frontend;

import javax.swing.*;
import java.awt.event.*;
import backend.UserReport;

public class UserLoginUI implements ActionListener {
    JFrame f;
    JTextField[] t;
    JButton view;
    JTextArea area;

    public UserLoginUI(){
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

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == view){
            if(!validateUser(t[0].getText().trim())){
                JOptionPane.showMessageDialog(f, "Name can contain only letters and spaces!");
                return;
            }
            if(!validateMobile(t[1].getText().trim())){
                JOptionPane.showMessageDialog(f, "Mobile must be 10 digits!");
                return;
            }
            String result = UserReport.getUserReport(
                    t[0].getText().trim(),
                    t[1].getText().trim()
            );
            if(result.equals("No report found.")){
                area.setText(result);
                return;
            }
            area.setText(result);
            if(result.contains("Status: Resolved")){
                return;
            }
            String city = "", district = "", state = "", pincode = "";
            String[] lines = result.split("\n");
            for(String line : lines){
                if(line.startsWith("City:")){
                    city = line.replace("City:", "").trim();
                }
                else if(line.startsWith("District:")){
                    district = line.replace("District:", "").trim();
                }
                else if(line.startsWith("State:")){
                    state = line.replace("State:", "").trim();
                }
                else if(line.startsWith("Pincode:")){
                    pincode = line.replace("Pincode:", "").trim();
                }
            }
            String ngo = UserReport.getNGOByLocation(
                    city, district, state, pincode
            );
            area.append("\n" + ngo);
        }
    }
    // Name validation
    public boolean validateUser(String user){
        for(char ch : user.toCharArray()){
            if(!Character.isLetter(ch) && ch != ' ') return false;
        }
        return true;
    }
    // Mobile validation
    public boolean validateMobile(String mobile){
        if(mobile.length() != 10) return false;
        for(char ch : mobile.toCharArray()){
            if(!Character.isDigit(ch)) return false;
        }
        return true;
    }
}