<template>
  <a-button @click="emitContent">正文保存</a-button>
  <div ref="divRef" style="height: 600px"></div>
</template>

<script setup lang="ts">
import { AiEditor } from "aieditor";
import "aieditor/dist/style.css";
import { onMounted, onUnmounted, ref } from "vue";
import { defineEmits } from 'vue';

const emit = defineEmits(['updateContent']);

const divRef = ref();
let aiEditor: AiEditor | null = null;

onMounted(() => {
  aiEditor = new AiEditor({
    element: divRef.value as Element,
    placeholder: "点击输入内容...",
    content: 'AiEditor 是一个面向 AI 的开源富文本编辑器。 ',
    ai:{
      models:{
        spark:{
          protocol:"ws",
          appId:"87798852",
          apiSecret:"YWY1M2Q3ZWQ0ODU3ZDExZmIzMWI3NDhm",
          apiKey:"f656ccd3e5fc092aab0bb00a4cdd837b",
          version: "v3.5",
        },
        bubblePanelEnable: true,
        bubblePanelModel: "spark",
      },
    },
  });
});

function getContent() {
  const html = aiEditor?.getHtml() || '';
  return html;
}

function emitContent() {
  const html = getContent();
  emit('updateContent', html);
}

onUnmounted(() => {
  aiEditor && aiEditor.destroy();
});
</script>
