package com.fline.form.util;

import java.util.List;

import com.fline.form.access.model.Department;

/**
 * 关系工具类进行列表中根据指定关系获取
 * @author hi
 *
 */
public class RelationUtil {
	
	/*
	 *获取所有的子孙节点的id 
	 */
	public static List<String> getDepartmentAllChildrenPoint(List<Department> departments,List<String> list,String parentId){
		for(Department department : departments) {
			if(department.getParentId().equals(parentId) ) {
				getDepartmentAllChildrenPoint(departments,list, "" + department.getId());
				list.add(""  + department.getId());
			}
		}
		return list;
	}
	
	/*
	 * 更新所有子孙节点的uniquecoding
	 */
	public static List<Department> updateAllChildrenPointUniquecoding(
			List<Department> departments,String parentId,String startUniquecoding) {
		int i = 1;
		String uniquecoding = "999";
		for(Department department : departments) {
			if(department.getParentId().equals(parentId) ) {
				if(i < 10) {
					uniquecoding = "00" + i;
				}else if(i < 100) {
					 uniquecoding = "0" + i;
				}
				department.setUniquecoding(startUniquecoding + uniquecoding);
				updateAllChildrenPointUniquecoding(departments, ""+department.getId(),department.getUniquecoding());
				i++;
			}
		}
		return departments;
	}
}
