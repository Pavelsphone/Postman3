# Contact Manager

## Запуск приложения

1. Убедитесь, что у вас установлен PostgreSQL и создана база данных `contactdb`
2. Обновите настройки подключения к базе данных в `application.properties` при необходимости
3. Запустите приложение: `mvn spring-boot:run`
4. Приложение будет доступно по адресу: http://localhost:8080

## Тестирование эндпоинтов в Postman:

### Получение всех контактов
GET http://localhost:8080/contacts

### Создание контакта
POST http://localhost:8080/contacts
Content-Type: application/json

{
  "firstName": "Иван",
  "lastName": "Иванов",
  "phone": "+79991234567",
  "email": "ivan.ivanov@example.com"
}

### Получение конкретного контакта
GET http://localhost:8080/contacts/1

### Обновление телефона контакта
PUT http://localhost:8080/contacts/1/phone
Content-Type: text/plain

+79999999999

### Обновление email контакта
PUT http://localhost:8080/contacts/1/email
Content-Type: text/plain

newemail@example.com

### Удаление контакта
DELETE http://localhost:8080/contacts/1

### Загрузка контактов из CSV файла
POST http://localhost:8080/contacts/upload-csv
Content-Type: multipart/form-data

Выберите файл CSV с контактами (формат: Имя Фамилия,Телефон,Email)

## Важные моменты:

- Убедитесь, что Content-Type в заголовках установлен правильно
- При создании контакта не указывайте ID - он присвоится автоматически
- При обновлении контакт перезаписывается полностью
- Если указать несуществующий ID, получите статус 404
- После каждого запроса проверяйте статус ответа (200 - успех, 404 - не найдено)
