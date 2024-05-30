<template>
  <div style="height: 800px">
    <a-card title="面试小助手" class="chat-card">
      <div class="chat-window">
        <div v-for="(message, index) in messages" :key="index" class="message">
          <strong>{{ message.sender }}:</strong>
          <pre class="language-html"><code><span v-html="formatMessage(message.text)"></span></code></pre>

        </div>
      </div>
      <div style="display: flex; width: 50%;margin: 0 auto">
        <a-textarea
            :value="userInput"
            @input="userInput = $event.target.value"
            @pressEnter="sendMessage"
            placeholder="请输入问题"
        />

        <a-button type="primary" @click="sendMessage" :loading="loading" style="margin-top: 10px ">
          发送
        </a-button>
      </div>
    </a-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const userInput = ref('');
const messages = ref([]);
const loading = ref(false);

const sendMessage = async () => {
  if (userInput.value.trim() === '') return;
  const userMessage = userInput.value.trim();
  const userQuestion = { sender: '用户', text: userMessage }; // 保存用户问题
  messages.value.push(userQuestion); // 推送用户问题到 messages 数组
  userInput.value = '';
  loading.value = true;
  try {
    const eventSource = new EventSource(`http://127.0.0.1:10001/blog/chat/?message=${userMessage}`);
    eventSource.onmessage = (event) => {
      let data = event.data;
      console.log(data);
      if (messages.value[messages.value.length - 1].sender === "用户") {
        let text = formatMessage(data)
        messages.value.push({ sender: '小助手', text: text })
      } else {
        let text = formatMessage(data)
        messages.value[messages.value.length - 1].text = text;
      }
      loading.value = false;
    };

    eventSource.onerror = (error) => {
      console.error('EventSource 错误: ', error);
      const errorMessage = { sender: '系统', text: '连接错误，请稍后再试。' }; // 保存错误消息
      messages.value.push(errorMessage); // 推送错误消息到 messages 数组
      loading.value = false;
      eventSource.close();
    };
  } catch (error) {
    const errorMessage = { sender: '系统', text: '发送失败，请稍后再试。' }; // 保存错误消息
    messages.value.push(errorMessage); // 推送错误消息到 messages 数组
    loading.value = false;
  }
};

const formatMessage = (text) => {
  // 将换行符替换为 <br> 标签
  return text.replace(/\\sb/g, '<br><br>');
};
</script>

<style>
.chat-card {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.chat-window {
  height: 400px;
  flex-grow: 1;
  overflow-y: auto;
  margin-bottom: 20px;
  border: 1px solid #f0f0f0;
  padding: 10px;
  background: #fafafa;
  white-space: pre-line; /* 允许换行 */
}
.message {
  margin-bottom: 10px;
}
.code-block {
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 5px;
  overflow-x: auto;
}
</style>
