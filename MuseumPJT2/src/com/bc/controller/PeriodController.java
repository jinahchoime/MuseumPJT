package com.bc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bc.model.dao.DAO;
import com.bc.model.vo.ExhibVO;
import com.bc.model.vo.PagingVO;

@WebServlet("/exhibition/period")
public class PeriodController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet 시작");
		
		int totalRecord = DAO.getTotalRecord();
		int nowPage = 1;
		
		PagingVO pvo = new PagingVO();
		pvo.setNumPerPage(4);
		pvo.setPagePerBlock(10);
		pvo.setPaging(nowPage, totalRecord);
		int totalPage = pvo.getTotalPage();
		
		List<ExhibVO> list = null;
		List<List<ExhibVO>> listArray = new ArrayList<>();
		
		for (int i = nowPage; i <= totalPage; i++) {
			Map<String, Integer> map = new HashMap<>();
			pvo.setPaging(i, totalRecord);
			map.put("begin", pvo.getBegin());
			map.put("end", pvo.getEnd());
			list = DAO.getPaging(map);
			if (list != null) {
				listArray.add(list);				
			}
		}
		
		System.out.println();
		
		request.setAttribute("pvo", pvo);
		request.setAttribute("listArray", listArray);
			
				
		//응답페이지로 위임처리
		request.getRequestDispatcher("exhibYear.jsp").forward(request, response);
		System.out.println("doGet 끝");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
