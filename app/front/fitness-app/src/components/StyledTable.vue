<template>
  <div class="table-container">
    <table class="styled-table">
      <thead>
        <tr>
          <th v-for="header in headers" :key="header">{{ header }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in rows" :key="row.id">
          <td v-for="column in row" :key="column">{{ column }}</td>
          <td><button @click="editRow(row.id)">Click</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { BodyWeight } from "../types/BodyWeightType"


defineProps<{
  headers: string[],
  rows: BodyWeight[]
}>();

const editRow = (id: number) => {
  console.log(`Editing row with ID: ${id}`);
};
</script>

<style scoped lang="scss">
.table-container {
  width: 100%;
  margin: 20px auto;
  overflow: auto;
  max-height: 500px;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 18px;
  text-align: left;

  th, td {
    padding: 12px 15px;
    border-bottom: 1px solid #ddd;
  }

  th {
    position: sticky;
    top: 0;
    background-color: #333;
    color: white;
  }

  tbody tr:nth-child(even) {
    background-color: #f2f2f2;
  }

  tbody tr:hover {
    background-color: #ddd;
  }

  tbody button {
    padding: 8px 12px;
    font-size: 14px;
    cursor: pointer;
    background-color: rgb(75, 192, 192);
    color: white;
    border: none;
    border-radius: 4px;
  }
}
</style>