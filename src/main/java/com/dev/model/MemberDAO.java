package com.dev.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dev.common.ConnectionManager;





public class MemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	
	//싱글톤
	static MemberDAO instance;
	public static MemberDAO getInstance() {
		if(instance == null)
			instance = new MemberDAO();
			return instance;
	}
	
	
	
	public ArrayList<MemberVO> selectAll(MemberVO memberVO) {//List혹은 ArrayList쓰기
		MemberVO resultVO = null;
		ResultSet rs = null;
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();//리턴값을 저장할 변수 저장
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "SELECT ID, PW, GENDER, JOB, REASON, MAILYN, HOBBY, REGDATE"
					+ " FROM MEMBER"
					+ " ORDER BY ID";
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				resultVO = new MemberVO();
				resultVO.setId(rs.getString("ID"));
				resultVO.setPw(rs.getString("PW"));
				resultVO.setGender(rs.getString("GENDER"));
				resultVO.setJob(rs.getString("JOB"));
				resultVO.setReason(rs.getString("REASON"));
				resultVO.setMailyn(rs.getString("MAILYN"));
				resultVO.setHobby(rs.getString("HOBBY"));
				resultVO.setRegdate(rs.getString("REGDATE"));
				list.add(resultVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	public MemberVO selectOne(MemberVO memberVO) {
		MemberVO resultVO = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "SELECT ID, PW, GENDER, JOB, REASON, MAILYN, HOBBY, REGDATE"
					+ " FROM MEMBER"
					+ " WHERE ID=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getId());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				resultVO = new MemberVO();
				resultVO.setId(rs.getString("ID"));
				resultVO.setPw(rs.getString("pw"));
				resultVO.setGender(rs.getString("GENDER"));
				resultVO.setJob(rs.getString("JOB"));
				resultVO.setReason(rs.getString("REASON"));
				resultVO.setMailyn(rs.getString("MAILYN"));
				resultVO.setHobby(rs.getString("HOBBY"));
				resultVO.setRegdate(rs.getString("REGDATE"));
			} else {
				System.out.println("no data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(rs, pstmt, conn);//rs를 try안에서 선언하면 지역 변수 이기 때문에 try안에서만 사용 됨 , 그러니까 변수를 try밖으로 빼주고 초기값 null을 주면 에러없이 사용 가능
		}
		
		return resultVO;
	}
	
	public void delete(MemberVO memberVO) {
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "delete member where id = ?";
			pstmt = conn.prepareStatement(sql);//statement 만들때 sql넣어 줌
			pstmt.setString(1, memberVO.getId());
			int r = pstmt.executeUpdate();
			System.out.println(r + " 건이 삭제됨");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(null, pstmt, conn);
		}
	}
	
	public void update(MemberVO memberVO) {
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "update member set pw =?, gender=?, job = ? reason=?, mailyn=?, hobby=? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getPw());
			pstmt.setString(2, memberVO.getGender());
			pstmt.setString(3, memberVO.getJob());
			pstmt.setString(4, memberVO.getReason());
			pstmt.setString(5, memberVO.getMailyn());
			pstmt.setString(6, memberVO.getHobby());
			pstmt.setString(7, memberVO.getId());
			int r = pstmt.executeUpdate();
			System.out.println(r + " 건이 수정됨");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(null, pstmt, conn);
		}
	}
	
	public int insert(MemberVO memberVO) { 
		int r=0;
		try {
			
			conn = ConnectionManager.getConnnect();
			
			String sql="insert into member (id,pw,gender,job,reason,mailyn,hobby,regdate) "
					+ "values (?,?,?,?,?,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPw());
			pstmt.setString(3, memberVO.getGender());
			pstmt.setString(4, memberVO.getJob());
			pstmt.setString(5, memberVO.getReason());
			pstmt.setString(6, memberVO.getMailyn());
			pstmt.setString(7, memberVO.getHobby());
			r = pstmt.executeUpdate();
			System.out.println(r + " 건이 처리됨");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn);
		}
		return r;
	}
	
	//메일수신회원수 : select count(id) from member where mailyn='y';
	public int getMailynCnt() {
		int cnt = 0;
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "select count(id) from member where mailyn='y'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			cnt = rs.getInt(1);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn);
		}
		return cnt;
	}
	
	//성별인원수:select gender, count(id) cnt from member group by gender;
	public List<HashMap<String, Object>> getGenderCnt() {
		List<HashMap<String, Object>> list = 
						new ArrayList<HashMap<String, Object>>();
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "select gender, count(id) cnt from member group by gender";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("gender", rs.getString("gender"));
				map.put("cnt", rs.getInt("cnt"));
				list.add(map);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn);
		}
		return list;
	}
}
