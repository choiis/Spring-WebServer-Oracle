package com.singer.infrastructure.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.singer.common.util.CommonUtil;
import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.application.service.comm.CommService;
import com.singer.application.service.sm.SM01Service;
import com.singer.domain.entity.CommVo;
import com.singer.domain.entity.sm.SM01Vo;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private SM01Service sm01Service;

	@Autowired
	private CommService commService;

//	@Inject
//	private SL01Service sl01Service;

	private static String UNKNOWN = "unknown";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		HttpSession session = request.getSession();
		String userid = authentication.getName();
		SM01Vo sm01Vo = new SM01Vo();
		sm01Vo.setUserid(userid);
		try {
			sm01Vo = sm01Service.login(sm01Vo);

			session.setAttribute("userid", userid);
			session.setAttribute("username", sm01Vo.getUsername());
			session.setAttribute("grade", sm01Vo.getGrade());
			session.setAttribute("regdate", sm01Vo.getRegdate());
			session.setAttribute("email", sm01Vo.getEmail());
			session.setAttribute("usertype", sm01Vo.getUsertype());

			List<CommVo> menuList = commService.selectMenu(sm01Vo.getUsertype());
			session.setAttribute("menuList", menuList);
			String browser = Optional.of(request.getParameter("browser")).orElseGet(() -> UNKNOWN);
			String device = Optional.of(request.getParameter("device")).orElseGet(() -> UNKNOWN);
			String ip = CommonUtil.getIp(request);
//			SL01Vo sl01Vo = new SL01Vo();
//			sl01Vo.setUserid(userid);
//			sl01Vo.setLogintime(DateUtil.getTodayTime());
//			sl01Vo.setIp(ip);
//			sl01Vo.setBrowser(browser);
//			sl01Vo.setDevice(device);
//			//sl01Service.insertLoginlog(sl01Vo);
		} catch (Exception e) {
			response.getWriter().write(RESULT_CODE.FAIL.name());
			response.getWriter().flush();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.getWriter().write(RESULT_CODE.SUCCESS.name());
		response.getWriter().flush();
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
