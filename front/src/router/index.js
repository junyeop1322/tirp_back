import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import UserLogin from "../components/UserLogin.vue";
import UserRegister from "../components/UserRegister.vue";
import BoardWrite from "../components/BoardWrite.vue";
import BoardView from "../views/BoardView.vue";
import { useUserInfoStore } from "../stores/useUserInfoStore.js";
import { menuStore } from "../stores/menuStore.js";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/user",
      name: "",
      component: LoginView,
      children: [
        {
          path: "login",
          name: "login",
          component: UserLogin,
          beforeEnter: async (to, from) => {
            const store = useUserInfoStore();
            const mStore = menuStore();
            const test = await store.isLoginCheck();
            if (from.name === "login") {
              return false;
            }
            if (!test) {
              mStore.changeMenuState(false);
              return true;
            } else {
              alert("이미 로그인 된 상태입니다.");
              mStore.changeMenuState(true);
              return { name: from.name };
            }
          },
        },
        {
          path: "join",
          name: "join",
          component: UserRegister,
          beforeEnter: async (to, from) => {
            const store = useUserInfoStore();
            const test = await store.isLoginCheck();
            const mStore = menuStore();
            if (from.name === "join") {
              return false;
            }
            if (!test) {
              mStore.changeMenuState(false);
              return true;
            } else {
              alert("이미 로그인 된 상태입니다.");
              mStore.changeMenuState(true);
              return { name: from.name };
            }
          },
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: BoardView,
      redirect: {
        name: "board-list",
        query: { pgno: 1, spp: 10, key: "", word: "", cate: "1" },
      },
      children: [
        {
          path: "list",
          name: "board-list",
          component: () => import("../components/BoardList.vue"),
        },
        {
          path: ":articleNo",
          name: "board-view",
          component: () => import("../components/BoardView.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: BoardWrite,
          beforeEnter: async (to, from) => {
            const userStore = useUserInfoStore();
            const chk = await userStore.isLoginCheck();
            if (!chk) {
              alert("로그인이 필요합니다.");
              return { name: "login" };
            }
          },
        },
        {
          path: "update/:articleNo",
          name: "board-update",
          component: () => import("../components/BoardWrite.vue"),
          beforeEnter: async (to, from) => {
            const userStore = useUserInfoStore();
            const chk = await userStore.isLoginCheck();
            if (!chk) {
              alert("로그인이 필요합니다.");
              return { name: "login" };
            }
          },
        },
      ],
    },
    {
      path: "/map",
      name: "map",
      component: () => import("../views/MapView.vue"),
    },
  ],
});
router.afterEach(async (to, from) => {
  const mStore = menuStore();
  const store = useUserInfoStore();
  const test = await store.isLoginCheck();
  mStore.changeMenuState(test);
});
export default router;
