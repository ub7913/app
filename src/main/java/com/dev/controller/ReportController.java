package com.dev.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		//db conn
		String jdbc_url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection conn = DriverManager.getConnection(jdbc_url, "hr", "hr");
		
		//jasper 파일 로드
//		InputStream jasperStream = getClass().getResourceAsStream("/emp_master.jasper");
//		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		
		//subreport 소스파일 컴파일
		String jrxmlFile = getClass().getResource("/reports/sales_subReport.jrxml")
								     .getFile();
		System.out.println(jrxmlFile);
		String jasperFile = JasperCompileManager.compileReportToFile( jrxmlFile );
		
		// 소스 컴파일 jrxml -> jasper
		InputStream stream = getClass().getResourceAsStream("/reports/emp_master.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(stream);
		
		
		
		//pdf 생성해서 브라우저로 출력
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
