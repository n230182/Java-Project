package frontend;

import javax.swing.*;
import java.awt.event.*;
import backend.NGO;

public class NGORegisterUI implements ActionListener {

    JFrame f;
    JTextField[] t;
    JButton submit;

    public NGORegisterUI() {

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

    public void actionPerformed(ActionEvent e) {

    if(e.getSource() == submit){

        NGO ngo = new NGO(
                t[0].getText(), t[1].getText(), t[2].getText(),
                t[3].getText(), t[4].getText(), t[5].getText(),
                t[6].getText(), t[7].getText(), t[8].getText()
        );

        ngo.saveToFile();

        JOptionPane.showMessageDialog(f,"Registered Successfully");
    }
}

}
