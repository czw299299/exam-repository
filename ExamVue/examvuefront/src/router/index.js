import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MenuPage from "@/views/MenuPage.vue";
import LoginView from "@/views/LoginView.vue";
import First from "@/views/FirstView.vue";
import ApplyExamPage from "@/views/ApplyExamPage.vue";
import AllowPage from "@/views/AllowPage.vue";
import joinexamPage from "@/views/joinexamPage.vue";
import gradePage from "@/views/GradePage.vue";

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
    children: [
      {
        path: '/menu/first',
        name: 'first',
        component: First,
      },
      {
        path: '/menu/initapplyexam',
        name: 'applyexam',
        component: ApplyExamPage,
      },
      {
        path: '/menu/initallow',
        name: 'allow',
        component: AllowPage,
      },
      {
        path: '/menu/joinexam',
        name: 'joimexam',
        component: joinexamPage,
      },
      {
        path: '/menu/initmygrades',
        name: 'mygrade',
        component: gradePage,
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
