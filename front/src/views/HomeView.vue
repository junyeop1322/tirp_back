<script setup>
console.log("fdsafafafdsfasfsa");
import axios from "axios";
import { useUserInfoStore } from "../stores/useUserInfoStore.js";
import { onMounted, ref, computed, watch } from "vue";
import { useRouter } from "vue-router";
import ContentTypeId from "../components/ContentTypeId.json";
const URL = {
  1: "http://localhost/vue",
  2: "http://192.168.0.2/vue",
  3: "http://192.168.31.75/vue",
};
const router = useRouter();
const store = useUserInfoStore();
const sidoList = ref([]);
const selectedSido = ref({
  sidoName: "",
  sidoCode: 0,
});
watch(
  () => selectedSido.value,
  ({ sidoName, sidoCode }) => {
    selectedGugun.value = {
      gugunName: "선택 해 주세요",
      gugunCode: 0,
    };
    if (sidoCode != 0) {
      gugunList.value = gugunData.value[sidoCode];
      axios.get(`${URL[1]}/attr/${sidoCode}/gugun`).then((res) => {
        gugunList.value = res.data.resultData;
      });
    }
  }
);
const isSelected = computed(() => {
  return selectedSido.value.sidoCode == 0;
});
const gugunData = ref({});
const gugunList = ref([]);
const selectedGugun = ref({
  gugunCode: 0,
  gugunName: "",
});

const selectedContentType = ref({});

const contentTypeList = ref([]);

const word = ref("");

const attrCount = ref(0);
const planCount = ref(0);
const boardCount = ref(0);
const searchRank = ref([]);

onMounted(() => {
  contentTypeList.value = ContentTypeId;
  selectedContentType.value = { contentName: "관광지", contentTypeId: 12 };

  axios.get(`${URL[1]}/attr/sido`).then((res) => {
    sidoList.value = res.data.resultData;
  });

  axios.get(`${URL[1]}/attr/count`).then((res) => {
    attrCount.value = parseInt(res.data.resultData);
  });

  axios.get(`${URL[1]}/plan/count`).then((res) => {
    planCount.value = parseInt(res.data.resultData);
  });

  axios.get(`${URL[1]}/board/count`).then((res) => {
    boardCount.value = parseInt(res.data.resultData);
  });
});

const submitEvent = () => {
  if (selectedSido.value.sidoCode == 0) {
    alert("시/도 선택은 검색 최소 조건입니다.");
    return false;
  }
  if (selectedContentType.value.contentTypeId < 12) {
    alert("잘못된 선택입니다.");
    return false;
  }

  router.push({
    name: "map",
    query: {
      type: 0,
      sidoCode: selectedSido.value.sidoCode,
      gugunCode: selectedGugun.value.gugunCode,
      contentTypeId: selectedContentType.value.contentTypeId,
      word: word.value,
    },
  });
};
const goBoardEvent = () => {
  router.push({ name: "board" });
};
</script>

<template>
  <div class="homelayout">
    <div class="searchlayout">
      <div class="homelayout-title">
        <h1>여행 가고싶은 곳을 선택하고 검색하세요</h1>
      </div>
      <div class="homelayout-body">
        <div class="searchBar">
          <v-select
            label="시/도"
            v-model="selectedSido"
            :items="sidoList"
            item-title="sidoName"
            item-value="sidoCode"
            variant="outlined"
            return-object
          ></v-select>
          <v-select
            clearable
            label="구/군"
            v-model="selectedGugun"
            :items="gugunList"
            item-title="gugunName"
            item-value="gugunCode"
            return-object
            variant="outlined"
            :disabled="isSelected"
          ></v-select>
          <v-select
            clearable
            label="종류"
            v-model="selectedContentType"
            :items="contentTypeList"
            item-title="contentName"
            item-value="contentTypeId"
            return-object
            variant="outlined"
          ></v-select>
          <v-text-field clearable label="검색어" variant="outlined" v-model="word"></v-text-field>
          <button class="submit-btn" @click="submitEvent">검색</button>
        </div>
      </div>
    </div>
  </div>
  <div class="belowlayout">
    <div class="cardlayout">
      <div class="cardTitle">
        <h1>저희 사이트는 이런 정보가 있어요</h1>
      </div>
      <div class="cardbody">
        <div class="carditem">
          <div class="carditem-title">
            전체 관광지 수
            <font-awesome-icon
              icon="fa-solid fa-arrow-up-right-from-square"
              style="cursor: pointer"
              @click="$router.push({ name: 'map', query: { type: 0 } })"
            />
          </div>
          <div class="carditem-body">
            <vue3-autocounter
              ref="counter"
              :startAmount="0"
              :endAmount="attrCount"
              :duration="1"
              suffix=" 개"
              separator=","
              :autoinit="true"
            />
          </div>
        </div>
        <div class="carditem">
          <div class="carditem-title">
            전체 플레너 수
            <!-- <font-awesome-icon
              icon="fa-solid fa-arrow-up-right-from-square"
              @click="goBoardEvent"
            /> -->
          </div>
          <div class="carditem-body">
            <vue3-autocounter
              ref="counter"
              :startAmount="0"
              :endAmount="planCount"
              :duration="1"
              suffix=" 개"
              separator=","
              :autoinit="true"
            />
          </div>
        </div>
        <div class="carditem">
          <div class="carditem-title">
            전체 게시글 개수
            <font-awesome-icon
              icon="fa-solid fa-arrow-up-right-from-square"
              @click="goBoardEvent"
              style="cursor: pointer"
            />
          </div>
          <div class="carditem-body">
            <vue3-autocounter
              ref="counter"
              :startAmount="0"
              :endAmount="boardCount"
              :duration="1"
              suffix=" 개"
              separator=","
              :autoinit="true"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.homelayout {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  background-color: aquamarine;
  height: 35%;
}
.homelayout-body {
  width: 100%;
}
.searchlayout {
  width: 80%;
  display: flex;
  flex-direction: column;
  gap: 24px;
}
.searchBar {
  display: flex;
  flex-direction: row;
  gap: 30px;
}
.submit-btn {
  border: 1px solid black;
  height: 56px;
  padding-left: 20px;
  padding-right: 20px;
}
.homelayout-title {
  display: row;
}
.belowlayout {
  margin-top: 30px;
  height: 65%;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.cardlayout {
  width: 80%;
  display: inherit;
  flex-direction: column;
  gap: 30px;
}
.cardbody {
  display: inherit;
  flex-direction: row;
  padding-left: 20px;
  padding-right: 20px;
  gap: 20px;
  flex-wrap: wrap;
  justify-content: center;
}
.carditem {
  border: solid 1px #555;
  min-width: 250px;
  min-height: 240px;
  gap: 1.125rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.carditem-title {
  font-size: 15pt;
  font-weight: bold;
  text-align: center;
}

.carditem-body {
  font-size: 12pt;
  font-weight: lighter;
  text-align: center;
}
</style>
