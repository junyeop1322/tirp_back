<script setup></script>

<template>
  <div class="layout">
    <div class="boardlayout"><router-view></router-view></div>
  </div>
</template>

<style scoped>
.layout {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.boardlayout {
  width: 65%;
}
</style>
