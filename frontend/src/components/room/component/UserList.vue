<template>
  <div>
    <div class="user-card">
      <div
        class="name-info"
        v-for="user in user_info.users"
        :key="user.connectionId"
      >
        <img src="@/assets/아이콘/아바타.png" alt="" class="avatar" />
        {{ user.name }} [ {{ callMyName[user.participantId] }} ]
      </div>
    </div>
    <div class="time-card">
      <div
        class="time-info"
        v-for="(time, idx) in user_info.total_time"
        :key="idx"
      >
        {{ time }}
      </div>
    </div>
  </div>
</template>

<script>
import { GetUserInfo } from "@/api/webrtc.js";
import { onMounted } from "@vue/runtime-core";
import { reactive } from "@vue/reactivity";
import moment from "moment";
export default {
  props: ["roomId", "callMyName"],
  setup(props) {
    var user_info = reactive({
      curNum: undefined,
      users: undefined,
      time: undefined,
      total_time: [],
    });

    onMounted(() => {
      //   console.log("방정보: " + props.roomId);
      GetUserInfo(props.roomId, (response) => {
        // console.log(response);
        user_info.curNum = response.data.roomInfo.curNum;
        // console.log(response.data.participantList);
        user_info.users = response.data.participantList;
        // user_info.time = response.data.participantList[];
        // console.log(response.data.participantList);
        var length = user_info.users.length;
        for (var i = 0; i < length; i++) {
          console.log(user_info.users[i].enterTime);
          var time = user_info.users[i].enterTime;
          var today = moment();
          var currentTime = Date.now().toString();
          var enterTime = new Date(user_info.users[i].enterTime);
          var duration = currentTime - enterTime;
          console.log("밀리초: " + duration);
          const hour = String(
            Math.floor((duration / (1000 * 60 * 60)) % 24)
          ).padStart(2, "0"); // 시
          const minutes = String(
            Math.floor((duration / (1000 * 60)) % 60)
          ).padStart(2, "0"); // 분
          console.log("시: " + hour);
          console.log("분: " + minutes);
          console.log("hours: " + moment(today).diff(moment(time), "hours"));

          // user_info.total_time.push(hour + "시" + " " + minutes + "분");
          user_info.total_time.push(hour + "시간" + " " + minutes + "분");
          // console.log();
        }
      });

      // user_info.time = user_info.time.format("HH:mm");

      // var curTime = Date.now();
      // curTime = moment(curTime);
      // curTime = curTime.format("HH:mm");
      // console.log(curTime);
    });

    return {
      user_info,
    };
  },
};
</script>

<style scoped>
.user-time-wrap {
  width: 100%;
  height: 100%;
}
.user-card {
  float: left;
  margin-left: 10px;
}
.time-card {
  float: right;
  margin-right: 10px;
}
.name-info {
  height: 50px;
  display: flex;
  align-items: center;
}
.time-info {
  height: 50px;
  display: flex;
  align-items: center;
}
.avatar {
  width: 30px;
  margin: 5px;
}
</style>
