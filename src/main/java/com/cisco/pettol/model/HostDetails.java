package com.cisco.pettol.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class HostDetails {

	private Double qId;
	private Double port;
	private String hostName;

	public Double getqId() {
		return qId;
	}

	public void setqId(Double qId) {
		this.qId = qId;
	}

	public Double getPort() {
		return port;
	}

	public void setPort(Double port) {
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Override
	public String toString() {
		return "HostDetails [qId=" + qId + ", port=" + port + ", hostName=" + hostName + "]";
	}

}
