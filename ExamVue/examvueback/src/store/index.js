import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginFlag: false,
    admin:[],
    role:[],
    exam:{},
    menuh:[],
    menus:[],
    tabCol:[],
    tabData:[],
    tabTotal:"",
    imgUrl:"http://localhost:8888/image/",
  },
  getters: {
    getLoginFlag: state => state.loginFlag,
    getAdmin:state => state.admin,
    getExam:state => state.exam,
    getRole:state => state.role,
    getImgUrl:state => state.imgUrl,
    getMenuh:state => state.menuh,
    getMenus:state => state.menus,
    getTabCol:state => state.tabCol,
    getTabData:state => state.tabData,
    getTabTotal:state => state.tabTotal,
  },
  mutations: {
    saveLoginFlag:(state,flag)=>state.loginFlag=flag,
    saveAdmin:(state,admin)=>state.admin=admin,
    saveExam:(state,exam)=>state.exam=exam,
    saveRole:(state,role)=>state.role=role,
    saveImgUrl:(state,payload)=>{
      state.imgUrl=payload;
    },
    saveMenuh:(state,payload)=>{
      state.menuh=payload;
    },

    saveMenus:(state,payload)=>{
      state.menus=payload;
    },
    saveTabCol:(state,payload)=>{
      state.tabCol=payload;
    },
    saveTabData:(state,payload)=>{
      state.tabData=payload;

    },
    saveTabTotal:(state,payload)=>{
      state.tabTotal=payload;
    },

  },
  actions: {
    asynLoginFlag:({commit},flag)=>commit("saveLoginFlag",flag),
    asynAdmin:({commit},admin)=>commit("saveAdmin",admin),
    asynExam:({commit},exam)=>commit("saveExam",exam),
    asynRole:({commit},role)=>commit("saveRole",role),
    asynSaveImgUrl:({commit},reqUrl)=>{
      commit("saveImgUrl",reqUrl)
    },
    asynSaveMenuh:({commit},menuh)=>{
      commit("saveMenuh",menuh)
    },
    asynSaveMenus:({commit},menus)=>{
      commit("saveMenus",menus)
    },
    asynSaveTabCol:({commit},cols)=>{
      commit("saveTabCol",cols)
    },
    asynSaveTabData:({commit},data)=>{
      commit("saveTabData",data)
    },
    asynSaveTabTotal:({commit},total)=>{
      commit("saveTabTotal",total)
    }
  },
  modules: {
  }
})
