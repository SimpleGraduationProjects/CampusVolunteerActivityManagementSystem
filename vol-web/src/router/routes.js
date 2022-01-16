
import Login from '@/views/Login.vue'
import Index from '@/views/Index.vue'
import PublishAct from '@/views/PublishAct.vue'
import Checking from '@/views/Checking.vue'
import Processing from '@/views/Processing.vue'
import Finished from '@/views/Finished.vue'
import AllActs from '@/views/AllActs.vue'
import FinishAct from '@/views/FinishAct.vue'
import OnlineVolunteers from '@/views/OnlineVolunteers.vue'
import AllVolunteer from '@/views/AllVolunteer.vue'
import NotFoundPage from '@/views/404.vue'

const routes = [
  {
    path: '/',
    redirect: 'index'
  },
  {
    path: '/index',
    name: 'index',
    component: Index
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      open: true
    }
  },
  {
    path: '/publishAct',
    name: 'publishAct',
    component: PublishAct
  },
  {
    path: '/checking/:eventId',
    name: 'checking',
    component: Checking
  },
  {
    path: '/processing/:eventId',
    name: 'processing',
    component: Processing
  },
  {
    path: '/finished/:eventId',
    name: 'finished',
    component: Finished
  },
  {
    path: '/allActs',
    name: 'allActs',
    component: AllActs
  },
  {
    path: '/finishAct/:eventId/:expired?',
    name: 'finishAct',
    component: FinishAct
  },
  {
    path: '/onlineVolunteers',
    name: 'onlineVolunteers',
    component: OnlineVolunteers
  },
  {
    path: '/allVolunteer',
    name: 'allVolunteer',
    component: AllVolunteer
  },
  {
    path: '/404',
    name: '404',
    component: NotFoundPage
  }
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
  // }
]

export default routes
