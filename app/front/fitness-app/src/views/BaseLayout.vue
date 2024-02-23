<template>
  <PageHeader @toggle-nav="toggleNav"></PageHeader>
  <ul class="nav nav-tabs" v-if="isNavVisible">
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
  <div class="main">
    <component :is="currentComponent"></component>
  </div>
    <PageFooter></PageFooter>
</template>

<script setup lang="ts">
import PageHeader from '@/components/PageHeader.vue';
import PageFooter from '@/components/PageFooter.vue';
import BodyWeight from './BodyWeight.vue';
import { computed, ref, inject } from 'vue';

/** ページ番号 */
const pageNo = ref<Number>(1);

/**
 * ページ番号変更
 */
const changePage = (i: Number) => {
  pageNo.value = i;
}

/**
 * 表示切り替え
 */
const currentComponent = computed(() => {
  switch (pageNo.value) {
    case 1:
      return BodyWeight;
    case 2:
      // 仮置き
      return BodyWeight;
    case 3:
      // 仮置き
      return BodyWeight;
    default:
      return BodyWeight;
  }
});

/** ナビゲーションタブ */
const isNavVisible = ref(false);

/**
 * ナビゲーションタブ表示切り替え
 */
const toggleNav = () => {
  isNavVisible.value = !isNavVisible.value;
}

</script>
<style>
.main {
  padding: 50px 200px;
}
.nav {
  list-style-type: none;
  padding: 0;
  margin: 0;
  background-color: #4A4A4A;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}
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
}
.nav-link:hover, .nav-link.active {
  background-color: #5C5C5C;
  color: #fff;
}
.nav-link.active {
  font-size: 18px;
  font-weight: bold;
  background-color: #525252;
}
</style>
