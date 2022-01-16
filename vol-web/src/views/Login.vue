<template>
  <Layout class="layout">
    <!-- <Header class="layout-header">12312</Header> -->
    <Content class="layout-content">
          <div class="layout-card">
              <h1 class="title">欢 迎 登 录</h1>
              <Row type="flex" justify="center" align="bottom">
                <i-col span="5" style="font-size:14px;">用户名：</i-col>
                <i-col span="19" style="margin-top:30px;">
                  <Input v-model="id" type="text" size="large" placeholder="请输入学号"/>
                </i-col>
              </Row>
              <Row type="flex" justify="center" align="bottom">
                <i-col span="5" style="font-size:14px;">密码：</i-col>
                <i-col span="19" style="margin-top:30px;">
                  <Input v-model="password" type="password" size="large" placeholder="请输入密码" />
                </i-col>
              </Row>
              <Button type="primary" size="large" class="button" @click="onSubmit" id="login-btn" style="margin-top: 30px;width:340px;font-size:14px;">登录</Button>
            </div>
    </Content>
  </Layout>
</template>

<script>
import { login, getUserName } from '@/services'
export default {
  name: 'login',
  mounted () {
    console.log('document.cookie', document.cookie)
  },
  data () {
    return {
      id: '',
      password: ''
    }
  },
  methods: {
    async onSubmit () {
      let res = await login({ stuId: this.id, password: this.password, role: 1 })
      if (res.statusCode === 200) {
        document.cookie = res.data // 本地设置cookie
        await this.getUserName() // 注意加await
        this.$router.push({ name: 'index' })
      } else {
        this.$Modal.error({
          title: res.msg
        })
      }
    },
    async getUserName () {
      var res = await getUserName()
      if (res) {
        console.log(111, res)
        localStorage.setItem('userName', res.userName)
      }
    }
  }
}
</script>

<style scoped>
  .layout {
    height: 100vh;
    width: 100%;
    background-image: url('../assets/bg1.jpg');
    background-size:100% 100%;
  }

  .layout-card {
    text-align:center;
    border-top: 5px solid #58B957;
    border-left: 1px solid #58B957;
    border-right: 1px solid #58B957;
    border-bottom: 1px solid #58B957;
    position: absolute;
    margin-top: 150px;
    margin-left: 230px;
    width: 380px;
    height: 320px;
    padding: 20px;
    background-color: #fff;
  }

  .button {
    margin-top: 20px;
    margin-bottom: 20px;
  }

</style>
