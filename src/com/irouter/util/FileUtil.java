package com.irouter.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.irouter.network.entity.WifiSpeedInfo;

public class FileUtil {

	/**
	 * Read file from web
	 * 
	 * @param urlString
	 *            specific web url
	 * @param wifiSpeedInfo
	 *            Wifi Speed Info
	 * @return
	 */
	public static byte[] getFileFromUrl(String urlString,
			WifiSpeedInfo wifiSpeedInfo) {
		int currentByte = 0;
		int fileLength = 0;
		long startTime = 0;
		long intervalTime = 0;

		byte[] b = null;

		int bytecount = 0;
		URL url = null;
		URLConnection con = null;
		InputStream stream = null;
		try {
			LogUtil.d("URL:" + urlString);
			url = new URL(urlString);
			con = url.openConnection();
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			fileLength = con.getContentLength();
			stream = con.getInputStream();
			wifiSpeedInfo.totalBytes = fileLength;
			b = new byte[fileLength];
			startTime = System.currentTimeMillis();
			while ((currentByte = stream.read()) != -1) {
				wifiSpeedInfo.hadFinishedBytes++;
				intervalTime = System.currentTimeMillis() - startTime;
				if (intervalTime == 0) {
					wifiSpeedInfo.speed = 1000;
				} else {
					wifiSpeedInfo.speed = (wifiSpeedInfo.hadFinishedBytes / intervalTime) * 1000;
				}
				if (bytecount < fileLength) {
					b[bytecount++] = (byte) currentByte;
				}
			}
		} catch (Exception e) {
			LogUtil.e("exception : " + e.getMessage() + "");
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
				LogUtil.e("exception : " + e.getMessage());
			}
		}
		return b;
	}

}