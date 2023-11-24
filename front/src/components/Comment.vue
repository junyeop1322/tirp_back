<script setup>
import { ref } from "vue";
import ChildComment from "./ChildComment.vue";
const props = defineProps({
  commentData: Object,
  editable: Boolean,
  loginUser: String,
  writer: String,
});
const emit = defineEmits([
  "updateComment",
  "deleteComment",
  "addChildComment",
  "updateChildComment",
  "deleteChildComment",
]);
console.log(props.editable);
console.log("item.userId", props.writer);
console.log("loginUser", props.loginUser);
console.log(props.writer === props.loginUser);
const commentContent = ref(props.commentData.content);
const editable = ref(props.editable);
const editToggle = ref(false);
const toggleClickEvent = () => {
  editToggle.value = !editToggle.value;
};
const childCommentView = ref(false);
const childCommentWriteView = ref(false);
const updateSubmitEvent = () => {
  let obj = {};
  obj = {
    ...props.commentData,
    content: commentContent.value,
  };
  emit("updateComment", obj);
  toggleClickEvent();
};

const removeEvent = () => {
  if (confirm("해당 댓글을 정말 삭제하시겠습니까?")) {
    emit("deleteComment", props.commentData);
  }
};

const childComment = ref("");

const childCommentViewToggle = () => {
  childCommentView.value = !childCommentView.value;
};

const childCommentWriteToggle = () => {
  childCommentWriteView.value = !childCommentWriteView.value;
};

const childCommentUpdate = (childComment) => {
  emit("updateChildComment", childComment);
};

const childCommentRemove = (childComment) => {
  emit("deleteChildComment", childComment);
};

const addChildComment = () => {
  let obj = {
    commentId: null,
    content: childComment.value,
    userId: props.loginUser,
    parentId: props.commentData.commentId,
  };

  emit("addChildComment", obj);
};
</script>

<template>
  <v-textarea
    :label="props.commentData.userId"
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
  <div class="comment-util-box">
    <template v-if="!childCommentView">
      <button @click="childCommentViewToggle">답글 보기</button>
    </template>
    <template v-if="childCommentView">
      <button @click="childCommentViewToggle">답글 닫기</button>
    </template>
    <!-- 로그인 체크해서 답글 달 수 있게 변경 -->
    <button @click="childCommentWriteToggle">답글 달기</button>
  </div>
  <div class="child-comment-layout">
    <div v-if="childCommentWriteView" class="child-comment-write-layout">
      <v-textarea
        :label="props.loginUser"
        v-model="childComment"
        auto-grow
        rows="1"
        variant="underlined"
      ></v-textarea>
      <div class="child-comment-util-box">
        <div></div>
        <div>
          <button @click="addChildComment">제출</button>
          <button @click="childCommentWriteToggle">취소</button>
        </div>
      </div>
    </div>
    <div v-if="childCommentView" class="child-comment-view-layout">
      <ChildComment
        v-for="(item, index) in props.commentData.child"
        :userId="props.loginUser"
        :editable="props.loginUser == item.userId"
        :childComment="item"
        @childCommentRemove="childCommentRemove"
        @childCommentUpdate="childCommentUpdate"
      ></ChildComment>
    </div>
  </div>
</template>

<style scoped>
.comment-util-box {
  display: flex;
  flex-direction: row;
  gap: 10px;
}
.child-comment-layout {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: end;
}
.child-comment-write-layout {
  width: 90%;
  gap: 8px;
  font-size: 9pt;
  justify-content: end;
}
.child-comment-util-box > div {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 15px;
  font-size: 9pt;
}
.child-comment-view-layout {
  width: 90%;
  display: flex;
  flex-direction: column;
  gap: 5px;
}
</style>
