**1. Окружение**
------
Приложение разрабатывалось на версии **Java 1.8** с использованием Базы данных **PostgreSQL 9.6**

**2. Настройка базы данных**
------
Для работы приложение необходимо настроить и создать 2 базы данных:

1. База данных для тестирования приложения. Для этой базы необходимо создать базу со следующими настройками:**test_db**

2. База данных для работы приложения. Для этой базы необходимо  создать базу со следующими настройками: **app_db**

По умолчанию настройки пользователя для обеих БД в приложении стоят следующие ```` spring.datasource.username=postgres

spring.datasource.password=123456 ```` 

**ВНИМАНИЕ!**  Если в PostgreSQL используется другой пользователь для базы данных или пароль их необходимо   изменить в файлах **application.properties** и **application-test.properties**

В приложении имеются миграции базы, которые в автоматическом режиме создадут таблицы, а так же наполнят их набором первоначальных данных.

**3. Сборка и запуск приложения**
------
Для запуска приложения необходимо перейти в директорию с проект при помощи команды в консоли ```cd```.  Далее выполнить команду ```mvn package && java -jar target/banner_app-0.0.1-SNAPSHOT.jar```. После успешной сборки   и запуска приложения оно будет доступно по адресу ```http://localhost:8080/```

Для входа используйте следующие данные:

- login: oleg23
- password : 123

или

- login: ekat1
- password : 123 

**4. Комментарии** 
------
При разработке не были учтены те требования, которые возникали непосредственно во время разработки программного продукта.  К примеру: 
- каким образом новый пользователь будет добавляться в систему 
- каким образом будут добавляться новые данные локализации
- необходимо ли делать для баннеров некую форму preview, чтобы пользователи системы могли сделать их предварительный просмотр
- нужно ли периодически чистить базу с данными аудита (при большом количестве операций она может быстро переполнится)

Эти требования возможно реализовывать по мере усовершенствования программы и добавления в нее нового функционала Изначально приложение разрабатывалось при помощи наследования от JPARepository. Это было сделано для того, чтобы, не отвлекаясь на возможные ошибки в JDBCTemplate, отладить функционал. Конечно, в первую очередь, необходимо было определиться с Entity, их взаимодействием и набором полей. После того как была закончена отладка требуемого функционала, была произведена миграция на JDBCTemplate. Для взаимодействия с базой данных использовались интерфейсы Repository и их реализация. Это позволяет не привязываться сильно к реализации взаимодействия. Frontend написан с использование jquery и шаблонизатора mustache. 


Файлы диаграмм добавлены в папку UML
