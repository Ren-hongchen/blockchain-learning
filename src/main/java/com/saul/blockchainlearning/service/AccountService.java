package com.saul.blockchainlearning.service;

import com.saul.blockchainlearning.algorithm.Base58;
import com.saul.blockchainlearning.algorithm.ECDSA;
import com.saul.blockchainlearning.algorithm.Hash160;
import com.saul.blockchainlearning.algorithm.Hash256;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountService {

    public String getPrivateKey(){
        byte[] value = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(value);
        return new String(Hex.encode(value));
    }

    public String getWIF(String private_key) throws Exception{
        return getWIFwithCompress(private_key); //use WIF with Compress in default
    }

    private String getWIFwithCompress(String private_key) throws Exception {
        String s = "80" + private_key + "01";
        String checksum = Hash256.hash256(Hex.decode(s)).substring(0,8);
        return Base58.encode(Hex.decode(s+ checksum));
    }

    private String getWIFwithNoCompress(String private_key) throws Exception {
        String s = "80" + private_key;
        String checksum = Hash256.hash256(Hex.decode(s)).substring(0,8);
        return Base58.encode(Hex.decode(s + checksum));
    }

    public String getPublicKey(String private_key) {
        return ECDSA.getPublicKey(private_key);
    }

    public String getAddress(String public_key) throws Exception {
        String hash160 = Hash160.hash160(Hex.decode(public_key));
        String hash160withPrefix = "00" + hash160;
        String checksum = Hash256.hash256(Hex.decode(hash160withPrefix)).substring(0,8);
        String finalData = hash160withPrefix + checksum;
        return Base58.encode(Hex.decode(finalData));
    }
}


