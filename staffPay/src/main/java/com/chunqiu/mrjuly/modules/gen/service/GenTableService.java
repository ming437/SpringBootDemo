package com.chunqiu.mrjuly.modules.gen.service;

import com.chunqiu.mrjuly.common.utils.GenUtils;
import com.chunqiu.mrjuly.common.utils.PageUtils;
import com.chunqiu.mrjuly.common.utils.StringUtils;
import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import com.chunqiu.mrjuly.common.persistence.CrudService;
import com.chunqiu.mrjuly.modules.gen.dao.GenDataBaseDictDao;
import com.chunqiu.mrjuly.modules.gen.dao.GenTableColumnDao;
import com.chunqiu.mrjuly.modules.gen.dao.GenTableDao;
import com.chunqiu.mrjuly.modules.gen.model.GenTable;
import com.chunqiu.mrjuly.modules.gen.model.GenTableColumn;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表Service
 * @author
 * @version 2013-10-15
 */
@Service
public class GenTableService extends CrudService<GenTableDao, GenTable, Integer> {

	@Resource
	private GenTableDao genTableDao;
	@Resource
	private GenTableColumnDao genTableColumnDao;
	@Resource
	private GenDataBaseDictDao genDataBaseDictDao;
	
	public GenTable get(Integer id) {
		GenTable genTable = genTableDao.get(id);
		GenTableColumn genTableColumn = new GenTableColumn();
		genTableColumn.setGenTable(new GenTable(genTable.getId()));
		genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
		return genTable;
	}
	
	public Grid find(GridParam gridParam, GenTable genTable) {
		/*genTable.setPage(page);
		page.setList(genTableDao.findList(genTable));
		return page;*/
		Grid grid = new Grid();

		Page page = PageUtils.offsetPage(gridParam);
		genTableDao.findList(genTable);

		grid.setPage(page);
		return grid;
	}

	public List<GenTable> findAll() {
		return genTableDao.findList(new GenTable());
	}
	
	/**
	 * 获取物理数据表列表
	 * @param genTable
	 * @return
	 */
	public List<GenTable> findTableListFormDb(GenTable genTable){
		return genDataBaseDictDao.findTableList(genTable);
	}
	
	/**
	 * 验证表名是否可用，如果已存在，则返回false
	 * @param tableName
	 * @return
	 */
	public boolean checkTableName(String tableName){
		if (StringUtils.isBlank(tableName)){
			return true;
		}
		GenTable genTable = new GenTable();
		genTable.setName(tableName);
		List<GenTable> list = genTableDao.findList(genTable);
		return list.size() == 0;
	}
	
	/**
	 * 获取物理数据表列表
	 * @param genTable
	 * @return
	 */
	public GenTable getTableFormDb(GenTable genTable){
		// 如果有表名，则获取物理表
		if (StringUtils.isNotBlank(genTable.getName())){
			
			List<GenTable> list = genDataBaseDictDao.findTableList(genTable);
			if (list.size() > 0){
				
				// 如果是新增，初始化表属性
				if (genTable.getId()==null){
					genTable = list.get(0);
					// 设置字段说明
					if (StringUtils.isBlank(genTable.getComments())){
						genTable.setComments(genTable.getName());
					}
					genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
				}
				
				// 添加新列
				List<GenTableColumn> columnList = genDataBaseDictDao.findTableColumnList(genTable);
				for (GenTableColumn column : columnList){
					boolean b = false;
					for (GenTableColumn e : genTable.getColumnList()){
						if (e.getName().equals(column.getName())){
							b = true;
						}
					}
					if (!b){
						//add by wcf at 2016.10.13，用来首次导入时javaType不存时，前端都显示String
						if(StringUtils.isEmpty(column.getJavaType())){
							if(column.getJdbcType().indexOf("int") > -1){
								column.setJavaType("Integer");
							}
						}
						genTable.getColumnList().add(column);
					}
				}
				
				// 删除已删除的列
				for (GenTableColumn e : genTable.getColumnList()){
					boolean b = false;
					for (GenTableColumn column : columnList){
						if (column.getName().equals(e.getName())){
							b = true;
						}
					}
					if (!b){
						e.setDelFlag(GenTableColumn.DEL_FLAG_DELETE);
					}
				}
				
				// 获取主键
				genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));
				
				// 初始化列属性字段
				GenUtils.initColumnField(genTable);
				
			}
		}
		return genTable;
	}
	
	public void save(GenTable genTable) {
		if (genTable.getId()==null){
			genTable.preInsert();
			genTableDao.insert(genTable);
		}else{
			genTable.preUpdate();
			genTableDao.update(genTable);
		}
		// 保存列
		for (GenTableColumn column : genTable.getColumnList()){
			column.setGenTable(genTable);
			if (column.getId()==null){
				column.preInsert();
				genTableColumnDao.insert(column);
			}else{
				column.preUpdate();
				genTableColumnDao.update(column);
			}
		}
	}
	
	public void delete(GenTable genTable) {
		genTableDao.delete(genTable.getId());
		genTableColumnDao.deleteByGenTableId(genTable.getId());
	}
	
}
