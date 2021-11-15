package com.ccn.my_scheduler.my_scheduler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class Test2 {
	public static void main(String[] args) {
		// 데이터베이스 연결 객체 생성
		Connection conn = DBConn.getConnection();

		if (conn != null) {
			System.out.println("데이터베이스 연결 성공~!!");

			try {
				// 작업 객체 생성
				Statement stmt = conn.createStatement();

				// 쿼리문 준비
				String sql = "SELECT STUDNO, NAME, TEL FROM STUDENT ORDER BY 1";

				// 쿼리문 수행 -> 결과집합 수신
				ResultSet rs = stmt.executeQuery(sql); // java.sql 에 있는 ResultSet 인터페이스
				// ---------------- -> 객체타입의 참조변수 데이터 저장x, 값이 있는 위치를 반환

				// executeQuery() 메소드를 사용하면
				// 질의 결과를 ResultSet 객체로 가져올 수 있다.
				// 하지만, ResultSet 객체가 질의에 대한 결과물 모두를
				// 한꺼번에 받아서 가지고 있는 구조가 아니다.
				// 단지, 데이터베이스로부터 획득한 질의 결과물에 대한
				// 관리가 가능한 상태가 되는 것이다.
				// 이 때문에..args ResultSet 을 수신했다고 해서
				// 데이터베이스와의 연결을 끊게 되면,
				// ResultSet 객체는 더이상 질의 결과를 관리할 수 없게 된다.

				// ResultSet 의 다음 값의 존재 여부 확인(반환) -> true / false
				// 반복문을 활용한 ResultSet 컨트롤
				while (rs.next()) // rs 의 next 메소드는 값이 있나 없나 확인해서 true/false 값을 반환
				{
					// 값을 가져오는데 getString, getInt 등 사용 가능 (값을 저장하는 것이 아니라 찾아서 반환)

					// 레코드에서 결과값을 가져오는 것은 getter() 메소드
					// → getXxx()
					String studno = rs.getString("STUDNO"); // String sid = rs.getString(1);
					String name = rs.getString("NAME"); // String name = rs.getString(2);
					String tel = rs.getString("TEL"); // String name = rs.getString(3);

					String str = String.format("%3s %8s %12s", studno, name, tel);

					System.out.println(str);
				}

				rs.close(); // 결과집합 리소스 반납
				stmt.close(); // 작업객체 리소스 반납

			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} // end if

		DBConn.close(); // 연결 객체 리소스 반납 (ex 열쇠반납)

		System.out.println("데이터베이스 연결 닫힘");
		System.out.println("프로그램 종료");
	}
}
