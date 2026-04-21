package mn.must.lab11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main анги — Алхам 2.1: Бүх сценарийг туршна
 * - Хэвийн deposit/withdraw
 * - Сөрөг оролт (WARN)
 * - Хэт их авах (ERROR)
 * - FATAL симуляц
 * - Customer NullPointerException debug
 * - Шилдэг туршлага: масклах (Алхам 5)
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("===== Лаб 11 — Log4j Debug Туршилт эхэлж байна =====");

        // =============================================
        // АЛХАМ 2.1: BankAccount туршилт
        // =============================================
        logger.info("----- BankAccount туршилт эхэлж байна -----");

        BankAccount account = new BankAccount("ACC-001", 500.0);

        // 1. Хэвийн deposit
        logger.info("--- Туршилт 1: Хэвийн deposit ---");
        account.deposit(200.0);

        // 2. Хэвийн withdraw
        logger.info("--- Туршилт 2: Хэвийн withdraw ---");
        account.withdraw(100.0);

        // 3. Сөрөг оролт — WARN гарах
        logger.info("--- Туршилт 3: Сөрөг deposit (WARN) ---");
        account.deposit(-50.0);

        // 4. Хэт их withdraw — ERROR гарах
        logger.info("--- Туршилт 4: Хэт их withdraw (ERROR) ---");
        account.withdraw(99999.0);

        // 5. Сөрөг withdraw — WARN гарах
        logger.info("--- Туршилт 5: Сөрөг withdraw (WARN) ---");
        account.withdraw(-10.0);

        // Эцсийн үлдэгдэл
        logger.info("Эцсийн үлдэгдэл: {}", account.getBalance());

        // FATAL симуляц
        logger.info("--- FATAL симуляц ---");
        BankAccount.simulateFatalError();

        // =============================================
        // АЛХАМ 3: Customer NullPointerException debug
        // =============================================
        logger.info("----- Customer debug туршилт эхэлж байна -----");

        // Хэвийн хэрэглэгч
        logger.info("--- Туршилт 6: Хэвийн Customer ---");
        Customer customer1 = new Customer("Батболд", "batbold@must.edu.mn");
        logger.info("Customer1 домэйн: {}", customer1.getDomain());

        // Email-гүй хэрэглэгч — NullPointerException гарах байсан (засагдсан)
        logger.info("--- Туршилт 7: email=null Customer (NPE засагдсан) ---");
        Customer customer2 = new Customer("Эрхэс", null);
        logger.info("Customer2 домэйн: {}", customer2.getDomain());

        // '@' тэмдэгтгүй буруу имэйл
        logger.info("--- Туршилт 8: '@'-гүй буруу имэйл ---");
        Customer customer3 = new Customer("Сарнай", "bademail.com");
        logger.info("Customer3 домэйн: {}", customer3.getDomain());

        // =============================================
        // АЛХАМ 5: Шилдэг туршлага — маскалах
        // =============================================
        logger.info("----- Алхам 5: Нууц мэдээлэл маскалах ---");

        String userId = "U123456789";
        String cardNumber = "4111111111111234";

        // Шууд бүү лог — маскал
        logger.info("Хэрэглэгч нэвтэрлээ: userId={}", Customer.mask(userId));
        logger.info("Картын дугаар (masked)={}", Customer.mask(cardNumber));

        // Параметрт лог — string concat хэрэглэхгүй (зөв арга)
        String orderId = "ORD-2026-001";
        double orderAmount = 75000.0;
        logger.info("Захиалга баталгаажлаа: id={}, amount={}", orderId, orderAmount);

        logger.info("===== Лаб 11 туршилт дууслаа =====");
    }
}
