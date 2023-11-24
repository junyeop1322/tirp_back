import { defineStore } from "pinia";
import { ref } from "vue";

export const menuStore = defineStore(
  "menuStore",
  () => {
    const menuList = ref([]);

    const unlogin = [
      { name: "회원가입", show: true, routeName: "join" },
      { name: "로그인", show: true, routeName: "login" },
    ];
    const login = [{ name: "로그아웃", show: true, routeName: "logout" }];

    const changeMenuState = (isLogin) => {
      if (isLogin) {
        menuList.value = login;
      } else {
        menuList.value = unlogin;
      }
      console.log(menuList.value);
    };
    return {
      menuList,
      changeMenuState,
    };
  },
  { persist: true }
);
