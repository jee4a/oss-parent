package com.jee4a.oss.framework.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jee4a.oss.framework.lang.StringUtils;



/**
 * 
 * Class Name : IpUtils<br>
 * 
 * Description : <br>
 * 
 * @see
 *
 */
public class IPUtils {

	private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * 获取服务器ip
	 * 
	 * @param isNetIp
	 *            是否优先取外网ip
	 * @return
	 * @throws SocketException
	 */
	public static String getRealIp(boolean isNetIp) {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();

			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
			if (netip != null && !"".equals(netip) && isNetIp) {
				return netip;
			} else {
				return localip;
			}
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 获取服务器ip
	 * 
	 * @param isNetIp
	 *            是否优先取外网ip
	 * @return
	 * @throws SocketException
	 */
	public static Map<String, String> getMuiltIp() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						map.put(ip.getHostAddress(), ip.getHostAddress());
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						map.put(ip.getHostAddress(), ip.getHostAddress());
					}
				}
			}

		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * ipv4 是否内网IP
	 * 
	 * @param addr
	 * @return
	 */
	public static boolean internalIp(String ip) {
		if ("127.0.0.1".equals(ip)) {
			return true;
		}
		byte[] addr = textToNumericFormatV4(ip);
		if (addr == null || addr.length != 4)
			return false;
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		// 10.x.x.x/8
		final byte SECTION_1 = 0x0A;
		// 172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return true;
			}
		default:
			return false;
		}
	}
	
	/**
	 * ip转换为255进制
	 * @param ipAddress
	 * @return
	 */
	public static long getIpNum(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);

		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	public static byte[] textToNumericFormatV4(String src) {
		if (src.length() == 0) {
			return null;
		}
		byte[] res = new byte[4];
		String[] s = src.split("\\.", -1);
		long val;
		try {
			switch (s.length) {
			case 1:
				val = Long.parseLong(s[0]);
				if (val < 0 || val > 0xffffffffL)
					return null;
				res[0] = (byte) ((val >> 24) & 0xff);
				res[1] = (byte) (((val & 0xffffff) >> 16) & 0xff);
				res[2] = (byte) (((val & 0xffff) >> 8) & 0xff);
				res[3] = (byte) (val & 0xff);
				break;
			case 2:
				val = Integer.parseInt(s[0]);
				if (val < 0 || val > 0xff)
					return null;
				res[0] = (byte) (val & 0xff);
				val = Integer.parseInt(s[1]);
				if (val < 0 || val > 0xffffff)
					return null;
				res[1] = (byte) ((val >> 16) & 0xff);
				res[2] = (byte) (((val & 0xffff) >> 8) & 0xff);
				res[3] = (byte) (val & 0xff);
				break;
			case 3:
				for (int i = 0; i < 2; i++) {
					val = Integer.parseInt(s[i]);
					if (val < 0 || val > 0xff)
						return null;
					res[i] = (byte) (val & 0xff);
				}
				val = Integer.parseInt(s[2]);
				if (val < 0 || val > 0xffff)
					return null;
				res[2] = (byte) ((val >> 8) & 0xff);
				res[3] = (byte) (val & 0xff);
				break;
			case 4:
				for (int i = 0; i < 4; i++) {
					val = Integer.parseInt(s[i]);
					if (val < 0 || val > 0xff)
						return null;
					res[i] = (byte) (val & 0xff);
				}
				break;
			default:
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return res;
	}
	
	/**
     * 获取请求者ip
     * 
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String remoteIp = null;
        remoteIp = request.getHeader("x-forwarded-for");
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("X-Forwarded-For");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("X-Real-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteAddr();
        }
        if (remoteIp == null || remoteIp.isEmpty() || "unknown".equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteHost();
        }

        /*
         * 对于获取到多ip的情况下，找到公网ip.
         */
        String sIP = null;
        if (remoteIp != null && remoteIp.indexOf("unknown") == -1 && remoteIp.indexOf(",") > 0) {
            String[] ipsz = remoteIp.split(",");
            for (int i = 0; i < ipsz.length; i++) {
                if (!IPUtils.internalIp(ipsz[i].trim())) {
                    sIP = ipsz[i].trim();
                    break;
                }
            }
            /*
             * 如果多ip都是内网ip，则取第一个ip.
             */
            if (null == sIP) {
                sIP = ipsz[0].trim();
            }
            remoteIp = sIP;
        }
        if (remoteIp != null && remoteIp.indexOf("unknown") != -1) {
            remoteIp = remoteIp.replaceAll("unknown,", "").trim();
        }
        if (StringUtils.isBlank(remoteIp)) {
            remoteIp = "127.0.0.1";
        }
        return remoteIp;
    }

	public static void main(String[] args) {
		// System.out.println(LocalIPUtils.getMuiltIp().toString());
		System.out.println(internalIp("172.16.26.191"));
	}
}