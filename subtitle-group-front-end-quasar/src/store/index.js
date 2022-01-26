import { store } from 'quasar/wrappers'
import Vuex from 'vuex'
import { createStore } from 'vuex'

import example from './module-example'

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */

export default store(function (/* { ssrContext } */) {
  // const Store = createStore({
  //   modules: {
  //     example
  //   },
  //
  //   // enable strict mode (adds overhead!)
  //   // for dev mode and --debug builds only
  //   strict: process.env.DEBUGGING
  // })

  const Store = new Vuex.Store({
    state: {
      user: null,
      uid: 0,
      uname: '',
      isAdmin: true,

      router: '',
      userMap: {},

      socket: null
    },
    mutations: {
      changeRouter(state, path){
        state.router = path
      },

      updateAdmin(state, isAdmin){
        state.isAdmin = isAdmin
      },

      addUser(state, user){
        state.user = user
      },

      addSocket(state, socket){
        if (state.socket!=null){
          state.socket.close()
        }
        state.socket = socket
      },

      addUserMap(state, data){
        state.userMap[data.group] = data.users
      }
    }
  })

  return Store
})
