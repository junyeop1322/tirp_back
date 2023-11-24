<script setup>
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref, watch } from "vue";
import { useUserInfoStore } from "../stores/useUserInfoStore";
import { menuStore } from "../stores/menuStore";
import { storeToRefs } from "pinia";
import axios from "axios";
import _default from "pinia-plugin-persistedstate";
const route = useRoute();
const router = useRouter();
const store = useUserInfoStore();
const mStore = menuStore();
const { menuList } = storeToRefs(mStore);
const go = (name) => {
  router.push({ name });
};
//const isLogin = ref(store.isLogin);
const userId = ref("");
// watch(
//   () => store.isLogin,
//   (login) => {
//     isLogin.value = login;
//     console.log("isLogin : " + isLogin.value);
//     if (isLogin.value) {
//       userId.value = store.decodedToken(localStorage.getItem("refreshToken"));
//     }
//   }
// );

const URL = {
  1: "http://localhost/vue",
  2: "http://192.168.0.2/vue",
  3: "http://192.168.31.75/vue",
};
const logoutEvent = () => {
  if (!localStorage.accessToken) {
    mStore.changeMenuState(false);
    return;
  }
  const userId = store.decodedToken(localStorage.accessToken);
  axios
    .get(`${URL[1]}/user/logout/${userId}`)
    .then((res) => {
      if (res.status === 200) {
        mStore.changeMenuState(true);
        localStorage.removeItem("accessToken");
        router.go(0, { replace: true });
      }
    })
    .catch((err) => console.log(err));
};
const goList = () => {
  router.push({ name: "board" });
};
const goMap = () => {
  router.push({ name: "map", query: { type: 0 } });
};
</script>

<template>
  <v-toolbar>
    <v-toolbar-title class="pointer" @click="go('home')">EnjoyTrip</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items>
      <v-btn flat @click="goMap">여행지도</v-btn>
      <v-btn flat @click="goList">게시판</v-btn>
      <template v-for="menu in menuList" :key="menu.routeName">
        <template v-if="menu.show">
          <template v-if="menu.routeName === 'logout'">
            <v-btn flat @click="logoutEvent">{{ menu.name }}</v-btn>
          </template>
          <template v-else>
            <v-btn flat @click="go(menu.routeName)">{{ menu.name }}</v-btn>
          </template>
        </template>
      </template>
    </v-toolbar-items>

    <!-- 
      <v-toolbar-items v-if="!isLogin">
      <v-btn flat @click="go('login')">로그인</v-btn>
      <v-btn flat @click="go('join')">회원가입</v-btn>
    </v-toolbar-items>
    <v-toolbar-items v-if="isLogin">
      <v-btn flat>{{ userId }}</v-btn>
      <v-btn flat @click="logoutEvent">로그아웃</v-btn>
    </v-toolbar-items>
     -->
  </v-toolbar>
</template>
<style scoped></style>
