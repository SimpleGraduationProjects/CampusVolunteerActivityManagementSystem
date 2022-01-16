<template>
  <Content :style="{margin: '24px',padding: '24px',background: '#fff'}">
    <Row style="margin-bottom:24px;">
      <Col span="6">
        <Input search enter-button placeholder="请输入志愿者学号" />
      </Col>
    </Row>
    <Table border :columns="columns" :data="volunteers"></Table>
    <Page :total="count" show-total @on-change="getAllVolunteers" :page-size= "pageSize" class="layout-page"/>
  </Content>
</template>

<script>
import { getAllVolunteers } from '@/services'
export default {
  name: 'volunteers',
  components: {
  },
  data () {
    return {
      pageSize: 1000,
      count: 0,
      columns: [
        {
          title: '头像',
          key: 'userAvator',
          render: (h, params) => {
            return h('div', [
              h('img', {
                domProps: {
                  'src': params.row.userAvator
                },
                style: {
                  display: 'block',
                  width: '60px',
                  height: '60px',
                  borderRadius: '50%',
                  margin: '0 auto',
                  marginTop: '5px',
                  marginBottom: '5px'
                }
              })
            ])
          }
        },
        {
          title: '学院',
          key: 'userSchool',
          filters: [
            {
              label: '计算机工程学院',
              value: '计算机工程学院'
            },
            {
              label: '机械工程学院',
              value: '机械工程学院'
            },
            {
              label: '通信工程学院',
              value: '通信工程学院'
            },
            {
              label: '自动化工程学院',
              value: '自动化工程学院'
            },
            {
              label: '电力工程学院',
              value: '电力学院'
            },
            {
              label: '能源与动力工程学院',
              value: '能源与动力工程学院'
            },
            {
              label: '经济与管理学院',
              value: '经济与管理学院'
            },
            {
              label: '工业中心',
              value: '工业中心'
            },
            {
              label: '艺术与设计学院',
              value: '艺术与设计学院'
            }
          ],
          filterMethod (value, row) {
            return row.userSchool.indexOf(value) > -1
          }
        },
        {
          title: '班级',
          key: 'userClass',
          filters: [
            {
              label: '数嵌161',
              value: '数嵌161'
            },
            {
              label: '网嵌162',
              value: '网嵌162'
            }
          ],
          filterMethod (value, row) {
            return row.userClass.indexOf(value) > -1
          }
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
        }
        // {
        //   title: '设置',
        //   key: 'setting',
        //   render: (h, params) => {
        //     return h('div', [
        //       h('Button', {
        //         props: {
        //           type: 'error',
        //           size: 'small'
        //         },
        //         style: {
        //           marginRight: '8px'
        //         }
        //       },
        //       [
        //         h('Poptip',
        //           { props: { confirm: true, transfer: true, title: '确定删除志愿者' + params.row.userName + '？', type: 'error', size: 'small' },
        //             on: {
        //               'on-ok': () => {
        //                 this.kickUser(params.row.stuId)
        //               }
        //             }
        //           }, '删除')
        //       ]
        //       ),
        //       h('Button', {
        //         props: {
        //           type: 'primary',
        //           size: 'small'
        //         },
        //         on: {
        //           click: () => {
        //             // this.$router.push({ name: '', params: { id: params.row.id } })
        //           }
        //         }
        //       }, '查看')
        //     ])
        //   }
        // }
      ],
      volunteers: []
    }
  },
  methods: {
    async getAllVolunteers (page = 1) {
      let res = await getAllVolunteers({ current: page, size: this.pageSize })
      console.log('getVolunteers:', res)
      if (res) {
        this.volunteers = res.records
        this.count = res.total
      } else {
        this.$Modal.error({
          title: '出错啦'
        })
      }
    }
  },
  mounted () {
    this.getAllVolunteers()
  }
}
</script>
