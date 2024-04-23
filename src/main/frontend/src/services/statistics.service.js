import axios from "axios";
import authHeader from "@/services/auth-header.js";

const API_URL = 'http://localhost:8080/api/v1/statistics'

class StatisticsService {

    getUserStatistics(userId) {
        return axios
            .get(`${API_URL}/user/${userId}`, {
                headers: authHeader()
            }).then(response => {
                return response.data
            })
    }

    getTaskStatistics(taskId) {
        return axios
            .get(`${API_URL}/task/${taskId}`, {
                headers: authHeader()
            }).then(response => {
                return response.data
            })
    }
}

export default new StatisticsService()