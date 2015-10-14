package com.irouter.network.entity;

public class WifiSpeedInfo {
	// Network speed
	public long speed = 0;
	// Had finished bytes
	public long hadFinishedBytes = 0;
	// Total bytes of a file, default is 1024 bytes,1K
	public long totalBytes = 10240;
	// Down load the file percent 0----100
	public int downloadPercent = 0;

	public WifiSpeedInfo(long speed, long hadFinishedBytes, long totalBytes,
			int downloadPercent) {
		super();
		this.speed = speed;
		this.hadFinishedBytes = hadFinishedBytes;
		this.totalBytes = totalBytes;
		this.downloadPercent = downloadPercent;
	}

	@Override
	public String toString() {
		return "WifiSpeedInfo [speed=" + this.speed + ", hadFinishedBytes="
				+ this.hadFinishedBytes + ", totalBytes=" + this.totalBytes
				+ ", downloadPercent=" + this.downloadPercent + "]";
	}

	public long getSpeed() {
		return this.speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public long getHadFinishedBytes() {
		return this.hadFinishedBytes;
	}

	public void setHadFinishedBytes(long hadFinishedBytes) {
		this.hadFinishedBytes = hadFinishedBytes;
	}

	public long getTotalBytes() {
		return this.totalBytes;
	}

	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public int getDownloadPercent() {
		return this.downloadPercent;
	}

	public void setDownloadPercent(int downloadPercent) {
		this.downloadPercent = downloadPercent;
	}

}
