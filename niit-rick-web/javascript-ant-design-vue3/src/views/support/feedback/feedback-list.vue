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
  <a-row>
    <a-col :span="12">
    <h1 class="title">点击start，开始抽奖</h1>
      <h4 class="title">您今天已经抽: <b>{{ num }}</b> 次奖！每天可抽 20 次</h4>
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
    </a-col>
    <a-col :span="12">
      <a-row justify="center" style="margin-top: 100px">
        <a-col style="width: 400px" :span="12">
          <a-card>
            <p style=" text-align: center; font-size: 26px; color: #426edc;">
            获奖结果
            </p>
            <a-table
                :columns="columns"
                :data-source="prizeResult"
                :pagination="false"
                :rowKey="(record) => record.id"
            />
          </a-card>
        </a-col>
        <a-col style="width: 400px" :span="12">
          <a-card>
            <p style=" text-align: center; font-size: 26px; color: #426edc;">
             奖品池
            </p>
            <a-table
                :columns="columns2"
                :data-source="prizeResult2"
                :pagination="false"
                :rowKey="(record) => record.id"
            />
          </a-card>
        </a-col>
      </a-row>
    </a-col>
  </a-row>

</template>

<script setup>
  import { onMounted, reactive, ref } from 'vue';
  import {prizeApi} from "/@/api/business/lottery/prize-api.js";
  import axios from "axios";
  import {useUserStore} from "/@/store/modules/system/user.js";

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      sorter: true,
      width: '20%',
    },
    {
      title: '奖品名称',
      dataIndex: 'prizeName',
      sorter: true,
      width: '40%',
    },
    {
      title: '中奖时间',
      dataIndex: 'sysCreated',
    },
  ];
  const columns2 = [
      {
        title: 'ID',
        dataIndex: 'id',
        sorter: true,
        width: '20%',
      },
      {
        title: '奖品名称',
        dataIndex: 'title',
        sorter: true,
        width: '40%',
      },
      {
        title: '剩余数量',
        dataIndex: 'leftNum',
      },
  ]
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
      //验证是否时游客，游客提示禁止抽奖
      if (useUserStore().loginName === '游客') {
        swal({
          title: '游客登录，请先回到首页注册参与抽奖哦',
          text: 'SB!',
          icon: 'error',
        });
        return;
      }
      //前端验证次数
      if (num.value >= 20) {
        swal({
          title: '您今天已经抽奖次数超过20次，请明天再来哦',
          text: 'SB!',
          icon: 'error',
        });
        return;
      }
      jumps.value = 0;
      speed.value = 100;
      prize.value = -1;
     /* const clientIp = await getClientIp();*/
      num.value = num.value + 1
      const param = {
        userId: useUserStore().employeeId,
        ip: 1324,
        userName: useUserStore().loginName
      }
       result = await prizeApi.getLucky(param)
      console.log(result)
      controllSpeed();
    };
  let num = ref(0)
  function getPrizeCount(){
    prizeApi.getLuckyCount(useUserStore().employeeId).then((res) => {
      if (res.code === 0) {
        num.value = res.data
        console.log(num)
      }
    })
  }
  //拿到获奖结果
  let prizeResult = ref([])
  function getPrizeResult(){
    prizeApi.getLuckyResult(useUserStore().employeeId).then((res) => {
      if (res.code === 0) {
        prizeResult.value  = res.data
      }
    })
  }
  let prizeResult2 = ref([])
  function  getPrizes(){
    prizeApi.getAllPrize().then((res) => {
      if (res.code === 0) {
        prizeResult2.value  = res.data
      }
    })
  }
    onMounted(() => {
      document.querySelector('#js-start').addEventListener('click', init);
      getPrizeResult();
      getPrizeCount();
      getPrizes();
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
  height: 150px;
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
a-card {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

p {
  font-size: 16px;
  color: #595959;
}

b {
  color: #1890ff; /* 重点内容颜色 */
}

a-table {
  margin-top: 20px;
}
</style>