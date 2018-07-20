package com.yy.modules.sys.admins;

import java.util.ArrayList;
import java.util.List;

public class AdminisUtils {

	// 系统启动后自动缓存加载枚举到该map
	public static List<AdminisEntity> cacheResult;

	static {
		cacheResult = new ArrayList<AdminisEntity>();
	}

	public static void updateEntitys(ArrayList<AdminisEntity> list) {
		cacheResult = list;
	}

}
