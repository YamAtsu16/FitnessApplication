<template>
  <PageHeader @change-page="changePage"></PageHeader>
  <div class="main">
    <component :is="currentComponent"></component>
  </div>
    <PageFooter></PageFooter>
</template>

<script setup lang="ts">
import PageHeader from '@/components/common/PageHeader.vue';
import PageFooter from '@/components/common/PageFooter.vue';
import BodyWeight from './BodyWeight.vue';
import Meals from './Meals.vue';
import Exercise from './Exercise.vue';
import { computed, ref } from 'vue';

/** ページ番号 */
const pageNo = ref<number>(1);

/**
 * ページ番号変更
 */
const changePage = (i: number) => {
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
      return Meals;
    case 3:
      return Exercise;
    default:
      return BodyWeight;
  }
});

</script>
<style scoped lang="scss">
.main {
  width: calc(100vw - 400px);
  padding: 50px 200px;
}
</style>
