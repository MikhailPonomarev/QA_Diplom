## **Процедура запуска авто-тестов:**
Перед началом работы:
1. Установить Java JDK11 (Eclipse Temurin)
2. Установить [allure-commandline](https://docs.qameta.io/allure-report/) (раздел 2.1. Installing a commandline)
3. Склонировать репозиторий https://github.com/MikhailPonomarev/QA_Diplom
4. Открыть проект autotests в Android Studio
5. Создать эмулятор Android устройства с API 29
6. Запустить созданный эмулятор

### **Запуск без формирования отчета Allure**
- Запустить все UI тесты консольной командой `./gradlew connectedAndroidTest`

### **Запуск с формированием отчета Allure**

1. Кликнуть правой кнопкой мыши на директорию `app/src/androidTest` и выбрать опцию `Run 'All Tests'`, либо кликнуть на ту же директорию и нажать комбинацию клавиш `Ctrl+Shift+F10`(Win) / `Ctrl+Shift+R` (Mac)
2. Открыть Device File Explorer для эмулятора, на котором запускались тесты и выгрузить директорию `/data/data/ru.iteco.fmhandroid/files` в заранее подготовленную директорию в любьм месте файловой системы компьютеры (например создать директорию "allure" в корне проекта)
3. С помощью терминала перейти в директорию с выгруженными allure-results, выполнить консольную команду для формирования отчета `allure generate allure-results --clean -o allure-report`
4. Для открытия отчета выполнить консольную команду `allure open allure-report`
