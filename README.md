# Nginx Log Analyzer

Nginx Log Analyzer — инструмент для анализа логов Nginx, поддерживающий однопоточную и многопоточную обработку для эффективной обработки больших объемов данных. Инструмент принимает данные из локальных файлов или с удаленного сервера и собирает статистику.

### Основные функции

Программа собирает и выводит следующую информацию:
- Общее количество запросов
- Средний размер ответа
- 95-й перцентиль размера ответа
- Запрашиваемые ресурсы с количеством обращений, отсортированные по убыванию
- Статистика кодов ответов с количеством, отсортированная по убыванию

### Использование

Для запуска программы укажите следующие параметры:
- `--path` (обязательный): glob-выражение для локальных файлов или URL для удаленного лог-файла
- `--format`: формат генерируемого отчета (`markdown` или `adoc`; по умолчанию `markdown`)
- `--from`: дата начала анализа логов
- `--to`: дата окончания анализа логов
- `--filter-field`: поле для фильтрации данных (например, "agent" или "method")
- `--filter-value`: значение фильтра

Отчеты по умолчанию сохраняются в папке [report](report).

### Конфигурация

Некоторые настройки можно изменить в классе `Constants`:
- `MULTITHREADING_MODE`: включает многопоточную обработку (по умолчанию `true`)
- `COMPRESSION_FACTOR`: точность расчета 95-го перцентиля в `t-digest`

### Многопоточность

Инструмент использует многопоточную обработку, что позволяет ускорить анализ в 2-3 раза по сравнению с однопоточным режимом. Ниже приведены результаты тестов на процессоре **Intel(R) Core(TM) i5-1135G7** (4 ядра, 8 потоков).

| Номер теста | Размер лог-файлов | FileSingleThreadingAnalyzer | FileMultithreadingAnalyzer |
| ----------- | ----------------- | --------------------------- | -------------------------- |
| 1           | 1 ГБ              | 12617 мc                    | 4304 мc                    |
| 2           | 2 ГБ              | 25173 мc                    | 10481 мc                   |
| 3           | 3 ГБ              | 37189 мc                    | 10555 мc                   |
| 4           | 4 ГБ              | 43463 мc                    | 13932 мc                   |
| 5           | 5 ГБ              | 54819 мc                    | 25462 мc                   |

Timeline-граф нагрузки процессора для однопоточного анализатора:
![single_threading_mode.png](img%2Fsingle_threading_mode.png)

Timeline-граф нагрузки процессора для многопоточного анализатора:
![multi_threading_mode.png](img%2Fmulti_threading_mode.png)
