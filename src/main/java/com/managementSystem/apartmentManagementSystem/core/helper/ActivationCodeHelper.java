package com.managementSystem.apartmentManagementSystem.core.helper;
import org.apache.commons.lang3.RandomStringUtils;


public class ActivationCodeHelper {
    public static String generateActivationCode() {
        // Rastgele 6 karakterden oluşan bir aktivasyon kodu oluştur
        return RandomStringUtils.randomAlphanumeric(6);
    }
}
