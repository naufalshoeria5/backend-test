package com.bni.test.backendfitness.helpers;

import com.bni.test.backendfitness.utils.AesEncrytionUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalHelper {

    public static String toEncrypt(String encrypt) {
        try {
            return AesEncrytionUtil.encrypt(encrypt);
        }catch (Exception e){
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR,"Error Encrypt");
        }
    }

    public static String toDecrypt(String encrypt) {
        try {
            return AesEncrytionUtil.decrypt(encrypt);
        }catch (Exception e){
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR,"Error Encrypt");
        }
    }

    public static LocalDateTime localDateTimeAdd(Integer minute, Integer hour){
        LocalDateTime localDateTime = LocalDateTime.now();

        if (minute != null)
            localDateTime = localDateTime.plusMinutes(minute);

        if (hour != null)
            localDateTime = localDateTime.plusHours(hour);

        return localDateTime;
    }
}
