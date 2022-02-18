package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Teacher;
import com.uniovi.notaneitor.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
   @RequestMapping("/teacher/details/{id}")
   public String getTeacherDetails(@PathVariable Long id){
        return teacherService.getTeacher(id).toString();
    }
    @RequestMapping(value="/teacher/add", method= RequestMethod.POST)
    public String addTeacher(@ModelAttribute Teacher teacher){
         teacherService.addTeacher(teacher);
         return "teacher: "+teacher.toString()+" added";
    }
    @RequestMapping(value="/teacher/edit", method= RequestMethod.POST)
    public String editTeacherCategory(@ModelAttribute Teacher teacher){
        teacherService.getTeacher(teacher.getId()).setCategory(teacher.getCategory());
        return "teacher's new category is :"+teacher.getCategory();
    }
    @RequestMapping(value="/teacher/delete/{id}", method= RequestMethod.POST)
    public String deleteTeacher(@PathVariable Long id){
       teacherService.removeTeacher(id);
        return "Teacher with id "+id+" was deleted";
    }
}
