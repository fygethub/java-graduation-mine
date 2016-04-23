package sy.service;

import java.util.List;

import sy.pageModel.ResourceType;

/**
 * 资源类型服务
 * 
 * @author fydor
 * 
 */
public interface ResourceTypeServiceI {

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public List<ResourceType> getResourceTypeList();

}
