<template>
  <Content :style="{margin: '24px',padding: '24px',background: '#fff'}">
    <Row style="margin-bottom:24px;">
      <i-col span="6">
        <Input search enter-button placeholder="请输入活动名称" @on-search="searchAct" v-model="titleInput"/>
      </i-col>
    </Row>
    <Table border :columns="columns" :data="data"></Table>
    <Page :total="count" show-total @on-change="getAllActs" :page-size= "pageSize" class="layout-page"/>
  </Content>
</template>

<script>
import { getAllActs, searchAct, checkAct } from '@/services'
export default {
  name: 'allActs',
  components: {
  },
  data () {
    return {
      pageSize: 10,
      count: 0,
      titleInput: '',
      columns: [
        {
          title: '时间',
          key: 'date',
          width: '270px'
        },
        {
          title: '名称',
          key: 'title'
        },
        {
          title: '地点',
          key: 'location'
        },
        {
          title: '主办方',
          key: 'supplyName'
        },
        {
          title: '状态',
          key: 'status',
          filters: [
            {
              label: '审核中',
              value: '审核中'
            },
            {
              label: '进行中',
              value: '进行中'
            },
            {
              label: '已结束',
              value: '已结束'
            },
            {
              label: '已过期',
              value: '已过期'
            },
            {
              label: '已删除',
              value: '已删除'
            }
          ],
          filterMethod (value, row) {
            return row.status.indexOf(value) > -1
          }
        },
        {
          title: '操作',
          key: 'setting',
          render: (h, params) => {
            var status = params.row.status
            switch (status) {
              case '审核中':
                return h('div', [
                  h('Button', {
                    props: {
                      type: 'warning',
                      size: 'small'
                    },
                    style: {
                      marginRight: '12px'
                    },
                    on: {
                      click: () => {
                        this.$router.push({ name: 'checking', params: { eventId: params.row.eventId } })
                      }
                    }
                  }, '审核活动'),
                  h('Button', {
                    props: {
                      type: 'error',
                      size: 'small'
                    }
                  },
                  [
                    h('Poptip',
                      { props: { confirm: true, transfer: true, title: '确定删除志愿活动' + params.row.title + '？', type: 'error', size: 'small' },
                        on: {
                          'on-ok': () => {
                            this.deleteAct(params.row.eventId)
                          }
                        }
                      }, '删除')
                  ]
                  )
                ])
                break
              case '进行中':
                return h('div', [
                  h('Button', {
                    props: {
                      type: 'primary',
                      size: 'small'
                    },
                    style: {
                      marginRight: '12px'
                    },
                    on: {
                      click: () => {
                        this.$router.push({ name: 'finishAct', params: { eventId: params.row.eventId, expired: true } })
                      }
                    }
                  }, '完结活动'),
                  h('Button', {
                    props: {
                      type: 'error',
                      size: 'small'
                    }
                  },
                  [
                    h('Poptip',
                      { props: { confirm: true, transfer: true, title: '确定删除志愿活动' + params.row.title + '？', type: 'error', size: 'small' },
                        on: {
                          'on-ok': () => {
                            this.deleteAct(params.row.eventId)
                          }
                        }
                      }, '删除')
                  ]
                  )
                ])
                break
              case '已结束':
                return h('div', [
                  h('Button', {
                    props: {
                      // type: 'primary',
                      size: 'small'
                    },
                    style: {
                      marginRight: '12px'
                    },
                    on: {
                      click: () => {
                        this.$router.push({ name: 'finished', params: { eventId: params.row.eventId } })
                      }
                    }
                  }, '查看已结束活动'),
                  h('Button', {
                    props: {
                      type: 'error',
                      size: 'small'
                    }
                  },
                  [
                    h('Poptip',
                      { props: { confirm: true, transfer: true, title: '确定删除志愿活动' + params.row.title + '？', type: 'error', size: 'small' },
                        on: {
                          'on-ok': () => {
                            this.deleteAct(params.row.eventId)
                          }
                        }
                      }, '删除')
                  ]
                  )
                ])
                break
              case '已过期':
                return h('div', [
                  h('Button', {
                    props: {
                      // type: 'primary',
                      size: 'small'
                    },
                    style: {
                      marginRight: '12px'
                    },
                    on: {
                      click: () => {
                        this.$router.push({ name: 'finishAct', params: { eventId: params.row.eventId, expired: false } })
                      }
                    }
                  }, '查看已过期活动'),
                  h('Button', {
                    props: {
                      type: 'error',
                      size: 'small'
                    }
                  },
                  [
                    h('Poptip',
                      { props: { confirm: true, transfer: true, title: '确定删除志愿活动' + params.row.title + '？', type: 'error', size: 'small' },
                        on: {
                          'on-ok': () => {
                            this.deleteAct(params.row.eventId)
                          }
                        }
                      }, '删除')
                  ]
                  )
                ])
                break
              case '已删除':
                return h('div', '')
                break
              default:
                return h('div', [
                  h('Button', {
                    props: {
                      type: 'error',
                      size: 'small'
                    }
                  },
                  [
                    h('Poptip',
                      { props: { confirm: true, transfer: true, title: '确定删除志愿活动' + params.row.title + '？', type: 'error', size: 'small' },
                        on: {
                          'on-ok': () => {
                            this.deleteAct(params.row.eventId)
                          }
                        }
                      }, '删除')
                  ]
                  )
                ])
                break
            }
          }
        }
      ],
      data: []
    }
  },
  methods: {
    async getAllActs (page = 1) {
      const res = await getAllActs({ current: page, size: this.pageSize })
      if (res) {
        console.log('here', res)
        let tableData = []
        for (let i in res.records) {
          // if (res.records[i].status !== '已删除') {
          this.count = res.total
          tableData.push({
            date: `${res.records[i].startTime}-${res.records[i].endTime}`,
            title: res.records[i].title,
            location: res.records[i].location,
            supplyName: res.records[i].supplyName,
            status: res.records[i].status,
            eventId: res.records[i].eventId
          })
          this.data = tableData
          // this.count = tableData.length
          // }
        }
      } else {
        this.$Message.error('出错啦')
      }
    },
    async searchAct (page = 1) {
      var res = await searchAct({ size: this.pageSize, current: 1, title: this.titleInput })
      if (res) {
        let tableData = []
        for (let i in res.records) {
          if (res.records[i].status !== '已删除') {
          // this.count = res.total
            tableData.push({
              date: `${res.records[i].startTime}-${res.records[i].endTime}`,
              title: res.records[i].title,
              location: res.records[i].location,
              supplyName: res.records[i].supplyName,
              status: res.records[i].status,
              eventId: res.records[i].eventId
            })
            this.data = tableData
            this.count = tableData.length
          }
        }
      } else {
        this.$Message.error('出错啦')
      }
    },
    async deleteAct (id) {
      const res = await checkAct({ eventId: id, status: '已删除' })
      if (res) {
        console.log(res.statusCode === 200)
        this.$Modal.success({
          title: '活动删除成功'
        })
        this.getAllActs()
      } else {
        console.log(res)
        this.$Modal.error({
          title: '活动删除失败'
        })
      }
    }
  },
  mounted () {
    this.getAllActs()
  }
}
</script>
