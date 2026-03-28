<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/5d5736b0-df1b-4b51-96c3-df7c44129204" /># Завдання №4: Поліморфізм

Згідно цього завдання потрібно було:

 - За основу використовувати вихідний текст проекту попередньої лабораторної роботи Використовуючи шаблон проектування Factory Method (Virtual Constructor), розширити ієрархію похідними класами, реалізують методи для подання результатів у вигляді текстової таблиці. Параметри відображення таблиці мають визначатися користувачем.
 - Продемонструвати заміщення (перевизначення, overriding), поєднання (перевантаження, overloading), динамічне призначення методів (Пізнє зв'язування, поліморфізм, dynamic method dispatch).
 - Забезпечити діалоговий інтерфейс із користувачем.
 - Розробити клас для тестування основної функціональності.
 - Використати коментарі для автоматичної генерації документації засобами javadoc.

## Пояснення структури коду

Для реалізації завданнябуло взято код із минулої роботи, та змінено із внесенням таких змін: 

 - Додав у Main команду 't'able settings, після вводу t можна поміняти вивід у якій системі буде опір 8-ричній чи 16-ричній
 - Створено новий клас AppTest із тестами виводу та числення
 - У клас Calc додано поля showOct і showHex та методи setShowOct() і setShowHex() щоб передавати налаштування в TextItemView. Також, тепер метод show()  виводить таблицю з шапкою і нижньою лінією.
 - Клас TextViewItem переписаний повністю, тепер там два конструктора для відображення HEX та OCT для рамок таблиці, яка залежить від значень змінних showOct та showHex


# Тести програми
**Тестування чи все правильно працює**
![фото проведення функцій](https://github.com/rubanAlexUa/practice/blob/main/Tasks%20descriptions/Task%20%E2%84%964/images/Test%20result.png)

**Робота нового виводу данних**
![фото проведення функцій](https://github.com/rubanAlexUa/practice/blob/main/Tasks%20descriptions/Task%20%E2%84%964/images/New%20design%20test.png)

**Усі варіанти вибору показу опору у OCT та HEX варіантах**
![фото проведення функцій](https://github.com/rubanAlexUa/practice/blob/main/Tasks%20descriptions/Task%20%E2%84%964/images/HEX%20OCT%20test1.png)

![фото проведення функцій](https://github.com/rubanAlexUa/practice/blob/main/Tasks%20descriptions/Task%20%E2%84%964/images/HEX%20OCT%20test2.png)

![фото проведення функцій](https://github.com/rubanAlexUa/practice/blob/main/Tasks%20descriptions/Task%20%E2%84%964/images/HEX%20OCT%20test3.png)
