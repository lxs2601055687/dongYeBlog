package cn.bitoffer.improve.service;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private static final long SSE_TIMEOUT = TimeUnit.MINUTES.toMillis(10); // 10分钟超时

    private static void handleGenerationResult(ApplicationResult result, SseEmitter emitter) throws Exception {
        String content = String.valueOf(result.getOutput().getText());  // Adjust this based on the actual structure of ApplicationResult
        logger.info("Received message: {}", JsonUtils.toJson(result));
        emitter.send(SseEmitter.event().data(content));
    }

    public SseEmitter streamCallWithSseEmitter(Message userMsg) throws NoApiKeyException, ApiException, InputRequiredException {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        Application app = new Application();
        ApplicationParam param = buildGenerationParam(userMsg);

        CompletableFuture.runAsync(() -> {
            try {
                Flowable<ApplicationResult> resultFlowable = app.streamCall(param);
                resultFlowable.blockingForEach(result -> {
                    try {
                        handleGenerationResult(result, emitter);
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                });
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    private static ApplicationParam buildGenerationParam(Message userMsg) {
        return ApplicationParam.builder()
                .appId("676f862f89c74e69b520a51e24108267")
                .apiKey("sk-87c476dd5822400990fc97a3d706d55a")
                .prompt(userMsg.getContent())
                .build();
    }
}
