<template>
  <Content :style="{margin: '24px'}">
    <!-- <Row>
      <Col span="8"> -->
      <div class="wrap">
        <div class="card">
          <Card :bordered="false">
              <p slot="title" class="card-title">当日发布活动数</p>
              <span class="card-left">{{today.sum}}</span>
              <Divider type="vertical" style="height: 80px;margin-left:45px;"/>
              <div class="card-right">
                <div>
                  <p>未审核</p>
                  <p>{{today.unchecked}}</p>
                </div>
                <div>
                  <p>未完结</p>
                  <p>{{today.unfinshed}}</p>
                </div>
              </div>
          </Card>
      </div>
      <!-- </Col>
      <Col span="8"> -->
        <div class="card">
          <Card :bordered="false">
              <p slot="title" class="card-title">当月发布活动数</p>
              <span class="card-left">{{curMonth.sum}}</span>
              <Divider type="vertical" style="height: 80px;margin-left:45px;"/>
              <div class="card-right">
                <div>
                  <p>未审核</p>
                  <p>{{curMonth.unchecked}}</p>
                </div>
                <div>
                  <p>未完结</p>
                  <p>{{curMonth.unfinshed}}</p>
                </div>
              </div>
          </Card>
      </div>
      <!-- </Col>
      <Col span="8"> -->
        <div class="card">
          <Card :bordered="false">
              <p slot="title" class="card-title">历史发布活动数</p>
              <span class="card-left">{{nowSum.sum}}</span>
              <Divider type="vertical" style="height: 80px;margin-left:45px;"/>
              <div class="card-right">
                <div>
                  <p>未审核</p>
                  <p>{{nowSum.unchecked}}</p>
                </div>
                <div>
                  <p>未完结</p>
                  <p>{{nowSum.unfinshed}}</p>
                </div>
              </div>
          </Card>
        </div>
      <!-- </Col>
    </Row> -->
    </div>
    <div class="lunbo">
      <h2>广场轮播图</h2>
      <div>
        <img v-for="(item,index) in imgs" :src="item.imgUrl" :key="index" class="img">
      </div>
      <Form ref="formValidate" style="text-align:left;">
        <FormItem label="" prop="logo">
          <Upload
            :format="['jpg','jpeg','png','bmp']"
            :before-upload="beforeUpload"
            action=""
            >
              <Button icon="ios-cloud-upload-outline" class="btn">上传图片</Button>
          </Upload>
          <div>{{ logoFile!=undefined?logoFile.name:''}}</div>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="handleSubmit()">确认上传</Button>
        </FormItem>
      </Form>
    </div>
  </Content>
</template>

<script>
import { getLunBo, getActCount } from '@/services'
import http from '../common/httpClass'
export default {
  name: 'login',
  components: {
  },
  data () {
    return {
      todayCount: 20,
      logoFile: undefined,
      imgs: '',
      today: { // 当天
        unfinshed: 0, // 未完结
        unchecked: 0, // 未审核
        sum: 1 // 总数
      },
      curMonth: { // 当前月
        unfinshed: 0,
        unchecked: 1,
        sum: 10
      },
      nowSum: { // 历史发布活动数
        unfinshed: 0,
        unchecked: 1,
        sum: 22
      }
    }
  },
  methods: {
    beforeUpload (res) {
      console.log(res)
      this.logoFile = res
      return false
    },
    async getImg () {
      let res = await getLunBo()
      if (res.records) {
        this.imgs = res.records
      }
    },
    async handleSubmit () {
      let fd = new FormData()
      fd.append('file', this.logoFile)
      const res = await http.uploadFile('/api/picture/add?' + document.cookie, fd)
      if (res) {
        this.$Modal.success({
          title: '图片上传成功',
          content: res.msg
        })
        this.getImg()
      } else {
        this.$Modal.error({
          title: '图片上传失败'
          // content: res.msg
        })
      }
    },
    async actCount () {
      let res = await getActCount()
      if (res.statusCode === 200) {
        this.today.sum = res.data.today.sum,
        this.today.unfinshed = res.data.today.unfinshed,
        this.today.unchecked = res.data.today.unchecked,
        this.curMonth.sum = res.data.curMonth.sum,
        this.curMonth.unfinshed = res.data.curMonth.unfinshed,
        this.curMonth.unchecked = res.data.curMonth.unchecked,
        this.nowSum.sum = res.data.nowSum.sum,
        this.nowSum.unfinshed = res.data.nowSum.unfinshed,
        this.nowSum.unchecked = res.data.nowSum.unchecked
      } else {
        console.log(res)
      }
    }
  },
  mounted () {
    this.getImg()
    this.actCount()
  }
}
</script>
<style>
.wrap {
  overflow: hidden;
}
.card {
  /* padding: 20px; */
  width: 390px;
  height: 180px;
  float: left;
  margin-right: 25px;
}
.card-title {
  text-align: left;
}
.card-left {
  text-align: center;
  vertical-align: middle;
  font-size: 50px;
}
.card-right {
  float: right;
  width: 140px;
  height: 80px;
  margin-right: 30px;
  display: flex;
  justify-content:space-between;
  align-items: center;
}
.lunbo {
  text-align: left;
}
.img {
  margin-top: 12px;
  width: 220px;
  height: 150px;
  margin-right: 16px;
}
.btn {
  margin-top: 12px;
}
</style>
