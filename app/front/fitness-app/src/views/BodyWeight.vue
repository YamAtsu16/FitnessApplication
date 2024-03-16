<template>
  <div class="button-container">
    <button @click="addClick">Add</button>
  </div>
  <LineChart :data="weight"></LineChart>
  <StyledTable :headers="headers" :rows="sortedWeight" @edit="editClick"></StyledTable>
  <!-- ポップアップ -->
  <BodyWeightDialog
    :data="postRecord"
    :visible="isPopupOpen"
    :mode="mode"
    @save="saveRecord"
    @cancel="closePopup"
    @delete="deleteRecord"
  >{{ dialogTitle }}
  </BodyWeightDialog>
</template>

<script setup lang="ts">
import { computed, onBeforeMount, ref } from "vue";
import StyledTable from "@/components/bodyWeight/StyledTable.vue";
import LineChart from "@/components/bodyWeight/LineChart.vue";
import BodyWeightDialog from "@/components/bodyWeight/BodyWeigtDialog.vue";
import axios from 'axios';
import { BodyWeight } from "../types/BodyWeightType"

const ModeConstants = {
  INQUIRY: 1,
  ADD: 2,
  EDIT: 3
} as const

/** テーブルヘッダー */
const headers = ref<string[]>(['No', 'Date', 'Weight', 'Edit']);

/** 体重データ */
const weight = ref<BodyWeight[]>([]);

/** 体重データ（ソート表示用） */
const sortedWeight = ref<BodyWeight[]>([]);

/** ポップアップ表示フラグ */
const isPopupOpen = ref(false);

/** 画面モード */
const mode = ref<1|2|3>(ModeConstants.INQUIRY);

const dialogTitle = computed(() => {
  if (mode.value === ModeConstants.ADD) {
    return "Add Record"
  } else if (mode.value === ModeConstants.EDIT){
    return "Edit Record"
  } else {
    return "Let's record the first data!!";
  }
})

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
      if (response.data.length !== 0) {
        const data: BodyWeight[] = response.data.map((data: BodyWeight) => {
          return {
            id: data.id,
            date: String(data.date),
            weight: data.weight,
          }
        })
        // グラフ用に取得（日付の昇順）
        weight.value = structuredClone(data);
        // テーブル表示用に取得（日付の降順）
        sortedWeight.value = data.sort((a,b) => {
          return a.date < b.date ? 1 : -1;
        })
      } else {
        openPopup();
      }
    })
    .catch(() => {
      alert("failed")
    })
}

/**
 * ADDボタンクリック時
 */
const addClick = () => {
  openPopup();
  mode.value = ModeConstants.ADD
  resetInput();
}

/**
 * EDITボタンクリック時
 * @param id 対象データのID
 */
const editClick = (id: number) => {
  openPopup();
  mode.value = ModeConstants.EDIT
  const selectRow = weight.value.filter((data: BodyWeight) => {
    return data.id === id
  })
  postRecord.value = structuredClone(selectRow[0]);
}

/**
 * ポップアップを開く
 */
const openPopup = () => {
  isPopupOpen.value = true;
};

/**
 * ポップアップを閉じる
 */
const closePopup = () => {
  mode.value = ModeConstants.INQUIRY
  isPopupOpen.value = false;
};

/**
 * 日付フォーマット
 * @param date 日付
 * @return 文字列の日付（GYYY-MM-DD）
 */
const formatDate = (date: Date): string => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
};

/** 送信データ */
const postRecord = ref<BodyWeight>({
  id: 0,
  date: formatDate(new Date()),
  weight: 0,
});

/**
 * データ登録
 */
const saveRecord = () => {
  // 新規登録の場合、すでに登録済みの日付がある場合は更新処理を実行する
  if (weight.value.length !== 0 && mode.value === ModeConstants.ADD) {
    weight.value.forEach((e: BodyWeight) => {
      if (e.date === postRecord.value.date) {
        postRecord.value.id = e.id;
      }
    })
  }
  axios.post("http://localhost:8099/body-weight", postRecord.value)
    .then(() => {
      getBodyWeight();
    })
    .catch(() => {
      alert("update failed!")
    })
  closePopup();
};

/**
 * データ削除
 * @param target 削除対象
 */
const deleteRecord = (target: BodyWeight) => {
  axios.delete("http://localhost:8099/body-weight", {
    params: {
      id: target.id,
    }
  })
    .then(() => {
      getBodyWeight();
    })
    .catch(() => {
      alert("delete failed!")
    })
  closePopup();
}

/**
 * 入力値のリセット
 */
const resetInput = () => {
  postRecord.value = {
    id: 0,
    date: formatDate(new Date()),
    weight: 0,
  }
}

</script>
<style scoped lang="scss">
.button-container {
  width: 100%;
  display: flex;
  justify-content: right;

  button {
    cursor: pointer;
    padding: 12px 22px;
    font-size: 18px;
    border: none;
    border-radius: 4px;
    background-color: rgb(75, 192, 192);
    color: white;
    font-weight: bold;
  }
}
</style>