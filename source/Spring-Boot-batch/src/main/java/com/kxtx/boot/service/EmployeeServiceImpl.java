/**
 *
 */
package com.kxtx.boot.service;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.kxtx.boot.client.OutServiceAgent;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.model.Employee;
import com.kxtx.boot.model.EmployeeVO;
import com.kxtx.boot.repository.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService, InServiceAgent<EmployeeVO, Employee>, OutServiceAgent<EmployeeVO, Employee> {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void translatePODataItem(EmployeeVO sourceData, Employee target) {
        logger.info("translatePODataItem rowId:" + sourceData.getRowId());
        //logger.info("translateDataItem");
        //特殊化处理
        String[] names = Splitter.on('-').omitEmptyStrings().splitToList(sourceData.getName()).toArray(new String[]{});
        target.setFirstName(names[0]);
        target.setLastName(names[1]);
    }

    @Override
    public void writer(Employee target) {
        logger.info("writer");
        this.add(target);
    }

    @Override
    @Transactional
    public int batchWriter(List<Employee> target) {
        int result = 0;
        logger.info("writers:" + target.size());
//        if (target.stream().filter(x -> x.getFirstName().equals("alex")).count() > 0) return 0;//故意失败下
        for (Employee employee : target) {
            if (employeeMapper.insert(employee) > 0) {
                ++result;
            }
        }
        return result;//每条记录都会会重试一次
//        return 1;
    }

    @Override
    public void add(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.queryAll();
    }

    @Override
    public List<EmployeeVO> pagingReader(HashMap parameters) {
        logger.info("pagingReader:" + parameters.keySet() + "," + parameters.values());
        List<Employee> employees = employeeMapper.queryByPage(parameters);
        if (employees != null && !employees.isEmpty()) {
            return Lists.transform(employees, new Function<Employee, EmployeeVO>() {
                @Override
                public EmployeeVO apply(Employee f) {
                    EmployeeVO employeeVO = new EmployeeVO();
                    employeeVO.setId(f.getEmpId());
                    if (f.getGender() != null) {
                        employeeVO.setGender(f.getGender().toString());
                    }
                    employeeVO.setEmail(f.getEmail());
                    employeeVO.setName(f.getFirstName() + "-" + f.getLastName());
                    return employeeVO;
                }
            });
        }
        return null;
    }

    @Override
    public int getItemCount(Map parameters) {
        logger.info("getItemCount");
        return employeeMapper.queryCount(parameters);
    }

    @Override
    public EmployeeVO partTranslateDataItem(EmployeeVO target) {
        //logger.info("partTranslateDataItem");
        return target;
    }
}
