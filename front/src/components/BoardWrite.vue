<script setup>
import { ref, onMounted, watch, renderSlot } from "vue";
import { onBeforeRouteLeave, useRoute, useRouter } from "vue-router";
import { useUserInfoStore } from "../stores/useUserInfoStore";
import axios from "axios";
const route = useRoute();
const router = useRouter();
const userStore = useUserInfoStore();

const URL = {
  1: "http://localhost/vue",
  2: "http://192.168.0.2/vue",
  3: "http://192.168.31.75/vue",
};
const curArticleNo = ref(0);
const curUserId = ref("");
const curSelectedCate = ref({
  name: "",
  value: 1,
});
const curSelectedLink = ref({
  title: "",
  value: "",
});
const data = ref({
  userId: "",
  subject: "",
  content: "",
  category: 1,
});
const selectList = ref([]);
const linkSelectList = ref([]);

watch(
  () => curSelectedCate.value,
  // async need
  async (category) => {
    if (category.value == 2) {
      isOpen.value = true;
      let newLinkList = [];
      const list = await axios.get(`${URL[1]}/plan/list/${curUserId.value}`);
      list.data.resultData.map((planner) => {
        newLinkList.push({
          value: planner.plannerId,
          title: planner.plannerTitle,
        });
      });
      linkSelectList.value = newLinkList;
    } else {
      curSelectedLink.value = { title: "", value: "" };
      isOpen.value = false;
    }
  }
);

onBeforeRouteLeave(() => {
  if (route.name === "board-write") {
    let subjectLen = data.value.subject.length;
    let contentlen = data.value.content.length;
    if (subjectLen > 0 || contentlen > 0) {
      return confirm("작성하던 내용이 있습니다. 정말 떠나시겠습니까?");
    }
  }
});

const isOpen = ref(false);

onMounted(async () => {
  let newObj = {};
  let newSelectList = [
    { name: "자유", value: 1 },
    { name: "후기", value: 2 },
  ];
  let name = "";
  curUserId.value = await userStore.getUserId();
  newObj.userId = curUserId.value;
  if (route.name == "board-update") {
    curArticleNo.value = route.params.articleNo;
    const res = await axios.get(`${URL[1]}/board/${curArticleNo.value}`);
    const { articleNo, category, content, date, hit, subject, userId, userName, plannerId } =
      res.data.resultData;
    // {
    //     "articleNo": 92,
    //     "userId": "ssafy4",
    //     "userName": "박싸피",
    //     "subject": "안녕안녕1",
    //     "content": "sodyddjlswkselfma",
    //     "hit": 2,
    //     "date": "2023-11-21 02:55:16",
    //     "category": 1,
    //     "fileInfos": []
    // }
    newObj.subject = subject;
    newObj.content = content;
    newObj.category = category;

    if (category == 2) {
      newObj.plannerId = plannerId;
      console.log(plannerId);
      name = "후기";
      const res = await axios.get(`${URL[1]}/plan/${newObj.plannerId}`);
      console.log(res.data.resultData);
      curSelectedLink.value = {
        title: res.data.resultData.plannerTitle,
        value: res.data.resultData.plannerId,
      };
      console.log(curSelectedLink.value);
    } else if (category == 1) {
      name = "자유";
    } else if (category == 0) {
      name = "공지";
    }
    newSelectList = { name, value: category };
    data.value.category = category;
    curSelectedCate.value = newSelectList;
    selectList.value = [newSelectList];
    data.value = newObj;
    console.log(data.value);
  } else {
    data.value.userId = curUserId.value;
    if (curUserId.value === "admin") {
      newSelectList.push({ name: "공지", value: 0 });
    }
    selectList.value = newSelectList;
  }
});

const goBackEvent = () => {
  router.push({
    name: "board-list",
    query: {
      pgno: route.query.pgno,
      spp: route.query.spp,
      key: route.query.key,
      word: route.query.word,
      cate: route.query.cate,
    },
  });
};

const submitEvent = () => {
  let obj = {
    userId: "",
    subject: "",
    content: "",
    category: 1,
  };
  obj.userId = data.value.userId;
  console.log(data.value.userId);
  obj.subject = data.value.subject;
  obj.content = data.value.content;
  obj.category = curSelectedCate.value.value;
  console.log(obj.category);
  if (route.name === "board-update") {
    obj.articleNo = curArticleNo.value;
  }
  if (obj.category == 2) {
    obj.plannerId = curSelectedLink.value.value;
    console.log(obj.plannerId);
  }

  if (route.name === "board-update") {
    try {
      const res = axios.put(`${URL[1]}/board`, JSON.stringify(obj), {
        headers: {
          "Content-type": "application/json",
        },
      });
      goBackEvent();
    } catch (err) {
      if (err.response.status == 500) {
        alert("서버에서 문제가 발생하였습니다.");
      }
      console.log(err);
    }
  } else {
    try {
      const res = axios.post(`${URL[1]}/board`, JSON.stringify(obj), {
        headers: {
          "Content-type": "application/json",
        },
      });
      console.log(res.data);
      goBackEvent();
    } catch (err) {
      if (err.response.status == 500) {
        alert("서버에 문제가 발생하였습니다.");
        console.log(err);
      }
    }
  }
};
</script>

<template>
  <v-card class="layout">
    <div class="write-layout">
      <div class="write-layout-header">
        <div>
          <v-text-field
            clearable
            label="제목"
            v-model="data.subject"
            variant="underlined"
          ></v-text-field>
        </div>
        <div>
          <v-select
            label="cate"
            v-model="curSelectedCate"
            :items="selectList"
            item-title="name"
            item-value="value"
            return-object
            variant="underlined"
          ></v-select>
        </div>
        <template v-if="isOpen">
          <div>
            <v-select
              label="플래너 선택"
              v-model="curSelectedLink"
              :items="linkSelectList"
              item-title="title"
              item-value="href"
              return-object
              variant="underlined"
            ></v-select>
          </div>
        </template>
      </div>
      <div class="write-layout-body">
        <v-textarea clearable label="내용" v-model="data.content" variant="outlined"></v-textarea>
      </div>
      <div class="util-box">
        <div></div>
        <div class="util-box-btn-group">
          <button @click="goBackEvent">뒤로가기</button>
          <button @click="submitEvent">
            {{ route.name === "board-update" ? "수정" : "글쓰기" }}
          </button>
        </div>
      </div>
    </div>
  </v-card>
</template>

<style scoped>
.layout {
  margin-top: 40px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  max-height: 480px;
}
.write-layout {
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  width: 80%;
}
.write-layout div {
  width: 100%;
}
.util-box {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.util-box-btn-group {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 10px;
}
</style>
<style>
textarea {
  resize: none !important;
}
</style>
