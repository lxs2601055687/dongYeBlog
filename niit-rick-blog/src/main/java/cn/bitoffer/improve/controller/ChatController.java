package cn.bitoffer.improve.controller;

import cn.bitoffer.improve.service.ChatService;
import com.alibaba.dashscope.common.Message;
import net.lab1024.sa.base.common.annoation.NoNeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/blog/chat")
    @NoNeedLogin
    public SseEmitter streamChat(@RequestParam String message) {
        Message userMsg = new Message();
        userMsg.setRole("user");
        userMsg.setContent(message);

        try {
            return chatService.streamCallWithSseEmitter(userMsg);
        } catch (Exception e) {
            SseEmitter emitter = new SseEmitter();
            emitter.completeWithError(e);
            return emitter;
        }
    }
}
