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
    <Table border :columns="userColumns" :data="userData" style="margin-top:12px">
    </Table>
    <div v-show="isExpired">
    <h3 style="margin-top:24px">活动总结</h3>
    <Input v-model="summary" type="textarea" placeholder="请输入不超过50字的总结" :maxlength="50" :rows="3" style="width: 500px;margin-top:12px;"/>
    <h3 style="margin-top:24px">活动照片</h3>
    <Row style="margin-top:12px;">
      <i-col>
        <!-- <FormItem label="活动logo" prop="logo"> -->
         <Upload
            multiple
            :format="['jpg','jpeg','png','bmp']"
            :on-error="handleSuccess"
            :on-remove="removeLogo"
            :before-upload="beforeUpload"
            action=""
            >
              <Button icon="ios-cloud-upload-outline">上传图片</Button>
          </Upload>
        <!-- </FormItem> -->
      </i-col>
    </Row>
    <Row type="flex" justify="start" style="margin-top:12px;">
      <i-col>
        <Button type="primary" class="btn-submit" @click="finishAct">完结活动</Button>
      </i-col>
    </Row>
    </div>
  </Content>
</template>

<script>
import { getActInfo, finishAct, getActPeople } from '@/services'
import http from '../common/httpClass'
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
      summary: '',
      files: [],
      isExpired: this.$route.params.expired,
      userColumns: [
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
      userData: []
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
    },
    async getActPeople () {
      const res = await getActPeople({ eventId: this.$route.params.eventId })
      if (res) {
        this.userData = res
      }
    },
    async finishAct () {
      // const res = await finishAct({ eventId: this.$route.params.eventId, files: this.files, eventSummary: this.summary })
      let fd = new FormData()
      console.log(this.files)
      for (let i = 0; i < this.files.length; i++) {
        fd.append('files', this.files[i])
      }
      fd.append('eventId', this.$route.params.eventId)
      fd.append('eventSummary', this.summary)
      const res = await http.uploadFile('/api/finishEvent?' + document.cookie, fd)
      console.log('返回信息', res)
      if (res.data.statusCode === 200) {
        this.$Modal.success({
          title: res.data.msg
        })
      } else {
        this.$Modal.error({
          title: res.data.msg
        })
      }
    },
    beforeUpload (res) {
      console.log(res)
      console.log(this.files)
      this.files.push(res)
      return false
    },
    handleReset (name) {
    },
    handleSuccess (res, file) {
    },
    removeLogo () {
      this.files = ''
    }
  },
  mounted () {
    this.getActInfo()
    this.getActPeople()
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
