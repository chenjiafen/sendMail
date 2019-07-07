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

		// ��������������һ������
		Timer timer = new Timer();
		System.out.println("��ʼ�����ʼ���ʱ�䣺"+new Date().toLocaleString());
		// ��ʱ�����ʼ�(��ȡ������00:00:00)
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// ����service�����÷�����ɲ�ѯ����
				BirthdayService bs = new BirthdayService();
				try {
					List<User> list=bs.findList();
					
					
					// ����list
					if(list !=null){
						for(User user:list){
							String email=user.getEmail();
							String emailMsg="�װ���"+user.getUsername()+":���տ��֣������";
							MailUtils.sendMail(email, emailMsg);
							System.out.println(user.getUsername()+":�ʼ����ͳɹ�");
							
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
