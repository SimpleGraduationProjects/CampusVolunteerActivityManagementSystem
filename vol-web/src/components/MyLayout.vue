<template>
  <div class="layout">
    <Layout>
      <Header class="layout-header">
        <Menu mode="horizontal" theme="primary" active-name="gravida">
          <div class="layout-logo">
            <span style="font-size:22px;color:#fff;font-weight:bold; line-height:30px;">校园志愿</span>
            <!-- <img :src="logo" class="logo"> -->
          </div>
          <div class="layout-drop">
            <Dropdown style="margin-left: 20px;" placement="bottom-end" :transfer="true">
              <a href="javascript:void(0)" style="color:#fff;">
                欢迎您，{{userName}}
                <Icon type="ios-arrow-down"></Icon>
              </a>
              <DropdownMenu slot="list">
                <DropdownItem><a @click="modify=true">重置密码</a></DropdownItem>
                <DropdownItem><a @click="signOut">退出登录</a></DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger :style="{ minHeight: '692px', minWidth: '180px'}">
          <Menu :theme="theme" active-name="1" width="auto" style="text-align:left;">
            <MenuGroup title="控制面板">
                <MenuItem name="1" to="/index">
                    <Icon type="ios-home" />
                    主页
                </MenuItem>
            </MenuGroup>
            <MenuGroup title="活动管理">
              <MenuItem name="2" to="/publishAct">
                  <Icon type="ios-add-circle-outline" />
                  发布活动
              </MenuItem>
              <!-- <MenuItem name="3" to="/checkAct">
                  <Icon type="md-eye" />
                  审核活动
              </MenuItem> -->
              <MenuItem name="4" to="/allActs">
                  <Icon type="md-eye" />
                  所有活动
              </MenuItem>
            </MenuGroup>
            <MenuGroup title="志愿者管理">
                <MenuItem name="5" to="/onlinevolunteers">
                    <Icon type="md-heart" />
                    在线志愿者
                </MenuItem>
                <MenuItem name="6" to="/allVolunteer">
                    <Icon type="md-body" />
                    所有志愿者
                </MenuItem>
            </MenuGroup>
          </Menu>
        </Sider>
        <slot></slot>
      </Layout>
    </Layout>
  </div>
</template>

<script>
export default {
  name: 'myLayout',
  data () {
    return {
      theme: 'dark',
      logo: '',
      modify: false,
      userName: '测试用户'
    }
  },
  methods: {
    signOut () {
      document.cookie = 'sessionId=' + ''
      localStorage.removeItem('userName')
      this.$router.push({ name: 'Login' })
    }
  },
  mounted () {
    let username=localStorage.getItem('userName')
    this.userName = username
  }
}
</script>

<style>
.layout {
  position: relative;
  min-height: 100vh;
  background: #f5f7f9;
  overflow: hidden;
}
.layout-header {
  /* background: #58B957; */
  margin: 0 -50px -4px -50px;
}

.ivu-layout-header {
  background: #58B957;
}
.logo{
  display: block;
  width: 100px;
  height: 30px;
}
.layout-logo {
  width: 100px;
  height: 30px;
  background: #58B957;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 5px;
  margin-left: 50px;
}
.layout-drop {
  position: absolute;
  right: 50px;
}
</style>
