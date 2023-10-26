package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.domain.Reply;
import com.wishme.myLetter.myLetter.dto.request.SaveMyLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.request.SaveReplyRequestDto;
import com.wishme.myLetter.myLetter.repository.MyLetterRepository;
import com.wishme.myLetter.myLetter.repository.ReplyRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final UserRepository userRepository;
    private final MyLetterRepository myLetterRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long saveReply(SaveReplyRequestDto saveReplyRequestDto) {
        MyLetter myLetter = myLetterRepository.findByMyLetterSeq(saveReplyRequestDto.getMyLetterSeq())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다. 해당 편지에 답장을 쓸 수 없습니다.", 1));

        User toUser = userRepository.findByUserSeq(myLetter.getFromUser())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다. 해당 유저에게 답장을 쓸 수 없습니다.", 1));

        Reply reply = Reply.builder()
                .myLetter(myLetter)
                .toUser(toUser)
                .content(saveReplyRequestDto.getContent())
                .fromUserNickname(saveReplyRequestDto.getFromUserNickname())
                .color(saveReplyRequestDto.getColor())
                .build();

        return replyRepository.save(reply).getReplySeq();
    }
}
