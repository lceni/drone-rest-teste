# lceni@drone-rest-teste
Teste prático para a vaga de Desenvolvedor Java, que consiste em um serviço REST e testes unitários de um Drone fictício cujo movimentos são executados a partir de uma interface REST.

**Tecnologias utilizadas:**
* Gradle 3.1
* Jersey 1.13
* JUnit

### Instruções para execução:

Faça uma cópia local deste repositório, em seguida, execute os passos abaixo, a partir do diretório drone-rest-teste:

**Para rodar o serviço:**

     cd drone
     gradlew appRun

**Para executar os testes unitários:**

     cd drone
     gradlew test

**Para gerar um WAR:**  
*O arquivo WAR gerado estará disponível em drone-rest-teste/drone/build/libs/drone.war*  
*Utilize este arquivo para fazer deploy em seu servidor de aplicação favorito (Tomcat, Jetty, Wildfly, Glassfish, etc)*

     cd drone
     gradlew war
