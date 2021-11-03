import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Vector;

public class bookingpage extends JFrame {

    JLabel lblmatch,name,seat_no,number,details;
    JTextField tf_name,tf_seat,tf_num;
    JButton btnadd,btnok,btnshow,btnlogout;
    JComboBox cmatch;
    JScrollPane pane;
    JTable table;
    JPanel pan;

    public bookingpage(){

        setTitle("Book your Seats");
        getContentPane().setBackground(Color.ORANGE);

        String opt[] = {"IND vs PAK","IND vs NZ","IND vs AFG","IND vs SCO","IND vs NMB"};
        cmatch = new JComboBox(opt);
        cmatch.setBounds(270,25,100,20);

        pan = new JPanel();
        pan.setBounds(10,50,250,200);
        pan.setBorder(BorderFactory.createRaisedBevelBorder());
        pan.setLayout(null);

        lblmatch = new JLabel("Match:");
        lblmatch.setBounds(220,10,100,50);

        btnshow = new JButton("SHOW");
        btnshow.setBounds(385,25,100,20);

        DefaultTableModel mode = new DefaultTableModel();
        table=new JTable(mode);
        pane = new JScrollPane(table);
        mode.addColumn("Match");
        mode.addColumn("Seats");
        mode.addColumn("Status");
        mode.addColumn("Name");
        mode.addColumn("Contact");
        pane.setBounds(270,50,500,300);

        details = new JLabel("Add Details");
        details.setBounds(85,2,100,50);
        details.setFont(new Font("Comic Sans MS",Font.BOLD,15));

        name = new JLabel("Customer Name");
        name.setBounds(10,30,100,50);

        seat_no = new JLabel("Seat No.");
        seat_no.setBounds(10,60,100,50);

        number = new JLabel("Contact No.");
        number.setBounds(10,90,110,50);

        tf_name = new JTextField();
        tf_name.setBounds(125,45,100,20);

        tf_seat = new JTextField();
        tf_seat.setBounds(125,75,100,20);

        tf_num = new JTextField();
        tf_num.setBounds(125,105,100,20);

        btnadd = new JButton("ADD");
        btnadd.setBounds(10,140,100,20);

        btnok = new JButton("OK");
        btnok.setBounds(125,140,100,20);

        btnlogout = new JButton("LOGOUT");
        btnlogout.setBounds(650,25,100,20);


        pan.add(details);
        pan.add(name);
        pan.add(seat_no);
        pan.add(number);
        pan.add(tf_name);
        pan.add(tf_num);
        pan.add(tf_seat);
        add(btnlogout);
        pan.add(btnadd);
        pan.add(btnok);
        add(cmatch);
        add(btnshow);
        add(lblmatch);
        add(pane);
        add(pan);

        btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nam = tf_name.getText();
                    String seat_num = tf_seat.getText();
                    String num = tf_num.getText();

                    DefaultTableModel model = (DefaultTableModel)table.getModel();
                    int selected = table.getSelectedRow();

                    String matchname = model.getValueAt(selected,0).toString();

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project", "root", "Shivam@1234");

                    String str = "insert into booking (matchname,seats,cust_name,cust_phone) values (?,?,?,?)";

                    PreparedStatement st = conn.prepareStatement(str);

                    st.setString(1,matchname);
                    st.setString(2,seat_num);
                    st.setString(3,nam);
                    st.setString(4,num);
                    st.executeUpdate();

                    String str1 = "update seat set status = ? where seats = ?";

                    PreparedStatement st1 = conn.prepareStatement(str1);

                    st1.setString(1,"Booked");
                    st1.setString(2,seat_num);
                    st1.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Seat Added Successfully");

                    Load();

                    tf_name.setText("");
                    tf_seat.setText("");
                    tf_num.setText("");
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Error adding seats!!!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

       btnlogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loginpage().setVisible(true);
                dispose();
            }
        });

        btnok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new seatbooked().setVisible(true);
                dispose();
            }
        });

        btnshow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                int selected = table.getSelectedRow();

                String status = model.getValueAt(selected,2).toString();

                if(!status.equals("Booked")){
                    String seat = model.getValueAt(selected,1).toString();
                    tf_seat.setText(seat);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"This seat is already booked");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        setLayout(null);
        setVisible(true);
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Load(){
        try{
            String match = cmatch.getSelectedItem().toString();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project", "root", "Shivam@1234");

            String str = "select seat.matchname,seat.seats,seat.status,booking.cust_name,booking.cust_phone from seat left join booking on seat.seats = booking.seats where seat.matchname = ?";

            PreparedStatement pst = conn.prepareStatement(str);
            pst.setString(1,match);
            ResultSet res = pst.executeQuery();

            ResultSetMetaData rsd = res.getMetaData();
            int a;

            a = rsd.getColumnCount();

            DefaultTableModel mod = (DefaultTableModel)table.getModel();
            mod.setRowCount(0);

            while (res.next())
            {
                Vector v = new Vector();

                for (int i=1;i<=a;i++){
                    v.add(res.getString("matchname"));
                    v.add(res.getString("seats"));
                    v.add(res.getString("status"));
                    v.add(res.getString("cust_name"));
                    v.add(res.getString("cust_phone"));
                }

                mod.addRow(v);
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error displaying contents of the table!!!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new bookingpage();
    }
}
