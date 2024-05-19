<!--
  * 意见反馈
  * 
  * @Author:    李祥生
  * @Date:      2024-05-21 21:55:12
  * @Wechat:    lxsdsg123
  * @Email:    2601055687@qq.com
  * @Copyright
-->
<template>
  <h1 class="title">点击start，开始抽奖</h1>
  <section class="container" id="js-lotto">
    <div class="square" data-order="0">
      <div class="square__content">🙈</div>
    </div>
    <div class="square" data-order="1">
      <div class="square__content">🤢</div>
    </div>
    <div class="square" data-order="2">
      <div class="square__content">💩</div>
    </div>
    <div class="square" data-order="7">
      <div class="square__content">🤖</div>
    </div>
    <div class="square square__start-btn" id="js-start">
      <div>START</div>
    </div>
    <div class="square" data-order="3">
      <div class="square__content">🦊</div>
    </div>
    <div class="square" data-order="6">
      <div class="square__content">👻</div>
    </div>
    <div class="square" data-order="5">
      <div class="square__content">👾</div>
    </div>
    <div class="square" data-order="4">
      <div class="square__content">👀</div>
    </div>
  </section>
</template>

<script setup>
  import { onMounted, reactive, ref } from 'vue';
  import {prizeApi} from "/@/api/business/lottery/prize-api.js";
  import axios from "axios";
  import {useUserStore} from "/@/store/modules/system/user.js";

    const prizes = {
      0: '🙈',
      1: '🤢',
      2: '💩',
      3: '🦊',
      4: '👀',
      5: '👾',
      6: '👻',
      7: '🤖',
    };
    const totalItems = 8;
    const minimumJumps = 30; // 超過這數字開始進入抽獎
    const currentIndex = ref(-1);
    const jumps = ref(0);
    const speed = ref(30);
    const timer = ref(0);
    const prize = ref(-1);

    const runCircle = () => {
      if (currentIndex.value >= 0) {
        document.querySelector(`[data-order="${currentIndex.value}"]`).classList.remove('is-active');
      }

      currentIndex.value += 1;

      if (currentIndex.value > totalItems - 1) {
        currentIndex.value = 0;
      }

      document.querySelector(`[data-order="${currentIndex.value}"]`).classList.add('is-active');
    };

    const generatePrizeNumber = () => {
      return Math.floor(Math.random() * totalItems);
    };

    const controllSpeed = () => {
      jumps.value += 1;
      runCircle();

      // 1. 抽到獎品停止遊戲
      if (jumps.value > minimumJumps + 10 && prize.value === currentIndex.value) {
        clearTimeout(timer.value);
        console.log(result.data.errcode)
        if(result.data.errcode === "SUCCESS"){
          swal({
            title: `You Have Won a Prize ${prizes[currentIndex.value] + result.data.lotteryPrize.title}`,
            text: 'Congratulations!',
            icon: 'success',
            content: {
              element: "img",
              attributes: {
                src: result.data.lotteryPrize.img,
                alt: result.data.lotteryPrize.title,
                style: "width: 100px; height: 100px;" // 可以根据需要调整图片大小
              },
            },
          });
        } else {
          swal({
            title: `You Don't Have Won a Prize `,
            text: 'Congratulations!',
            icon: 'error',
          });
        }



        prize.value = -1;
        jumps.value = 0;
      } else {
        // 還沒進入關鍵抽獎階段前的速度 (前菜轉特效)
        if (jumps.value < minimumJumps) {
          speed.value -= 5; // 加快
        } else if (jumps.value === minimumJumps) {
          const randomNumber = generatePrizeNumber();
          prize.value = randomNumber;
        } else {
          // 下一個就是獎品時放慢鈍一下
          if (jumps.value > minimumJumps + 10 && prize.value === (currentIndex.value + 1)) {
            speed.value += 600;
          } else {
            speed.value += 20; // 減速
          }
        }

        if (speed.value < 40) {
          speed.value = 40;
        }

        timer.value = setTimeout(controllSpeed, speed.value);
      }
    };
  async function getClientIp() {
    try {
      const response = await axios.get('http://localhost:3000/get-ip');
      console.log('Client IP:', response.data.ip);
      return response.data.ip;
    } catch (error) {
      console.error('Error fetching IP address:', error);
      return null;
    }
  }
  let result  =new reactive({});
    const init = async () => {
      jumps.value = 0;
      speed.value = 100;
      prize.value = -1;
     /* const clientIp = await getClientIp();*/

      const param = {
        userId: useUserStore().employeeId,
        ip: 1324,
        userName: useUserStore().loginName
      }
       result = await prizeApi.getLucky(param)
      console.log(result)
      controllSpeed();
    };

    onMounted(() => {
      document.querySelector('#js-start').addEventListener('click', init);
    });



</script>
<style lang="less" scoped>
*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font-family: 'Do Hyeon', sans-serif;
  background: #7049f7;
}

.title {
  text-align: center;
  margin: 1.2em 0;
  font-size: 2em;
  color: #426edc;
  text-transform: uppercase;
}

.container {
  display: flex;
  flex-wrap: wrap;
  width: 620px;
  margin: 20px auto;
}

.square {
  border: 1px solid lightpink;
  flex: 0 0 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  background: #426edc;

  &.square:not(:nth-child(3n)) {
    margin-right: 10px;
}

  &.square:not(:nth-child(n+7)) {
    margin-bottom: 10px;
}

  &.is-active {
    border: 20px solid gold;
}
}

.square__content {
  font-size: 2.8em;
}

.square__start-btn {
  background: gold;
  color: #e97573;
  font-size: 2em;
  cursor: pointer;

  &:hover {
    background: lighten(gold, 25%);
}
}

</style>