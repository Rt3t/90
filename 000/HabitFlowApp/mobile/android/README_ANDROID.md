# HabitFlow Android Studio

Открывать в Android Studio нужно папку `mobile/android`.

1. File -> Open -> `mobile/android`.
2. Если Android Studio предложит Gradle Sync — подтвердить.
3. Установить Android SDK 35, если попросит.
4. Device Manager -> создать Pixel с API 35 -> Start.
5. Выбрать `app` в конфигурации запуска и нажать Run (▶).
6. Backend: из корня проекта `docker compose up --build`.
7. Для Android Emulator приложение использует `http://10.0.2.2:8080`.
