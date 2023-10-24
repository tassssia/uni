Виконати завдання
a) java з використанням самостійної реалізації синхронізатора, 
б) java з стандартної бібліотеки 
c) на мові Golang
 
а) Друге завдання про Вінні-Пуха. В одному лісі живуть n бджіл і один ведмідь, які використовують один горщик меду, місткістю N ковтків. Спочатку горщик порожній. Поки горщик не наповниться, ведмідь спить. Як тільки горщик заповнюється, ведмідь прокидається і з'їдає весь мед, після чого знову засинає. Кожна бджола багаторазово збирає по одній порції меду і кладе його в горщик. Бджола, яка приносить останню порцію меду, будить ведмедя. Створить багатопоточний додаток, моделюючий поведінку бджіл і ведмедя.
 
б) Завдання про перукаря. У тихому містечку є перукарня. Салон перукарні малий, ходити там може тільки перукар і один відвідувач. Перукар все життя обслуговує відвідувача. Коли в салоні нікого немає, він спить в кріслі. Коли відвідувач приходить і бачить сплячого перукаря, він буде його, сідає в крісло і спить, поки перукар зайнятий стрижкою. Якщо відвідувач приходить, а перукар зайнятий, то він встає в чергу і засинає. Після стрижки перукар сам проводжає відвідувача. Якщо є інші відвідувачі які очікують, то перукар будить одного з них і чекає поки той сяде в крісло перукаря і починає стрижку. Якщо нікого немає, він знову сідає в своє крісло і засинає. Створити багатопоточний додаток, що моделює робочий день перукарні.
 
с) Завдання про курців. Є три процесу-курця і один процес-посередник. Курець безперервно скручує сигарети і курить їх. Щоб скрутити цигарку, потрібні тютюн, папір і сірники. У одного процесу-курця є тютюн, у другого - папір, а у третього - сірники. Посередник кладе на стіл по два різних випадкових компонента. Той процес-курець, у якого є третій компонент, забирає компоненти зі столу, скручує цигарку і курить. Посередник до чекає, поки курець не скінчить курити, потім процес повторюється. Створити багатопоточний додаток, що моделює поведінку курців і посередника. При вирішенні завдання використати семафори.