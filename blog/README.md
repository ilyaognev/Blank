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

####Heroku
Приложение настроено для деплоя на хероку.
В application.properties поменял порт на 33500
Настроено подключение к БД на Postgres (креденшиалс могли устареть). Файл для создания БД изменён для postgres.
Изменены html файлы. Из-за ошибки в thymeleaf с двойными слешами, изменил подобные строки:
Было:
```
<div th:replace="/fragments/header :: header"></div>
```
Стало:
```
<div th:replace="fragments/header :: header"></div>
```

В файле Procfile прописывается что будет делать хероку при старте. В нашем случае, запускается файл, который при инстале создаёт мавен.