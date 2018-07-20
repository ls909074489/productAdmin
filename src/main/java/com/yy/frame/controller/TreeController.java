package com.yy.frame.controller;

import com.yy.frame.entity.ITreeEntity;
import com.yy.frame.service.TreeServiceImpl;

public class TreeController<T extends ITreeEntity> extends BaseController<T> {
	private TreeServiceImpl getService() {
		return (TreeServiceImpl) super.baseService;
	}
}
