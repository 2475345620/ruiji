package com.xiaoren.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoren.ruiji.common.BaseContext;
import com.xiaoren.ruiji.common.R;
import com.xiaoren.ruiji.entity.Employee;
import com.xiaoren.ruiji.mapper.EmployeeMapper;
import com.xiaoren.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 员工信息(Employee)表服务实现类
 *
 * @author makejava
 * @since 2022-06-23 15:16:25
 */
@Service("EmployeeService")
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Resource
    private RedisTemplate<String,Long> redisTemplate;

    @Override
    public R login(HttpServletRequest request, Employee employee) {
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果

        if (emp == null) {
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果

        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果

        if (emp.getStatus() == 0) {
            return R.error("账号被禁用");
        }
        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        Long empId = (Long) request.getSession().getAttribute("employee");
       redisTemplate.opsForValue().set("userId",empId);
        return R.success(emp);
    }

    @Override
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @Override
    public R<String> insertEmployee(HttpServletRequest request, Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        /*employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/



        int insert = employeeMapper.insert(employee);
        return R.success("新增员工" + (insert > 0 ? "成功" : "失败"));
    }


    @Override
    public R<Page> pageEmployee(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}");
        Page<Employee> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);
        pageInfo = employeeMapper.selectPage(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    @Override
    public R<String> update(HttpServletRequest request, Employee employee) {
       /* Long empId = (Long) request.getSession().getAttribute("employee");
        BaseContext.setCurrentId(empId);*/
       /* employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);*/

        int i = employeeMapper.updateById(employee);
        return R.success("修改" + ((i > 0) ? "成功" : "失败"));
    }

    @Override
    public R<Employee> getById(Long id) {
        Employee byId = employeeMapper.selectById(id);
        if (byId!=null){
            return R.success(byId);
        }
        return R.error("查询失败");
    }


}
