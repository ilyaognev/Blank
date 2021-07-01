###Образец простейшего блога на Spring boot

####Технологии
+ spring boot
+ spring security
+ thymeleaf
+ lombok

####Проблемы
+ не работала валидация (@Length, @NotBlank, @NotEmpty)  
  Такая проблема проявляется на spring boot  версии 2.3.0 и выше.
  Необходимо поставить зависимость:
  ```
  <dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  ```
  И обязательно убрать, если есть, лишние зависимости: javax.validation (validation-api), org.hibernate (hibernate-validator)


####Источники:
+ https://github.com/skarware/spring-boot-blog-app
+ https://github.com/reljicd/spring-boot-blog
