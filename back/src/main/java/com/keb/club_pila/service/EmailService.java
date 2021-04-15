package com.keb.club_pila.service;

import com.keb.club_pila.model.entity.user.Member;
import com.keb.club_pila.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;


    public void sendMail(String toEmail, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setFrom("PILAS");
        helper.setTo(toEmail);
        helper.setSubject(subject); //메일 제ㅁ목
        helper.setText(message, true); //true->html

        javaMailSender.send(mimeMessage);

    }
    @Transactional
    public Long updateCertified(String username, String certified) {
        return userRepository.findByUsername(username).filter(p->p.getCertified().equals(certified))
                .map(user->user.updateCertified("Y")).orElse(0L);
    }
//    @Transactional
//    public int userCertified(String username, String certified){
//        int resultCnt=0;
//
//
//    }
}
