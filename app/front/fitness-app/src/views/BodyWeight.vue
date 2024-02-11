<template>
  <h1>Body Weight</h1>
    <div class="container">
      <button @click="openPopup">Add</button>
    </div>
    <LineChart :data="rows"></LineChart>
    <StyledTable :headers="headers" :rows="rows"></StyledTable>
    <!-- ポップアップ -->
    <div v-if="isPopupOpen" class="popup">
      <div class="popup-main">
        <h2>Add Record</h2>
        <div>
          <span>Date</span>
          <input v-model="newRecord.date" type="date">
        </div>
        <div>
          <span>Weight</span>
          <input v-model="newRecord.weight" type="number">
        </div>
        <button @click="saveRecord">Save</button>
        <button @click="closePopup">Close</button>
      </div>
    </div>
</template>

<script setup lang="ts">
import { onBeforeMount, onMounted, ref, watch } from "vue";
import StyledTable from "@/components/StyledTable.vue";
import LineChart from "@/components/LineChart.vue";
import axios from 'axios';
import { BodyWeight } from "../types/BodyWeightType"

/** テーブルヘッダー */
const headers = ref<string[]>(['ID', 'Date', 'Weight', 'Edit']);

/** テーブルデータ */
const rows = ref<BodyWeight[]>([]);

/** ポップアップ表示フラグ */
const isPopupOpen = ref(false);

/**
 * マウント前の処理
 */
onBeforeMount(() => {
  getBodyWeight();
})

/**
 * 体重データ取得処理
 */
const getBodyWeight = () => {
  axios.get("http://localhost:8099/body-weight")
    .then((response) => {
      rows.value = response.data.map((data: BodyWeight) => {
        return {
          id: data.id,
          date: String(data.date),
          weight: data.weight,
        }
      })
    })
    .catch(() => {
      alert("failed")
    })
}

/**
 * ポップアップを開く
 */
const openPopup = () => {
  resetInput();
  isPopupOpen.value = true;
};

/**
 * ポップアップを閉じる 
 */
const closePopup = () => {
  resetInput();
  isPopupOpen.value = false;
};

/**
 * 日付フォーマット（GYYY-MM-DD）
 * @param date 
 */
const formatDate = (date: Date): string => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
};

/** 新規登録データ */
const newRecord = ref({
  date: formatDate(new Date()),
  weight: 0,
});

/**
 * データ登録
 */
const saveRecord = () => {
  axios.post("http://localhost:8099/body-weight", newRecord.value)
    .then(() => {
      getBodyWeight();
    })
    .catch(() => {
      alert("update failed!")
    })

  closePopup();
};

/**
 * 入力値のリセット
 */
const resetInput = () => {
  newRecord.value = {
    date: formatDate(new Date()),
    weight: 0,
  }
}

</script>
<style scoped lang="scss">
.container {
  display: flex;
  justify-content: right;

  button {
    height: 40px;
    width: 80px;
    font-size: 16px;
  }
}

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

    button {
      margin-top: 10px;
    }
}
</style>