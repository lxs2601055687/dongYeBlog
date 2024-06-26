<!--
  * 文章管理
  *
  * @Author:    李祥生
  * @Date:      2024-05-22 15:28:58
  * @Copyright  niit-阿升
-->
<template>
    <a-modal
        :title="form.articleId ? '编辑' : '添加'"
        width="1000"
        :open="visibleFlag"
        @cancel="onClose"
        :maskClosable="false"
        :destroyOnClose="true"
    >
      <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
        <a-row :gutter="16">
          <a-col :span="6">
            <a-form-item label="文章标题" name="articleTitle">
              <a-input v-model:value="form.articleTitle" placeholder="文章标题" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="浏览量" name="articleViewCount">
              <a-input-number v-model:value="form.articleViewCount" style="width: 100%;" placeholder="文章浏览量" />
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item label="点赞量" name="articleLikeCount">
              <a-input-number v-model:value="form.articleLikeCount" style="width: 100%;" placeholder="文章点赞量" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="评论数量" name="articleCommentCount">
              <a-input-number v-model:value="form.articleCommentCount" style="width: 100%;" placeholder="文章评论数量" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="分类名称" name="articleCategoryName">
              <a-input v-model:value="form.articleCategoryName" placeholder="分类" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="24">
            <a-form-item name="articleContent">
              <Aieditor @updateContent="handleUpdateContent" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>

      <template #footer>
        <a-space>
          <a-button @click="onClose">取消</a-button>
          <a-button type="primary" @click="onSubmit">保存</a-button>
        </a-space>
      </template>
    </a-modal>
</template>
<script setup>
  import { reactive, ref, nextTick } from 'vue';
  import _ from 'lodash';
  import { message } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { articleApi } from '/@/api/business/blog/article-api.js';
  import { smartSentry } from '/@/lib/smart-sentry';

  import Aieditor from "/@/views/business/blog/companent/aieditor.vue";
  const editorContent = ref('');

  function handleUpdateContent(html) {
    editorContent.value = html;
  }
  // ------------------------ 事件 ------------------------

  const emits = defineEmits(['reloadList']);

  // ------------------------ 显示与隐藏 ------------------------
  // 是否显示
  const visibleFlag = ref(false);

  function show(rowData) {
    Object.assign(form, formDefault);
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
    }
    visibleFlag.value = true;
    nextTick(() => {
      formRef.value.clearValidate();
    });
  }

  function onClose() {
    Object.assign(form, formDefault);
    visibleFlag.value = false;
  }

  // ------------------------ 表单 ------------------------

  // 组件ref
  const formRef = ref();

  const formDefault = {
              articleId: undefined, //文章id
              articleTitle: undefined, //文章标题
              articleContent: undefined, //文章内容
              articleViewCount: undefined, //文章浏览量
              articleLikeCount: undefined, //文章点赞量
              articleCommentCount: undefined, //文章评论数量
              articleCategoryId: undefined, //外键，对应category_id
              articleUserId: undefined, //外键，对应user_id
              articleCategoryName: undefined, //分类表中对应category_name
  };

  let form = reactive({ ...formDefault });

  const rules = {
                  articleId: [{ required: true, message: '文章id 必填' }],
                  articleTitle: [{ required: true, message: '文章标题 必填' }],
                  articleUserId: [{ required: true, message: '外键，对应user_id 必填' }],
                  articleCategoryName: [{ required: true, message: '分类表中对应category_name 必填' }],
  };

  // 点击确定，验证表单
  async function onSubmit() {
    try {
      await formRef.value.validateFields();
      await save();
    } catch (err) {
      message.error('参数验证错误，请仔细填写表单数据!');
    }
  }

  // 新建、编辑API
  async function save() {
    SmartLoading.show();
    try {
      if (form.articleId) {
        await articleApi.update(form);
      } else {
        form.articleContent = editorContent;
        await articleApi.add(form);
      }
      message.success('操作成功');
      emits('reloadList');
      onClose();
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      SmartLoading.hide();
    }
  }

  defineExpose({
    show,
  });
</script>
<style>
a-form-item {
  margin-bottom: 16px;
}

a-modal .ant-modal-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
