package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.domain.QaMenu;

/**
 * 持久层接口
 * 
 * @author: Frankjiu
 * @date: 2018年4月6日 下午8:00:49
 */
public interface MacoMenuRepository extends JpaRepository<QaMenu, String>, JpaSpecificationExecutor<QaMenu> {
	
	//List<MacoMenu> getByAuth(MacoMenu macoMenu, String[] authArr);
	
	//List<MacoMenu> findTree(String[] authArr);
	
}
