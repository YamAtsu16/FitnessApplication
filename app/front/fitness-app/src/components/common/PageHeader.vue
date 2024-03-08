<template>
  <div class="header-container">
    <div class="header">
      <div class="header-left">
        <button @click="toggleNav" class="menu-icon">≡</button>
        <span class="title">
          Fitness Application
        </span>
      </div>
      <div class="header-right">
        <router-link to="/" class="menu-content logout-icon">Logout</router-link>
      </div>
    </div>
    <Trandition>
      <ul class="nav" v-if="isNavVisible">
        <li class="nav-item">
          <a class="nav-link" :class="{ active: pageNo === 1 }" @click="changePage(1)">Weight</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" :class="{ active: pageNo === 2 }"  @click="changePage(2)">Meals</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" :class="{ active: pageNo === 3 }"   @click="changePage(3)">Exercise</a>
        </li>
      </ul>
    </Trandition>
  </div>
</template>
<script setup lang="ts">
import { ref, defineEmits } from 'vue';

/** Emits */
const emit = defineEmits(['changePage']);

/** ナビゲーションタブ */
const isNavVisible = ref(false);

/** ページ番号 */
const pageNo = ref<number>(1);

/**
 * ページ番号変更
 */
const changePage = (i: number) => {
  pageNo.value = i;
  toggleNav();
  emit("changePage", i)
}

/**
 * ナビゲーションタブ表示切り替え
 */
const toggleNav = () => {
  isNavVisible.value = !isNavVisible.value;
}
</script>
<style scoped lang="scss">
.header-container {
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 999;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  width: 100%;
  box-sizing: border-box;
  background-color: #333;
  padding: 0 10px;
  .title {
    font-size: 24px;
    font-weight: bold;
    color: white;
    line-height: 1;
  }
  .header-right {
    display: flex;
    align-items: center;
    .menu-content {
      height: 100%;
      line-height: inherit;
      color: white;
      font-weight: bold;
      margin-right: 20px;
      text-decoration: none;
      &.logout-icon {
        background: none;
        border: none;
        color: white;
        font-size: 24px;
        cursor: pointer;
        transition: transform 0.3s ease;
        &:hover {
          transform: scale(1.1);
        }
      }
    }
  }
}
.menu-icon {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
  &:hover {
    transform: scale(1.1);
  }
}
.nav {
  list-style-type: none;
  padding: 0;
  margin: 0;
  width: 100%;
  background-color: #4A4A4A;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  .nav-item {
    flex-grow: 1;
    text-align: center;
  }
  .nav-link {
    font-size: 16px;
    display: block;
    padding: 15px 20px;
    color: #fff;
    text-decoration: none;
    transition: background-color 0.3s ease-in-out, color 0.3s ease-in-out;
    &:hover {
      background-color: #5C5C5C;
      color: #fff;
    }
    &.active {
      font-size: 18px;
      font-weight: bold;
      background-color: #525252;
    }
  }
}
</style>