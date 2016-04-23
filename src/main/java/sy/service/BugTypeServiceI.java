package sy.service;

import java.util.List;

import sy.model.Tbugtype;

/**
 * 
 * @author fydor
 * 
 */
public interface BugTypeServiceI {

	/**
	 * 获得BUG类型列表
	 * 
	 * @return
	 */
	public List<Tbugtype> getBugTypeList();

}
