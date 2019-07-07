package com.itheima.listenter;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.itheima.bean.User;
import com.itheima.service.BirthdayService;
import com.itheima.utils.DataSourceUtils;
import com.itheima.utils.DateUtils;
import com.itheima.utils.MailUtils;

public class BirthdayListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		// 服务器启动创建一个任务
		Timer timer = new Timer();
		System.out.println("开始发送邮件的时间："+new Date().toLocaleString());
		// 定时发送邮件(获取到明日00:00:00)
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// 创建service，调用方法完成查询操作
				BirthdayService bs = new BirthdayService();
				try {
					List<User> list=bs.findList();
					
					
					// 遍历list
					if(list !=null){
						for(User user:list){
							String email=user.getEmail();
							String emailMsg="亲爱的"+user.getUsername()+":生日快乐，多吃肉";
							MailUtils.sendMail(email, emailMsg);
							System.out.println(user.getUsername()+":邮件发送成功");
							
						}
					}
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
//		}, DateUtils.getDelayTime(), DateUtils.getOneDay());
		}, 4000, 1000);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
