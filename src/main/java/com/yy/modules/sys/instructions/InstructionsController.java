package com.yy.modules.sys.instructions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.common.bean.TreeBaseBean;
import com.yy.frame.controller.BaseController;
import com.yy.modules.sys.func.FuncEntity;
import com.yy.modules.sys.func.FuncService;

/**
 * 使用说明
 * @ClassName: InstructionsController
 * @author liusheng
 * @date 2016年4月13日 下午6:37:17
 */
@Controller
@RequestMapping(value = "/sys/instructions")
public class InstructionsController extends BaseController<InstructionsEntity> {

	@Autowired
	private InstructionsService instructionsService;
	@Autowired
	private FuncService funcService;

	/**
	 * 
	 * @Title: view 
	 * @author liusheng
	 * @date 2016年4月13日 下午6:38:57 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String main(String funcId,Model model) {
		model.addAttribute("funcId", funcId);
		return "modules/sys/instructions/instructions_main";
	}

	/**
	 * yy_main页查看
	 * @Title: view 
	 * @author liusheng
	 * @date 2016年4月21日 下午5:20:33 
	 * @param @param funcId
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/view")
	public String view(String funcId,Model model) {
		model.addAttribute("func", funcService.findById(funcId));
		model.addAttribute("instructions", instructionsService.findByFuncId(funcId));
		return "modules/sys/instructions/instructions_view";
	}
	
	/**
	 * 获取使用说明左边的菜单 
	 * @Title: getLeftFunc 
	 * @author liusheng
	 * @date 2016年4月13日 下午7:15:49 
	 * @param @param funcId
	 * @param @return 设定文件 
	 * @return List<FuncEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getLeftFunc")
	private List<TreeBaseBean> getLeftFunc(String funcId){
		List<FuncEntity> list=(List<FuncEntity>) funcService.findAll(new Sort(Sort.Direction.ASC, "func_code"));
		List<TreeBaseBean> result=null;
		if(list!=null&&list.size()>0){
			result=new ArrayList<TreeBaseBean>();
			for(FuncEntity func:list){
				if(!StringUtils.isEmpty(funcId)&&func.getUuid().equals(funcId)){
					result.add(new TreeBaseBean(func.getUuid(), func.getFunc_name(), func.getParentId(), false, true));
				}else{
					result.add(new TreeBaseBean(func.getUuid(), func.getFunc_name(), func.getParentId(), false, false));
				}
			}
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/findByFuncId")
	private InstructionsEntity findByFuncId(String funcId){
		return instructionsService.findByFuncId(funcId);
	}
	
}
