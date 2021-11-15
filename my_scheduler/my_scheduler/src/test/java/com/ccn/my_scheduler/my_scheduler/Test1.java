package com.ccn.my_scheduler.my_scheduler;

import java.sql.Connection;

public class Test1 {
	
	public static void main(String[] args) {
		Connection conn = DBConn.getConnection(); // 정적메소드로 호출

		if (conn != null) {
			System.out.println("데이터베이스 연결 성공~!!!");
		}
		DBConn.close();
	}
}
