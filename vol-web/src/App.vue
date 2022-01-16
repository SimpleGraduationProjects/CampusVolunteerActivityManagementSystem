
<template>
  <div id="app">
    <MyLayout v-if="showLayout">
      <router-view></router-view>
    </MyLayout>
    <router-view v-else></router-view>
  </div>
</template>

<script>
import MyLayout from '@/components/MyLayout'
export default {
  components: {
    MyLayout
  },
  data () {
    return {
      showLayout: true
    }
  },
  methods: {
    refreshLayout () {
      var that = this
      if (that.$route.name === 'Login' || that.$route.path === '/404') {
        that.showLayout = false
      } else {
        that.showLayout = true
      }
    }
  },
  created () {
    // document.body.removeChild(document.getElementById('spinner'))
    var that = this
    that.refreshLayout()
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        this.refreshLayout()
      }
    }
  }
}
</script>

<style lang="less">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;
    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
.layout-page {
  margin-top: 24px;
  text-align: left;
}
</style>
