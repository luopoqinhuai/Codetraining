package com.reno.zhihu.zhihuLoginTest.showPicyure;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

public class DisplayImg extends JFrame {
	JPanel pic = new JPanel();

	public DisplayImg() {
		pic.setSize(200, 44);
		this.getContentPane().add(pic); // 将面板添加到JFrame上
		this.setSize(400, 100); // 初始窗口的大小
		this.setLocationRelativeTo(null); // 设置窗口居中
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	
	@Test
	public void test(){
		new DisplayImg();
	}

}
