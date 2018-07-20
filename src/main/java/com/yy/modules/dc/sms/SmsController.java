package com.yy.modules.dc.sms;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;

/**
 * 短信配置
 * @ClassName: SmsConfigController
 * @author liusheng 
 * @date 2016年1月5日 下午3:05:43
 */
@Controller
@RequestMapping(value = "/dc/sms")
public class SmsController {
	

//	@Autowired
//	private TestService testService;

	/**
	 * 短信配置
	 * @Title: page 
	 * @author 
	 * @date 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value="/config",method = RequestMethod.GET)
	public String main() {
		return "modules/dc/sms/sms_config_main";
	}
	

	/**
	 * 短信模板配置
	 * @Title: main 
	 * @author liusheng
	 * @date 2016年1月6日 上午9:17:14 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
//	@RequestMapping(value="/template",method = RequestMethod.GET)
//	public String template() {
//		return "modules/dc/sms/sms_template_main";
//	}
	
	@ResponseBody
	@RequestMapping(value = "/data_smsconfig")
	protected ActionResultModel<SmsBean> dataSmsConfig(String groupId,ServletRequest request) {
		ActionResultModel<SmsBean> arm = new ActionResultModel<SmsBean>();
		try {
			 SAXReader reader=new SAXReader();
			 Document document = null;
			try {
				document = reader.read(new File(getClass().getResource("/sms/sms.xml").getPath()));
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			 Element element=document.getRootElement();
			 List<Element> elements = element.elements(); 
			 List<SmsBean> list = new ArrayList<SmsBean>(); 
			  SmsBean bean=null;
			  for(Element e:elements){
				  bean=new SmsBean();
				  bean.setUuid(e.elementText("uuid"));
				  bean.setName(e.elementText("name"));
				  bean.setAddress(e.elementText("address"));
				  bean.setExt(e.elementText("ext"));
				  bean.setKey(e.elementText("key"));
				  bean.setSecret(e.elementText("secret"));
				  bean.setRemark(e.elementText("remark"));
				  list.add(bean);
			  }
			arm.setRecords(list);
			arm.setTotal(list.size());
			arm.setTotalPages(1);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	} 
	
	
	/**
	 * 修改短信配置
	 * @Title: updateSmsconfig 
	 * @author liusheng
	 * @date 2016年1月14日 下午2:44:01 
	 * @param @param request
	 * @param @param model
	 * @param @param entity
	 * @param @return 设定文件 
	 * @return ActionResultModel 返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/update_smsconfig")
	@ResponseBody
	public ActionResultModel updateSmsconfig(SmsBean entity) {
		ActionResultModel arm = new ActionResultModel();
		try {
			SAXReader reader=new SAXReader();
			Document document=reader.read(new File(getClass().getResource("/sms/sms.xml").getPath()));
			//Element sms=(Element) document.getRootElement().elements("sms").get(1);
			
			Element element=document.getRootElement();
			  List<Element> elements = element.elements(); 
			  for(Element e:elements){
				 if(e.elementText("uuid").equals(entity.getUuid())){
					 e.element("name").setText(entity.getName());
					 e.element("address").setText(entity.getAddress());
					 e.element("ext").setText(entity.getExt());
					 e.element("key").setText(entity.getKey());
					 e.element("secret").setText(entity.getSecret());
					 e.element("remark").setText(entity.getRemark());
					 break;
				 }
			  }
			OutputFormat format=OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");		
			XMLWriter Writer=new XMLWriter(new FileOutputStream(new File(getClass().getResource("/sms/sms.xml").getPath())),format);
			Writer.write(document);
			Writer.close();
			arm.setRecords(entity);
			arm.setSuccess(true);
			arm.setMsg("操作成功!");
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg("操作失败!");
			e.printStackTrace();
		}
		return arm;
	}

	
    public static void sendSms( ){
    	try {
//    		https://sms.meizu.com/service/send/sms?phones={0}&content={1}&ext=625&key=SMS-SALES&secret=98DM2fi973es
	   		 List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	         formparams.add(new BasicNameValuePair("phones", "13680358940"));
	         formparams.add(new BasicNameValuePair("content", "测试csc系统短信发送"));
	         formparams.add(new BasicNameValuePair("ext", "625"));
	         formparams.add(new BasicNameValuePair("key", "SMS-SALES"));
	         formparams.add(new BasicNameValuePair("secret", "98DM2fi973es"));
	         
	         HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");
	         
	   			RequestConfig requestConfig = RequestConfig.custom()
	   	                .setSocketTimeout(5000)
	   	                .setConnectTimeout(5000)
	   	                .setConnectionRequestTimeout(5000)
	   	                .build();
	   	            HttpClient client = new DefaultHttpClient();
	   	            HttpPost post = new HttpPost("https://sms.meizu.com/service/send/sms");
	   	            post.setEntity(reqEntity);
	   	            post.setConfig(requestConfig);
	   		        HttpResponse response = client.execute(post);
	   		        if (response.getStatusLine().getStatusCode() == 200) {
	   		            org.apache.http.HttpEntity resEntity = response.getEntity();
	   		            String message = EntityUtils.toString(resEntity, "utf-8");
	   		            System.out.println("===========================================================");
	   		           System.out.println("message=====>"+message);
	   		        }else{
	   		        	System.out.println("error");
	   		        }
	   			} catch (Exception e) {
	   				e.printStackTrace();
	   			}
    }
    
    public static void main(String[] args) {
    	sendSms();
	}
}
