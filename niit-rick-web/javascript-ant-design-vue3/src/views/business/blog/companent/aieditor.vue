<template>
  <div>
    <h1>AiEditor，一个面向 AI 的富文本编辑器</h1>
  </div>
  <div ref="divRef" style="height: 600px"/>
</template>

<script setup lang="ts">
import {AiEditor} from "aieditor";
import "aieditor/dist/style.css"
import {onMounted, onUnmounted, ref} from "vue";
import axios from "axios";

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
  })
})

onUnmounted(() => {
  aiEditor && aiEditor.destroy();
})
</script>