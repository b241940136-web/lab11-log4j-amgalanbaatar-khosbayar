package mn.must.lab11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Customer анги — Алхам 3: NullPointerException алдааг логоор debug хийж засах
 *
 * АЛДААНЫ ТАЙЛБАР:
 *   Анхны кодод email null үед getDomain() дотор email.substring(...) дуудагдвал
 *   NullPointerException үүсдэг байв.
 *
 * DEBUG ПРОЦЕСС:
 *   1. Методын эхэнд TRACE лог нэмж, email-ийн утгыг харав.
 *   2. logger.debug("getDomain called with email={}", email) гаргахад email=null харагдав.
 *   3. indexOf("@") дуудагдахаас өмнө null шалгалт нэмж засав.
 *
 * ЗАСВАР:
 *   email != null && email.contains("@") шалгалтыг нэмсэн.
 */
public class Customer {

    private static final Logger logger = LogManager.getLogger(Customer.class);

    private String name;
    private String email;

    public Customer(String name, String email) {
        logger.trace("Customer конструктор дуудагдлаа — name={}, email={}", name, email);

        // DEBUG: оролтын утгуудыг баталгаажуулах
        if (name == null || name.isBlank()) {
            logger.warn("Customer үүсгэхэд name хоосон эсвэл null байна");
        }
        if (email == null) {
            logger.warn("Customer үүсгэхэд email null байна — getDomain() ажиллахгүй");
        }

        this.name = name;
        this.email = email;

        logger.debug("Customer үүсгэгдлээ: name={}, email={}", name, email);
    }

    /**
     * Имэйлийн домэйн хэсгийг буцаах
     * Засварласан хувилбар: null болон '@' агуулаагүй имэйлийг шалгана
     */
    public String getDomain() {
        logger.trace("getDomain() дуудагдлаа — email={}", email);

        // ЗАСВАР: email null эсэхийг шалгах (анхны кодод энэ шалгалт байгаагүй)
        if (email == null) {
            logger.error("getDomain() алдаа: email null байна — NullPointerException-аас урьдчилан сэргийлж байна");
            return "UNKNOWN";
        }

        // ЗАСВАР: '@' тэмдэгт байгаа эсэхийг шалгах
        if (!email.contains("@")) {
            logger.warn("getDomain() анхааруулга: email '{}' нь '@' агуулаагүй байна", email);
            return "INVALID_EMAIL";
        }

        logger.debug("getDomain() завсрын үр дүн: email={}, @ индекс={}", email, email.indexOf("@"));

        String domain = email.substring(email.indexOf("@") + 1).toUpperCase();

        logger.debug("getDomain() үр дүн: domain={}", domain);
        logger.trace("getDomain() гарлаа");

        return domain;
    }

    /**
     * Нууц мэдээллийг маскалах туслах метод — Алхам 5
     * @param s маскалах утга
     * @return маскалагдсан утга
     */
    public static String mask(String s) {
        if (s == null || s.length() < 4) return "***";
        return s.substring(0, 2) + "***" + s.substring(s.length() - 2);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        // Нууц мэдээлэл — имэйлийг шууд бүү лог, маскал
        logger.debug("getEmail() дуудагдлаа — email (masked)={}", mask(email));
        return email;
    }
}
