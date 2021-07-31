package com.exam.myntexam.config;

import com.exam.myntexam.storage.data.VoucherSettings;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lorenzomalafo
 */
public final class VoucherConfig {
    private VoucherSettings settings;
    
    private static final VoucherConfig VOUCHER_CONFIG;
    
    static {
        try (InputStream inputStream = VoucherConfig.class.getClassLoader().getResourceAsStream("voucher.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            
            VoucherSettings vs = new VoucherSettings();
            vs.setUrl(prop.getProperty("voucher.validate.url", "https://mynt-exam.mocklab.io/voucher/"));
            vs.setKey(prop.getProperty("voucher.validate.key", "apikey"));
            
            VOUCHER_CONFIG = new VoucherConfig(vs);
        } catch (IOException ioe) {
            Logger.getLogger(VoucherConfig.class.getName())
                    .log(Level.SEVERE, "Failed to Read voucher.properties", ioe);

            throw new ExceptionInInitializerError("Failed to Read voucher.properties");
        }
    }
    
    private VoucherConfig(VoucherSettings settings) {
        this.settings = settings;
    }
    
    public static VoucherConfig getInstance() {
        return VOUCHER_CONFIG;
    }
    
    public String constructVoucherUrl(final String voucherCode) {
        return settings.getUrl() + voucherCode + "?key=" + settings.getKey();
    }

    public String getVoucherUrl() {
        return settings.getUrl();
    }

    public String getVoucherKey() {
        return settings.getKey();
    }
    
    /**
     * Updates the current Voucher Settings. (NOTE: This is thread safe operation.)
     * 
     * @param apiurl
     * @param apikey 
     */
    public void updateVoucherSettings(final String apiurl, final String apikey) {
        /*
            Need to make sure this is thread safe, just incase multiple
            requests attempts to update the settings.
        */
        synchronized(this) {
            settings.setUrl(apiurl);
            settings.setKey(apikey);
        }
    }
}
