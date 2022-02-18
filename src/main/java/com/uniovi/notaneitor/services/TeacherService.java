package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Teacher;
import com.uniovi.notaneitor.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    /*
    private List<Teacher> teacherList=new ArrayList<Teacher>();
    @PostConstruct
    public void init(){
        teacherList.add(new Teacher("José","Fernández","342456y","ciencias",1L));
        teacherList.add(new Teacher("Ana","Gutiérrez","55216y","ciencias",6L));

    }
    */

    public Teacher getTeacher(Long id){
       // return teacherList.stream().filter(teacher->teacher.getId().equals(id)).findFirst().get();
        return teacherRepository.findById(id).get();
    }

    public void addTeacher(Teacher teacher){
         teacherRepository.save(teacher);
    }

    public void removeTeacher(Long id){
       // teacherList.removeIf(teacher -> teacher.getId().equals(id));
        teacherRepository.deleteById(id);
    }


}
