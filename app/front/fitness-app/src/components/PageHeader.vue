<template>
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
</template>
<script setup lang="ts">
import { ref } from 'vue';

/** Emits */
const emit = defineEmits(['toggleNav']);

/**
 * ナビゲーションタブ切り替え
 */
const toggleNav = () => {
  emit('toggleNav');
}
</script>
<style scoped lang="scss">
.header {
  position: sticky;
  top: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  width: calc(100% - 20px);
  background-color: #333;
  padding: 0 10px;
}
.title {
  font-size: 24px;
  font-weight: bold;
  color: white;
  line-height: 1;
}
.header-right {
  display: flex;
  align-items: center;
}
.menu-content {
  height: 100%;
  line-height: inherit;
  color: white;
  font-weight: bold;
  margin-right: 20px;
  text-decoration: none;
}
.menu-icon {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
}
.logout-icon {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
  transition: transform 0.3s ease;
}
.logout-icon:hover {
  transform: scale(1.1);
}
</style>