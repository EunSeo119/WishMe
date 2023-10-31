package com.wishme.schoolLetter;

import com.wishme.schoolLetter.config.RSAUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@SpringBootApplication
public class SchoolLetterApplication {

	public static void main(String[] args) throws Exception{

		//암호키를 만드는 로직
//		RSAUtil rsaUtil = new RSAUtil();
//		KeyPair keyPair = rsaUtil.genRSAKeyPair();
//		PublicKey publicKey = keyPair.getPublic();
//		PrivateKey privateKey = keyPair.getPrivate();
//
//		byte[] bytePublicKey = publicKey.getEncoded();
//		String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
//		System.out.println("Base64 Public Key : " + base64PublicKey);
//
//		byte[] bytePrivateKey = privateKey.getEncoded();
//		String base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);
//		System.out.println("Base64 Private Key : " + base64PrivateKey);
//
		SpringApplication.run(SchoolLetterApplication.class, args);
	}

}
