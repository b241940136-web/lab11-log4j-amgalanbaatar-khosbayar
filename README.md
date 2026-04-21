# Лаб 11 — Log4j ашиглан Debug хийх

**Хичээл:** F.CSM202 Объект хандлагат программчлал  
**Оюутан:** [Хосбаяр]  
**Repository:** lab11-log4j-[Амгаланбаатар]-[Хосбаяр]

---

## Төслийн бүтэц

```
lab11-logging/
├── pom.xml
├── src/main/java/mn/must/lab11/
│   ├── BankAccount.java      ← Алхам 2: 6 log level жишээ
│   ├── Customer.java         ← Алхам 3: NPE debug
│   └── Main.java             ← Алхам 2.1: бүх туршилт
├── src/main/resources/
│   └── log4j2.xml            ← Алхам 1 & 1.1: тохиргоо
├── logs/
│   └── app.log               ← Автоматаар үүснэ
├── analysis.md               ← Алхам 4: командын шинжилгээ
└── README.md
```

---

## Ангиудын тайлбар

### BankAccount анги
`BankAccount.java` нь банкны данс дүрслэх энгийн анги бөгөөд Log4j 2-ийн **6 бүх түвшний логийг** хэрэгжүүлнэ:

| Метод | Log Level | Зорилго |
|-------|-----------|---------|
| `deposit(amount)` | TRACE | Методод орох/гарахыг бүртгэх |
| `deposit(amount)` | DEBUG | Deposit өмнөх үлдэгдэл |
| `deposit(amount)` | INFO | Амжилттай гүйлгээ |
| `deposit(amount < 0)` | WARN | Сөрөг оролт |
| `withdraw(amount > balance)` | ERROR | Үлдэгдэл хүрэлцэхгүй |
| `simulateFatalError()` | FATAL | Системийн критик нөхцөл |

Logger нь `private static final` хэлбэрээр зарлагдсан нь **encapsulation**-ийг баримтална.

### Customer анги
`Customer.java` нь хэрэглэгчийн имэйлийн домэйн хэсгийг буцаадаг анги юм. Анхны кодод `email` null үед `getDomain()` доторх `email.substring(...)` дуудагдвал **NullPointerException** үүсдэг байв.

**Debug процесс:**
1. Методын эхэнд `logger.trace("getDomain() дуудагдлаа — email={}", email)` нэмсэн
2. `email=null` гэж лог гарсанаас алдааг олсон
3. `email != null` шалгалт нэмж засав

**Засварын өмнөх лог:**
```
TRACE Customer:28 - getDomain() дуудагдлаа — email=null
ERROR - NullPointerException: Cannot invoke "String.indexOf(String)" because "this.email" is null
```

**Засварын дараах лог:**
```
TRACE Customer:28 - getDomain() дуудагдлаа — email=null
ERROR Customer:35 - getDomain() алдаа: email null байна — NullPointerException-аас урьдчилан сэргийлж байна
```

---

## log4j2.xml тохиргооны тайлбар

### Алхам 1: Үндсэн тохиргоо
- **Console Appender**: Огноо, thread ID (`%t`), түвшин, анги:мөр, мессеж бүхий PatternLayout
- **Root logger**: `level="debug"` — TRACE-аас бусад бүгдийг барина

### Алхам 1.1: ROLL_BY_SIZE тохиргоо
```xml
<RollingFile name="RollingFile" fileName="logs/app.log"
             filePattern="logs/app-%i.log.gz">
    <SizeBasedTriggeringPolicy size="1 MB"/>
    <DefaultRolloverStrategy max="5"/>
</RollingFile>
```
- `SizeBasedTriggeringPolicy size="1 MB"` — 1 MB хэмжээнд хүрэхэд файл солигдоно
- `filePattern="logs/app-%i.log.gz"` — `%i` нь 1, 2, 3... гэж нэмэгдэнэ
- `DefaultRolloverStrategy max="5"` — хамгийн ихдээ 5 архив хадгалагдана

---

## Алхам 2.1 — 6 log level баталгаажуулалт

`Main.java`-г ажиллуулахад `logs/app.log`-д дараах 6 түвшний лог гарна:

```
TRACE  BankAccount:28 - deposit() руу орлоо — accountId=ACC-001, amount=200.0
DEBUG  BankAccount:42 - Deposit өмнөх үлдэгдэл: balance=500.0, accountId=ACC-001
INFO   BankAccount:45 - Deposit амжилттай: amount=200.0, шинэ balance=700.0, accountId=ACC-001
WARN   BankAccount:35 - Буруу оролт: amount -50.0 сөрөг байна, accountId=ACC-001
ERROR  BankAccount:72 - Амжилтгүй: үлдэгдэл хүрэлцэхгүй! requestedAmount=99999.0, ...
FATAL  BankAccount:88 - КРИТИК АЛДАА: Банкны системийн тохиргоо (bank-config.xml) олдсонгүй!
```

---

## Алхам 4.1 — `tail -f` vs `cat`/`less`

`tail -f` нь файлд шинэ мөр нэмэгдэх бүрт **шууд дэлгэцэнд харуулдаг** тул програм ажиллаж байх үед логийн урсгалыг **бодит цаг хугацаанд** ажиглах боломжтой. Харин `cat` нь тухайн мөчид файлд байгаа агуулгыг нэг удаа хэвлэж зогсдог тул шинэ лог мөр нэмэгдсэнийг харж чадахгүй. Debug хийхэд програмын хариу үйлдлийг **шууд харах** шаардлагатай тул `tail -f` хамгийн тохиромжтой.

---

## Ажиллуулах заавар

```bash
# Нэгдүгээрт Maven compile & run
mvn compile
mvn exec:java -Dexec.mainClass="mn.must.lab11.Main"

# Эсвэл JAR болгоод ажиллуул
mvn package
java -jar target/lab11-logging-1.0-SNAPSHOT.jar
```

---

## Ашигласан эх сурвалж
- [Apache Log4j 2 Manual](https://logging.apache.org/log4j/2.x/manual/)
- Хичээлийн слайд: Log4j-ийн Тусламжтайгаар debug хийх
