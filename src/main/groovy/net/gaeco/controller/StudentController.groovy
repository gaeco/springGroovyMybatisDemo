package net.gaeco.controller

import net.gaeco.exception.CustomException
import net.gaeco.exception.FileNotFoundException
import net.gaeco.common.VelocityTemplate
import net.gaeco.service.StudentService
import net.gaeco.common.Constants
import org.apache.velocity.VelocityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class StudentController {
    @Autowired
    StudentService studentService

    @GetMapping("/student/all")
    def getStudentAll(){
        [list:studentService.getStudentAll(), status:"OK"]
    }

    @GetMapping("/student/{id}")
    def getStudent(@PathVariable(value="id") Integer id){
        studentService.getStudents([id:id])
    }

    @GetMapping("/students")
    def getStudents(@RequestParam(value = 'ids',required = false) String ids){
        def idArr = []
        if(ids){
            idArr = ids.split(",")
        }
        studentService.getStudentsInIds([ids:idArr])
    }

    @PostMapping("/students")
    def Object putStudents(@RequestBody List<Map> list){
//        println(list)
        studentService.insertStudents(list)
        list
    }

    //Exception 테스트용
    @GetMapping("/test/exception/excel")
    def getExcelException(){
        throw new CustomException("엑셀 Error 입니다.",Constants.ERROR_CODE_EXCEL);
    }

    //Exception 테스트용
    @GetMapping("/test/exception/session")
    def getSessionException(){
        throw new CustomException("Session Error 입니다.",Constants.ERROR_CODE_SESSION);
    }
    @GetMapping("/test/exception/file")
    def getFileNouFound(){
        throw new FileNotFoundException("파일이 없습니다.")
    }

    //Exception 테스트용
    @GetMapping("/test/exception/etc")
    def getEtcException(){
        throw new Exception("Etc Error 입니다.");
    }

    //Exception 테스트용
    @GetMapping("/test/exception/run")
    def getRuntimeException(){
        throw new RuntimeException("Runtime Error 입니다.")
    }


}
