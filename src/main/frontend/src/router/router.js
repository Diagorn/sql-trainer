import TaskList from "@/pages/TaskList.vue";
import {createRouter, createWebHistory} from "vue-router";
import AdminPanel from "@/pages/AdminPanel.vue";

const routes = [
    {
        path: '/',
        component: TaskList
    },
    {
        path: '/users',
        component: AdminPanel
    },
]

const router = createRouter({
    routes,
    history: createWebHistory()
})

export default router