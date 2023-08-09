//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource({ "classpath:mail_labels.properties" })
@EnableTransactionManagement
@Component
public class Mailer {
	@Autowired
	private Environment environment;
	private JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return this.mailSender;
	}

	public void sendMail(final String[] to, final String subject, final String text) {
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		this.mailSender.send(message);
	}

	public void sendMail(final String[] to, final String subject, final String text, final String[] bcc) {
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setBcc(bcc);
		this.mailSender.send(message);
	}

	public void sendMailWithAttachment(final String[] to, final String subject, final String text, final File file) {
		try {
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			message.addAttachment(file.getName(), file);
			this.mailSender.send(mimeMessage);
		} catch (Exception ex) {
		}
	}

	public void sendMailWithNewPropertyFile(final String to, final String subject, final String text) {
		try {
			final String fromEmail = this.environment.getRequiredProperty("mail.from");
			final String password = this.environment.getRequiredProperty("mail.password");
			final Properties props = new Properties();
			(props).put("mail.smtp.host", this.environment.getRequiredProperty("mail.smtp.host"));
			(props).put("mail.smtp.socketFactory.port",
					this.environment.getRequiredProperty("mail.smtp.socketFactory.port"));
			(props).put("mail.smtp.socketFactory.class",
					this.environment.getRequiredProperty("mail.smtp.socketFactory.class"));
			(props).put("mail.smtp.auth", this.environment.getRequiredProperty("mail.smtp.auth"));
			(props).put("mail.smtp.port", this.environment.getRequiredProperty("mail.smtp.port"));
			// final Authenticator auth = (Authenticator)new Mailer.Mailer$1(this,
			// fromEmail, password);
			final Authenticator auth = new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			final Session session = Session.getInstance(props, auth);
			final Transport transport = session.getTransport("smtps");
			final String FROM = this.environment.getRequiredProperty("mail.fromaddress");
			final String FROMNAME = "";
			final MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			msg.setSubject(subject, "UTF-8");
			msg.setText(text, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			Transport.send(msg);
			if (transport.isConnected()) {
				transport.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMailSender(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
