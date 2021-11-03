import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginpage extends JFrame {

    JLabel lblUser,lblPass,lblwelcome;
    JTextField tf_user;
    JPasswordField pf_pass;
    JButton btnuserlog,btnadminlog,btnexit;
    JPanel panel;

    public loginpage(){

        setTitle("Login Page");
        getContentPane().setBackground(Color.GRAY);

        lblwelcome = new JLabel("Welcome to stadium seats booking system");
        lblwelcome.setBounds(15,10,420,50);
        lblwelcome.setOpaque(true);
        lblwelcome.setBackground(Color.ORANGE);
        lblwelcome.setFont(new Font("Comic Sans MS",Font.BOLD,20));

        panel = new JPanel();
        panel.setBounds(20,90,410,300);
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.setLayout(null);

        lblUser = new JLabel("Username");
        lblUser.setBounds(95,40,100,50);

        tf_user = new JTextField();
        tf_user.setBounds(190,55,110,20);

        lblPass = new JLabel("Password");
        lblPass.setBounds(95,80,100,50);

        pf_pass = new JPasswordField();
        pf_pass.setBounds(190,95,110,20);

        btnadminlog = new JButton("Admin Login");
        btnadminlog.setBounds(80,150,110,30);

        btnuserlog = new JButton("User Login");
        btnuserlog.setBounds(210,150,110,30);

        btnexit = new JButton("Exit");
        btnexit.setBounds(155,200,90,30);


        add(panel);
        panel.add(lblUser);
        panel.add(lblPass);
        panel.add(tf_user);
        panel.add(pf_pass);
        panel.add(btnuserlog);
        panel.add(btnexit);
        panel.add(btnadminlog);
        add(lblwelcome);


        btnuserlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String un = tf_user.getText();
                    String pwd = pf_pass.getText();

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project", "root", "Shivam@1234");

                    String str = "Select * from login where username=? and password=?";

                    PreparedStatement st = conn.prepareStatement(str);

                    st.setString(1,un);
                    st.setString(2,pwd);

                    ResultSet rs = st.executeQuery();

                   if(rs.next()) {
                       JOptionPane.showMessageDialog(null, "You are logged in");
                       new bookingpage().setVisible(true);
                       dispose();
                   }
                   else
                       JOptionPane.showMessageDialog(null, "Username or Password is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                       tf_user.setText("");
                       pf_pass.setText("");
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Error while establishing the connection","Error",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnadminlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String adminUser = tf_user.getText();
                    String adminPwd = pf_pass.getText();

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project", "root", "Shivam@1234");

                    String str = "Select * from adminlogin where username=? and password=?";

                    PreparedStatement st = conn.prepareStatement(str);

                    st.setString(1,adminUser);
                    st.setString(2,adminPwd);

                    ResultSet rs = st.executeQuery();

                    if(rs.next()) {
                        JOptionPane.showMessageDialog(null, "You are logged in");
                        new admin().setVisible(true);
                        dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Username or Password is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                        tf_user.setText("");
                        pf_pass.setText("");
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Error while establishing the connection","Error",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470,490);

    }

    public static void main(String[] args) {
        new loginpage();
    }
}
