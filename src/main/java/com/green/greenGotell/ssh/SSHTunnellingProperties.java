package com.green.greenGotell.ssh;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@Profile("ssh")
@Component
@ConfigurationProperties("spring.ssh.tunnel")
public class SSHTunnellingProperties {
	
	private String username;
	private String sshHost;
	private int	sshPort;
	private String privateKey;
	//private int localPort;//config에서 random적용
	private String localPort;
	private String rdsHost;
	private int rdsPort;

}
