
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      { path: ':group', component: () => import('pages/Index.vue') },
      { path: ':group/:user', component: () => import('pages/GroupWorkSpace.vue'),
        children: [
          { path: '', component: () => import('pages/space/Space.vue') },
          { path: 'sheet', component: () => import('pages/space/DataTable.vue') },
          { path: 'skill', component: () => import('pages/space/Skill.vue') },
          { path: 'manage', component: () => import('pages/space/ManageSkill.vue') },
          { path: 'live', component: () => import('pages/space/Live.vue') }
        ]
      },

    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
