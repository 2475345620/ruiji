package com.xiaoren.ruiji.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 员工信息(Employee)表服务接口
 *
 * @author makejava
 * @since 2022-06-23 15:16:24
 */
public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpServletRequest request,Employee employee);
    R<String> logout(HttpServletRequest request);
    R<String> insertEmployee(HttpServletRequest request,Employee employee);

//    R<Page> pageEmployee(int page,int pageSize,String name);

    R<Page> pageEmployee(int page, int pageSize, String name);

    R<String> update(HttpServletRequest request, Employee employee);
    R<Employee> getById(Long id);
}
