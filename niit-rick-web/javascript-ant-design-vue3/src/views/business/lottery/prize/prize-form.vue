<!--
  * 奖品表
  *
  * @Author:    李祥生
  * @Date:      2024-05-16 16:35:02
  * @Copyright  niit-阿升
-->
<template>
  <a-modal
      :title="form.id ? '编辑' : '添加'"
      width="600"
      :open="visibleFlag"
      @cancel="onClose"
      :maskClosable="false"
      :destroyOnClose="true"
  >
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 4 }">
      <!-- 表单项 -->
      <a-row>
        <a-col :span="12">
          <a-form-item label="奖品名称" name="title">
            <a-input style="width: 100%" v-model:value="form.title" placeholder="奖品名称" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="奖品数量" name="prizeNum">
            <a-input-number style="width: 100%" v-model:value="form.prizeNum" placeholder="奖品数量" />
          </a-form-item>
        </a-col>
        <!-- 其他表单项 -->
        <a-col :span="12">
          <a-form-item label="剩余数量"  name="leftNum">
            <a-input-number style="width: 100%" v-model:value="form.leftNum" placeholder="剩余数量" />
          </a-form-item>
        </a-col>
        <!-- 第二组表单项 -->
        <a-col :span="12">
          <a-form-item label="中奖概率"  name="prizeCode">
            <a-input style="width: 100%" v-model:value="form.prizeCode" placeholder="0-9999表示100%，0-0表示万分之一的中奖概率" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="发奖周期"  name="prizeTime">
            <a-input-number style="width: 100%" v-model:value="form.prizeTime" placeholder="发奖周期" />
          </a-form-item>
        </a-col>
        <!-- 第三组表单项 -->
        <a-col :span="12">
          <a-form-item label="奖品图片"  name="img">
            <a-input style="width: 100%" v-model:value="form.img" placeholder="奖品图片" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="位置序号"  name="displayOrder">
            <a-input-number style="width: 100%" v-model:value="form.displayOrder" placeholder="位置序号" />
          </a-form-item>
        </a-col>
        <!-- 第四组表单项 -->
        <a-col :span="12">
          <a-form-item label="奖品类型" name="prizeType">
            <a-select style="width: 100%" v-model:value="form.prizeType" placeholder="请选择奖品类型">
              <a-select-option value="1">虚拟币</a-select-option>
              <a-select-option value="2">虚拟券</a-select-option>
              <a-select-option value="3">实物小奖</a-select-option>
              <a-select-option value="4">实物大奖</a-select-option>
            </a-select>
          </a-form-item>

        </a-col>
        <a-col :span="12">
          <a-form-item label="开始时间"  name="beginTime">
            <a-date-picker show-time valueFormat="YYYY-MM-DD HH:mm:ss" v-model:value="form.beginTime" style="width: 100%" placeholder="开始时间" />
          </a-form-item>
        </a-col>

      </a-row>

      <!-- 按钮 -->

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
  import { smartSentry } from '/@/lib/smart-sentry';
  import {prizeApi} from "/@/api/business/lottery/prize-api.js";
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
              id: undefined, //id
              title: undefined, //奖品名称
              prizeNum: undefined, //奖品数量
              leftNum: undefined, //剩余数量
              prizeCode: undefined, //0-9999表示100%，0-0表示万分之一的中奖概率
              prizeTime: undefined, //发奖周期
              img: undefined, //奖品图片
              prizePlan: undefined, //奖品计划
              displayOrder: undefined, //位置序号
              prizeType: undefined, //奖品类型
              beginTime: undefined, //开始时间
              endTime: undefined, //结束时间
              prizeBegin: undefined, //发奖计划周期的开始
              prizeEnd: undefined, //发奖计划周期的结束
              sysCreated: undefined, //创建时间
              sysUpdated: undefined, //修改时间
  };

  let form = reactive({ ...formDefault });

  const rules = {
                  id: [{ required: true, message: 'id 必填' }],
                  title: [{ required: true, message: '奖品名称 必填' }],
                  prizeNum: [{ required: true, message: '奖品数量 必填' }],
                  leftNum: [{ required: true, message: '剩余数量 必填' }],
                  prizeCode: [{ required: true, message: '0-9999表示100%，0-0表示万分之一的中奖概率 必填' }],
                  prizeTime: [{ required: true, message: '发奖周期 必填' }],
                  img: [{ required: true, message: '奖品图片 必填' }],
                  displayOrder: [{ required: true, message: '位置序号 必填' }],
                  prizeType: [{ required: true, message: '奖品类型 必填' }],
  };

  // 点击确定，验证表单
  async function onSubmit() {
    try {
      await formRef.value.validateFields();
      save();
    } catch (err) {
      message.error('参数验证错误，请仔细填写表单数据!');
    }
  }

  // 新建、编辑API
  async function save() {
    SmartLoading.show();
    try {
      if (form.id) {
        await prizeApi.update(form);
      } else {
        await prizeApi.add(form);
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
