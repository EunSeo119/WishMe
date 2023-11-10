package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.common.exception.CustomException;
import com.wishme.myLetter.common.exception.code.ErrorCode;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.domain.Reply;
import com.wishme.myLetter.myLetter.dto.request.SaveReplyRequestDto;
import com.wishme.myLetter.myLetter.dto.response.MyReplyListResponseDto;
import com.wishme.myLetter.myLetter.dto.response.MyReplyResponseDto;
import com.wishme.myLetter.myLetter.dto.response.ReplyDetailResponseDto;
import com.wishme.myLetter.myLetter.repository.MyLetterRepository;
import com.wishme.myLetter.myLetter.repository.ReplyRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import com.wishme.myLetter.util.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    @Value("${key.AES256_Key}")
    String key;

    private final UserRepository userRepository;
    private final MyLetterRepository myLetterRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long saveReply(SaveReplyRequestDto saveReplyRequestDto, Authentication authentication) throws Exception {
        MyLetter myLetter = myLetterRepository.findByMyLetterSeq(saveReplyRequestDto.getMyLetterSeq())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다. 해당 편지에 답장을 쓸 수 없습니다.", 1));

        if(replyRepository.findByMyLetter(myLetter) != null) {
//            throw new CustomException(ErrorCode.ALREADY_CREATED_ERROR);
            throw new IllegalArgumentException("답장은 한번만 할 수 있습니다.");
        }

        if(myLetter.getToUser().getUserSeq() != Long.parseLong(authentication.getName())) {
            throw new IllegalArgumentException("해당 유저는 답장을 보낼 수 없습니다.");
        }

        User toUser = userRepository.findByUserSeq(myLetter.getFromUser())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다. 해당 유저에게 답장을 쓸 수 없습니다.", 1));

        AES256 aes256 = new AES256(key);
        String cipherContent = aes256.encrypt(saveReplyRequestDto.getContent());

        Reply reply = Reply.builder()
                .myLetter(myLetter)
                .toUser(toUser)
                .content(cipherContent)
                .fromUserNickname(myLetter.getToUser().getUserNickname())
                .color(saveReplyRequestDto.getColor())
                .fromUser(myLetter.getToUser())
                .build();

        return replyRepository.save(reply).getReplySeq();
    }

    public MyReplyListResponseDto getMyReplyList(Authentication authentication, int page) {
        User toUser = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        long totalReplyCount = replyRepository.countByToUser(toUser);

        // 9개씩 페이징 처리해줌 (최신 순으로 정렬)
        Sort sort = Sort.by(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page - 1, 12, sort);

        List<Reply> replies = replyRepository.findAllByToUser(toUser, pageable);

        List<MyReplyResponseDto> myReplyResponseDtos = new ArrayList<>();

        for(Reply reply : replies) {
            MyReplyResponseDto myReplyResponseDto = MyReplyResponseDto.builder()
                    .replySeq(reply.getReplySeq())
                    .fromUserNickname(reply.getFromUserNickname())
                    .color(reply.getColor())
                    .build();

            myReplyResponseDtos.add(myReplyResponseDto);
        }

        MyReplyListResponseDto myReplyListResponseDto = MyReplyListResponseDto.builder()
                .totalReplyCount(totalReplyCount)
                .toUserSeq(toUser.getUserSeq())
                .toUserNickname(toUser.getUserNickname())
                .myReplyResponseDtos(myReplyResponseDtos)
                .build();

        return myReplyListResponseDto;
    }

    public ReplyDetailResponseDto getReplyDetail(Authentication authentication, Long replySeq) throws Exception{
        Reply reply = replyRepository.findByReplySeq(replySeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 답장은 존재하지 않습니다.", 1));

        if(Long.parseLong(authentication.getName()) != reply.getToUser().getUserSeq()) {
            throw new IllegalArgumentException("열람할 권한이 없습니다.");
        }

        AES256 aes256 = new AES256(key);
        String decryptContent = aes256.decrypt(reply.getContent());

        ReplyDetailResponseDto replyDetailResponseDto = ReplyDetailResponseDto.builder()
                .replySeq(reply.getReplySeq())
                .toUserNickname(reply.getToUser().getUserNickname())
                .fromUserNickname(reply.getFromUserNickname())
                .content(decryptContent)
                .letterSeq(reply.getMyLetter().getMyLetterSeq())
                .color(reply.getColor())
                .build();

        return replyDetailResponseDto;
    }
}
