package mn.must.lab11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BankAccount анги — Log4j 2-ийн 6 түвшний лог жишээ
 * Алхам 2: TRACE, DEBUG, INFO, WARN, ERROR, FATAL-ийг хэрэгжүүлсэн
 */
public class BankAccount {

    // Encapsulation: logger нь private static final
    private static final Logger logger = LogManager.getLogger(BankAccount.class);

    private double balance;
    private String accountId;

    public BankAccount(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
        logger.info("BankAccount үүсгэгдлээ: accountId={}, initialBalance={}", accountId, initialBalance);
    }

    /**
     * Дансанд мөнгө хийх
     * @param amount хийх мөнгөний хэмжээ
     */
    public void deposit(double amount) {
        logger.trace("deposit() руу орлоо — accountId={}, amount={}", accountId, amount);

        // WARN: сөрөг оролт
        if (amount < 0) {
            logger.warn("Буруу оролт: amount {} сөрөг байна, accountId={}", amount, accountId);
            logger.trace("deposit() гарлаа (warn шалтгаанаар)");
            return;
        }

        // WARN: тэг оролт
        if (amount == 0) {
            logger.warn("Анхааруулга: тэг мөнгө хийж байна, accountId={}", accountId);
        }

        logger.debug("Deposit өмнөх үлдэгдэл: balance={}, accountId={}", balance, accountId);
        balance += amount;
        logger.info("Deposit амжилттай: amount={}, шинэ balance={}, accountId={}", amount, balance, accountId);

        logger.trace("deposit() гарлаа — эцсийн balance={}", balance);
    }

    /**
     * Дансаас мөнгө авах
     * @param amount авах мөнгөний хэмжээ
     */
    public void withdraw(double amount) {
        logger.trace("withdraw() руу орлоо — accountId={}, amount={}", accountId, amount);

        // WARN: сөрөг оролт
        if (amount < 0) {
            logger.warn("Буруу оролт: amount {} сөрөг байна, accountId={}", amount, accountId);
            logger.trace("withdraw() гарлаа (warn шалтгаанаар)");
            return;
        }

        logger.debug("Withdraw өмнөх үлдэгдэл: balance={}, requestedAmount={}, accountId={}", balance, amount, accountId);

        // ERROR: үлдэгдэлээс их мөнгө авах гэж оролдоход
        if (amount > balance) {
            logger.error("Амжилтгүй: үлдэгдэл хүрэлцэхгүй! " +
                    "requestedAmount={}, currentBalance={}, accountId={}", amount, balance, accountId);
            logger.trace("withdraw() гарлаа (error шалтгаанаар)");
            return;
        }

        balance -= amount;
        logger.info("Withdraw амжилттай: amount={}, шинэ balance={}, accountId={}", amount, balance, accountId);

        logger.trace("withdraw() гарлаа — эцсийн balance={}", balance);
    }

    /**
     * Одоогийн үлдэгдлийг буцаах
     * @return balance
     */
    public double getBalance() {
        logger.trace("getBalance() дуудагдлаа — accountId={}", accountId);
        logger.debug("Одоогийн balance={}, accountId={}", balance, accountId);
        return balance;
    }

    /**
     * Системийн критик нөхцөл — FATAL жишээ
     * (Жишээ нь: config файл олдохгүй, тохиргоо алдаатай)
     */
    public static void simulateFatalError() {
        logger.fatal("КРИТИК АЛДАА: Банкны системийн тохиргоо (bank-config.xml) олдсонгүй! " +
                "Систем ажиллахаа зогсооно. Системийн администраторт хандана уу.");
    }
}
