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
    <h2 style="margin-top:24px">活动报名名单</h2>
    <Table border :columns="columns" :data="data" style="margin-top:12px">
        <!-- <template slot-scope="{ row }" slot="name">
            <strong>{{ row.name }}</strong>
        </template> -->
        <template slot-scope="{ row }" slot="action">
            <!-- <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button> -->
            <Button type="error" size="small" @click="remove(row)">驳回</Button>
        </template>
    </Table>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col>
        <Button type="primary" class="btn-submit" @click="checkActPeople">审核通过</Button>
      </i-col>
    </Row>
  </Content>
</template>

<script>
import { getActInfo, getActPeople, checkAct, removePeople } from '@/services'
export default {
  name: 'checking',
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
          title: '操作',
          slot: 'action'
          // width: 150,
          // align: 'center'
        }
      ],
      data: [
        // {
        //   department: '计算机工程学院',
        //   class: '数嵌161',
        //   name: '王王',
        //   id: '202160511',
        //   score: 100
        // }
      ]
    }
  },
  methods: {
    // show (index) {
    //   this.$Modal.info({
    //     title: 'User Info',
    //     content: `Name：${this.data6[index].name}<br>Age：${this.data6[index].age}<br>Address：${this.data6[index].address}`
    //   })
    // },
    async remove (row) {
      console.log('这里:', row.stuId)
      const res = await removePeople({ eventId: this.$route.params.eventId, stuId: row.stuId, check: 'false' })
      if (res.statusCode === 200) {
        console.log(res)
        this.$Message.info(res.msg)
        this.getActInfo()
      } else {
        this.$Message.error(res.msg)
      }
    },
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
        let list = []
        for (let i in res2) {
          if (res2[i].applyStatus !== '报名未通过') {
            list.push(res2[i])
          }
        }
        this.data = list
      }
    },
    async checkActPeople () {
      var actPeople = []
      this.data.forEach(val => {
        actPeople.push(val.userId)
      })
      // if (actPeople.length === 0) {
      //   this.$Modal.error({
      //     title: '活动报名人数为0，暂不能审核'
      //   })
      //   return
      // }
      const res = await checkAct({ eventId: this.$route.params.eventId, status: '进行中' })
      if (res) {
        console.log(res.statusCode === 200)
        this.$Modal.success({
          title: '活动审核通过'
        })
      } else {
        this.$Modal.error({
          title: '活动审核失败'
        })
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
