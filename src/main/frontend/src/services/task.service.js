import axios from "axios";
import authHeader from "@/services/auth-header.js";

const API_URL = 'http://localhost:8080/api/v1/task'

class TaskService {

    getAll(page, limit) {
        return axios
            .get(API_URL, {
                params: {
                    page: page,
                    limit: limit
                },
                headers: authHeader(),
            }).then(response => {
                return response.data
            }).catch(error => {
                console.log(error)
            })
    }

    addNewTask(task) {
        return axios
            .post(API_URL, task, {
                headers: authHeader(),
            })
            .then(response => {
                return response.data
            })
            .catch(error => console.log(error))
    }

    getById(id) {
        return axios.get(API_URL + '/' + id, {
            headers: authHeader()
        })
            .then(response => {
                return response.data
            })
            .catch(error => console.log(error))
    }

    editTask(newTask, id) {
        return axios.patch(API_URL + '/' + id, newTask, {
            headers: authHeader()
        })
            .catch(error => console.log(error))
    }

    deleteTask(id) {
        return axios.delete(API_URL + '/' + id, {
            headers: authHeader()
        })
            .catch(error => console.log(error))
    }

    getPagesCount(limit) {
        return axios.get(API_URL + '/pages', {
            params: {
                limit: limit
            },
            headers: authHeader(),
        }).then(
            response => {
                return response.data
            },
            error => {
                console.log(error)
            }
        )
    }
}

export default new TaskService()