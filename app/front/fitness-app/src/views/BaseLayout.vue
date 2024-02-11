<template>
  <PageHeader></PageHeader>
  <div class="main">
    <component :is="currentComponent"></component>
  </div>
    <PageFooter></PageFooter>
</template>

<script setup lang="ts">
import PageHeader from '@/components/PageHeader.vue';
import PageFooter from '@/components/PageFooter.vue';
import BodyWeight from './BodyWeight.vue';
import { computed, ref } from 'vue';

const pageNo = ref(1);

const currentComponent = computed(() => {
  switch (pageNo.value) {
    case 1:
      return BodyWeight;
    default:
      return BodyWeight;
  }
});
</script>

<style>
.main {
  padding: 0 200px;
}
</style>