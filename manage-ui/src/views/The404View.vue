<template>
    <div class="main404">
        <h1>404</h1>
        <p>页面找不到了</p>
        <el-button @click="toHome">返回首页</el-button>
    </div>
</template>
<script lang="ts" setup>
function toHome() {
    window.location.href = '/'
}
</script>
<style lang="scss" scoped>
.main404 {
    height: 100%;
    text-align: center;
    padding-top: 20vh;
    box-sizing: border-box;
}
</style>
