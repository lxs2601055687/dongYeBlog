<!--
  * 登录
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-09-12 22:34:00
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
  *
-->
<template>
  <div class="login-container">
    <div class="box-item desc">
      <div class="welcome">
        <p>niit项目课设： 社区项目</p>
        <p class="desc"> 博客+大学活动报名秒杀+抽奖平台</p>
        <p class="desc">抽奖平台功能: 用户可以发起抽奖活动，设置抽奖规则，设置奖品，设置抽奖计划，黑名单。</p>
        <p class="desc">活动秒杀功能: 管理员发起活动，设置活动截至时间，设置活动奖品（抽奖次数），设置活动人数限制。</p>
        <p class="desc"> 社区板块功能: 发帖，点赞，全文搜索，评论，点赞排行榜，个人主页。</p>
      </div>
    </div>
    <div class="box-item login">
      <img class="login-qr" :src="loginQR" />
      <div class="login-title">账号登录</div>
      <a-form ref="formRef" class="login-form" :model="loginForm" :rules="rules">
        <a-form-item name="loginName">
          <a-input v-model:value.trim="loginForm.loginName" placeholder="请输入用户名" />
        </a-form-item>
        <a-form-item name="password">
          <a-input
            v-model:value="loginForm.password"
            autocomplete="on"
            placeholder="请输入密码"
            :type="showPassword ? 'text' : 'password'"
          />
        </a-form-item>
        <a-form-item name="captchaCode">
          <a-input class="captcha-input" v-model:value.trim="loginForm.captchaCode" placeholder="请输入验证码" />
          <img class="captcha-img" :src="captchaBase64Image" @click="getCaptcha" />
        </a-form-item>
        <a-form-item>
          <a-checkbox v-model:checked="rememberPwd">记住密码</a-checkbox>
        </a-form-item>
        <a-form-item>
          <div class="btn" @click="onLogin">登录</div>
        </a-form-item>
      </a-form>
<!--      <div class="more">
        <div class="title-box">
          <p class="line"></p>
          <p class="title">其他方式登录</p>
          <p class="line"></p>
        </div>
        <div class="login-type">
          <img :src="wechatIcon" />
          <img :src="aliIcon" />
          <img :src="douyinIcon" />
          <img :src="qqIcon" />
          <img :src="weiboIcon" />
          <img :src="feishuIcon" />
          <img :src="googleIcon" />
        </div>
      </div>-->
    </div>
  </div>
</template>
<script setup>
  import { message, notification, Button } from 'ant-design-vue';
  import { onMounted, onUnmounted, reactive, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { loginApi } from '/@/api/system/login-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { LOGIN_DEVICE_ENUM } from '/@/constants/system/login-device-const';
  import { useUserStore } from '/@/store/modules/system/user';
  import zhuoda from '/@/assets/images/1024lab/zhuoda-wechat.jpg';
  import loginQR from '/@/assets/images/login/login-qr.png';
  import gzh from '/@/assets/images/1024lab/gzh.jpg';
  import wechatIcon from '/@/assets/images/login/wechat-icon.png';
  import aliIcon from '/@/assets/images/login/ali-icon.png';
  import douyinIcon from '/@/assets/images/login/douyin-icon.png';
  import qqIcon from '/@/assets/images/login/qq-icon.png';
  import weiboIcon from '/@/assets/images/login/weibo-icon.png';
  import feishuIcon from '/@/assets/images/login/feishu-icon.png';
  import googleIcon from '/@/assets/images/login/google-icon.png';

  import { buildRoutes } from '/@/router/index';
  import { smartSentry } from '/@/lib/smart-sentry';
  import { encryptData } from '/@/lib/encrypt';
  import { h } from 'vue';
  import { localSave } from '/@/utils/local-util.js';
  import LocalStorageKeyConst from '/@/constants/local-storage-key-const.js';

  //--------------------- 登录表单 ---------------------------------

  const loginForm = reactive({
    loginName: '',
    password: '',
    captchaCode: '',
    captchaUuid: '',
    loginDevice: LOGIN_DEVICE_ENUM.PC.value,
  });
  const rules = {
    loginName: [{ required: true, message: '用户名不能为空' }],
    password: [{ required: true, message: '密码不能为空' }],
    captchaCode: [{ required: true, message: '验证码不能为空' }],
  };

  const showPassword = ref(false);
  const router = useRouter();
  const formRef = ref();
  const rememberPwd = ref(false);

  onMounted(() => {
    document.onkeyup = (e) => {
      if (e.keyCode == 13) {
        onLogin();
      }
    };

    notification['success']({
      message: '温馨提示',
      description: 'SmartAdmin 提供 9种 登录背景风格哦！',
      duration: 8,
      onClick: () => {},
      btn: () =>
        h(
          Button,
          {
            type: 'primary',
            target: '_blank',
            size: 'small',
            href: 'https://smartadmin.vip/views/v3/front/Login.html',
            onClick: () => {},
          },
          { default: () => '去看看' }
        ),
    });
  });

  onUnmounted(() => {
    document.onkeyup = null;
  });

  //登录
  async function onLogin() {
    formRef.value.validate().then(async () => {
      try {
        SmartLoading.show();
        // 密码加密
        let encryptPasswordForm = Object.assign({}, loginForm, {
          password: encryptData(loginForm.password),
        });
        const res = await loginApi.login(encryptPasswordForm);
        stopRefrestCaptchaInterval();
        localSave(LocalStorageKeyConst.USER_TOKEN, res.data.token ? res.data.token : '');
        message.success('登录成功');
        //更新用户信息到pinia
        useUserStore().setUserLoginInfo(res.data);
        //构建系统的路由
        buildRoutes();
        router.push('/home');
      } catch (e) {
        if (e.data && e.data.code !== 0) {
          loginForm.captchaCode = '';
          getCaptcha();
        }
        smartSentry.captureError(e);
      } finally {
        SmartLoading.hide();
      }
    });
  }

  //--------------------- 验证码 ---------------------------------

  const captchaBase64Image = ref('');
  async function getCaptcha() {
    try {
      let captchaResult = await loginApi.getCaptcha();
      captchaBase64Image.value = captchaResult.data.captchaBase64Image;
      loginForm.captchaUuid = captchaResult.data.captchaUuid;
      beginRefrestCaptchaInterval(captchaResult.data.expireSeconds);
    } catch (e) {
      console.log(e);
    }
  }

  let refrestCaptchaInterval = null;
  function beginRefrestCaptchaInterval(expireSeconds) {
    if (refrestCaptchaInterval === null) {
      refrestCaptchaInterval = setInterval(getCaptcha, (expireSeconds - 5) * 1000);
    }
  }

  function stopRefrestCaptchaInterval() {
    if (refrestCaptchaInterval != null) {
      clearInterval(refrestCaptchaInterval);
      refrestCaptchaInterval = null;
    }
  }

  onMounted(getCaptcha);
</script>
<style lang="less">
  @import './login.less';

</style>
