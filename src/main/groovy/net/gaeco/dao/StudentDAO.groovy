package net.gaeco.dao

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Lang
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.mybatis.scripting.velocity.Driver
import org.springframework.beans.factory.annotation.Qualifier

//@Repository
@Mapper
@Qualifier("oneSqlSessionTemplate")
interface StudentDAO {

    @Select('''
        SELECT 
            id, 
            studentName, 
            email, 
            regDate, 
            modDate
        FROM Student
        ''')
    def List<LinkedHashMap> getStudentsAll()

    @Lang(org.mybatis.scripting.velocity.Driver.class)
    @Select('''
        SELECT id, name, idNo, age, modUser, modDate
        FROM Student
        WHERE id = @{id}
        ''')
    def Map getStudentById(Map obj)

    /* Velocity를 사용할 경우 #if 과 같이 #은 문법예약어에 사용하고 파라미터는 @{param} 형식으로 사용한다. */
    @Lang(org.mybatis.scripting.velocity.Driver.class)
    @Select('''
        SELECT 
            id, 
            name as NAME123, 
            idNo, 
            age, 
            modUser, 
            modDate
        FROM Student
        WHERE 1=1
          #if($_parameter.id) /* 주의! 객체혹은 Map의 형태로 들어온 인자값중 특정 필드가 있는지 확인할 때 $_parameter.xxx 형태를 사용한다. */
          AND id = @{id} 
          #else
          AND id >= 1 
          #end
        ''')
    def List<LinkedHashMap> getStudents(Map x)

    @Lang(Driver.class)
    @Select('''
        SELECT 
            id, 
            name as NAME123, 
            idNo, 
            age, 
            modUser, 
            modDate
        FROM Student
        WHERE 1=1
          #if($_parameter.id)
          AND id = @{id} 
          #else
          AND id >= 1 
          #end
        ''')
    def List<LinkedHashMap> getStudents1(Map x)

    @Insert('''
        INSERT INTO Student(name, idNo, age, modUser, modDate, strVar) 
        VALUES(#{name}, #{idNo}, #{age}, 'TEST', DATE_FORMAT(now(), "%Y%m%d%H%i%s"), #{strVar})
        ''')
    def void insertStudent(x)


}


