package com.yy.modules.info.channel;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.modules.sys.org.OrgEntity;

/**
 * 厂站通道信息
 * @author ls2008
 * @date 2017-11-18 20:44:23
 */
@Controller
@RequestMapping(value = "/info/channel")
public class ChannelController extends BaseController<ChannelEntity> {

	@Autowired
	private ChannelService service;

	/**
	 * 
	 * @Title: listView
	 * @author ls2008
	 * @date 2017-11-18 20:44:23
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping("/list")
	public String listView(Model model) {
		return "modules/info/channel/channel_list";
	}

	@Override
	public String addView(Model model, ServletRequest request) {
		return "modules/info/channel/channel_edit";
	}

	@Override
	public String editView(Model model, ServletRequest request, ChannelEntity entity) {
		return "modules/info/channel/channel_edit";
	}

	@Override
	public String detailView(Model model, ServletRequest request, ChannelEntity entity) {
		return "modules/info/channel/channel_detail";
	}

	@Override
	public ActionResultModel<ChannelEntity> update(ServletRequest request, Model model, ChannelEntity entity) {
		OrgEntity station=null;
		if(entity.getStation()!=null&&!StringUtils.isEmpty(entity.getStation().getUuid())){
			station=new OrgEntity();
			station.setUuid(entity.getStation().getUuid());
		}
		entity.setStation(station);
		return super.update(request, model, entity);
	}

}