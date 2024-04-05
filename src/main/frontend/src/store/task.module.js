import TaskService from "@/services/task.service.js";

const initialState = {
    tasks: [],
    page: 0,
    limit: 15,
    totalPages: 5,
    lastTaskId: null,
}

export const task = {
    namespaced: true,
    state: () => initialState,
    actions: {
        getTasks({commit, state}, page) {
            TaskService.getPagesCount(state.limit)
                .then(
                    pagesCount => {
                        commit('setTotalPages', pagesCount)
                    },
                    error => {
                        console.log(error)
                        return Promise.reject(error)
                    }
                )
            return TaskService.getAll(page, state.limit)
                .then(
                    tasks => {
                        commit('setTasksResult', tasks, page, state.limit)
                        return Promise.resolve(tasks)
                    },
                    error => {
                        commit('setTasksResult', [], page, state.limit)
                        return Promise.reject(error)
                    }
                )
        },

        addNewTask({commit}, newTask) {
            return TaskService.addNewTask(newTask)
                .then(
                    newTaskId => {
                        commit('setLastTaskId', newTaskId)
                        return Promise.resolve(newTaskId)
                    },
                    error => {
                        return Promise.reject(error)
                    }
                )
        },

        getById({commit}, taskId) {
            return TaskService.getById(taskId)
                .then(
                    task => {
                        commit('setLastTaskId', taskId)
                        return Promise.resolve(taskId)
                    },
                    error => {
                        return Promise.reject(taskId)
                    }
                )
        },

        editTask({commit}, editedTask, taskId) {
            return TaskService.editTask(editedTask, taskId)
                .then(
                    response => {
                        commit('setLastTaskId', taskId)
                        return Promise.resolve(response)
                    },
                    error => {
                        return Promise.reject(error)
                    }
                )
        },

        deleteTask({commit}, taskId) {
            return TaskService.deleteTask(taskId)
                .then(
                    response => {
                        commit('setLastTaskId', null)
                        return Promise.resolve(response)
                    },
                    error => {
                        return Promise.reject(error)
                    }
                )
        }
    },
    mutations: {
        setTasksResult(state, tasks, page, limit) {
            state.tasks = tasks
            state.page = page
            state.limit = limit
        },
        setLastTaskId(state, lastTaskId) {
            state.lastTaskId = lastTaskId
        },
        setTotalPages(state, totalPages) {
            state.totalPages = totalPages
        }
    }
}