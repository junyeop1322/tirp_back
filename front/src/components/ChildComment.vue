<script setup>
import { ref } from "vue";
const props = defineProps({
  userId: String,
  editable: Boolean,
  childComment: Object,
});
const emit = defineEmits(["childCommentRemove", "childCommentUpdate"]);

const commentContent = ref(props.childComment.content);
const editable = ref(props.editable);
const editToggle = ref(false);
const toggleClickEvent = () => {
  editToggle.value = !editToggle.value;
};
const updateSubmitEvent = () => {
  let obj = {};
  obj = {
    ...props.childComment,
    content: commentContent.value,
  };
  emit("childCommentUpdate", obj);
  toggleClickEvent();
};

const removeEvent = () => {
  if (confirm("해당 댓글을 정말 삭제하시겠습니까?")) {
    emit("childCommentRemove", props.childComment);
  }
};
</script>

<template>
  <v-textarea
    :label="props.childComment.userId"
    v-model="commentContent"
    auto-grow
    rows="1"
    variant="underlined"
    :readonly="!editToggle"
  >
  </v-textarea>
  <div v-if="editable" class="comment-util-box">
    <button @click="toggleClickEvent">수정</button>
    <button @click="removeEvent">삭제</button>
    <div v-if="editToggle">
      <button @click="updateSubmitEvent">수정완료</button>
    </div>
  </div>
</template>

<style scoped>
.comment-util-box {
  display: flex;
  flex-direction: row;
  gap: 10px;
  font-size: 9pt;
}
</style>
