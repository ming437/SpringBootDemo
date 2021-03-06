package com.chunqiu.mrjuly.modules.system.service;

import java.util.List;

import javax.annotation.Resource;

import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import org.springframework.stereotype.Service;

import com.chunqiu.mrjuly.modules.system.dao.LyConfigStarDao;
import com.chunqiu.mrjuly.modules.system.model.LyConfigStar;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 用户星级折扣SERVICE层
 * 
 * @author wy
 *
 */
@Service
public class LyConfigStarService {

	@Resource
	private LyConfigStarDao configStarDao;

	/**
	 * 根据主键删除记录
	 * 
	 * @param id
	 * @return 成功删除的行数
	 * @author wy
	 */
	public int deleteByPrimaryKey(Long id) {
		return configStarDao.deleteByPrimaryKey(id);
	}

	/**
	 * 新增用户星级折扣记录
	 * 
	 * @param record
	 *            用户星级折扣实体数据
	 * @return 成功新增的行数
	 * @author wy
	 */
	public int insertSelective(LyConfigStar record) {
		return configStarDao.insertSelective(record);
	}

	/**
	 * 根据ID查询用户星级折扣记录
	 * 
	 * @param id
	 * @return 用户星级折扣实体数据
	 * @author wy
	 */
	public LyConfigStar selectByPrimaryKey(Long id) {
		return configStarDao.selectByPrimaryKey(id);
	}

	/**
	 * 根据ID更新用户星级折扣记录
	 * 
	 * @param record
	 *            用户星级折扣实体数据
	 * @return 成功更新的行数
	 * @author wy
	 */
	public int updateByPrimaryKeySelective(LyConfigStar record) {
		return configStarDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 获取所有用户星级折扣记录
	 * 
	 * @return 用户星级折扣记录列表
	 * @author wy
	 */
	public Grid getAllConfigStar(GridParam param) {
		Grid grid = new Grid();
		Page page = PageHelper.offsetPage(param.getOffset(), param.getLimit(),
				true);
		configStarDao.getAllConfigStar();
		grid.setRows(page.getResult());
		grid.setTotal(page.getTotal());
		return grid;
	}
	
	public List<LyConfigStar> getAllConfigStar() {
		return configStarDao.getAllConfigStar();
	}

	/**
	 * 获取最大星级
	 * 
	 * @return 最大星级
	 * @author wy
	 */
	public int getMaxStar() {
		Integer max = configStarDao.getMaxStar();
		return max == null ? 0 : max;
	}
}
