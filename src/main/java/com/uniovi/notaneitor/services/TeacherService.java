package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    private List<Teacher> teacherList=new ArrayList<Teacher>();
    @PostConstruct
    public void init(){
        teacherList.add(new Teacher("José","Fernández","342456y","ciencias",1L));
        teacherList.add(new Teacher("Ana","Gutiérrez","55216y","ciencias",6L));

    }

    public Teacher getTeacher(Long id){
        return teacherList.stream().filter(teacher->teacher.getId().equals(id)).findFirst().get();
    }

    public void addTeacher(Teacher teacher){

        if(teacher.getId()==null){
            teacher.setId(teacherList.get(teacherList.size()-1).getId() + 1);
        }

        this.teacherList.add(teacher);}

    public void removeTeacher(Long id){
        teacherList.removeIf(teacher -> teacher.getId().equals(id));
    }
}
