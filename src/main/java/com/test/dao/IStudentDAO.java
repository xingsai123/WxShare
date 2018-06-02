package com.test.dao;

import com.test.model.Student;

public interface IStudentDAO {
    int deleteByPrimaryKey(Integer sid);

    int insert(Student record);

    int insertSelective(Student record);


    // 根据id查询单个学生用户
    public Student queryById(Integer sid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}