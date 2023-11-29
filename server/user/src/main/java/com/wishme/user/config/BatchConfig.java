package com.wishme.user.config;

import com.wishme.user.user.dto.request.MailContentDto;
import com.wishme.user.user.repository.UserRepository;
import com.wishme.user.user.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@ComponentScan(basePackages={"org.springframework.batch.core.configuration.annotation"})
public class BatchConfig {

    public final JobBuilderFactory jobBuilderFactory;

    public final StepBuilderFactory stepBuilderFactory;

    private final UserRepository userRepository;

    private final AlarmService alarmService;

    @Bean
    public Job job() {

        Job job = jobBuilderFactory.get("job")
                .start(startStep())
                .next(lastStep())
                .build();

        return job;
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Start Step!");
                    List<String> userEmailList = userRepository.findEmailsByUserAndMyletter();

                    for(String userEmail : userEmailList) {
                        MailContentDto mailContentDto = alarmService.createDownloadLetterAlarm(userEmail);
                        alarmService.sendMail(mailContentDto);
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step lastStep(){
        return stepBuilderFactory.get("lastStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Last Step!!");
                    List<String> userEmailList = userRepository.findEmailsByUserAndReply();

                    for(String userEmail : userEmailList) {
                        MailContentDto mailContentDto = alarmService.createReplyAlarm(userEmail);
                        alarmService.sendMail(mailContentDto);
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
