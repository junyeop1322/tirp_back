<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRoute, onBeforeRouteUpdate } from "vue-router";
import { useRouter } from "vue-router";
import { useUserInfoStore } from "../stores/useUserInfoStore.js";
import { menuStore } from "../stores/menuStore";
import { localAxios } from "../util/localAxios.js";

const userInfo = ref({
  userId: "",
  userPwd: "",
});

const router = useRouter();
const store = useUserInfoStore();
const mStore = menuStore();
const msg = ref("");
const URL = {
  1: "http://localhost/vue",
  2: "http://192.168.0.2/vue",
  3: "http://192.168.31.75/vue",
};
const clickEvent = () => {
  axios
    .post(URL[1] + "/user/login", JSON.stringify(userInfo.value), {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      store.loginProcess(res.data["access-token"]);
      mStore.changeMenuState(false);
      router.go(-1);
    })
    .catch((err) => {
      console.log(err);
      msg.value = err.response.data.message;
    });
};
</script>

<template>
  <v-app id="inspire">
    <v-main class="blue-grey lighten-4">
      <!-- Login Component -->
      <v-container class="loginComplayout">
        <v-card class="loginComp" title="로그인" :text="msg" variant="outlined">
          <v-col class="">
            <v-col>
              <v-text-field
                v-model="userInfo.userId"
                label="아이디"
                required
                variant="outlined"
                hide-details
              ></v-text-field>
            </v-col>

            <v-col>
              <v-text-field
                v-model="userInfo.userPwd"
                label="비밀번호"
                type="password"
                hide-details
                variant="outlined"
                required
              ></v-text-field>
            </v-col>
          </v-col>
          <v-col>
            <v-col>
              <v-btn @click="clickEvent" variant="outlined" class="md-6" block size="large"
                >로그인 하기</v-btn
              >
              <!--<v-btn @click="joinEvent">회원가입 하기</v-btn>-->
            </v-col>
          </v-col>
        </v-card>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>
.msgBox {
  text-align: center;
  color: orangered;
}
.loginLayout {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.loginComplayout {
  display: flex;
  height: 80%;
  align-items: center;
  justify-content: center;
}
.loginComp {
  min-width: 450px;
}
</style>
