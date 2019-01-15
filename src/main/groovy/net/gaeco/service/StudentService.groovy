package net.gaeco.service

import net.gaeco.dao.StudentDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudentService {
    @Autowired
    StudentDAO studentDAO

    def List getStudentAll() {
        studentDAO.studentsAll
    }

    def getStudentById (Integer id){
        studentDAO.getStudentById([id:id])
    }

    def List getStudents(Map obj){
        studentDAO.getStudents(obj)
    }

    def List getStudentsInIds(Map obj){
        studentDAO.getStudentsInIds(obj)
    }

    @Transactional(rollbackFor = Exception.class)
    def void insertStudents(List<Map> list){
        def cnt = 0
        list.each{ it ->
            /*-----------------------------------------------------------
             * Object로 형변환해야 DAO.yyyy(x) 형태의 groovy 메소드를 찾을 수 있다.
             * 형변환 하지않을 경우 DAO.yyy(LinkedHashMap x)로 형을 명시해야한다
             *-----------------------------------------------------------*/
            studentDAO.insertStudent((Object)it)
            if(cnt++ > 3){
                throw new Exception("TEST AAAAAAA")
            }
        }
    }
}
