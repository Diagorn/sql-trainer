import TaskList from "@/pages/task/TaskList.vue";
import {createRouter, createWebHistory} from "vue-router";
import AdminPanel from "@/pages/admin/AdminPanel.vue";
import Login from "@/pages/auth/Login.vue";
import Register from "@/pages/auth/Register.vue";
import CreateTask from "@/pages/task/CreateTask.vue";
import TaskSolution from "@/pages/task/TaskSolution.vue";

const routes = [
    {
        path: '/',
        component: TaskList
    },
    {
        path: '/users',
        component: AdminPanel
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/register',
        component: Register
    },
    {
        path: '/task/create',
        component: CreateTask
    },
    {
        path: '/task/:taskId/solve',
        component: TaskSolution
    },
]

const router = createRouter({
    routes,
    history: createWebHistory()
})

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register']
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = localStorage.getItem('user')

    if (authRequired && !loggedIn) {
        next('/login')
    } else {
        next()
    }
})

export default router