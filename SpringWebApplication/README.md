# Учебный проект на SpringBoot

## Разработка веб-приложения на SpringBoot.
### Веб-сервис по обмену книгами.


#### Программа: Программист - Разработчик
#### Специализация: Веб-разработка на Java 


#### Город: Баку
#### Год: 2025

-------------------------------------------------

### Называние проекта: «Веб-приложение по обмену книгами».
#### Логика: После регистрации пользователь попадает на главную страницу приложения, где может бесплатно скачать или загрузить книги. Эти книги загружают сами пользователи. Все книги загруженные на ресурс (в облако к которому подключается веб-приложение, в моем случаи Yandex Cloud) могут посмотреть и скачать любые зарегистрированные пользователи.



#### Каждая часть проекта построена на модели MVC.
#### Проект состоит из следующих частей:
1) Регистрация Пользователя - Registration

        1.1) User
        1.2) UserRepository
        1.3) SecurityConfig - для настройки  login,register,account,upload
        1.4) MyUserDetailsService - для получения пользователя из БД
        1.5) UserService
        1.6) UserController
           1.7) Форма регистрации - register.html
           1.8) Форма для входа в аккаунт - login.html

2) Загрузка Книг Пользователем FilesForUpload

        2.1) Book
        2.2) BookRepository
        2.3) BookService
        2.4) FileUploadController - конроллер для загрузки книг, осуществляет связь пользователя с загруженными им книгами
        2.5) BookAlreadyExistException
            2.6) Форма загрузки - upload.html

3) Аккаунт Пользователя - Account 

        3.1) UserBook
        3.2) UserBookRepository
        3.3) UserBookService
        3.4) UserBookController
        3.5) UserBookExeptions
           3.6) Форма аккаунта - account.html

4) Главная Страница Ресурса - Home

       4.1) HomeController - он сочетает в себе два сервиса UserService и BookService
       Это сделано для того, что бы на странице можно было одновременно отображать информацию о пользователи(account)  и видеть список книг.
           4.2) Форма главной страницы - home.html

5) Облако - Cloud

       5.1)YandexCloudServiceСервис - для подключению к облачному хранилищу

На данном этапе программа запускается локально, но при этом картинки книг и файлы самих книг хранятся в облаке - YandexCloud.
Ссылки на картинки и сами книги хранятся в базе данных на PostgeSQL.
База Данных размещена на сайте https://railway.com/
В проекте создаются три таблицы:
1. - users c полями id, password, role,username
2. - books с полями id, book_author,book_image(ссылка на картинку),book_name,book_url(ссылка на книгу),page,year
3. - user_book смежная таблица, для получения списка книг загруженных пользователем, поля id,book_id,user_id


#### Запуск Проекта

1.    Запускаем main файл : SpringWebApplication.java
2.    В браузере проходим по адресу: http://localhost:8080/register
3.    Заполняем поля логин и два раза пароль.
- если логин занят появится сообщение: Пользователь с именем {} уже существует
- если пароли будут отличаться, то благодаря функции validatePasswords() в файле registrationSuccess.js появится всплывающие окно, с сообщением: "Пароли не совпадают! Пожалуйста, повторите попытку."
- если процесс регистрации прошел успешно, благодаря другой функции showSuccessMessage(), вас переправит на страницу http://localhost:8080/login
4.    Входим в аккаунт. Вводим логин и пароль и нас переправляет по адресу: http://localhost:8080/home
5.    На главной странице по центру находится строка поиска по имени книги или по автору
-  Под строкой поиска расположен список книг с картинкой, описанием и ссылкой на скачивание
-  Сверху справа расположены две кнопки: Account и Logout
-  В самом низу страницы расположена ссылка для добавления новых книг
 
#### Аккаунт 

- Нажав на кнопку Account http://localhost:8080/account попадаем в аккаунт
- Сверху отображается информация о пользователе - логин пользователя
- Внизу список загруженных книг и кнопка удалить, для их удаления
- И в самом низу кнопка для перехода на главную страницу.

#### Загрузка Книг

- На главной странице http://localhost:8080/home в самом низу находиться ссылка для загрузки
- При нажатии на которую переходим http://localhost:8080/upload
- Заполняем поля, если книга уже есть будет выдано сообщение
- После успешной загрузки внизу появится информация о книги и сылки для скачивания.
- И в самом ссылка для перехода на главную страницу


#### Поиск

- После поиска мы увидим количество найденных книг, под строкой поиска и сами книги
- Под строкой поиска ссылка вернуться на главную



#### Картинки, облака:
- Ссылка на облако https://storage.yandexcloud.net/book-storage-on-springbot
- ![Final_work](https://i.ibb.co/BZ6M5xr/01.png "JavaCloud")
- ![Final_work](https://i.ibb.co/4s9GKV9/02.png "JavaCloud")
- ![Final_work](https://i.ibb.co/hDnY0mb/03.png "JavaCloud")
- ![Final_work](https://i.ibb.co/4NNFLj8/04.png "JavaCloud")


#### База Данных:
- Подключение к БД: postgresql://postgres:KHlfXSfcXAOKURoJFlBvqSFNRIWexgCx@junction.proxy.rlwy.net:15675/railway
- ![Final_work](https://i.ibb.co/x13MBnm/05.png "JavaDB")
- ![Final_work](https://i.ibb.co/2FgP1Fm/06.png "JavaDB")
- ![Final_work](https://i.ibb.co/mBLzMB4/07.png "JavaDB")
- ![Final_work](https://i.ibb.co/9GVmn8X/08.png "JavaDB")
- ![Final_work](https://i.ibb.co/1Q1Y4s5/09.png "JavaDB")


#### Картинки интерфейса:

- ![Final_work](https://i.ibb.co/Kx4J8NS/11.jpg "JavaInfo")
- ![Final_work](https://i.ibb.co/Gt2ZP29/12.png "JavaInfo")
- ![Final_work](https://i.ibb.co/jRLxSGZ/13.jpg "JavaInfo")
- ![Final_work](https://i.ibb.co/2g17npQ/14.png "JavaInfo")
- ![Final_work](https://i.ibb.co/mDpBqps/15.png "JavaInfo")
- ![Final_work](https://i.ibb.co/QvNpymy/16.png "JavaInfo")
- ![Final_work](https://i.ibb.co/rmQmpvv/17.jpg "JavaInfo")
- ![Final_work](https://i.ibb.co/wyJQ4tH/18.png "JavaInfo")
- ![Final_work](https://i.ibb.co/9VBSCpT/19.png "JavaInfo")
- ![Final_work](https://i.ibb.co/PD68Mk5/20.png "JavaInfo")
