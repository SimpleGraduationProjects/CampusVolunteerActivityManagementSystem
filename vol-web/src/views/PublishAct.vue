<template>
  <Content :style="{margin: '24px',padding: '24px',background: '#fff'}">
    <div class="form">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" style="text-align:left;">
        <FormItem label="标题" prop="name">
          <i-input v-model="formValidate.name" placeholder="请输入活动标题"></i-input>
        </FormItem>
        <FormItem label="主办方" prop="department">
          <Select v-model="formValidate.department" placeholder="请选择主办方">
            <Option v-for="(item,index) in departments" :key="index" :value="item">{{item}}</Option>
          </Select>
        </FormItem>
        <FormItem label="时间">
          <Row>
            <i-col span="11">
              <FormItem prop="startTime">
                <DatePicker type="datetime" format="yyyy-MM-dd HH:mm" :options="startDateOptions" placeholder="请选择活动开始时间" v-model="formValidate.startTime"></DatePicker>
              </FormItem>
            </i-col>
            <i-col span="2" style="text-align: center">-</i-col>
            <i-col span="11">
              <FormItem prop="endTime">
                <DatePicker type="datetime" format="yyyy-MM-dd HH:mm" :options="endDateOptions" placeholder="请选择活动结束时间" v-model="formValidate.endTime"></DatePicker>
              </FormItem>
            </i-col>
          </Row>
        </FormItem>
        <FormItem label="地点" prop="location">
          <i-input v-model="formValidate.location" placeholder="请输入活动地点"></i-input>
        </FormItem>
        <FormItem label="简介" prop="desc">
          <i-input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 2,maxRows: 5}" maxlength="50" show-word-limit placeholder="请输入活动介绍"></i-input>
        </FormItem>
        <FormItem label="标签" prop="tags">
          <CheckboxGroup v-model="formValidate.tags">
            <Checkbox label="敬老院"></Checkbox>
            <Checkbox label="儿童"></Checkbox>
            <Checkbox label="机房"></Checkbox>
            <Checkbox label="地铁站"></Checkbox>
            <Checkbox label="图书馆"></Checkbox>
            <Checkbox label="学校"></Checkbox>
            <Checkbox label="院级"></Checkbox>
            <Checkbox label="校级"></Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="积分" prop="score">
          <InputNumber :max="10" :min="1" v-model="formValidate.score"></InputNumber>
        </FormItem>
        <FormItem label="允许申请人数" prop="maxPeople">
          <InputNumber :min="1" v-model="formValidate.maxPeople"></InputNumber>
        </FormItem>
        <FormItem label="活动logo" prop="logo">
          <Upload
          :format="['jpg','jpeg','png','bmp']"
          :on-success="handleSuccess"
          :headers="uploadHeader"
          :on-remove="removeLogo"
          :before-upload="beforeUpload"
          action=""
          >
            <Button icon="ios-cloud-upload-outline">上传图片</Button>
          </Upload>
          <div>{{ logoFile!=undefined?logoFile.name:''}}</div>
          <!-- <div v-show="logoFile!=undefined" class="img-logo">
              <img :src={{logoFile.url}}>
          </div> -->
        </FormItem>
        <FormItem>
          <Button type="primary" @click="handleSubmit('formValidate')" class="btn-submit">发布</Button>
          <Button @click="handleReset('formValidate')" style="margin-left: 12px" class="btn-submit">重置</Button>
        </FormItem>

        <!-- <FormItem>
            <div>
              <Upload
                  :before-upload="handleUpload">
                  <Button icon="ios-cloud-upload-outline">Select the file to upload</Button>
              </Upload>
              <div v-if="file !== null">Upload file: {{ file.name }} <Button type="text" @click="upload" :loading="loadingStatus">{{ loadingStatus ? 'Uploading' : 'Click to upload' }}</Button></div>
          </div>
        </FormItem> -->
      </Form>
    </div>
  </Content>
</template>

<script>
import { publishActSubmit } from '@/services'
import http from '../common/httpClass'
export default {
  name: 'publishAct',
  components: {
  },
  data () {
    return {
      departments: [
        '计算机工程学院',
        '机械工程学院',
        '能源与动力工程学院',
        '通信工程学院',
        '电力工程学院',
        '艺术与设计学院',
        '经济与管理学院',
        '工业中心',
        '校青协'
      ],
      logoUrl: '',
      logoFile: undefined,
      uploadHeader: {

      },
      formValidate: {
        name: '',
        mail: '',
        city: '',
        gender: '',
        tags: [],
        startTime: '',
        endTime: '',
        location: '',
        desc: '',
        score: 1,
        maxPeople: 1,
        department: []
      },
      startDateOptions: {
        disabledDate (date) {
          return date && date.valueOf() < Date.now() - 86400000
        }
      },
      endDateOptions: {
        disabledDate (date) {
          return date && date.valueOf() < Date.now() - 86400000
        }
      },
      ruleValidate: {
        name: [
          { required: true, message: '活动标题不能为空', trigger: 'blur' }
        ],
        department: [
          { required: true, message: '请选择主办方', trigger: 'blur' }
        ],
        startTime: [
          { required: true, type: 'date', message: '请选择开始时间', trigger: 'change' }
        ],
        endTime: [
          { required: true, type: 'date', message: '请选择结束时间', trigger: 'change' }
        ],
        location: [
          { required: true, message: '请输入活动地点', trigger: 'blur' },
          { type: 'string', max: 15, message: '活动地点最多15字', trigger: 'blur' }
        ],
        desc: [
          { required: true, message: '请输入活动介绍', trigger: 'blur' },
          { type: 'string', max: 50, message: '活动介绍最多50字', trigger: 'blur' }
        ],
        // tags: [
        //   { required: true, type: 'array', min: 1, message: '至少选择1个标签', trigger: 'change' },
        //   { type: 'array', max: 5, message: '最多选择5个标签', trigger: 'change' }
        // ],
        score: [
          { required: true, type: 'number', trigger: 'blur' }
        ],
        maxPeople: [
          { required: true, type: 'number', trigger: 'blur' }
        ]
        // logo: [
        //   { required: true, trigger: 'blur' }
        // ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.submitAct()
        } else {
          this.$Message.error('Fail!')
        }
      })
    },
    formartDate (date) {
      var times = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() +
      ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
      return times
    },
    async submitAct () {
      console.log('here2' + this.formValidate.startTime)
      let fd = new FormData()
      // for (const item in this.formValidate) {
      //   console.log(item, this.formValidate[item])
      // }

      // 1.获得开始时间，结束时间，时间是GMT格式
      let startTime = new Date(this.formValidate['startTime'])
      let endTime = new Date(this.formValidate['endTime'])
      // 2.根据比较两个Date对象的大小，若开始时间晚，直接return 拦截不往下执行
      if (startTime.getTime() > endTime.getTime()) {
        // console.log("时间错误，不能提交")
        this.$Message.error('结束时间不能早于开始时间')
        return
      }
      startTime = this.formartDate(startTime)
      endTime = this.formartDate(endTime)
      let tags = this.formValidate['tags'].join(',')
      // console.log('tags:', tags)
      fd.append('startTime', startTime)
      fd.append('endTime', endTime)
      fd.append('title', this.formValidate['name'])
      fd.append('location', this.formValidate['location'])
      fd.append('description', this.formValidate['desc'])
      fd.append('supplyName', this.formValidate['department'])
      fd.append('eventScore', this.formValidate['score'])
      fd.append('maxnum', this.formValidate['maxPeople'])
      fd.append('tags', tags)
      fd.append('file', this.logoFile)
      const res = await http.uploadFile('/api/addEvent?' + document.cookie, fd)
      if (res) {
        this.$Modal.success({
          title: '活动发布成功'
          // content: res.msg
        })
      } else {
        this.$Modal.error({
          title: '活动发布失败'
          // content: res.msg
        })
      }
    },
    handleReset (name) {
      this.$refs[name].resetFields()
    },
    beforeUpload (res) {
      console.log(res)
      this.logoFile = res
      return false
    },
    handleSuccess (res, file) {
      this.logoUrl = res.src
    },
    removeLogo () {
      this.logoUrl = ''
    }
  },
  mounted () {
    // this.logoUrl = './assets/3.jpg'
  }
}
</script>

<style>
.form {
  width: 450px;
}
.img-logo {
  width: 150px;
  height: 150px;
  border: solid 1px #515A6E;
}
.btn-submit {
  font-size: 14px;
}
</style>
