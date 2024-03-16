<template>
  <div class="line-chart-container">
    <Line :data="formattedChartData" :options="chartOptions" />
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed, defineComponent } from 'vue';
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, LineElement, Legend, CategoryScale, LinearScale, PointElement } from 'chart.js';
import { BodyWeight } from "../../types/BodyWeightType";

ChartJS.register(Title, Tooltip, LineElement, Legend, CategoryScale, LinearScale, PointElement);

const props = defineProps<{
  data: BodyWeight[]
}>();

const formattedChartData = computed(() => ({
  labels: props.data.map(item => item.date),
  datasets: [
    {
      label: 'Weight',
      borderColor: 'rgb(75, 192, 192)',
      data: props.data.map(item => item.weight),
    },
  ],
}));

const chartOptions = {
  responsive: true,
};
</script>

<style scoped lang="scss">
.line-chart-container {
  width: 100%;
  height: 600px;
}

#line-chart {
  width: 100%;
  height: 100%;
}
</style>