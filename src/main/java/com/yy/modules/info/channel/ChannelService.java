package com.yy.modules.info.channel;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;

/**
 * 厂站通道信息
 * @author ls2008
 * @date 2017-11-18 20:44:23
 */
@Service
@Transactional(readOnly=true)
public class ChannelService extends BaseServiceImpl<ChannelEntity,String> {

	@Autowired
	private ChannelDao dao;
	//@Autowired
	//private DbUtilsDAO dbDao;

	protected IBaseDAO<ChannelEntity, String> getDAO() {
		return dao;
	}

	@Override
	public void beforeSave(ChannelEntity entity) throws ServiceException {
//		StationEntity station=null;
//		if(entity.getStation()!=null&&!StringUtils.isEmpty(entity.getStation().getUuid())){
//			station=new StationEntity();
//			station.setUuid(entity.getStation().getUuid());
//		}
//		entity.setStation(station);
		super.beforeSave(entity);
	}
	
	

}