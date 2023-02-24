## P.S.
Изначально при написании автотестов использовал фреймворк Espresso (как и обозначил в плане), но в процессе работы обнаружил, 
что данный фреймворк очень неудобен и требуется много overengineering/workaround для самых простейших вещей, например написание собственных Helper-ов для ожидания элементов, проверки состояния элементов.
В последствии решил использовать UIAutomator, т.к. у него более простой и лаконичный API, да и с ним просто приятнее работать.

## Результаты сравнения времени ручной проверки приложения:
- Ручная проверка: ~ 70 минут
- Функциональные e2e автотесты: ~ 7 минут 