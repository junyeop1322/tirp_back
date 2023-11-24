<script setup>
import { useRoute } from "vue-router";
defineEmits(["toggle"]);
const route = useRoute();
console.log(route.fullPath);
</script>

<template>
  <div>
    <p>사이드 바 입니다.</p>
    <router-link :to="{ name: 'login' }">로그인</router-link>
  </div>
</template>

<style scoped></style>
