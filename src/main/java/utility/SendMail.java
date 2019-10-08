package utility;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import executionEngine.DriverScript;



public class SendMail extends DriverScript{



	public SendMail() throws NoSuchMethodException, SecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Date d = new Date();
	public static Timestamp t = new Timestamp(d.getTime());
	public static String dateWithoutTime = d.toString().substring(0, 10);
	public static final String dir = System.getProperty("user.dir");
	
	private static void addAttachment(Multipart multipart, String filename) throws MessagingException
	{
	    DataSource source = new FileDataSource(filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(filename);
	    multipart.addBodyPart(messageBodyPart);
	}

	public static void SendEmail(String subject, String Msg) {
		
		final String username = "peaastest@gmail.com";
		final String password = "Peaas@123";

		dateWithoutTime = dateWithoutTime.replace(' ','_');
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			//Transport transport = session.getTransport();
			Message message = new MimeMessage(session);
			
			 //MimeBodyPart messageBodyPart = new MimeBodyPart();
			 BodyPart messageBodyPart = new MimeBodyPart();
		     BodyPart attachmentPart1 = new MimeBodyPart();
		     Multipart multipart = new MimeMultipart();
			
			message.setFrom(new InternetAddress("peaastest@gmail.com"));
			message.setRecipients(RecipientType.TO,InternetAddress.parse("dhaval.vankani.152@gmail.com"));
			message.addRecipients(RecipientType.CC, InternetAddress.parse("bhoomi.padaliya@peaas.co"));
			message.setSubject(subject);
		
			String filename1 = "DentoClikAutomationReport_" + dateWithTime + ".html";
			
			DataSource source1 = new FileDataSource(EmailReportfilePath);
	        //DataSource source = new FileDataSource(getTheNewestFile(file, ".html"));
	        attachmentPart1.setDataHandler(new DataHandler(source1));
			      
	        messageBodyPart.setText("Hello,"+"\n\n"+Msg+ "\n\nThanks,"+"\n\n QA Team");
	        attachmentPart1.setFileName(filename1);
	        multipart.addBodyPart(messageBodyPart);
	        multipart.addBodyPart(attachmentPart1);
	        // session.setDebug(true);
	        message.setContent(multipart);

	        Transport.send(message);
	        
			System.out.println("Email Sent Successfully...!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
