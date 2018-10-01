package com.my.jwt;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;


/**
 * Hello world!
 * gen key ref https://github.com/jwtk/jjwt/issues/131
 */
public class App
{
	public static void main(String[] args)
	{
		try
		{
			String privateKeyPath = "/Users/tony/Work/git_repo/java-jwt-test/private_key.der";
			PrivateKey privateKey = getPrivateKey(privateKeyPath);

			String publicKeyPath = "/Users/tony/Work/git_repo/java-jwt-test/public_key.der";
			PublicKey publicKeyKey = getPublicKey(publicKeyPath);

			String subject = "test";
			Date issuedAt = new Date();
			System.out.println(issuedAt.getTime() / 1000); // get unix time stamp

			JwtBuilder jwtBuilder = Jwts.builder();
			String jws = jwtBuilder
					// although jjwt will auto check secret type, to keep same with https://jwt.io/ Header sequence, override here
					.setHeaderParam("alg", "RS256")
					.setHeaderParam("typ", "JWT")
					.setSubject(subject)
					.setIssuedAt(issuedAt)
					.signWith(privateKey)
					.compact();

			System.out.println("jwt:");
			// copy the token and go https://jwt.io/ to cross check
			System.out.println(jws);

			Jws<Claims> token = Jwts.parser().setSigningKey(publicKeyKey).parseClaimsJws(jws);

			System.out.println("token:");
			System.out.println(token);

			if (token.getBody().getSubject().equals(subject))
			{
				System.out.println("We can trust this jwt");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static PrivateKey getPrivateKey(String filename)
			throws Exception {

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

		PKCS8EncodedKeySpec spec =
				new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public static PublicKey getPublicKey(String filename)
			throws Exception {

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

		X509EncodedKeySpec spec =
				new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
}
