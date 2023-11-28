package com.wishme.user.user.service;

import com.wishme.user.user.domain.User;
import com.wishme.user.user.dto.request.MailContentDto;
import com.wishme.user.user.repository.UserRepository;
import com.wishme.user.util.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${key.AES256_Key}")
    String key;

    private final UserRepository userRepository;

    public MailContentDto createDownloadLetterAlarm(String userEmail) throws Exception {

        AES256 aes256 = new AES256(key);
        String decryptEmail = aes256.decrypt(userEmail);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        String message = "<html><body>";
        message += "<h3 style='color: #333;'>저희 Wish Me는 11월 30일 서비스를 종료하게 되었습니다.</h3>";
        message += "<p style='color: #666;'>받으신 편지를 보관하고 싶으신 분들은 <a href='https://wishme.co.kr/'>Wish Me</a>에서 제공하는 편지 다운로드 기능을 이용하여 다운받아 주세요!</p>";
        message += "<p style='color: #888;'><a href='http://localhost:8081/api/users/noEmail/"+user.getUserSeq()+"'>다시 보지 않기</a></p>";
        message += "</body></html>";
        MailContentDto mailContentDto = MailContentDto.builder()
                .address(decryptEmail)
                .title("Wish Me 공지 사항 전해드립니다.")
                .message(message).build();

        return mailContentDto;
    }

    public MailContentDto createReplyAlarm(String userEmail) throws Exception {

        AES256 aes256 = new AES256(key);
        String decryptEmail = aes256.decrypt(userEmail);

        String message = "<html><body>";
        message += "<h3 style='color: #333;'>회원님께서는 아직 읽지 않은 답장이 있습니다!</h3>";
        message += "<p style='color: #666;'>저희 Wish Me는 11월 30일 서비스를 종료하게 되었습니다. 해당일 전까지 어서 답장을 확인해 주세요!</p>";
        message += "<p style='color: #888;'><a href='https://wishme.co.kr/'>Wish Me 바로가기</a></p>";
        message += "</body></html>";
        MailContentDto mailContentDto = MailContentDto.builder()
                .address(decryptEmail)
                .title("Wish Me에 읽지 않은 답장이 있습니다.")
                .message(message).build();

        return mailContentDto;
    }

    public void sendMail(MailContentDto mailContentDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mailContentDto.getAddress());
            message.setSubject(mailContentDto.getTitle());
            message.setText(mailContentDto.getMessage(),"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }
}
