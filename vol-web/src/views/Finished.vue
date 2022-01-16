<template>
  <Content :style="{margin: '24px',padding: '24px',background: '#fff',textAlign:'left'}">
    <h2>活动信息</h2>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col span="8">名称：{{title}}</i-col>
      <i-col span="8">时间：{{time}}</i-col>
      <i-col span="8">地点：{{location}}</i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col span="8">主办方：{{supplyName}}</i-col>
      <i-col span="8">积分：{{eventScore}}</i-col>
      <i-col span="8">允许申请人数：{{maxnum}}</i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col>简介：{{description}}</i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col>logo：<img :src="logo" class="logo-img"></i-col>
    </Row>
    <!-- <h2 style="margin-top:24px">活动照片</h2>
    <div class="pic-wrap">
      <div class="pic" v-for="(item,index) in actPic" :key="index">
        <img :src="item">
      </div>
    </div> -->
    <h2 style="margin-top:24px">活动名单</h2>
    <Table border :columns="columns" :data="data" style="margin-top:12px"></Table>
  </Content>
</template>

<script>
import { getActInfo, getActPeople } from '@/services'
export default {
  name: 'finished',
  data () {
    return {
      title: '',
      time: '',
      location: '',
      supplyName: '',
      eventScore: 0,
      maxnum: 0,
      description: '',
      logo: '',
      columns: [
        {
          title: '学院',
          key: 'userSchool'
        },
        {
          title: '班级',
          key: 'userClass'
        },
        {
          title: '姓名',
          key: 'userName'
        },
        {
          title: '学号',
          key: 'stuId'
        },
        {
          title: '积分',
          key: 'userScore'
        },
        {
          title: '状态',
          key: 'applyStatus'
        }
      ],
      data: []
    }
  },
  methods: {
    async getActInfo () {
      const res = await getActInfo({ eventId: this.$route.params.eventId })
      if (res) {
        this.title = res.title
        this.time = `${res.startTime}-${res.endTime}`
        this.location = res.location
        this.supplyName = res.supplyName
        this.eventScore = res.eventScore
        this.maxnum = res.maxnum
        this.description = res.description
        this.logo = res.eventImgUrl
      }
      const res2 = await getActPeople({ eventId: this.$route.params.eventId })
      if (res2) {
        this.data = res2
      }
    }
  },
  mounted () {
    this.getActInfo()
  }
}
</script>
<style>
.btn-submit {
  font-size: 14px;
}
.logo-img {
  width: 100px;
  height: 100px;
}
</style>
