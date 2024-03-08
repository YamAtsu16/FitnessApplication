<template>
  <div v-if="props.visible" class="popup">
    <div class="popup-main">
      <h2><slot></slot></h2>
      <div v-if="hasError" class="error-message">{{ errorMessage }}</div>
      <div class="input-field">
        <span>Date</span>
        <input v-model="record.date" type="date">
      </div>
      <div class="input-field">
        <span>Weight</span>
        <input v-model="record.weight" type="number">
      </div>
      <div class="button-container">
        <div class="left">
          <button v-if="props.mode === 3" class="button delete-button" @click="deleteRecord">Delete</button>
        </div>
        <div class="right">
          <button class="button save-button" @click="save">Save</button>
          <button class="button close-button" @click="cancel">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { BodyWeight } from "@/types/BodyWeightType";
import { computed, ref } from "vue";
import { defineProps, defineEmits } from 'vue';

/** Emits */
const emit = defineEmits(['save', 'cancel', 'delete']);

/** Props */
const props = defineProps<{
  data: BodyWeight,
  visible: boolean,
  mode: number
}>();

/** 入力データ */
const record = computed(() => props.data)

/** 入力エラーの有無 */
const hasError = ref(false);

const errorMessage = ref("");

/**
 * 保存ボタンクリック時
 */
const save = () => {
  if (record.value.date === "") {
    hasError.value = true;
    errorMessage.value = "Date is required"
    return;
  }
  hasError.value = false;
  emit('save', record.value);
};

/**
 * 削除ボタンクリック時
 */
const deleteRecord = () => {
  hasError.value = false;
  emit('delete', record.value);
};

/**
 * キャンセルボタンクリック時
 */
const cancel = () => {
  hasError.value = false;
  emit('cancel');
};
</script>

<style scoped lang="scss">
.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;

  .popup-main {
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    max-width: 400px;
    width: 90%;
  }

  .error-message {
    color: red;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .input-field {
    margin-bottom: 15px;
    display: flex;
    align-items: center;
  }

  span {
    width: 60px;
    margin-right: 10px;
    font-weight: bold;
  }

  input {
    flex: 1;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }

  .button-container {
    display: flex;
    justify-content: space-between;
    margin-top: 30px;
  }

  .button {
    padding: 8px 12px;
    font-size: 14px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    color: white;
  }

  .left {
    text-align: left;
  }

  .right {
    text-align: right;
  }

  .save-button {
    margin-right: 10px;
    background-color: rgb(75, 192, 192);
  }

  .close-button {
    background-color: gray;
  }

  .delete-button {
    background-color: red;
  }
}
</style>
