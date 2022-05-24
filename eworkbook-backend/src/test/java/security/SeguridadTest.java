/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Util.WeEncoder;

/**
 *
 * @author geova
 */
public class SeguridadTest {

    WeEncoder weEncoder;

    public SeguridadTest() {
        weEncoder = new WeEncoder();
    }

    @Test
    public void givenPassword_whenHashingUsingCommons_thenVerifying() {
//        String hash = "C4CA4238A0B923820DCC509A6F75849B";
//        String password = "1";
//        String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
//        System.out.println(md5Hex);
//        assertEquals(md5Hex, hash);
    }

    @Test
    public void encriptar() throws Exception {
//        weEncoder = new WeEncoder();
//        String data = "";
//        String result = "";
//        data = weEncoder.textEncryptor(data);
//        System.out.println("RESULTADO");
//        System.out.println(data);
//        assertEquals(data, result);
    }

    @Test
    public void desencriptar() {
//        weEncoder = new WeEncoder();
//        String data = "";
//        String result = "";
//        data = weEncoder.textDecryptor(data);
//        System.out.println(data);
//        assertEquals(data, result);
    }

    @Test
    public void getAbdRandom() {
//        weEncoder = new WeEncoder();
//        String data = "";
//        String result = "";
//        data = weEncoder.getUrlGenericAmount(6);
//        System.out.println(data);
//        assertEquals(data, result);
    }
}
