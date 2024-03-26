import TaskList from "@/pages/TaskList.vue";
import {createRouter, createWebHistory} from "vue-router";
import AdminPanel from "@/pages/AdminPanel.vue";
import Login from "@/pages/Login.vue";
import Register from "@/pages/Register.vue";
import CreateTask from "@/pages/CreateTask.vue";

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
]

const router = createRouter({
    routes,
    history: createWebHistory()
})

export default router