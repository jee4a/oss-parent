package com.jee4a.oss.framework.lang;

public class BytesUtils {

	/**
	 * 将short转化为2字节
	 * @param s short
	 * @return 2字节byte
	 */
	public static byte[] short2byte(short s) {
		byte[] shortBuf = new byte[2];
		shortBuf[1] = (byte) (s & 0xff);
		shortBuf[0] = (byte) ((s >>> 8) & 0xff);
		return shortBuf;
	}

	/**
	 * 将2字节byte转化为short
	 * @param b 4字节byte
	 * @return short
	 */
	public static short byte2short(byte[] b) {
		return (short) (((b[0] << 8) & 0xff00) | (b[1] & 0xff));
	}
	
	/**
	 * 将int转化为4字节byte
	 * @param res int
	 * @return 4字节byte
	 */
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[3] = (byte) (res & 0xff);
		targets[2] = (byte) ((res >> 8) & 0xff);
		targets[1] = (byte) ((res >> 16) & 0xff);
		targets[0] = (byte) ((res >> 24) & 0xff);
		return targets;
	}

	/**
	 * 将4字节byte转化为int
	 * @param res 4字节byte
	 * @return int
	 */
	public static int byte2int(byte[] res) {
		int targets = (res[3] & 0xff) | ((res[2] << 8) & 0xff00)
				| ((res[1] << 16) & 0xff0000) | ((res[0] << 24) & 0xff000000);
		return targets;
	}
	
	/** Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
	* @param src byte[] data  
	* @return hex string  
	*/       
	public static String bytesToHexString(byte[] src){  
		StringBuilder stringBuilder = new StringBuilder("");  
		if (src == null || src.length <= 0) {  
			return null;  
		}  
		for (int i = 0; i < src.length; i++) {  
			int v = src[i] & 0xFF;  
			String hv = Integer.toHexString(v);  
			if (hv.length() < 2) {  
				stringBuilder.append(0);  
			}  
			stringBuilder.append(hv);  
		}  
		return stringBuilder.toString();  
	} 
	
	/** 
	* Convert hex string to byte[] 
	* @param hexString the hex string 
	* @return byte[] 
	*/  
	public static byte[] hexStringToBytes(String hexString) {  
		if (hexString == null || hexString.equals("")) {  
			return null;  
		}  
		hexString = hexString.toUpperCase();  
		int length = hexString.length() / 2;  
		char[] hexChars = hexString.toCharArray();  
		byte[] d = new byte[length];  
		for (int i = 0; i < length; i++) {  
			int pos = i * 2;  
			d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
		}  
		return d;  
	}  

	private static byte charToByte(char c) {  
		return (byte) "0123456789ABCDEF".indexOf(c);  
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		byte[] a= int2byte(66666);
		System.out.println("a=" + new String(a));
	}
}
