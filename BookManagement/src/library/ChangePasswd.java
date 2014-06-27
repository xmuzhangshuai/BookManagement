package library;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ChangePasswd
        extends JFrame
        implements ActionListener{

    JPasswordField old_passwd, new_passwd, new_passwd_confirm;    //定义文本框
    static String username;
    JButton ok = new JButton("确定");
    JButton cancle = new JButton("取消");

    public ChangePasswd(String s){
        username = s;
        Container cp = getContentPane();

        JLabel label1 = new JLabel("原密码");
        JLabel label2 = new JLabel("新密码");
        JLabel label3 = new JLabel("密码确认");
        JPanel pn1 = new JPanel();
        JPanel pn2 = new JPanel();
        JPanel pn1_west = new JPanel(new GridLayout(6, 1));
        JPanel pn1_center = new JPanel(new GridLayout(6, 1));
        old_passwd = new JPasswordField(18);
        new_passwd = new JPasswordField(18);
        new_passwd_confirm = new JPasswordField(18);

        pn1_west.add(new JLabel(""));
        pn1_center.add(new JLabel(""));
        pn1_west.add(label1);
        pn1_center.add(old_passwd);
        pn1_west.add(new JLabel(""));
        pn1_center.add(new JLabel(""));
        pn1_west.add(label2);
        pn1_center.add(new_passwd);
        pn1_west.add(new JLabel(""));
        pn1_center.add(new JLabel(""));
        pn1_west.add(label3);
        pn1_center.add(new_passwd_confirm);

        pn1.add(pn1_west, "West");
        pn1.add(pn1_center, "Center");
        pn1_west.setBackground(Color.PINK);
        pn1_center.setBackground(Color.PINK);
        pn1.setBackground(Color.PINK);
        pn2.add(ok);
        pn2.add(cancle);
        JLabel label4 = new JLabel("<html><font color=#CC00FF size='7'><i>"
                + "密码修改</i></font>", SwingConstants.CENTER);
        cp.add(label4, "North");
        cp.add(pn1, "Center");
        cp.add(pn2, "South");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        setSize(300, 300);
        int xcenter = (x - 300) / 2;
        int ycenter = (y - 300) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setTitle("修改密码");
        setResizable(false);

        ok.addActionListener(this);//注册监听器
        cancle.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        //String cmd=e.getActionCommand();
        Object source = e.getSource();
        if(source == ok){
            if((new_passwd.getText()) == null ? (new_passwd_confirm.getText())
                    != null : !(new_passwd.getText()).equals(new_passwd_confirm.
                    getText())){
                JOptionPane.showMessageDialog(null, "新密码不一致！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(new_passwd.getText().length() < 6){
                JOptionPane.showMessageDialog(null, "新密码强度太弱！"
                        + "请设6位以上密码", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(new_passwd.getText().trim().equals(new_passwd_confirm.getText().
                    trim())){
                updatePasswd();
                /*if (Login.isAdmin)
                {
                updatePasswd("admin", "adminID", "passwd");
                }
                else
                {
                updatePasswd("reader", "readerID", "readerPasswd");
                }*/
            }

        }
        else if(source == cancle){
            this.hide();
        }
    }

    /* public static void main(String[]args)
    {
    ChangePasswd changePasswd = new ChangePasswd("");
    }*/
    //public void updatePasswd(String table, String name, String pw)
    public void updatePasswd(){
        Database db = new Database();
        db.dbCon();
        try{
//            String query = "select * from " + table + " where " + name + "='" + username + "'";
            String query = "select * from admin where adminID='" + username
                    + "'";
            ResultSet rs = db.stmt.executeQuery(query);
            if(rs.next()){


                String oldPasswd = rs.getString(2);
                String newPasswd = new_passwd.getText().trim();
                if(old_passwd.getText().trim().equals(oldPasswd)){
                    String s = "update admin set passwd='" + newPasswd
                            + "' where adminID='" + username + "'";
//                String s = "update " + table + " set " + pw + "='" + newPasswd
//                        + "' where " + name + "='" + username + "'";
                    //sql_=con_.createStatement();
                    int updatePasswd = db.stmt.executeUpdate(s);
                    if(updatePasswd == 1){
                        JOptionPane.showMessageDialog(this,
                                "密码修改成功！");

                    }
                    db.dbClose();
                    this.hide();
                }
                else{
                    JOptionPane.showMessageDialog(null, "密码不正确！", "提示！",
                            JOptionPane.YES_NO_OPTION);
                    return;
                }
                old_passwd.setText("");
                new_passwd.setText("");
                new_passwd_confirm.setText("");
            }
            else{
                JOptionPane.showMessageDialog(null, "密码不正确！", "提示！",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
        }
        catch(SQLException g){
            System.out.println("E Code" + g.getErrorCode());
            System.out.println("E M" + g.getMessage());
            JOptionPane.showMessageDialog(null, "操作失败！");
        }

    }
}
