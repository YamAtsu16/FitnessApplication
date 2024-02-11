<template>
  <div class="line-chart-container">
    <canvas id="line-chart" ref="lineChart"></canvas>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted, watch, normalizeStyle } from 'vue';
import Chart from 'chart.js/auto';
import { defineProps } from 'vue';
import { BodyWeight } from "../types/BodyWeightType"

const props = defineProps<{
  data: BodyWeight[]
}>();

/** グラフ */
const lineChart = ref<HTMLCanvasElement | null>(null);

/** グラフのデータ */
const chartData = ref({
  labels: props.data.map(item => item.date),
  datasets: [
    {
      label: 'Weight',
      borderColor: 'rgb(75, 192, 192)',
      data: props.data.map(item => item.weight),
    },
  ],
});

/** グラフ描画用のオプション */
const chartOptions = ref({
  responsive: true,
});

/** 
 * 画面描画時の処理
 **/
onMounted(() => {
  if (lineChart.value) {
    createGraph();
  } else {
    console.error('lineChart is null');
  }
})

/**
 * グラフデータ変更時の処理
 */
watch(
  () => props.data,
  () =>{
    updateGraph();
  })

/**
 * グラフ生成
 */
const createGraph = () => {
  const ctx = lineChart.value?.getContext('2d');
  if (!ctx) {
    console.error('getContext returned null');
    return;
  }

  new Chart(ctx, {
    type: 'line',
    data: chartData.value,
    options: chartOptions.value,
  });
}

/**
 * グラフ再描画
 */
const updateGraph = () => {
  // グラフのデータを更新
  chartData.value.labels = props.data.map(item => item.date);
  chartData.value.datasets[0].data = props.data.map(item => item.weight);

  // グラフを再描画する
  if (lineChart.value) {
    const chart = Chart.getChart(lineChart.value);
    if (chart) {
      chart.data.labels = chartData.value.labels;
      chart.data.datasets[0].data = chartData.value.datasets[0].data;
      chart.update();
    } else {
      createGraph();
    }
  } else {
    console.error('lineChart is null');
  }
}
</script>
