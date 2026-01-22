# Country Explorer

**ФИО:** Зинченко Мария Сергеевна  
**Группа:** Б9123.03.03ПИКД(6)

## API
**REST Countries API v2** (https://restcountries.com/v2/)  
- Предоставляет информацию о всех странах мира: названия, столицы, население, площадь, регионы
- Не требует API-ключа
- Бесплатный и открытый
- Пример запроса: `GET https://restcountries.com/v2/all`

## Как запустить
1. Открыть проект в Android Studio
2. Дождаться завершения синхронизации Gradle
3. Нажать зелёную стрелку "Run"
4. Выбрать эмулятор или подключить телефон с режимом разработчика

## Скриншоты
1. Loading screen
2. List of countries
3. Country details  
4. Favourites screen
5. Search
6. Error screen

## Чеклист выполненных требований

### Обязательный функционал:
- [x] **A) Навигация:** 2+ экрана (List + Detail + Favourites)
- [x] **B) Архитектура:** UiState + ViewModel + Repository
- [x] **C) Coroutines + Retrofit:** suspend функции + viewModelScope
- [x] **D) UI состояния:** Loading, Error, Empty, Success
- [x] **E) Избранное:** локальное хранение в ViewModel

### Технические требования:
- [x] Compose + Material3
- [x] Navigation Compose  
- [x] ViewModel + viewModelScope
- [x] Retrofit + Gson converter
- [x] Без Flow (использован mutableStateOf)

### Бонусы:
1. Debounce поиска (400ms)
2. Отдельный экран избранного
3. Кнопка Refresh
4. Кэш в памяти ViewModel
