<template>
  <div class="error-container">
    <div class="flex-container">
      <img :src="errorImg">
    </div>
    <div class="flex-container">
      <div class="errorTitle">404</div>
      <div class="errorDesc">抱歉，你访问的页面不存在</div>
      <a-button type="primary" @click="goback" style="cursor: pointer">返回首页</a-button>
    </div>
  </div>
</template>
<script>
export default {
   name: '404',
  data () {
    return {
      errorImg: require('@/assets/404.png')
    }
  },
  methods: {
    goback () {
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style lang="less" scoped>
.error-container {
  height: 100%;
  display: flex;
  justify-content: center;
  img {
    width: 373px;
    height: 309px;
  }
  .errorTitle {
    color: rgba(0, 0, 0, 0.65);
    font-size: 73px;
  }

  .errorDesc {
    font-size: 20px;
    color: rgba(0, 0, 0, 0.45);
    line-height: 28px;
    margin-bottom: 16px;
  }
}

.flex-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>
