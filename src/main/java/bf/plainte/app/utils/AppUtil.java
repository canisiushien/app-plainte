/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
public class AppUtil {

    private static InputStream[] imagesStream = new InputStream[2];

    public static String TYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String FR_SHORT_DATE_FORMAT = "dd/MM/yyyy";

    private static final DateFormat appFullMonthDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
    private static final DateFormat appShortDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);

    public static final String appStoreRootPath = "/opt/app-plainte";
    public static final String DEFAULT_PASSWORD = "Def@ult%P@ssw0rd!.2@24";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    // EXTENSIONS AUTORISEES
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_JPEG = ".jpeg";

    /**
     * CONVERTIR DATE EN FORMAT : 18 mai 2023
     *
     * @param date : date fournie
     * @return
     */
    public static String convertDatWithFullMonthToString(Date date) {
        return appFullMonthDateFormat.format(date);
    }

    /**
     * CONVERTIR DATE EN FORMAT : 18-05-2023
     *
     * @param date
     * @return
     */
    public static String convertDateToShort(Date date) {
        return appShortDateFormat.format(date);
    }

    public static int getCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }
}
