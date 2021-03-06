package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.QaRole;
import com.service.QaRoleService;
import com.utils.LogAssist;
import com.utils.LogOperation;

import net.sf.json.JSONObject;

/**
 * 角色控制层
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
@Controller
@RequestMapping("/role")
public class QaRoleController {

	@Autowired
	private QaRoleService qaRoleService;

	/**
	 * 跳转页面
	 */
	@RequestMapping("/")
	@LogAssist(operationType = LogOperation.OP_GOTO, operationModule = LogOperation.WP_SYSTEM, describe = "角色--跳转页面")
	public String to(String id) {
		return "/role_manage";
	}
	
	/**
	 * 主键查询
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "角色--主键查询")
	public String getOne(String id) {
		JSONObject js = new JSONObject();
		try {
			QaRole qaRole = qaRoleService.getOne(id);
			js.put("data", qaRole);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 树查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/findTree")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_QUERY, operationModule = LogOperation.WP_SYSTEM, describe = "角色--树查询")
	public String findTree() {
		JSONObject js = new JSONObject();
		List<QaRole> list = null;
		try {
			list = qaRoleService.findTree();
			List newList = new ArrayList<>();
			for(QaRole role : list){
				Map<Object,Object> map  = new LinkedHashMap<>();
				map.put("id", role.getId());
				map.put("name", role.getRoleName());
				map.put("is_stop", role.getIsStop());
				map.put("create_time", role.getCreateTime());
				map.put("update_time", role.getUpdateTime());
				newList.add(map);
			}
			js.put("data", newList);
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}
	
	/**
	 * 新增
	 */
	@RequestMapping("/save")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_ADD, operationModule = LogOperation.WP_SYSTEM, describe = "角色--新增")
	public String saveQaRole(QaRole qaRole) {
		JSONObject js = new JSONObject();
		try {
			qaRole.setCreateTime(new Date());
			qaRole.setUpdateTime(new Date());
			qaRole = qaRoleService.save(qaRole);
			js.put("id", qaRole.getId());
			js.put("flag", true);
			js.put("msg", "success");
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_DEL, operationModule = LogOperation.WP_SYSTEM, describe = "角色--删除")
	public String delete(String id) {
		JSONObject js = new JSONObject();
		try {
			// 查询角色
			QaRole role = qaRoleService.getOne(id);
			// 禁止删除管理员
			if (role.getRoleName().contains("管理员")) {
				js.put("flag", false);
				js.put("msg", "failure");
			} else {
				qaRoleService.delete(id);
				js.put("flag", true);
				js.put("msg", "success");
			}
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	@LogAssist(operationType = LogOperation.OP_MODIFY, operationModule = LogOperation.WP_SYSTEM, describe = "角色--修改")
	public String update(QaRole newQaRole) {
		JSONObject js = new JSONObject();
		newQaRole.setUpdateTime(new Date());
		try {
			// 查询角色
			QaRole role = qaRoleService.getOne(newQaRole.getId());
			// 禁止修改管理员
			if (role.getRoleName().contains("管理员")) {
				js.put("flag", false);
				js.put("msg", "failure");
			} else {
				qaRoleService.update(newQaRole);
				js.put("flag", true);
				js.put("msg", "success");
			}
		} catch (Exception e) {
			js.put("flag", false);
			js.put("msg", "failure");
			e.printStackTrace();
		}
		return js.toString();
	}

}
