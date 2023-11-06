package com.wishme.myLetter.openAPI.dto.response;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GPTCompletionChatResponseDto {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Message> messages;

    private Usage usage;

    @Builder
    public GPTCompletionChatResponseDto(String id, String object, Long created, String model, List<Message> messages, Usage usage) {

        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.messages = messages;
        this.usage = usage;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Message {

        private String role;

        private String message;

        @Builder
        public Message(String role, String message) {

            this.role = role;
            this.message = message;
        }

        public static Message of(ChatMessage chatMessage) {

            return new Message(chatMessage.getRole(), chatMessage.getContent());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Usage {

        private Long promptTokens;

        private Long completionTokens;

        private Long totalTokens;

        @Builder
        public Usage(Long promptTokens, Long completionTokens, Long totalTokens) {

            this.promptTokens = promptTokens;
            this.completionTokens = completionTokens;
            this.totalTokens = totalTokens;
        }

        public static Usage of(com.theokanning.openai.Usage usage) {

            return new Usage(usage.getPromptTokens(), builder().completionTokens, builder().totalTokens);
        }
    }

    public static List<GPTCompletionChatResponseDto.Message> toResponseListBy(List<ChatCompletionChoice> choices) {

        return choices.stream()
                .map(completionChoice -> Message.of(completionChoice.getMessage()))
                .collect(Collectors.toList());
    }

    public static GPTCompletionChatResponseDto of(ChatCompletionResult result) {

        return new GPTCompletionChatResponseDto(result.getId(), result.getObject(), result.getCreated(), result.getModel()
                , toResponseListBy(result.getChoices()), Usage.of(result.getUsage()));
    }
}
