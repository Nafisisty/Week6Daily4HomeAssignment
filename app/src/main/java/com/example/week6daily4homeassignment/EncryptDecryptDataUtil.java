package com.example.week6daily4homeassignment;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptDecryptDataUtil {

    private CipherWrapper cipherWrapper;
    private KeyStoreWrapper keyStoreWrapper;
    private String alias = "Master_Key";
    private KeyPair masterKey;
    Context context;

    public EncryptDecryptDataUtil(Context context) {
        this.context = context;
        try {
            initWrapper();
            keyStoreWrapper.createKeyPair(alias);
            masterKey = keyStoreWrapper.getAsymkey(alias);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException
                | UnrecoverableKeyException
                | NoSuchProviderException
                | KeyStoreException
                | CertificateException | IOException e){

        }

    }

    public String dataEncrption(String plainText) {

        String encryptedData="";

        try {

            encryptedData = cipherWrapper.encrypt(plainText, masterKey.getPublic());
            Log.d("TAG", "onCreate: " + encryptedData);


        } catch (BadPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException e) {

            e.printStackTrace();

        }

        return encryptedData;
    }

    public String dataDecrytion(String encryptData) {

        String decrypedData = "";

        try {

            decrypedData = cipherWrapper.decrypt(encryptData, masterKey.getPrivate());
            Log.d("TAG", "onCreate: " + decrypedData);

        } catch (BadPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException e) {

            e.printStackTrace();

        }

        return decrypedData;
    }


    private void initWrapper() throws NoSuchAlgorithmException, NoSuchPaddingException,
            CertificateException, KeyStoreException, IOException {

        cipherWrapper = new CipherWrapper("RSA/ECB/PKCS1Padding");
        keyStoreWrapper = new KeyStoreWrapper(context);

    }

}
