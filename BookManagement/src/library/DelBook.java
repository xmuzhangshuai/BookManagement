package library;

import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author xia
 */
//图书删除类
public class DelBook {

	public void delBook() {
		//删除操作时先判断是否选中表格行或表格行是否为空
		//int index = MainWin.table.getSelectedRow();
		int[] index = MainWin.table.getSelectedRows();
		if (index.length == 0) //if (index.length == 0 || index.length == 0 && MainWin.table.getValueAt(int[0], 0) == "")
		{
			JOptionPane.showMessageDialog(null, "请选定要删除的表格行", "", JOptionPane.YES_NO_OPTION);
		} else {
			int selection = JOptionPane.showConfirmDialog(null, "确定要删除所选中图书吗？", "警告！", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (selection == JOptionPane.OK_OPTION) {
				//连接数据库
				Database db = new Database();
				db.dbCon();
				//将选中要删除的表格行第二项即图书号转换成字符串赋值给变量str_v
				try {
					int n = 0;
					for (int i = 0; i < index.length; ++i) {
						String str_v = (String) MainWin.table.getValueAt(index[i], 0);
						String s = "delete from book  where bookNum ='" + str_v + "'";

						//删除数据库中的记录
						int del = db.stmt.executeUpdate(s);
						if (del == 1) {
							n++;
						}
					}
					if (n == index.length) {
						JOptionPane.showMessageDialog(null, "删除成功！", "信息", JOptionPane.YES_NO_OPTION);
						//更新显示藏书

					} else {
						JOptionPane.showMessageDialog(null, n + " 条记录被删除，" + (index.length - n) + " 条记录无法删除！", "信息",
								JOptionPane.YES_NO_OPTION);
					}
					String str = "select * from book;";
					ShowBook showbk = new ShowBook();
					showbk.showFirst(str);
					MainWin.label1.setText("书库现在共有图书 " + ShowBook.count + " 本");
					db.dbClose();
				} catch (SQLException g) {
					System.out.println("E Code:" + g.getErrorCode());
					System.out.println("E M:" + g.getMessage());
					JOptionPane.showMessageDialog(null, "数据库操作失败！(错误的数据库语句)", "信息", JOptionPane.YES_NO_OPTION);
				}
			}
		}
	}
}
