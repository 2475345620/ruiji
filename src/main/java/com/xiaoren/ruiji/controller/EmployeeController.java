package com.xiaoren.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.entity.Employee;
import com.xiaoren.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.login(request, employee);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        return employeeService.logout(request);

    }

    /**
     * * 新增员工
     *
     * @param request
     * @param employee
     * @return
     */


    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {

        return employeeService.insertEmployee(request, employee);
    }


    /**
     * 员工信息的分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageEmployee(int page, int pageSize, String name) {
      return   employeeService.pageEmployee(page, pageSize, name);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.update(request, employee);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        return employeeService.getById(id);
    }


}
