# springGroovyMybatisDemo
Spring + Groovy + Mybatis(Velocity Expression) Integration Example

Intellij Community 버전에서 Spring MVC를 쉽게 개발하기 위한 환경입니다.
프로젝트 구성요소는 아래와 같습니다.

* Gradle
* Spring Boot (On-Tomcat availible) : 
  * Spring Boot Dev Tool을 사용하여 변경 클래스를 Tomcat재구동 없이 반영
  * Tomcat과 같은 Application Server를 이용한 프로젝트 구성도 가능
  * Test환경에서만 Spring Boot가 구동 됨.
* Mybatis
* Groovy : 공통코드가 아닌 업무 로직의 경우 모두 Groovy로 구성됩니다.
* non-XML : XML을 전혀 사용하지 않고 Annotation많으로 모든 로직을 구성합니다.
  * Config : Java Config 사용
  * Mybatis : Groovy Interface 사용
