import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView"
import MenuPage from "@/views/MenuPage.vue";
import First from "@/views/FirstView.vue";
import adminPage from "@/views/AdminPage.vue";
import rolePage from "@/views/RolePage.vue";
import SubjectPage from "@/views/SubjectPage.vue";
import subjectPage from "@/views/SubjectPage.vue";
import topicPage from "@/views/TopicPage.vue";
import paperPage from "@/views/PaperPage.vue";
import examPage from "@/views/ExamPage.vue";
import examineePage from "@/views/ExamineePage.vue";
import allowMangerPage from "@/views/AllowMangerPage.vue";
import startExamPage from "@/views/StartExamPage.vue";
import joinExamPage from "@/views/joinExamPage.vue";
import logsPage from "@/views/LogsPage.vue";
import countPage from "@/views/CountPage.vue";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/menu',
    name: 'menu',
    component: MenuPage,
    children:[
      {
          path:'/menu/first',
          name:'first',
          component:First,
      },
      {
        path: '/menu/initadmin',
        name: 'admin',
        component: adminPage,
      },
      {
        path: '/menu/initrole',
        name: 'role',
        component: rolePage,
      },
      {
        path: '/menu/initsubject',
        name: 'subject',
        component: subjectPage,
      },
      {
        path: '/menu/inittopic',
        name: 'topic',
        component: topicPage,
      },
      {
        path: '/menu/initpaper',
        name: 'paper',
        component: paperPage,
      },
      {
        path: '/menu/initexam',
        name: 'exam',
        component: examPage,
      }
      ,
      {
        path: '/menu/initexaminee',
        name: 'examinee',
        component: examineePage,
      }
      ,
      {
        path: '/menu/initallowmanger',
        name: 'allowmanger',
        component: allowMangerPage,
      }
      ,
      {
        path: '/menu/initstartexam',
        name: 'startexam',
        component: startExamPage,
      },
      {
        path: '/menu/joinexam',
        name: 'joinexam',
        component: joinExamPage,
      },
      {
        path: '/menu/initlogs',
        name: 'initlogs',
        component: logsPage,
      },
      {
        path: '/menu/initcounts',
        name: 'initcounts',
        component: countPage,
      }
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router