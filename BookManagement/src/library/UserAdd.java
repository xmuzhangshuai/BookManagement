package library;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;
import java.sql.*;

public class UserAdd
        extends JFrame
        implements ActionListener{

    JTextField user_name, lib_card_num;
    JPasswordField user_passwd, user_passwd_confirm, lib_card_passwd;
    JButton ok = new JButton("注册");
    JButton cancle = new JButton("取消");
    int j;

    //static boolean isRoot=false;
    public UserAdd(int i){
        j = i;
        setTitle("注册");
        Container cp = getContentPane();
        JLabel label1 = new JLabel("用    户   名");
        JLabel label2 = new JLabel("密          码");
        JLabel label3 = new JLabel("密 码  确 认");
        JLabel label4 = new JLabel("图 书  证 号");
        JLabel label5 = new JLabel("密          码");
        JPanel pn1 = new JPanel();
        JPanel pn2 = new JPanel();
        JPanel pn1_north = new JPanel();
        JPanel pn1_south = new JPanel();
        JPanel pn1_north_west = new JPanel(new GridLayout(4, 1));
        JPanel pn1_north_center = new JPanel(new GridLayout(4, 1));
        JPanel pn1_south_west = new JPanel(new GridLayout(9, 1));
        JPanel pn1_south_center = new JPanel(new GridLayout(7, 1));
        user_name = new JTextField(18);
        user_passwd = new JPasswordField(18);
        user_passwd_confirm = new JPasswordField(18);
        lib_card_num = new JTextField(18);
        lib_card_passwd = new JPasswordField(18);
        JLabel label6 = new JLabel("<html><font color=#CC00FF size='7'><i>"
                + "欢迎注册</i></font>", SwingConstants.CENTER);

        cp.add(label6, "North");
        cp.add(pn1, "Center");
        cp.add(pn2, "South");

        if(j == 1){


            pn1.add(pn1_north, "North");
            pn1_north.add(pn1_north_west);
            pn1_north.add(pn1_north_center);
            pn1_north_west.add(new JLabel(""));
            pn1_north_center.add(new JLabel("身份验证："));
            pn1_north_west.add(label4);
            pn1_north_center.add(lib_card_num);
            pn1_north_west.add(new JLabel(""));
            pn1_north_center.add(new JLabel(""));
            pn1_north_west.add(label5);
            pn1_north_center.add(lib_card_passwd);
            pn1_north.setBackground(Color.PINK);
            pn1_north_center.setBackground(Color.PINK);
            pn1_north_west.setBackground(Color.PINK);
        }

        pn1.add(pn1_south, "South");
        pn1_south.add(pn1_south_west);
        pn1_south.add(pn1_south_center);
        pn1_south_west.add(new JLabel(""));
        pn1_south_west.add(new JLabel(""));
        pn1_south_west.add(new JLabel(""));
        pn1_south_center.add(new JLabel(""));
        pn1_south_center.add(new JLabel("注册信息："));
        pn1_south_west.add(label1);
        pn1_south_center.add(user_name);
        pn1_south_west.add(new JLabel(""));
        pn1_south_center.add(new JLabel(""));
        pn1_south_west.add(label2);
        pn1_south_center.add(user_passwd);
        pn1_south_west.add(new JLabel(""));
        pn1_south_center.add(new JLabel(""));
        pn1_south_west.add(label3);
        pn1_south_center.add(user_passwd_confirm);

        pn1.setBackground(Color.PINK);

        pn1_south.setBackground(Color.PINK);
        pn1_south_center.setBackground(Color.PINK);
        pn1_south_west.setBackground(Color.PINK);
        pn2.add(ok);
        pn2.add(cancle);


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        //setSize(x,y); /*让系统窗口平铺整个显示器窗口*/

        setSize(320, 380);
        int xcenter = (x - 300) / 2;
        int ycenter = (y - 430) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setResizable(false);

        /*if (!Login.isRegisterButton)
        {
        pn1_north.setVisible(false);
        }*/

        ok.addActionListener(this);//注册事件监听器
        cancle.addActionListener(this);
        addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e){
        //String cmd=e.getActionCommand();
        Object source = e.getSource();
        if(source == ok){
            if(user_name.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入用户名！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(user_passwd.getText().length() < 6){
                JOptionPane.showMessageDialog(null, "密码强度太弱！"
                        + "请设6位以上密码", "提示", JOptionPane.YES_NO_OPTION);
                user_passwd.setText("");
                user_passwd_confirm.setText("");
                return;
            }
            if(!user_passwd.getText().trim().equals(user_passwd_confirm.getText().
                    trim())){
                JOptionPane.showMessageDialog(null, "密码不一致！", "提示",
                        JOptionPane.YES_NO_OPTION);
                user_passwd.setText("");
                user_passwd_confirm.setText("");
                return;
            }
            /*if (!MainWin.userName.equals("root"))
            {
            confirm("reader", "readerID");
            }
            else
            {
            confirm("admin", "adminID");
            }*/
            confirm();
        }
        else if(source == cancle){
            dispose();
        }
    }

    /*public static void main(String[] arg)
    {
    new UserAdd(1).setVisible(true);
    }*/
//    public void confirm(String aaa, String bbb)
    public void confirm(){
        String sql_queryStudent, sql_inset, sql_queryID;
        String name = user_name.getText().trim();
        String tf2_stuNum = lib_card_num.getText().trim();
        sql_queryStudent = "select * from student where stuNum='" + tf2_stuNum
                + "'";
        if(j == 1){

            sql_queryID =
                    "select * from reader where readerID='" + name + "'";
            sql_inset =
                    "insert into reader values('" + user_name.getText().
                    trim() + "','" + user_passwd.getText().trim() + "')";
        }
        else{
            sql_queryID = "select * from admin where adminID='" + tf2_stuNum
                    + "'";
            sql_inset =
                    "insert into admin values('" + user_name.getText().
                    trim() + "','" + user_passwd.getText().trim() + "')";
        }
        try{
            Database db = new Database();
            db.dbCon();
            db.stmt2 = Database.con.createStatement();
            //if (!MainWin.userName.equals("root"))
            //{
            ResultSet student = db.stmt.executeQuery(sql_queryStudent);
            ResultSet user = db.stmt2.executeQuery(sql_queryID);
            if(user.next()){
                JOptionPane.showMessageDialog(null,
                        "该用户名已存在！", "提示！", JOptionPane.YES_NO_OPTION);
                user_name.setText("");
                user_passwd.setText("");
                user_passwd_confirm.setText("");
                return;
            }
            /*else
            {
            int inseted = db.stmt.executeUpdate(sql_inset);
            if (inseted == 1)
            {
            JOptionPane.showMessageDialog(null, "注册成功！");
            db.dbClose();
            hide();
            }
            }*/
            //}
            //else
            //{

            //boolean b2 = user.next();
            //ResultSet rs_readerID = db.stmt.executeQuery(queryID);
            //boolean b3 = rs_readerID.next();

            if(j == 1){
                if(!student.next()){
                    JOptionPane.showMessageDialog(null,
                            "无效图书证号！", "提示！", JOptionPane.YES_NO_OPTION);
                    lib_card_num.setText("");
                    lib_card_passwd.setText("");
                    return;
                }
                else if(student.next() && user.next()){
                    JOptionPane.showMessageDialog(null,
                            "该图书证号已注册！", "提示！", JOptionPane.YES_NO_OPTION);
                }
                else{
                    String passwd = student.getString(6);
                    String pf3_passwd = lib_card_passwd.getText().trim();
                    if(passwd.equals(pf3_passwd) == false){
                        JOptionPane.showMessageDialog(null,
                                "图书证密码错误！", "提示！", JOptionPane.YES_NO_OPTION);
                        lib_card_passwd.setText("");
                    }
                    return;
                }

            }
            if(user_passwd.getText().trim().equals(user_passwd_confirm.getText().
                    trim())){
                int inseted = db.stmt.executeUpdate(sql_inset);
                if(inseted == 1){
                    JOptionPane.showMessageDialog(null, "注册成功！");
                    db.dbClose();
                    hide();
                }
            }

        }
        catch(SQLException g){
            System.out.println("E Code:" + g.getErrorCode());
            System.out.println("E M:" + g.getMessage());
            JOptionPane.showMessageDialog(null, "操作失败！");
        }
    }
}
