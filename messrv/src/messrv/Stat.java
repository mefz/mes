package messrv;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;

public class Stat extends JFrame {
	DefaultListModel lmodel = new DefaultListModel();
	JList list = new JList(lmodel);
	JScrollPane scroll = new JScrollPane(list);

	public Stat() {
		setLayout(new FlowLayout());
		setSize(400,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("MESsrv");
		add(list);
		list.setPreferredSize(new Dimension(390,160));
		setVisible(true);
	}

}
