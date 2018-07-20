package com.yy.modules.sys.feedback;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.user.UserRef;

/**
 * 意见反馈
 * @ClassName: FeedbackController
 * @author liusheng
 * @date 2016年4月18日 下午7:31:23
 */
@Controller
@RequestMapping(value = "/sys/feedback")
public class FeedbackController extends BaseController<FeedbackEntity> {
	
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "modules/sys/feedback/feed_back_add";
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page() {

		return "modules/sys/feedback/feed_back_main";
	}

	
	@Override
	public ActionResultModel<FeedbackEntity> add(ServletRequest request,
			Model model, FeedbackEntity entity) {
		entity.setUser(new UserRef(ShiroUser.getCurrentUserEntity().getUuid()));
		entity.setLoginIp(request.getRemoteAddr());
		return super.add(request, model, entity);
	}
	
	

}
