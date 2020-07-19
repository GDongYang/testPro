package com.fline.request;


import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.feixian.tp.common.util.Detect;
import com.fline.yztb.util.DataShare;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class Qy {
	
	private static void test() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://10.49.132.41:8888/tzxwy?useUnicode=true&characterEncoding=UTF8", "fdp", "fdp!@#");
			String sql = "select * from c_enterprise";
			PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet  resultSet  = pstmt.executeQuery();
			while(resultSet.next()) {
				String entName = resultSet.getString(3);
				int id = resultSet.getInt(1);
				System.out.println(entName);
				insert(id,entName,connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void insert(int id, String entName,Connection connection) {
		try {
			List<Map<String, Object>> datas = DataShare.getDatas("http://10.49.132.13/gateway/api/001008010010001/dataSharing/z5J4dyb20n4dJuW9.htm",
					"&entname=" + URLEncoder.encode(entName, "UTF-8"),"");
			if(!Detect.notEmpty(datas)) {
				return;
			}
			Map<String, Object> data = datas.get(0);
			String sql = "insert into c_enterprise (uniscid,entname,dom,name,reporttype,regcap,opscope,regorg,regstate,compform,oploc,otherinfo,puburl,qrcode,apprdate,estdate,opfrom,opto,elcLicenceCode) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setString(1, data.get("uniscid") + "");
			pstmt.setString(2, data.get("entname") + "");
			pstmt.setString(3, data.get("dom") + "");
			pstmt.setString(4, data.get("name") + "");
			pstmt.setString(5, data.get("reporttype") + "");
			pstmt.setString(6, data.get("regcap") + "");
			pstmt.setString(7, data.get("opscope") + "");
			pstmt.setString(8, data.get("regorg") + "");
			pstmt.setString(9, data.get("regstate") + "");
			pstmt.setString(10, data.get("compform") + "");
			pstmt.setString(11, data.get("oploc") + "");
			pstmt.setString(12, data.get("otherinfo") + "");
			pstmt.setString(13, data.get("puburl") + "");
			pstmt.setString(14, data.get("qrcode") + "");
			pstmt.setString(15, data.get("apprdate") + "");
			pstmt.setString(16, data.get("estdate") + "");
			pstmt.setString(17, data.get("opfrom") + "");
			pstmt.setString(18, data.get("opto") + "");
			pstmt.setString(19, data.get("elcLicenceCode") + "");
	        pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void run() {
		test();
	}

}
