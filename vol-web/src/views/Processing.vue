<template>
  <Content :style="{margin: '24px',padding: '24px',background: '#fff',textAlign:'left'}">
    <h2>活动信息</h2>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col span="8">名称：</i-col>
      <i-col span="8">时间：</i-col>
      <i-col span="8">地点：</i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col span="8">主办方：</i-col>
      <i-col span="8">积分：</i-col>
      <i-col span="8">允许申请人数：</i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col>简介：</i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col>logo：<img src=""></i-col>
    </Row>
    <h2 style="margin-top:24px">活动名单</h2>
    <Table border :columns="columns" :data="data" style="margin-top:12px"></Table>
    <Row type="flex" justify="end" style="margin-top:12px;">
      <i-col>
        <Button type="primary" class="btn-submit" @click="finishAct">完结活动</Button>
      </i-col>
    </Row>
  </Content>
</template>

<script>
export default {
  name: 'processing',
  data () {
    return {
      columns: [
        {
          title: '学院',
          key: 'department'
        },
        {
          title: '班级',
          key: 'class'
        },
        {
          title: '姓名',
          key: 'name'
        },
        {
          title: '学号',
          key: 'id'
        },
        {
          title: '积分',
          key: 'score'
        }
      ],
      data: [
        {
          department: '计算机工程学院',
          class: '网嵌162',
          name: '张三',
          id: '202110502',
          score: 100
        },
        {
          department: '计算机工程学院',
          class: '数嵌161',
          name: '王王',
          id: '202160511',
          score: 100
        },
        {
          department: '计算机工程学院',
          class: '数嵌161',
          name: '王王',
          id: '202160511',
          score: 100
        },
        {
          department: '计算机工程学院',
          class: '数嵌161',
          name: '王王',
          id: '202160511',
          score: 100
        },
        {
          department: '计算机工程学院',
          class: '数嵌161',
          name: '王王',
          id: '202160511',
          score: 100
        },
        {
          department: '计算机工程学院',
          class: '数嵌161',
          name: '王王',
          id: '202160511',
          score: 100
        }
      ]
    }
  },
  methods: {
    finishAct () {
      this.$router.push({ name: 'finishAct' })
    }
  }
}
</script>
<style>
.btn-submit {
  font-size: 14px;
}
</style>
