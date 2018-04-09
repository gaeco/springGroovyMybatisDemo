# springGroovyMybatisDemo
Spring + Groovy + Mybatis(Velocity Expression) Integration Example

Intellij Community 버전에서 Spring MVC를 쉽게 개발하기 위한 환경입니다.
프로젝트 구성요소는 아래와 같습니다.

* Gradle (Maven) 프로젝트로 구성되었습니다.
  * Test 환경에서 Spring Boot가 구동 됨. Embedded Tomcat사용.
  * Main 환경에서는 운영서버에 반영될 소스만 생성됨. Tomcat을 이용한 서버구동 가능함.
* Spring Boot (Embedded Tomcat & On-Tomcat availible) 
  * Spring Boot Dev Tool을 사용하여 변경 클래스를 Tomcat재구동 없이 반영
  * Tomcat과 같은 Application Server를 이용한 프로젝트 구성도 가능
* Mybatis : Velocity Expression사용.
* Groovy : 공통코드가 아닌 업무 로직의 경우 모두 Groovy로 구성됩니다.
* non-XML : XML을 전혀 사용하지 않고 Annotation많으로 모든 로직을 구성합니다.
  * Config : Java Config 사용
  * Mybatis : Groovy Interface 사용
