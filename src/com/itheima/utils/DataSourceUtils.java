package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	private static DataSource ds=new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	
	//��ȡ���ӳ�
	public static DataSource getDataSource(){
		return ds;
	}
	
	/**
	 * �ӵ�ǰ�߳��л�ȡ����
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		//���ǵ�һ�λ�ȡ ��null
		if(conn	== null){
			conn=ds.getConnection();
			
			//��������Ӻ͵�ǰ�̰߳�
			tl.set(conn);
		}
		
		return conn;
	}
	
	/**
	 *	��������
	 * @throws SQLException 
	 */
	public static void beginTransaction() throws SQLException{
		//��ȡ����
		Connection conn = getConnection();
		
		//��������
		conn.setAutoCommit(false);
	}
	
	
	/**
	 * �ύ�������ͷ���Դ
	 */
	public static void commitAndClose(){
		try {
			//��ȡ����
			Connection conn = getConnection();
			
			//�ύ����
			if(conn != null){
				conn.commit();
			}
			
			closeConn(conn);
		} catch(Exception e){
			
		}
		
	}
	
	/**
	 * �ع��������ͷ���Դ
	 */
	public static void rollbackAndClose(){
		try {
			//��ȡ����
			Connection conn = getConnection();
			
			//�ع�����
			if(conn != null){
				conn.rollback();
			}
			
			closeConn(conn);
			
		} catch (SQLException e) {
			//
		}
	}
	
	/**
	 * �ͷ���Դ �Һ͵�ǰ�߳̽��
	 * @param conn
	 */
	private static void  closeConn(Connection conn){
		try {
			//�ͷ���Դ
			if(conn != null){
				conn.close();
			}
			//���̺߳����ӽ��
			tl.remove();
		} catch (Exception e) {
		}
		
		conn = null;
	}
}
