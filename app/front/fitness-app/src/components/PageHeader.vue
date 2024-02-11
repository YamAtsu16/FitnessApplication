<template>
  <div class="header">
    <div class="header-left">
      <span class="title">
        Fitness Application （仮）
      </span>
    </div>
    <div class="header-right">
      <router-link to="/" class="menu-content">ログアウト</router-link>
    </div>
  </div>
</template>
<script setup lang="ts">
</script>
<style scoped lang="scss">
.header{
  position: sticky;
  top: 0;
  display: flex;
  justify-content: space-between;
  height: 50px;
  width: 100%;
  background-color: #333;
}
.title {
  font-size: 20px;
  font-weight: bold;
  color: white;
  line-height: 50px;
  margin-left: 10px;
}
.header-right {
  display: flex;
}
.menu-content {
  height: 50px;
  line-height: 50px;
  color: white;
  font-weight: bold;
  margin-right: 30px;
}
</style>