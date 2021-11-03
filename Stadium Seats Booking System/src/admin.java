import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class admin extends JFrame {
    JComboBox addmatch;
    JButton btnadd,btnlogout;
    JLabel lblEnter,lblmanage;
    JPanel panel1;

    public admin(){

        setTitle("Admin Page");
        getContentPane().setBackground(Color.YELLOW);

        lblmanage = new JLabel("Manage Matches");
        lblmanage.setBounds(55,10,169,45);
        lblmanage.setOpaque(true);
        lblmanage.setBackground(Color.GREEN);
        lblmanage.setFont(new Font("Comic Sans MS",Font.BOLD,20));

        panel1 = new JPanel();
        panel1.setBounds(20,60,230,155);
        panel1.setBorder(BorderFactory.createRaisedBevelBorder());
        panel1.setLayout(null);

        lblEnter = new JLabel("Add Match:");
        lblEnter.setBounds(10,25,100,50);
        
        btnadd = new JButton("ADD");
        btnadd.setBounds(10,75,100,30);

        btnlogout = new JButton("LOGOUT");
        btnlogout.setBounds(120,75,100,30);

        String opt[] = {"IND vs PAK","IND vs NZ","IND vs AFG","IND vs SCO","IND vs NMB"};
        addmatch = new JComboBox(opt);
        addmatch.setBounds(120,40,100,20);

        add(panel1);
        panel1.add(lblEnter);
        panel1.add(addmatch);
        panel1.add(btnadd);
        panel1.add(btnlogout);
        add(lblmanage);

        btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String match = addmatch.getSelectedItem().toString();

                for(int i=1;i<=50;i++){
                    try{
                        int seats = i;
                        String Status = "Unbooked";

                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project", "root", "Shivam@1234");

                        String str = "insert into seat (matchname,seats,status) values (?,?,?)";

                        PreparedStatement ps = conn.prepareStatement(str);
                        ps.setString(1,match);
                        ps.setInt(2,i);
                        ps.setString(3,Status);

                        ps.executeUpdate();

                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(null,"Error inserting match details!!!","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                JOptionPane.showMessageDialog(null,"Match Added Successfully");
            }
        });

        btnlogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new loginpage().setVisible(true);
                 dispose();
            }
        });

        setLayout(null);
        setSize(290,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new admin();
    }
}
