package test;

import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class XorUtils {

	public XorUtils() {
		// TODO Auto-generated constructor stub
		try {
			LdapContext ctx = new InitialLdapContext(null, null);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args)
//	{
//		String data = "639870573";
//		String secretKey = "beson";
//		String encryptData = encrypt("645849154");
//		System.out.println(encryptData);
////		System.out.println(decrypt("ejQDU1BVAA0KCgs="));
////		System.out.println("https://datsin.deppon.com/#/?s="+encryptData);
//	}

	/**
	 * 位移计算
	 * @param data 数据
	 * @param key 密钥
	 * @return byte array
	 */
	private static final String keyvalue="8f3bab281381d396a84d2f0a6821d5a6";
	public static byte[] compute(byte[] data)
	{
		byte[] key=keyvalue.getBytes();
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return data;
        }
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            //data ^ key ^ (i & 0xFF)低8位（增加复杂度）
            result[i] = (byte) (data[i] ^ key[i % key.length] ^ (i & 0xFF));
        }
        return result;
    }
	/**
	 * encrypt
	 * @param data
	 * @return encryptData
	 */
/*	public static String encrypt(String data){
		return Base64Utils.encodeToString(compute(data.getBytes()));
	}
	*//**
	 * decrypt
	 * @param data
	 * @return decryptData
	 *//*
	public static String decrypt(String data){
		return new String(compute(Base64Utils.decodeFromString(data)));
	}*/
}
