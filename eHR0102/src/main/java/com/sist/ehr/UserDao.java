package com.sist.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class UserDao {
	static final Logger LOG = Logger.getLogger(UserDao.class);
	/**
	 * 등록
	 * @param user
	 * @return int
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public int add(User user) throws ClassNotFoundException, SQLException {
		int flag = 0;
		//DB연결
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String url    = "jdbc:oracle:thin:@211.238.142.124:1521:xe";//url
		String userId = "HR_SPRING";//id
		String passwd = "0522";//비번
		Connection connection = DriverManager.getConnection(url, userId, passwd);
		LOG.debug("==============================");
		LOG.debug("=connection="+connection);
		LOG.debug("==============================");
//		2020-03-30 14:11:58,653 DEBUG [main] ehr.UserDao       ( UserDao.java:27)     - ==============================
//		2020-03-30 14:11:58,655 DEBUG [main] ehr.UserDao       ( UserDao.java:28)     - =connection=oracle.jdbc.driver.T4CConnection@7c30a502
//		2020-03-30 14:11:58,655 DEBUG [main] ehr.UserDao       ( UserDao.java:29)     - ==============================
		StringBuilder  sb=new StringBuilder();
		sb.append(" INSERT INTO hr_member ( \n"); 
		sb.append("     u_id,               \n");
		sb.append("     name,               \n");
		sb.append("     passwd              \n");
		sb.append(" ) VALUES (              \n");
		sb.append("     ?,                  \n");
		sb.append("     ?,                  \n");
		sb.append("     ?                   \n");
		sb.append(" )                       \n");		
		//Query수행
		LOG.debug("==============================");
		LOG.debug("=Query=\n"+sb.toString());
		LOG.debug("==============================");	
		PreparedStatement pstmt = connection.prepareStatement(sb.toString());
		pstmt.setString(1, user.getU_id());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPasswd());
		LOG.debug("==============================");
		LOG.debug("=Param="+user);
		LOG.debug("==============================");
		flag = pstmt.executeUpdate();
		LOG.debug("==============================");
		LOG.debug("=flag="+flag);
		LOG.debug("==============================");	
		
		//자원반납
		pstmt.close();
		connection.close();
		return flag;
				
	}
	
	/**
	 * 1. DB연결을 위한 커넥션을 어떻게 가지고 올까?
	 *    
	 * 2. 사용등록을 위해 DB에 보낼 SQL문장을 담은 PreparedStatement를 만들고 실행.
	 * 3. 작업이 끝나면 Connection,PreparedStatement 리소스반납. 
	 * 단건 조회 
	 * @param id
	 * @return User
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public User get(String id) throws ClassNotFoundException, SQLException {
		User outVO = null;
		//DB연결
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String url    = "jdbc:oracle:thin:@211.238.142.124:1521:xe";//url
		String userId = "HR_SPRING";//id
		String passwd = "0522";//비번
		Connection connection = DriverManager.getConnection(url, userId, passwd);
		LOG.debug("==============================");
		LOG.debug("=connection="+connection);
		LOG.debug("==============================");
//				2020-03-30 14:11:58,653 DEBUG [main] ehr.UserDao       ( UserDao.java:27)     - ==============================
//				2020-03-30 14:11:58,655 DEBUG [main] ehr.UserDao       ( UserDao.java:28)     - =connection=oracle.jdbc.driver.T4CConnection@7c30a502
//				2020-03-30 14:11:58,655 DEBUG [main] ehr.UserDao       ( UserDao.java:29)     - ==============================
		StringBuilder  sb=new StringBuilder();
		sb.append(" SELECT            \n");  
		sb.append("     u_id,         \n"); 
		sb.append("     name,         \n"); 
		sb.append("     passwd        \n"); 
		sb.append(" FROM              \n"); 
		sb.append("     hr_member     \n"); 
		sb.append(" where  u_id = ?   \n"); 		
		//Query수행
		LOG.debug("==============================");
		LOG.debug("=Query=\n"+sb.toString());
		LOG.debug("==============================");	
		PreparedStatement pstmt = connection.prepareStatement(sb.toString());
		pstmt.setString(1, id);
		LOG.debug("==============================");
		LOG.debug("=Param="+id);
		LOG.debug("==============================");
		ResultSet rs = pstmt.executeQuery();
		LOG.debug("==============================");
		LOG.debug("=rs="+rs);
		LOG.debug("==============================");
		
		if(rs.next()) {
			outVO =new User();
			outVO.setU_id(rs.getString("u_id"));
			outVO.setName(rs.getString("name"));
			outVO.setPasswd(rs.getString("passwd"));
		}
		
		LOG.debug("==============================");
		LOG.debug("=outVO="+outVO);
		LOG.debug("==============================");		
		
		//자원반납
		rs.close();
		pstmt.close();
		connection.close();		
		return outVO;
	}
	
	
	
	
	
	public static void main(String[] args) {
		UserDao dao=new UserDao();
		User user=new User("j02_124","이상무","1234");
		try {
			int flag = dao.add(user);
			if(flag==1) {
				LOG.debug("==================");
				LOG.debug("=단건성공=");
				LOG.debug("==================");				
			}
			
			User vsUser= dao.get(user.getU_id());
			if(user.getU_id().equals(vsUser.getU_id()) 
			  &&user.getName().equals(vsUser.getName()) 		
			  &&user.getPasswd().equals(vsUser.getPasswd())) {
				LOG.debug("==================");
				LOG.debug("=단건조회 성공=");
				LOG.debug("==================");				
			}else {
				LOG.debug("==================");
				LOG.debug("=단건조회 실패=");
				LOG.debug("==================");				
			}
			  
					
			
		} catch (ClassNotFoundException e) {
			LOG.debug("==================");
			LOG.debug("=ClassNotFoundException="+e.getMessage());
			LOG.debug("==================");
			e.printStackTrace();
		} catch (SQLException e) {
			LOG.debug("==================");
			LOG.debug("=SQLException="+e.getMessage());
			LOG.debug("==================");			
			e.printStackTrace();
		}

	}

}














