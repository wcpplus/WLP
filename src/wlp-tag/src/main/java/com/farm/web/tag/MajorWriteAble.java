package com.farm.web.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.farm.authority.domain.User;
import com.farm.authority.service.UserServiceInter;
import com.farm.category.enums.FuncPOP;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.learn.enums.MajorFuncPOP;
import com.farm.learn.service.MajorpopServiceInter;
import com.farm.util.spring.BeanFactory;

/**
 * 如果当前用户是管理员则展示标签内的内容
 * 
 *
 */
public class MajorWriteAble extends TagSupport {
	private String userid;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static UserServiceInter userIMP = (UserServiceInter) BeanFactory.getBean("userServiceImpl");
	private final static MajorpopServiceInter classTypeIMP = (MajorpopServiceInter) BeanFactory
			.getBean("majorpopServiceImpl");

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(userid)) {
			return SKIP_BODY;
		}
		User user = userIMP.getUserEntity(userid);
		List<String> writeTypes = classTypeIMP.getPopMajorIds(user, MajorFuncPOP.write);
		if (writeTypes.size() > 0) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
