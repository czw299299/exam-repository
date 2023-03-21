import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginFlag: false,
    examinee:[],
    allowQo:{},
    exam:{},
    role:[],
    menuh:[],
    menus:[],
    tabCol:[],
    tabData:[],
    tabTotal:"",
    imgUrl:"http://localhost:8888/image/",
  },
  getters: {
    getLoginFlag: state => state.loginFlag,
    getExaminee:state => state.examinee,
    getExam:state => state.exam,
    getAllowQo:state => state.allowQo,
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
    saveExaminee:(state,examinee)=>state.examinee=examinee,
    saveExam:(state,exam)=>state.exam=exam,
    saveAllowQo:(state,allowQo)=>state.allowQo=allowQo,
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
    asynExaminee:({commit},examinee)=>commit("saveExaminee",examinee),
    asynExam:({commit},exam)=>commit("saveExam",exam),
    asynAllowQo:({commit},allowQo)=>commit("saveAllowQo",allowQo),
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
