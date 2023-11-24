<script setup>
import { onMounted, ref, computed, watch } from "vue";
import { useUserInfoStore } from "../stores/useUserInfoStore.js";
import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";
import ContentTypeId from "../components/ContentTypeId.json";
import VKakaoMap from "../components/VKakaoMap.vue";
import { toStringByFormatting } from "../util/dateFormat.js";
import axios from "axios";

const rail = ref(true);
const drawer = ref(true);

const URL = {
  1: "http://localhost/vue",
  2: "http://192.168.0.2/vue",
  3: "http://192.168.31.75/vue",
};

const route = useRoute();
const router = useRouter();
const store = useUserInfoStore();
const isFromAnotherRoute = ref(false);
const searchAttrResult = ref([]);
const searchPlanResult = ref([]);
const presentList = ref([]);

const userId = ref("");
const sidoList = ref([]);
const sWord = ref("");
const contentTypeList = ref([]);

const curSelectedPlanner = ref({
  plannerId: 0,
  plannerTitle: "",
});

const curPlannerList = ref([]);

watch(userId, (newUserId) => {});

const gugunData = ref({});
const gugunList = ref([]);
const selectedGugun = ref({
  gugunCode: 0,
  gugunName: "",
});
const selectedContentType = ref({
  contentName: "",
  contentTypeId: 0,
});
const selectedSido = ref({
  sidoName: "",
  sidoCode: 0,
});

const contentType = {
  12: "관광지",
  14: "문화시설",
  15: "축제공연행사",
  28: "레포츠",
  32: "숙박",
  38: "쇼핑",
  39: "음식점",
};

const isSelected = computed(() => {
  return selectedSido.value.sidoCode == 0;
});

const selectList = ref([]); // 지도 용

const startDate = ref("");
const endDate = ref("");

const selectedPlannerId = ref(0);
const planItems = ref([]);

const planItemsLen = computed(() => {
  return planItems.value.length > 0;
});

const isLogin = computed(() => {
  return userId.value.length > 0;
});

const toggle = ref(0);
const searchBoxToggle = ref(true);

const selectedMapItem = ref({});

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

watch(
  () => route.query,
  async ({ type, sidoCode, gugunCode, contentTypeId, word, startDate, endDate }) => {
    console.log("여기로 들어오나요?");
    if (type == 0) {
      let obj = {
        sidoCode: parseInt(sidoCode),
        gugunCode: parseInt(gugunCode),
        contentTypeId: parseInt(contentTypeId),
        searchWord: word,
      };
      const res = await axios.post(`${URL[1]}/attr/search`, JSON.stringify(obj), {
        headers: {
          "Content-type": "application/json",
        },
      });
      searchAttrResult.value = res.data.resultData;
      selectedContentType.value = {
        contentName: "관광지",
        contentTypeId: 12,
      };
      selectedSido.value = {
        sidoCode: 0,
        sidoName: "",
      };
      selectedGugun.value = {
        gugunCode: 0,
        gugunName: "",
      };
      sWord.value = "";
    } else {
      let obj = {
        searchWord: word,
        startDate: toStringByFormatting(new Date(startDate)),
        endDate: toStringByFormatting(new Date(endDate)),
      };
      const res = await axios.post(`${URL[1]}/plan/search`, JSON.stringify(obj), {
        headers: {
          "Content-type": "application/json",
        },
      });
      searchPlanResult.value = res.data.resultData;
    }
  }
);

const myPlanList = ref([]);
const curSelectedMyPlan = ref({
  startDate: "",
  endDate: "",
  plannerTitle: "",
  plannerId: 0,
  attractionDto: [],
});

const startDateInput = ref("");
const endDateInput = ref("");
const plannerTitleInput = ref("");

const selectedPlanItems = ref([]);

watch(curSelectedMyPlan, (newValue) => {
  if (newValue.plannerId != 0) {
    selectList.value = newValue.attractionDto;
    let newPlanItems = [];
    newValue.attractionDto.map((plannerItem, idx) => {
      newPlanItems.push({
        ...plannerItem,
        idx,
      });
    });
    startDateInput.value = curSelectedMyPlan.value.startDate;
    endDateInput.value = curSelectedMyPlan.value.endDate;
    plannerTitleInput.value = curSelectedMyPlan.value.plannerTitle;
    selectedPlanItems.value = newPlanItems;
  }
});

watch(selectedPlanItems, (newValue) => {
  selectList.value = newValue;
});

const removePlannerItem = (idx) => {
  selectedPlanItems.value = selectedPlanItems.value.filter((item) => {
    return item.idx != idx;
  });
};

const savePlan = async () => {
  if (curSelectedMyPlan.value.plannerId == 0) {
    alert("적어도 하나 이상의 plan을 선택 해 주세요");
    return false;
  }

  if (
    new Date(startDateInput.value).getMilliseconds() >
    new Date(endDateInput.value).getMilliseconds()
  ) {
    alert("시작 날짜는 끝 날짜보다 더 클수 없습니다.");
    return false;
  }

  if (selectedPlanItems.value.length == 0) {
    alert("플래너에 선택된 장소는 적어도 하나 이상이어야 합니다.");
    return false;
  }

  let obj = {
    plannerTitle: plannerTitleInput.value,
    startDate: toStringByFormatting(new Date(startDateInput.value)),
    endDate: toStringByFormatting(new Date(endDateInput.value)),
    plannerItems: selectedPlanItems,
  };
  await axios.put(`${URL[1]}/plan/${curSelectedMyPlan.value.plannerId}`, JSON.stringify(obj), {
    headers: {
      "Content-Type": "application/json",
    },
  });
  await refreshPlan();
};

const removePlan = async () => {
  if (confirm("해당 계획을 정말 삭제하시겠습니까?")) {
    await axios.delete(`${URL[1]}/plan/${curSelectedMyPlan.value.plannerId}`);
  }

  await refreshPlan();
};

const refreshPlan = async () => {
  if (confirm("지금까지 수정한 사항에 대해서 날라갈 수도 있습니다. 초기화 하겠습니까?")) {
    const res = await axios.get(`${URL[1]}/plan/${userId}`);
    myPlanList.value = res.data.resultData;
  }
};

const planToggle = ref(0);

watch(planToggle, async (newPlanToggleFlg, oldPlanToggleFlg) => {
  if (newPlanToggleFlg == 0) {
    await refreshPlan();
  }
});

const addedPlanStartDate = ref("");
const addedPlanEndDate = ref("");
const addedPlanTitle = ref("");
const addedPlanItems = ref([]);

const saveAddedPlan = async () => {
  let obj = {
    userId: userId.value,
    startDate: toStringByFormatting(new Date(addedPlanStartDate.value)),
    endDate: toStringByFormatting(new Date(addedPlanEndDate.value)),
    plannerTitle: addedPlanTitle.value,
    plannerItems: addedPlanItems.value,
  };
  await axios.post(`${URL[1]}/plan`, JSON.stringify(obj), {
    headers: {
      "Content-Type": "application/json",
    },
  });
};

const removeAddedPlanItems = (idx) => {
  addedPlanItems.value = addedPlanItems.value.filter((item) => {
    return item.idx != idx;
  });
};

onMounted(async () => {
  // init
  const res = await store.getUserId();
  userId.value = res;
  console.log(userId.value);
  contentTypeList.value = ContentTypeId;
  selectedContentType.value = { contentName: "관광지", contentTypeId: 12 };
  axios.get(`${URL[1]}/attr/sido`).then((res) => {
    sidoList.value = res.data.resultData;
  });
  const { type, sidoCode, gugunCode, contentTypeId, word, startDate, endDate, plannerId } =
    route.query;
  let obj = {};
  toggle.value = type;
  if (type == 0) {
    obj = {
      sidoCode,
      gugunCode,
      contentTypeId,
      searchWord: word,
    };
    const res = await axios.post(`${URL[1]}/attr/search`, JSON.stringify(obj), {
      headers: {
        "Content-Type": "application/json",
      },
    });
    searchAttrResult.value = res.data.resultData;

    console.log(searchAttrResult.value);
  } else if (type == 1 && plannerId) {
    const res = await axios.get(`${URL[1]}/plan/${plannerId}`);
    searchPlanResult.value = [res.data.resultData];
    selectedPlannerId.value = plannerId;
  }

  if (userId.value) {
    const res = await axios.get(`${URL[1]}/plan/user/${userId.value}`);
    myPlanList.value = res.data.resultData;
    console.log(myPlanList.value);
  }
});

watch(searchAttrResult, (newData) => {
  searchAttrResult.value = newData;
  if (newData.length > 0) {
    selectList.value = newData;
  }
});

const submitAttrEvent = () => {
  if (toggle.value == 0 && selectedSido.value.sidoCode == 0) {
    alert("시/도 선택은 검색 최소 조건입니다.");
    return false;
  }
  if (toggle.value == 0 && selectedContentType.value.contentTypeId == 0) {
    alert("옳바르지 않은 선택지 입니다.");
    return false;
  }
  router.push({
    name: "map",
    query: {
      type: toggle.value,
      sidoCode: selectedSido.value.sidoCode,
      gugunCode: selectedGugun.value.gugunCode,
      contentTypeId: selectedContentType.value.contentTypeId,
      word: sWord.value,
    },
  });
};

const selectPlanOne = (plannerId) => {
  selectedPlannerId.value = plannerId;
};

const searchBoxToggleEvent = () => {
  searchBoxToggle.value = !searchBoxToggle.value;
};

const submitPlannerEvent = () => {
  if (startDate.value == "" || endDate.value == "") {
    alert("시작 일과 끝 일은 필수 선택입니다.");
    return false;
  }
  if (new Date(startDate.value).getMilliseconds() > new Date(endDate.value).getMilliseconds()) {
    alert("시작일 또는 끝 일이 잘못 선택되었습니다.");
    return false;
  }
  router.push({
    name: "map",
    query: {
      type: toggle.value,
      startDate: startDate.value,
      endDate: endDate.value,
      word: sWord.value,
    },
  });
};

const getPlanItems = async () => {
  const res = await axios.get(`${URL[1]}/plan/${selectedPlannerId.value}/items`);
  planItems.value = res.data.resultData.attractionDto;
  selectList.value = res.data.resultData.attractionDto;
};

watch(selectedPlannerId, (newValue) => {
  getPlanItems();
});

watch(planItems, (newValue) => {
  selectList.value = newValue;
  planItems.value = newValue;
});

const goBoard = () => {
  router.push({ name: "board" });
};

const goHome = () => {
  router.push({ name: "home" });
};

const selectOne = ref({});

const selectMapItem = (item) => {
  selectOne.value = item;
};

const markerFlag = ref(false);
const setMarker = () => {
  markerFlag.value = true;
};

const markSearch = () => {
  if (toggle.value == 0) {
    selectList.value = searchAttrResult.value;
  } else {
    selectList.value = planItems.value;
  }
};

const markPlan = () => {
  if (planToggle == 0) {
    selectList.value = selectedPlanItems.value;
  } else if (planToggle == 1) {
    selectList.value = addedPlanItems.value;
  }
};

const addPlanItem = (item) => {
  if (planToggle.value == 0) {
    let obj = [];
    if (selectedPlanItems.value.length == 0) {
      obj.push({
        ...item,
        idx: 0,
      });
    } else {
      obj = [...selectedPlanItems.value];
      let lastidx = obj[obj.length - 1].idx;
      obj.push({
        ...item,
        idx: lastidx + 1,
      });
    }
    selectedPlanItems.value = obj;
  } else {
    let obj = [];
    if (addedPlanItems.value.length == 0) {
      obj.push({
        ...item,
        idx: 0,
      });
    } else {
      obj = [...addedPlanItems.value];
      let lastidx = obj[obj.length - 1].idx;
      obj.push({
        ...item,
        idx: lastidx + 1,
      });
    }
    addedPlanItems.value = obj;
  }
};
</script>

<template>
  <VKakaoMap
    :selectOne="selectOne"
    :selectList="selectList"
    :isSetMarker="markerFlag"
    :isLogin="isLogin"
    @get-item="addPlanItem"
  ></VKakaoMap>
  <v-layout>
    <!-- 왼쪽 사이드바 영역 시작 -->
    <v-navigation-drawer permanent :width="400" class="layout">
      <div class="header-layout">
        <v-btn variant="outlined" @click="markSearch"> 검색결과마킹 </v-btn>
        <v-btn variant="outlined" @click="goBoard"> 게시판 </v-btn>
        <v-btn variant="outlined" @click="goHome"> 홈으로 </v-btn>
        <v-tooltip text="검색결과가 반영이 안될때 눌러주세요">
          <template v-slot:activator="{ props }">
            <v-btn variant="outlined" @click="setMarker" v-bind="props">마커 로딩</v-btn>
          </template>
        </v-tooltip>
      </div>

      <v-divider></v-divider>
      <div class="toggle-box">
        <v-btn-toggle v-model="toggle" divided>
          <v-btn>관광지 검색</v-btn>
          <v-btn>플래너 검색</v-btn>
        </v-btn-toggle>
      </div>

      <v-divider></v-divider>
      <div v-if="searchBoxToggle" class="search-layout">
        <template v-if="toggle == 0">
          <div class="select-layout">
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
          </div>
          <div class="util-box search-util-box">
            <v-text-field
              clearable
              label="검색어"
              variant="outlined"
              v-model="sWord"
            ></v-text-field>
            <v-btn variant="outlined" @click="submitAttrEvent"> 검색 </v-btn>
          </div>
        </template>
        <template v-if="toggle == 1">
          <div class="select-layout mg">
            <div class="date-box">
              <div>기간</div>
              <input id="startDate" type="date" v-model="startDate" />
              ~
              <input id="endDate" type="date" v-model="endDate" />
            </div>
          </div>
          <div class="util-box search-util-box">
            <v-text-field
              clearable
              label="검색어"
              variant="outlined"
              v-model="sWord"
            ></v-text-field>
            <v-btn variant="outlined" @click="submitPlannerEvent"> 검색 </v-btn>
          </div>
        </template>
      </div>
      <div v-if="searchBoxToggle" class="searchBoxToggleBtn">
        <div class="toggle-btn-layout">
          <v-btn style="" variant="outlined" @click="searchBoxToggleEvent">
            <i
              class="mdi-chevron-up mdi v-icon notranslate v-theme--light v-icon--size-default"
            ></i>
          </v-btn>
        </div>
      </div>
      <div v-if="!searchBoxToggle" class="searchBoxToggleBtn">
        <v-btn class="toggle-btn-layout" variant="outlined" @click="searchBoxToggleEvent">
          <i
            class="mdi-chevron-down mdi v-icon notranslate v-theme--light v-icon--size-default"
          ></i>
        </v-btn>
      </div>
      <div class="result-layout">
        <div class="data-layout">
          <template v-if="toggle == 0">
            <v-virtual-scroll
              v-if="searchAttrResult.length > 0"
              :height="500"
              :items="searchAttrResult"
            >
              <template v-slot:default="{ item }">
                <v-card :title="item.title" @click="selectMapItem(item)">
                  <div>주소 : {{ item.addr1 }}</div>
                  <div><img :src="item.firstImage" width="80" height="80" /></div>
                </v-card>
              </template>
            </v-virtual-scroll>
          </template>
          <template v-if="toggle == 1">
            <v-virtual-scroll
              v-if="searchPlanResult.length > 0"
              :height="250"
              :items="searchPlanResult"
            >
              <template v-slot:default="{ item }">
                <v-card :title="item.title" @click="selectPlanOne(item.plannerId)">
                  <div>제목 : {{ item.plannerTitle }}</div>
                  <div>
                    기간 :
                    {{ toStringByFormatting(new Date(item.startDate)) }} ~
                    {{ toStringByFormatting(new Date(item.endDate)) }}
                  </div>
                </v-card>
              </template>
            </v-virtual-scroll>
            <v-divider></v-divider>
            <v-virtual-scroll :height="250" :items="planItems">
              <template v-slot:default="{ item }">
                <v-card :title="item.title" @click="selectMapItem(item)">
                  <div>{{ item.title }}</div>
                  <div>{{ item.addr1 }}</div>
                  <v-button v-if="isLogin" @click="addPlanItem">담기</v-button>
                </v-card>
              </template>
            </v-virtual-scroll>
          </template>
          <template
            v-if="
              (toggle == 1 && searchPlanResult.length == 0) ||
              (toggle == 0 && searchAttrResult.length == 0)
            "
          >
            <div>검색된 결과가 없습니다.</div>
          </template>
        </div>
      </div>
    </v-navigation-drawer>
    <!-- 왼쪽 사이드바 영역 끝 -->

    <!-- 오른쪽 사이드바 영역 시작 -->
    <!-- 구현 미완이라 주석처리 -->
    <!-- 넓이 수정방법 = :width="" -->
    <!-- <v-navigation-drawer
      v-model="drawer"
      :rail="rail"
      permanent
      @click="rail = false"
      location="right"
    >
      <v-list-item nav>
        <template v-slot:append>
          <v-btn variant="text" icon="mdi-chevron-left" @click.stop="rail = !rail"></v-btn>
        </template>
      </v-list-item>

      <v-divider></v-divider>

      <div class="right-sidebar-layout">
        <div class="right-inner-layout">
          <template v-if="isLogin">
            <div>My Planner</div>
            <v-divider></v-divider>
            <div class="right-header">
              <div class="toggle-box">
                <v-btn-toggle v-model="planToggle" divided>
                  <v-btn>나의 플랜 확인</v-btn>
                  <v-btn>플랜 +</v-btn>
                </v-btn-toggle>
              </div>
              <div>
                <v-button @click="markPlan">플랜 마킹</v-button>
                <template v-if="planToggle == 0">
                  <v-select
                    label="내 플래너"
                    v-model="curSelectedMyPlan"
                    :items="myPlanList"
                    item-title="plannerTitle"
                    item-value="plannerId"
                    return-object
                    variant="underlined"
                  ></v-select>
                  <v-text-field
                    clearable
                    label="플래너 제목"
                    variant="outlined"
                    v-model="plannerTitleInput"
                  ></v-text-field>
                  <input type="date" v-model="startDateInput" />
                  <input type="date" v-model="endDateInput" />
                  <v-virtual-scroll :height="250" :items="selectedPlanItems">
                    <template v-slot:default="{ item }">
                      <v-card :title="item.title" @click="selectMapItem(item)">
                        <div class="plannerItem-layout">
                          <div class="plannerItem-body">
                            <div>{{ contentType[item.contentTypeId] }}</div>
                            <div>{{ item.title }}</div>
                            <div>{{ item.addr1 }}</div>
                          </div>
                          <div class="plannerItem-utilbox">
                            <v-button @click="removePlannerItem(item.idx)">제거</v-button>
                          </div>
                        </div>
                      </v-card>
                    </template>
                  </v-virtual-scroll>
                  <div>
                    <v-button @click="savePlan">저장</v-button>
                    <v-button @click="removePlan">삭제</v-button>
                    <v-button @click="refreshPlan">취소</v-button>
                  </div>
                </template>
                <template v-if="planToggle == 1">
                  <input type="date" v-model="startDateInput" />
                  <input type="date" v-model="endDateInput" />
                  <v-virtual-scroll :height="250" :items="selectedPlanItems">
                    <template v-slot:default="{ item }">
                      <v-card :title="item.title" @click="selectMapItem(item)">
                        <div class="plannerItem-layout">
                          <div class="plannerItem-body">
                            <div>{{ contentType[item.contentTypeId] }}</div>
                            <div>{{ item.title }}</div>
                            <div>{{ item.addr1 }}</div>
                          </div>
                          <div class="plannerItem-utilbox">
                            <v-button @click="removeAddedPlanItems(item.idx)">제거</v-button>
                          </div>
                        </div>
                      </v-card>
                    </template>
                  </v-virtual-scroll>
                  <div>
                    <v-button @click="saveAddedPlan">플랜추가</v-button>
                  </div>
                </template>
              </div>
            </div>
          </template>
          <template v-if="!isLogin">
            <div>로그인이 필요한 서비스입니다.</div>
            <div>
              <v-button @click="$router.push({ name: 'login' })">로그인</v-button>
            </div>
          </template>
        </div>
      </div>
    </v-navigation-drawer> -->
    <v-main style="height: 250px"> </v-main>
    <!-- 오른쪽 사이드바 영역 끝 -->
  </v-layout>
</template>

<style scoped>
.header-layout {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  padding-top: 10px;
  padding-bottom: 10px;
}
.search-layout {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
}
.select-layout {
  display: inherit;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
  gap: 8px;
  width: 80%;
}
.result-layout {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
}
.data-layout {
  display: inherit;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
  gap: 8px;
  width: 80%;
}
.mg {
  margin-top: 10px;
  margin-bottom: 10px;
}
.toggle-box {
  width: 100%;
  display: flex;
  justify-content: center;
}
.util-box {
  display: flex;

  gap: 10px;
}
.search-util-box {
  width: 80%;
  margin: 0 auto;
  flex-direction: column;
}
.searchBoxToggleBtn {
  margin-top: 15px;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.toggle-btn-layout {
  width: 80%;
}
.toggle-btn-layout > button {
  width: 100%;
}
.right-sidebar-layout {
  width: 100%;
  height: 100%;
}
right-inner-layout {
  width: 80%;
  height: 100%;
}
.right-header-login-util-Box {
  display: flex;
  flex-direction: row;
}

.plannerItem-layout {
  display: flex;
  flex-direction: column;
}
.plannerItem-body {
  display: flex;
  flex-direction: column;
}
.plannerItem-utilbox {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
</style>
