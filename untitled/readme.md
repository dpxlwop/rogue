# Документация проекта RogueLike

## Обзор

RogueLike — это roguelike игра на Java с процедурной генерацией уровней, боевой системой и системой инвентаря. Игра использует терминальный интерфейс (Lanterna) для вывода.

---

## Архитектура проекта

### Структура пакетов

```
org.example
├── Config.java                 # Константы конфигурации
├── Main.java                   # Точка входа
├── Data/
│   ├── DataClass.java         # Сохранение/загрузка игры
│   ├── LeaderBoard.java        # Таблица лидеров
│   └── Score.java              # Данные счета игрока
├── Game/
│   └── Game.java               # Основной класс игры
├── backend/
│   ├── GameTick.java           # Главный цикл игры
│   ├── GameTickExitCodes.java  # Коды выхода
│   ├── MessageLog.java         # Логирование сообщений
│   ├── Tile.java               # Типы плиток карты
│   ├── Entity/                 # Сущности (враги, игрок)
│   ├── Item/                   # Система предметов
│   ├── MapGenerator/           # Генерация уровней
│   └── Interaction/            # Логика взаимодействия
└── ui/
    ├── Drawer.java             # Отрисовка игры
    ├── KeyHandler.java         # Обработка ввода
    └── UiMaster.java           # Управление UI
```

---

## Основные компоненты

### 1. Config.java

Хранит глобальные константы игры.

| Константа | Значение | Описание |
|-----------|----------|---------|
| `WIDTH` | 120      | Ширина экрана |
| `MAP_HEIGHT` | 38       | Высота карты |
| `SCREEN_HEIGHT` | 41       | Общая высота экрана |
| `VERSION` | "1.1"    | Версия игры |
| `PLAYER_SIGHT_RADIUS` | 100      | Радиус видимости игрока |

### 2. Game.java

Основной класс игры, управляет состоянием всей игровой сессии.

**Поля:**
- `id` — уникальный ID игровой сессии
- `level` — текущий уровень (от 1 до 21)
- `map` — текущая карта уровня
- `player` — игрок
- `enemiesOnLevel` — враги на текущем уровне
- `itemsOnLevel` — предметы на текущем уровне
- `messageLog` — логирование сообщений игры

**Основные методы:**
```java
generateNextLevel()          // Генерирует следующий уровень
removeItemFromGame(Item)     // Удаляет предмет из игры
removeEntityFromGame(Entity) // Удаляет врага из игры
getItemByCords(x, y)         // Получает предмет по координатам
```

### 3. GameTick.java

Управляет основным цикл игры — обработка одного такта.

**Процесс такта:**
1. Получить команду игрока (движение, использование предмета)
2. Проверить, оглушен ли игрок
3. Движение игрока
4. Движение врагов и их атаки
5. Отрисовка

**Коды выхода:**
- `OK` — такт прошел нормально
- `NEXT_LEVEL` — переход на следующий уровень
- `GAME_OVER_PLAYER_DIED` — смерть игрока
- `GAME_OVER_BY_PLAYER` — выход игроком
- `GAME_OVER_WIN` — победа (уровень 21 пройден)

### 4. Drawer.java

Отрисовка всех элементов игры в терминале.

**Методы отрисовки:**
```java
draw(Game)              // Основной метод отрисовки
drawField(Tile[][])     // Отрисовка карты
drawPlayer(Player)      // Отрисовка игрока
drawEnemies(...)        // Отрисовка врагов
drawItems(...)          // Отрисовка предметов
drawHUD(...)            // Отрисовка интерфейса
drawWelcomeScreen()     // Экран приветствия
drawDeadScreen()        // Экран смерти
drawWinScreen()         // Экран победы
```

---

## Система предметов

### Иерархия классов Item

```
Item (abstract)
├── Elix (расходуемый)
├── Food (расходуемый)
├── Weapon (экипируемый)
├── Roll (экипируемый)
├── Treasure (ценность)
└── ExitItem (выход)
```

### Item.java

Базовый класс для всех предметов.

```java
public abstract class Item {
    protected int itemValue;      // Стоимость/эффект
    protected int[] itemPos;      // Координаты [x, y]
    
    public abstract char getSymbol();
}
```

### Интерфейсы

**Usable** — для использующихся предметов:
```java
public interface Usable {
    void use(Player player);
}
```

**Equipable** — для экипируемых предметов:
```java
public interface Equipable {
    void equip(Player player);
}
```

### Типы предметов

| Класс | Эффект | Описание |
|-------|--------|---------|
| `Elix` | Баф на характеристику | Временный баф на Ловкость/Силу/Здоровье |
| `Food` | Восстановление HP | Восстанавливает здоровье |
| `Weapon` | Увеличение урона | Экипируемое оружие |
| `Roll` | Защита | Экипируемая броня |
| `Treasure` | Валюта | Золото от убитых врагов |
| `ExitItem` | Выход со уровня | Портал на следующий уровень |

---

## Система инвентаря

### Backpack.java

Хранилище предметов игрока (максимум 9 слотов).

```java
public class Backpack {
    private ArrayList<Item> backpack;  // Максимум 9 предметов
    
    public boolean addItemInBackpack(Item item)    // Добавить предмет
    public void removeItemFromBackpack(Item item)  // Удалить предмет
    public ArrayList<Item> getBackpack()           // Получить инвентарь
    public int getItemsCounter()                   // Количество предметов
}
```

---

## Генерация уровней

### MapGenerator

Процедурная генерация уровней с использованием:
- Случайное распределение комнат
- Алгоритм Краскала (минимальное остовное дерево) для коридоров

### Основные классы

**GameMap.java** — карта уровня
```java
public class GameMap {
    private Tile[][] map;              // Сетка карты
    private ArrayList<Room> rooms;     // Комнаты уровня
    private ArrayList<Entity> enemiesInRooms;
    private ArrayList<Item> itemsOnLevel;
}
```

**CorridorGenerator.java** — генерация коридоров
```java
public static Tile[][] generateCorridors(Tile[][] map, ArrayList<Room> rooms)
```

**Edge.java** — ребро графа (расстояние между комнатами)
```java
public class Edge {
    private Room roomA;
    private Room roomB;
    private double distance;  // Вычисляется автоматически
}
```

---

## Сохранение и загрузка

### DataClass.java

Работа с сохранениями через JSON (Jackson).

```java
public static void saveGame(Game game)        // Сохранить игру
public static Game loadGame()                 // Загрузить игру
public static void saveLeaderBoard(LeaderBoard lb)  // Сохранить таблицу лидеров
public static LeaderBoard loadLeaderBoard()   // Загрузить таблицу лидеров
public static void clearSave()                // Удалить сохранение
public static boolean isSaveExist()           // Проверить наличие сохранения
```

**Пути к файлам:**
- Сохранение игры: `data/save.json`
- Таблица лидеров: `data/leaderboard.json`

---

## Главный цикл игры

### Main.java

```
┌─────────────────────────────────┐
│  Загрузить таблицу лидеров      │
└────────────┬────────────────────┘
             │
    ┌────────▼────────┐
    │ Экран меню      │
    │ (загрузка/новая)│
    └────────┬────────┘
             │
    ┌────────▼─────────────────┐
    │  Начать игру              │
    │  (создать GameTick)       │
    └────────┬─────────────────┘
             │
    ┌────────▼──────────────────────┐
    │  Цикл GameTick                 │
    │ ┌──────────────────────────┐   │
    │ │ 1. Ввод игрока           │   │
    │ │ 2. Движение игрока       │   │
    │ │ 3. Движение врагов       │   │
    │ │ 4. Проверка условий      │   │
    │ │ 5. Отрисовка             │   │
    │ └──────────────────────────┘   │
    │                                 │
    │ Условия выхода:                │
    │ • Смерть игрока                │
    │ • Выход игрока                 │
    │ • Победа (уровень 21)          │
    └────────┬──────────────────────┘
             │
    ┌────────▼──────────────────┐
    │ Сохранить результат       │
    │ (очистить сохранение)     │
    │ (добавить в лидерборд)    │
    └────────┬──────────────────┘
             │
         Новая игра?
         ├─ ДА → Возврат к меню
         └─ НЕТ → Завершение
```

---

## HUD (интерфейс)

Информация отображается в двух строках:

**Строка 1:**
```
Agility: X Strength: Y Treasures: Z | Level: N Health: H/MAX
```

**Строка 2:**
```
Инвентарь: [✝] [✠] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
```

---

## Обработка ввода

### KeyHandler.java

- `handleInput()` — получить команду игрока (движение, действие)
- `handleName()` — ввод имени игрока

**Поддерживаемые команды:**
- Стрелки/WASD — движение
- Enter — использовать предмет / загрузить игру
- Esc — выход

---

## Система боевой системы

Разделена на два класса в пакете `Interaction`:

**FightPlayerAgressor.java** — атака игрока
**FightEntityAgressor.java** — атака врагов

Урон зависит от характеристики `Strength`, шанс попадания — от `Agility`.

---

## Видимость и туман войны

Враги и предметы видны только в радиусе `PLAYER_SIGHT_RADIUS` (100 клеток).

```java
private boolean isVisible(int playerX, int playerY, int entityX, int entityY) {
    int dx = Math.abs(entityX - playerX);
    int dy = Math.abs(entityY - playerY);
    return dx <= Config.PLAYER_SIGHT_RADIUS && dy <= Config.PLAYER_SIGHT_RADIUS;
}
```

---

## Типы плиток карты

```java
public enum Tile {
    WALL('█'),      // Стена
    FLOOR(' '),     // Пол
    CORRIDOR(' ')   // Коридор
}
```

---

## Таблица лидеров

### Score.java

Хранит информацию об одном рекорде:
- Имя игрока
- Финальный счет (сумма сокровищ)
- Достигнутый уровень
- ID игровой сессии

### LeaderBoard.java

Управляет списком рекордов, сортирует по убыванию счета.

---

## Технологический стек

| Технология | Назначение |
|-----------|-----------|
| **Java 17+** | Язык программирования |
| **Kotlin** | Дополнительный язык (если используется) |
| **Gradle** | Система сборки |
| **Lanterna 3.x** | Терминальный интерфейс |
| **Jackson** | Сериализация JSON |

---

## Ошибки и возможные проблемы

### Возможные улучшения кода

1. **Drawer.java** — большой класс, можно разделить на подклассы
2. **GameTick.java** — сложный метод `playerMovement()`, нужен рефакторинг
3. **GameMap.java** — множество условных операторов в `summonTreasure()`
4. **CorridorGenerator.java** — использование индексов вместо прямых ссылок

---

## Сборка и запуск

```bash
# Сборка проекта
gradle build

# Запуск игры
gradle run

# Очистка
gradle clean
```

---

## Файлы конфигурации

- `build.gradle` — конфигурация Gradle
- `settings.gradle` — настройки проекта

---

*Документация актуальна для версии 1.1*