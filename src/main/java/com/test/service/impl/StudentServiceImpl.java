package com.test.service.impl;

import com.test.dao.IStudentDAO;
import com.test.model.Student;
import com.test.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("studentServiceImpl")
public class StudentServiceImpl implements IStudentService {

    @Resource
    public IStudentDAO iStudentDAO;


    @Override
    public Student loginVertifiled(int sid) {

        Student stu = iStudentDAO.queryById(sid);
        return stu;
    }
}
