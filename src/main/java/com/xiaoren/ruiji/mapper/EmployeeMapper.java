package com.xiaoren.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoren.ruiji.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;


/**
 * 员工信息(Employee)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-23 15:16:23
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

}
