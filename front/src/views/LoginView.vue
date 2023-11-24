<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRoute, onBeforeRouteUpdate } from "vue-router";
import { useRouter } from "vue-router";
import { useUserInfoStore } from "../stores/useUserInfoStore.js";
</script>

<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<style scoped></style>
