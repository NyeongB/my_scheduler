/*==========================
        DBConn.java
============================
 */

// ※ 싱글톤(singleton) 디자인 패턴을 이용한 Database 연결 객체 생성 전용 클래스
//		→ DB 연결 과정이 가장 부하가 크기 때문에
//         연결이 필요할 때 마다 객체를 생성하는 것이 아니라
//		   한 번 연결된 객체를 계속 사용할 수 있도록 처리.  ex) 화장실 키 하나 걸어두는 것

package com.ccn.my_scheduler;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConn
{                
	// 변수 선언
				//컨트롤+스페이스
	private static Connection dbConn;
	//-- 전연벽수 - 자동으로 초기화 지원 → null 로 초기화지원
	
	// 메소드 정의 → 연결
	public static Connection getConnection() //throws ClassNotFoundException, SQLException
	{
		// 한 번 연결된 객체를 계속 사용...
		// 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
		// → singleton(디자인 패턴)
		if (dbConn == null)   // Connection 개체가 없을 때 
		{
			try
			{
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				// localhost 는 오라클 서버의 IP 주소를 기재하는 부분
				// 1521은 오라클 port number
				// xe 는 오라클 sid(express edition 은 xe)
				
				String user = "scott";	//-- 오라클 사용자 계정 이름
				String pwd = "tiger";   //-- 오라클 사용자 계정 암호
				
				// Class는 "oracle.jdbc.driver.OracleDriver"를 찾고 forName으로 인해 찾지못한 exception을 넘김
				Class.forName("oracle.jdbc.driver.OracleDriver"); // Class.forName은 ClassNotFoundException을 던짐
				//-- OracleDriver 클래스에 대한 객체 생성
				//	 드라이버 로딩 → JVM(자바가상머신)에 전달
				
				
				dbConn = DriverManager.getConnection(url, user, pwd);  // DriverManager.getConnection은 SQLException을 던짐
				//-- 오라클 서버 실제 연결
				//   위 (line 35~44)는... 연결을 위한 환경을 설정하는 과정
				//   갖고있는 인자값(매개변수)은 오라클주소, 계정명, 패스워드
			}
			catch(Exception e) // (ClassNotFoundException, SQLException) 예외발생 오라클 서버접속 실패
			{
				System.out.println(e.toString());
				//-- 오라클 서버 연결 실패 시... 오류 메세지 출력하는 부분
			}
		}
		// 구성된 연결 객체 반환
		return dbConn; //연결된 계정 계속 사용
		
	}//end getConnection()
	

	
	
	
	
	
	
	
	// 위에 있는 getConnection 오버로딩하여 매개변수를 받음
	// getConnection() 메소드의 오버로딩 → 연결
	public static Connection getConnection(String url, String user, String pwd)
	{
		if(dbConn == null)
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);  // static 정적메소드
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
			
		}
		return dbConn;
		
	}//end getConnection()
	
	
	
	// 메소드 정의 → 연결 종료 메소드
	public static void close()
	{
		// dbConn 변수(멤버 변수)는
		// Database 가 연결된 상태일 경우 Connection 을 갖는다.
		// 연결되지 않은 상태라면 null 인 상태가 된다.
		if(dbConn != null)
		{
			try
			{
				// 연결 객체의 isClosed() 메소드를 통해 연결상태 확인
				//-- 연결이 닫혀있는경우 true 반환   			 -> 종료
				//   연결이 닫혀있지 않은 경우 false 반환        -> 연결중
				if(!dbConn.isClosed())   // true가 아니면
				{						 //    ↓
					dbConn.close();      //  연결 종료 시킴
					//-- 연결 객체의 close() 메소드 호출을 통해 연결 종료
				}
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
		}
		dbConn = null;
	}// end close();
		
	
	
}
