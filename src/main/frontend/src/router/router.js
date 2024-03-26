import TaskList from "@/pages/TaskList.vue";
import {createRouter, createWebHistory} from "vue-router";
import AdminPanel from "@/pages/AdminPanel.vue";
import Login from "@/pages/Login.vue";
import Register from "@/pages/Register.vue";

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
]

const router = createRouter({
    routes,
    history: createWebHistory()
})

export default router