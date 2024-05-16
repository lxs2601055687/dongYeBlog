<template>
  <div>
    <a-modal v-model:visible="visible" title="请注册" @ok="handleOk">
      <a-form
          :model="formState"
          name="basic"
          :label-col="{ span: 6 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
      >
        <a-form-item
            label="昵称"
            name="loginName"
            :rules="[{ required: true, message: 'Please input your username!' }]"
        >
          <a-input v-model:value="formState.loginName" />
        </a-form-item>
        <a-form-item
            label="手机号"
            name="phone"
            :rules="[{ required: true, message: 'Please input your phone!' }]"
        >
          <a-input v-model:value="formState.phone" />
        </a-form-item>
        <a-form-item
            label="密码"
            name="password"
            :rules="[{ required: true, message: 'Please input your password!' }]"
        >
          <a-input-password v-model:value="formState.password" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { useUserStore } from '/@/store/modules/system/user.js';
import encryptPasswordForm from '/@/constants/system/visitor-loginInfo-const.js';
const useStore = useUserStore()
import {onMounted, reactive, ref} from 'vue';
import {loginApi} from "/@/api/system/login-api.js";
import {message} from "ant-design-vue";
import {localSave} from "/@/utils/local-util.js";
import LocalStorageKeyConst from "/@/constants/local-storage-key-const.js";
import {buildRoutes} from "/@/router/index.js";
const visible = ref(false);
const showModal = () => {
  visible.value = true;
};
const handleOk = e => {
  console.log(e);
  const param = formState;
  register(param);
  visible.value = false;
};
 async function register(param) {
   const res = await loginApi.register(param);
   console.log(res)
   if(res.code === 0) {
     //保存登录状态，reload页面
     localSave(LocalStorageKeyConst.USER_TOKEN, res.data.token ? res.data.token : '');
     //更新用户信息到pinia
     useUserStore().setUserLoginInfo(res.data);
     //构建系统的路由
     buildRoutes();
     location.reload();
     message.success('注册成功,欢迎访问');
   } else {
     message.error(register.data.message)
   }

}
const formState = reactive({
  loginName: '',
  phone:'',
  password: '',
});
onMounted(() => {
  //从pinia拿到用户信息判断是否为游客登录，弹窗提醒用户登录
  if(useStore.loginName === encryptPasswordForm.loginName) {
    visible.value = true;
  }
});
</script>

<style scoped>

</style>