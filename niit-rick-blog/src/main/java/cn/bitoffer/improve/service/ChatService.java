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
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private static final long SSE_TIMEOUT = TimeUnit.MINUTES.toMillis(2); // 10分钟超时

    public SseEmitter streamCallWithSseEmitter(Message userMsg) throws NoApiKeyException, ApiException, InputRequiredException {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        Application app = new Application();
        ApplicationParam param = buildGenerationParam(userMsg);
        int i = 0;

        try {
            Flowable<ApplicationResult> resultFlowable = app.streamCall(param);
            Disposable disposable = resultFlowable.subscribe(result -> {
                try {
                    handleGenerationResult(result, emitter);
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            });
            emitter.onCompletion(disposable::dispose); // 取消订阅流
            emitter.onTimeout(emitter::complete); // 处理超时情况
        } catch (Exception e) {
            emitter.completeWithError(e);
        }

        // 添加超时处理逻辑
        emitter.onTimeout(() -> emitter.completeWithError(new TimeoutException("Connection timed out")));

        return emitter;
    }

    private static void handleGenerationResult(ApplicationResult result, SseEmitter emitter) {
        try {
            String content = String.valueOf(result.getOutput().getText()); // 修改这里获取消息内容的方式
            content = content.replace("\n", "\\sb");
            System.out.println("Received message-----------------------------: " + JsonUtils.toJson(content));
            emitter.send(SseEmitter.event().data(content));
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }

    private static ApplicationParam buildGenerationParam(Message userMsg) {
        return ApplicationParam.builder()
                .appId("676f862f89c74e69b520a51e24108267")
                .apiKey("sk-87c476dd5822400990fc97a3d706d55a")
                .prompt(userMsg.getContent())
                .build();
    }
}
