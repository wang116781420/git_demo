import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/login.vue"
import Main from '../views/main.vue'
import Users from '../views/sys/users/index.vue'
import Roles from '../views/sys/roles/index.vue'
import Depts from '../views/sys/depts/index.vue'
import Menus from '../views/sys/menus/index.vue'
import pos from '../views/sys/pos/index.vue'
import Logs from '../views/sys/logs/index.vue'

Vue.use(VueRouter)

const routes = [{
		path: '/',
		redirect: '/login'
	},
	{
		path: '/login',
		component: Login
	},
	{
		path: '/main',
		component: Main,
		children: [{
				path: '/users',
				component: Users
			},
			{
				path: '/depts',
				component: Depts
			},
			{
				path: '/roles',
				component: Roles
			},
			{
				path: '/menus',
				component: Menus
			},
			{
				path: '/pos',
				component: pos
			},
			{
				path: '/logs',
				component: Logs
			}
		]
	},
]

const router = new VueRouter({
	routes
})

export default router
