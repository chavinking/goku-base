package com.pgman.goku.example.demojdbc.service.impl;

import com.pgman.goku.example.demojdbc.dao.IEmpDao;
import com.pgman.goku.example.demojdbc.domain.Emp;
import com.pgman.goku.example.demojdbc.service.IEmpService;

import java.util.List;

public class EmpServiceImpl implements IEmpService {

    private IEmpDao empDao;

    public IEmpDao getEmpDao() {
        return empDao;
    }

    public void setEmpDao(IEmpDao empDao) {
        this.empDao = empDao;
    }

    public List<Emp> findAllEmp() {

        return empDao.findAllEmp();

    }

    public Emp findById(Integer empno) {

        return empDao.findById(empno);

    }

    public void saveEmp(Emp emp) {
        empDao.saveEmp(emp);

    }

    public void updateEmp(Emp emp) {

        empDao.updateEmp(emp);

    }

    public void deleteEmp(Integer empno) {
        empDao.deleteEmp(empno);
    }
}
