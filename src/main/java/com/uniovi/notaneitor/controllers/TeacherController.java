package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Teacher;
import com.uniovi.notaneitor.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    @RequestMapping(value = "/teacher/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teacherService.getTeacher(id));
        return "edited teacher";
    }


    @RequestMapping(value = "/teacher/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute Teacher teacher, @PathVariable Long id) {
       teacher.setId(id);
       teacherService.addTeacher(teacher);
        return "Editing teacher with id" + id;
    }
    @RequestMapping(value="/teacher/delete/{id}", method= RequestMethod.POST)
    public String deleteTeacher(@PathVariable Long id){
       teacherService.removeTeacher(id);
        return "Teacher with id "+id+" was deleted";
    }

}
