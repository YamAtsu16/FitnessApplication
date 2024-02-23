<template>
  <div class="container">
    <button @click="addClick">Add</button>
  </div>
  <LineChart :data="rows"></LineChart>
  <StyledTable :headers="headers" :rows="rows" @edit="editClick"></StyledTable>
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
import StyledTable from "../components/StyledTable.vue";
import LineChart from "../components/LineChart.vue";
import BodyWeightDialog from "../components/BodyWeigtDialog.vue";
import axios from 'axios';
import { BodyWeight } from "../types/BodyWeightType"

const ModeConstants = {
  INQUIRY: 1,
  ADD: 2,
  EDIT: 3
} as const

/** テーブルヘッダー */
const headers = ref<string[]>(['ID', 'Date', 'Weight', 'Edit']);

/** テーブルデータ */
const rows = ref<BodyWeight[]>([]);

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
    return "";
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
  const selectRow = rows.value.filter((data: BodyWeight) => {
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
  if (rows.value.length !== 0 && mode.value === ModeConstants.ADD) {
    rows.value.forEach((e: BodyWeight) => {
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
.container {
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
      display: flex;
      justify-content: center;
    }
    
    button {
      margin-top: 10px;
    }
}
</style>