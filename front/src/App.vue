<script setup>
import SideBar from "./components/SideBar.vue";
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
import Header from "./components/Header.vue";

const route = useRoute();

const display = ref(true);

watch(
  () => route.name,
  (name, oldName) => {
    if (name === "map") {
      display.value = false;
    } else {
      display.value = true;
    }
  }
);
</script>

<template>
  <Header v-if="display"></Header>
  <div class="mainLayout">
    <router-view></router-view>
  </div>
</template>

<style scoped>
.layout {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: row;
}
.mainLayout {
  height: 100%;
  width: 100%;
}
.sidelayout {
  border: 1px solid #dfdfdf;
  height: 100%;
  width: 0%;
  transition: all ease 0.5s 0s;
}
.sideBarSlideOut {
  width: 30%;
}
.sideBarSlideIn {
  width: 0%;
}

.fulllayout {
  width: 100%;
}
.contentlayout {
  width: 70%;
}
</style>
<style>
html {
  height: 100%;
}
body {
  padding: 0;
  margin: 0;
  height: 100%;
}
#app {
  height: 100%;
}
</style>
<style>
.pointer {
  cursor: pointer;
}
</style>
<style>
@font-face {
  font-family: "customFont";
  src: url("./components/font.ttf");
}
* {
  font-family: "customFont";
}
</style>
