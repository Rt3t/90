 Запуск backend + PostgreSQL через Docker

```bash
docker compose up --build
```

Backend будет на `http://localhost:8080`, PostgreSQL — на `localhost:5432`.
При первом запуске автоматически создаётся demo-пользователь с ID `1`.

Проверка:

```bash
curl http://localhost:8080/api/users/demo
curl http://localhost:8080/api/habits/1
```

## 2. Запуск мобильного приложения

```bash
cd mobile
npm install
npm start
```