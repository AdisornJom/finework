package com.finework.core.utils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AES256Cipher
{
  private static final String password = "adf5fc7453ad0b7a6f75bc59aee65e8cc2317d0029b1d9bf01f61e537c4d9447";
  private static final String initialVector = "AAAAAAAAAAAAAAAA";
  private static final String salt = "GD";
  private static final int iteration = 16;
  private static final int keySize = 128;

  public static void main(String[] args)
  {
    try
    {
      String txt1 = encrypt("1234567890123456");
      String txt2 = decrypt(txt1);
      System.out.println(txt1);
    } catch (Exception ex) {
      Logger.getLogger(AES256Cipher.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static String encrypt(String plainText)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException
  {
    byte[] saltBytes = "GD".getBytes("UTF-8");
    byte[] ivBytes = "AAAAAAAAAAAAAAAA".getBytes("UTF-8");

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    PBEKeySpec spec = new PBEKeySpec("adf5fc7453ad0b7a6f75bc59aee65e8cc2317d0029b1d9bf01f61e537c4d9447"
      .toCharArray(), saltBytes, 16, 128);

    SecretKey secretKey = factory.generateSecret(spec);
    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(1, secret, new IvParameterSpec(ivBytes));

    byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
    return new Base64().encodeAsString(encryptedTextBytes);
  }

  public static String decrypt(String encryptedText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    byte[] saltBytes = "GD".getBytes("UTF-8");
    byte[] ivBytes = "AAAAAAAAAAAAAAAA".getBytes("UTF-8");
    byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText);

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    PBEKeySpec spec = new PBEKeySpec("adf5fc7453ad0b7a6f75bc59aee65e8cc2317d0029b1d9bf01f61e537c4d9447"
      .toCharArray(), saltBytes, 16, 128);

    SecretKey secretKey = factory.generateSecret(spec);
    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(2, secret, new IvParameterSpec(ivBytes));

    byte[] decryptedTextBytes = null;
    decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
    return new String(decryptedTextBytes);
  }
}