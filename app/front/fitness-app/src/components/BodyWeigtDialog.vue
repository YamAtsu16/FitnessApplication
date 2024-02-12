<template>
  <div v-if="props.visible" class="popup">
    <div class="popup-main">
      <h2><slot></slot></h2>
      <div>
        <span>Date</span>
        <input v-model="record.date" type="date">
      </div>
      <div>
        <span>Weight</span>
        <input v-model="record.weight" type="number">
      </div>
      <div class="button-container">
        <div>
          <button class="save-button" @click="save">Save</button>
          <button class="close-button" @click="cancel">Close</button>
        </div>
        <div>
          <button v-if="props.mode === 3" class="delete-button" @click="deleteRecord">Delete</button>
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

/**
 * 保存ボタンクリック時
 */
const save = () => {
  emit('save', record.value);
};

/**
 * 削除ボタンクリック時
 */
const deleteRecord = () => {
  emit('delete', record.value);
};

/**
 * キャンセルボタンクリック時
 */
const cancel = () => {
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
  }

  div {
    margin-bottom: 10px;
  }

  span {
    display: inline-block;
    width: 60px;
    text-align: left;
  }

  input {
    width: 150px;
  }

  .button-container {
    margin-top: 30px
  }

  .save-button,
  .close-button,
  .delete-button {
    padding: 8px 12px;
    font-size: 14px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    color: white;
    font-weight: bold;
  }

  .save-button {
    margin-right: 10px;
    background-color: rgb(75, 192, 192);
  }

  .close-button {
    background-color: gray;
  }

  .delete-button {
    margin-top: 50px;
    background-color: red;
  }
}
</style>
