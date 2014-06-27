package library;

import javax.swing.JOptionPane;
import java.sql.*;

public class ShowBook{

    public static int count, maxPage;
    private final int ROW = MainWin.ROW;
    private final int PAGE = 20;
    static String array[][][];
//    static MainWin mw;

    /**
     * 第一次显示图书时调用的显示函数
     */
    public void showFirst(String s){
        String sql = s;

        array = get(sql);
        clear(MainWin.ar);
        //MainWin.ar = array[0];
        for(int i = 0; i < ROW; i++){
            System.arraycopy(array[0][i], 0, MainWin.ar[i], 0, 7);
        }
        MainWin.table.repaint();
        MainWin.pageValue=0;
        MainWin.showPage.setText("第 1 页");
        //new MainWin(MainWin.userName).setVisible(true);
        if(maxPage> 0){
            MainWin.nextButton = true;
            MainWin.lastButton = true;
        }

    }

    /**
     *
     * 点击 “首页”，“末页”，“前一页”，“下一页”按钮时调用的显示函数
     */
    public void showPage(int n){
        if(n > maxPage|| n < 0){
            return;
        }
        clear(MainWin.ar);
        //MainWin.ar = array[n];
        for(int i = 0; i < ROW; i++){
            System.arraycopy(array[n][i], 0, MainWin.ar[i], 0, 7);
        }
        MainWin.table.repaint();
        //new MainWin(MainWin.userName).setVisible(true);
        MainWin.showPage.setText("第 "+(n+1)+" 页");
    }

    /**
     * 在重新显示图书前擦除全局静态数组变量ar，从而实现清空table表
     */
    private void clear(String[][] ar){
        for(int i = 0; i < ROW; i++){
            ar[i][0] = "";
            ar[i][1] = "";
            ar[i][2] = "";
            ar[i][3] = "";
            ar[i][4] = "";
            ar[i][5] = "";
            ar[i][6] = "";
        }
    }

    /**从数据库中获取查询的结果，保存在三维字符串数组中，
     * maxPage用以将结果分段，便于多页显示
     */
    private String[][][] get(String s){
        String sql = s;
        int page=0;
        count=0;
        String ar[][][] = new String[PAGE][ROW][7];
        Database db = new Database();
        db.dbCon();
        try{
            ResultSet rs = db.stmt.executeQuery(sql);
            page = 0;
            for(int i = 0; i < PAGE; i++){
                for(int j = 0; j < ROW; j++){
                    if(rs.next()){
                        ar[page][j][0] = rs.getString(1);
                        ar[page][j][1] = rs.getString(2);
                        ar[page][j][2] = rs.getString(3);
                        ar[page][j][3] = rs.getString(4);
                        ar[page][j][4] = rs.getString(5);
                        ar[page][j][5] = rs.getString(6);
                        ar[page][j][6] = rs.getString(7);
                        count++;
                    }
                    else{
                        j = ROW;
                        i = PAGE;
                    }
                }
                page++;
            }
            db.dbClose();
            if(count%ROW==0){
                maxPage=page-2;
            }else{
                maxPage=page-1;
            }
            
            return ar;
        }
        catch(SQLException g){
            System.out.println("E Code:" + g.getErrorCode());
            System.out.println("E M:" + g.getMessage());
            JOptionPane.showMessageDialog(null, "查询失败！",
                    "信息", JOptionPane.YES_NO_OPTION);
        }
        return null;
    }
}
